package org.xuqi.geophysics.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileReader {
    //????(????????)
    private String filePath;
    //???(?????)
    private String separator;
    //???(?????)(????????)
    private String filter;

    private List<File> fileList;

    private static Log logger = LogFactory.getLog(FileReader.class);

    private static final String DEFAULT_SEPARATOR = "\t";

    private Comparator<File> comparator;

    public FileReader(String filePath, String separator, String filter) {
        this.filePath = filePath;
        this.separator = (separator == null ? DEFAULT_SEPARATOR : separator);
        this.filter = filter;
        this.fileList = initFileList();
    }

    public FileReader(String filePath, String separator, String filter, Comparator<File> comparator) {
        this.filePath = filePath;
        this.separator = (separator == null ? DEFAULT_SEPARATOR : separator);
        this.filter = filter;
        this.comparator = comparator;
        this.fileList = initFileList();
    }

    public FileRowIterator fileRowIterator() {
        return new FileRowIterator();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSeparator() {
        return separator;
    }

    public Comparator<File> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<File> comparator) {
        this.comparator = comparator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    private List<File> initFileList() {
        List<File> fileList = new ArrayList<File>();
        if (this.filePath == null) {
            throw new IllegalArgumentException("file path must present");
        }

        File file = new File(this.filePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.isFile() && shouldRemain(f.getName())) {
                        fileList.add(f);
                    }
                }
            } else {
                if (shouldRemain(file.getName())) {
                    fileList.add(file);
                }
            }
        } else {
            throw new IllegalArgumentException("file not exist for path: " + this.filePath);
        }
        if (comparator != null) {
            Collections.sort(fileList, comparator);
        }
        return fileList;
    }

    private boolean shouldRemain(String fileName) {
        if (this.filter == null) {
            return true;
        }
        Pattern p = Pattern.compile(this.filter);
        Matcher m = p.matcher(fileName);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    private class FileRowIterator implements Iterator {
        private int idx = 0;
        private BufferedReader rdr;
        String line = null;
        int rowNum = 0;

        @Override
        public boolean hasNext() {
            try {
                if (rdr == null) {
                    if (fileList.size() > idx) {
                        logger.info(String.format("handling file %s", fileList.get(idx).getPath()));
                        rdr = new BufferedReader(new InputStreamReader(new FileInputStream(fileList.get(idx)), "UTF-8"));
                        return hasNext();
                    } else {
                        return false;
                    }
                } else if (rdr != null && (line = rdr.readLine()) != null) {
                    rowNum++;
                    return true;
                } else if (rdr != null && (line = rdr.readLine()) == null) {
                    try {
                        rdr.close();
                    } catch (IOException e) {
                        logger.warn(e);
                    }
                    idx++;
                    if (fileList.size() > idx) {
                        logger.info(String.format("handling file %s", fileList.get(idx).getPath()));
                        rdr = new BufferedReader(new InputStreamReader(new FileInputStream(fileList.get(idx)), "UTF-8"));
                        rowNum = 0;
                        return hasNext();
                    } else {
                        return false;
                    }

                }
            } catch (IOException e) {
                logger.error(e);
            }

            return false;
        }

        @Override
        public Object next() {
            String[] rowData = line.split(separator);
            return new FileRow(rowNum, fileList.get(idx).getName(), rowData);
        }

        @Override
        public void remove() {
            if (rdr != null) {
                try {
                    rdr.close();
                } catch (IOException e) {
                    logger.warn(e);
                }
            }
        }
    }

}
