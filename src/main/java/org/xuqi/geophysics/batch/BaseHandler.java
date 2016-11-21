package org.xuqi.geophysics.batch;

import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xuqi.geophysics.mapper.DataStore;

import java.io.File;
import java.util.Iterator;
import java.util.List;


public abstract class BaseHandler<T> implements Handler {
    private static Log logger = LogFactory.getLog(BaseHandler.class);
  private int batchSize = 0;
  private List<T> datas;
  private boolean skipFirst = false;
  private DataStore<T> store;
  private String filter;
  private String separator;
  private int totalCount = 0;

  public BaseHandler(int batchSize, String filter, DataStore store) {
    this(batchSize, filter, store, false);
  }

  public BaseHandler(int batchSize, String filter, DataStore store, boolean skipFirst) {
    this(batchSize, filter, store, "\t", skipFirst);
  }

  public BaseHandler(int batchSize, String filter, DataStore store, String separator) {
    this(batchSize, filter, store, separator, false);
  }

  public BaseHandler(int batchSize, String filter, DataStore store, String separator, boolean skipFirst) {
    this.batchSize = batchSize;
    this.filter = filter;
    this.skipFirst = skipFirst;
    this.separator = separator;
    this.store = store;
    datas = Lists.newArrayListWithCapacity(batchSize);
  }

  @Override
  public void handle(File dir) throws Exception {
    long startTime = System.currentTimeMillis();
    FileReader rdr = new FileReader(dir.getAbsolutePath(), separator, filter);
    Iterator<FileRow> rowIterator = rdr.fileRowIterator();

    while (rowIterator.hasNext()) {
      FileRow row = rowIterator.next();
      if ((row.getRowNum() == 1) && skipFirst) {
        continue;
      }
      save(convert(row, FilenameUtils.getBaseName(dir.getName())));
      totalCount++;
    }

    if (datas.size() > 0) {
      //store.insert(datas.get(0));
      store.batchInsert(datas);
      datas.clear();
    }

    long endTime = System.currentTimeMillis();
    logger.info(String.format("%s has total %d records in %d sec",
            dir.getName(), totalCount, (endTime - startTime) / 1000));
  }

  private void save(T data) throws Exception {
    if (datas.size() >= batchSize) {
      store.batchInsert(datas);
      datas.clear();
    }
    datas.add(data);
  }

  @Override
  public String getName() {
    return getClass().getName();
  }

  @Override
  public int getHandlerCount() {
    return totalCount;
  }

  public abstract T convert(FileRow row, String datetime);
}
