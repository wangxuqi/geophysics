package org.xuqi.geophysics.batch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.*;
import java.util.*;

public abstract class BaseService implements BatchService {
	private final Log logger = LogFactory.getLog(getClass());
	private final static int SCAN_INTERNAL = 5 * 60 * 1000;
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private File dataFilePath;
	private File dataBakFilePath;
	private int numTries = 24;
	private boolean check = true;
	private boolean packageZip = false;
	private int outOfDays = 7;
	private int batchSize = 5000;
	private int compareDays = 7;
	private int compareThreshold = 2000;
	protected boolean handlerResultCheck = true;
	private Map<String, Map<String, Integer>> handlerResults = Maps.newHashMap();

	protected void initService(Properties properties) {
		String dataFileStr = properties.getProperty("dataFilePath");
		String dataBakFileStr = properties.getProperty("dataBakFilePath");

		this.dataFilePath = new File(dataFileStr);
		this.dataBakFilePath = new File(dataBakFileStr);

		if (!dataFilePath.exists()) {
			throw new BatchException(String.format("data directory %s does not exist", dataFilePath));
		}

		if (!dataBakFilePath.exists()) {
			throw new BatchException(String.format("backup data directory %s does not exist", dataBakFileStr));
		}

		String numberStr = properties.getProperty("numTries");
		if (!Strings.isNullOrEmpty(numberStr)) {
			this.numTries = Integer.valueOf(numberStr);
		}

		String checkStr = properties.getProperty("checkOutOfDate");
		if (!Strings.isNullOrEmpty(checkStr)) {
			this.check = Boolean.valueOf(checkStr);
		}

		String outofDayStr = properties.getProperty("outofDay");
		if (!Strings.isNullOrEmpty(outofDayStr)) {
			this.outOfDays = Integer.valueOf(outofDayStr);
		}

		String packageZipStr = properties.getProperty("packageZip");
		if (!Strings.isNullOrEmpty(packageZipStr)) {
			this.packageZip = Boolean.valueOf(packageZipStr);
		}

		String batchSizeStr = properties.getProperty("batchSize");
		if (!Strings.isNullOrEmpty(batchSizeStr)) {
			this.batchSize = Integer.valueOf(batchSizeStr);
		}

		String compareDaysStr = properties.getProperty("compareDays");
		if (!Strings.isNullOrEmpty(compareDaysStr)) {
			this.compareDays = Integer.valueOf(compareDaysStr);
		}

		String compareThresholdStr = properties.getProperty("compareThreshold");
		if (!Strings.isNullOrEmpty(compareThresholdStr)) {
			this.compareThreshold = Integer.valueOf(compareThresholdStr);
		}

		String handlerResultCheckStr = properties.getProperty("handlerResultCheck");
		if (!Strings.isNullOrEmpty(handlerResultCheckStr)) {
			this.handlerResultCheck = Boolean.valueOf(handlerResultCheckStr);
		}
	}

	@Override
	public void doService(Properties properties) {
		initService(properties);
		execute();
	}

	private void execute() {
		try {
			int tries = 0;
			List<Handler> handlers = getHandles();
			for (; tries < numTries; tries++) {
				List<File> readyDataDirs = fetchReadyDataDirs();
				if (readyDataDirs.isEmpty()) {
					logger.info("waiting for data coming...");
					Thread.sleep(SCAN_INTERNAL);
				} else {
					handle(readyDataDirs, handlers);
					if (handlerResultCheck) {
						checkHandlerResults();
					}
					break;
				}
			}

			if (tries > numTries) {
				logger.error(getClass().getName() + "has no coming date to handle");
			}
		} catch (Exception e) {
			logger.error("handle error happend: " + e.getMessage(), e);
		}
	}

	protected void handle(List<File> readyDataDirs, List<Handler> handlers) throws Exception {
		for (File dir : readyDataDirs) {
			Map<String, Integer> handlerResult = Maps.newHashMap();
			for (Handler handler : handlers) {
				handler.handle(dir);
				handlerResult.put(handler.getName(), handler.getHandlerCount());
			}

			if (packageZip) {
				backupDataFile(dir);
			}

			String dateKey = FilenameUtils.getBaseName(dir.getName());
			handlerResults.put(dateKey, handlerResult);
		}
	}

	protected List<File> fetchReadyDataDirs() {
		List<File> readyDataDirs = new ArrayList<File>();
		File[] files = this.dataFilePath.listFiles(getFilter());
		for (File f : files) {
			if (f.isDirectory() && isDataReady(f)) {
				if (checkOutOfDate(FilenameUtils.getBaseName(f.getName()))) {
					logger.warn(String.format("data out of date for %s", f.getName()));
					continue;
				}
				readyDataDirs.add(f);
			}

		}
		return readyDataDirs;
	}

	private boolean checkOutOfDate(String filename) {
		if (!check)
			return false;
		LocalDate fileDate = new LocalDate(filename);
		LocalDate outofDate = LocalDate.now().minusDays(outOfDays);
		return fileDate.isBefore(outofDate);
	}

	private boolean isDataReady(File dir) {
		return new File(dir.getAbsolutePath() + File.separator + "dataReady.properties").exists();
	}

	private void backupDataFile(File dir) {
		FilesOperation filesOperation = new FilesOperation();
		filesOperation.doZip(dir.getAbsolutePath(),
				this.dataBakFilePath + File.separator + FilenameUtils.getBaseName(dir.getName()) + ".zip");
		filesOperation.deleteFile(dir);
	}

	private void checkHandlerResults() {
		Map<String, Map<String, Integer>> localHandlerResults = getHandlerResultFromLocal();

 		for (Map.Entry<String, Map<String, Integer>> handlersEntry : handlerResults.entrySet()) {
			String dateKey = handlersEntry.getKey();
			Map<String, Integer> handlerResult = handlersEntry.getValue();

			DateTime compareTime = new DateTime(dateKey).minusDays(compareDays);
			String compareTimeKey = getDateString(compareTime);
			if (!localHandlerResults.containsKey(compareTimeKey)) {
				continue;
			}
			Map<String, Integer> localHandlerResult = localHandlerResults.get(compareTimeKey);
			for (Map.Entry<String, Integer> entry : handlerResult.entrySet()) {
				String handlerName = entry.getKey();
				int count = entry.getValue();
				if (!localHandlerResult.containsKey(handlerName)) {
					continue;
				}
				int compareCount = localHandlerResult.get(handlerName);
				if (Math.abs(count - compareCount) > compareThreshold) {
					logger.error(handlerName + " compare at " + compareTimeKey + " exceed the threshold "
							+ compareThreshold);
				}
			}
		}

		for (Map.Entry<String, Map<String, Integer>> localHandlersEntry : localHandlerResults.entrySet()) {
			String dateKey = localHandlersEntry.getKey();
			Map<String, Integer> localHandlerResult = localHandlersEntry.getValue();
			if (!handlerResults.containsKey(dateKey)) {
				handlerResults.put(dateKey, localHandlerResult);
			}
       			Map<String, Integer> handlerResult = handlerResults.get(dateKey);
  			for (Map.Entry<String, Integer> handerEntry : localHandlerResult.entrySet()) {
				String handlerName = handerEntry.getKey();
 				Integer count = handerEntry.getValue();
				if (!handlerResult.containsKey(handlerName)) {
					handlerResult.put(handlerName, count);
				}
			}
		}

		writeHandlerResultToLocal();
	}

	private void writeHandlerResultToLocal() {
		File file = new File(dataFilePath.getAbsolutePath() + File.separator + "handlersResult.json");
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			bufferedWriter.write(OBJECT_MAPPER.writeValueAsString(handlerResults));
		} catch (Exception ex) {
			logger.error("writeHandlerResultToLocal error happened:" + ex, ex);
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			} catch (Exception ex) {
			}
		}
	}

	private String getDateString(DateTime dateTime) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		return dtf.print(dateTime);
	}

	private Map<String, Map<String, Integer>> getHandlerResultFromLocal() {
		File file = new File(dataFilePath.getAbsolutePath() + File.separator + "handlersResult.json");
		if (!file.exists()) {
			return new HashMap<String, Map<String, Integer>>();
		}

		BufferedReader bufferedReader = null;
		Map<String, Map<String, Integer>> result = new HashMap<String, Map<String, Integer>>();
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			result = (Map<String, Map<String, Integer>>) objectMapper.readValue(bufferedReader.readLine().getBytes(),
					new TypeReference<Map<String, Map<String, Integer>>>() {
					});
			return result;
		} catch (Exception ex) {
			logger.error("getHandlerResultFromLocal has error happened:" + ex.getMessage(), ex);
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception ex) {
			}
		}
		return result;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public FilenameFilter getFilter() {
		return new DateFileNameFilter();
	}

	public abstract List<Handler> getHandles();
}
