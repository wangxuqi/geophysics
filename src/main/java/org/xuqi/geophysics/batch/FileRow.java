package org.xuqi.geophysics.batch;

public class FileRow {
    int rowNum;
    String fileName;
    String[] rowData;

    public FileRow(int rowNum,String fileName,String[] rowData){
        this.rowNum=rowNum;
        this.fileName=fileName;
        this.rowData=rowData;
    }
    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getRowData() {
        return rowData;
    }

    public void setRowData(String[] rowData) {
        this.rowData = rowData;
    }
}