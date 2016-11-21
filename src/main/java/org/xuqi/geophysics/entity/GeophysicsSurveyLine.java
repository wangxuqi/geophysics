package org.xuqi.geophysics.entity;

/**
 * Created by wangxuqi on 2016/3/23.
 */
public class GeophysicsSurveyLine {
    private long id; // survey line id
    private double startPointLon; // start point longitude
    private double startPointLat; // start point latitude
    private double endPointLon; // end point longitude
    private double endPointLat; // end point latitude
    private double distance; // survey line distance
    private int number; // contains points number
    private int typeId; // survey line type
    private int methodId; // survey line method
    private String collectTime;
    private String processedTime;
    private String collector;
    private String processor;
    private int surveyAreaId;
    private int projectId;
    private String originDataPath;
    private String processedDataPath;
    private String processedDataImagePath;
    private String anomaly3DModelPath;

    public String getAnomaly3DModelPath() {
        return anomaly3DModelPath;
    }

    public void setAnomaly3DModelPath(String anomaly3DModelPath) {
        this.anomaly3DModelPath = anomaly3DModelPath;
    }

    public String getProcessedDataImagePath() {

        return processedDataImagePath;
    }

    public void setProcessedDataImagePath(String processedDataImagePath) {
        this.processedDataImagePath = processedDataImagePath;
    }

    public String getProcessedDataPath() {

        return processedDataPath;
    }

    public void setProcessedDataPath(String processedDataPath) {
        this.processedDataPath = processedDataPath;
    }

    public String getOriginDataPath() {

        return originDataPath;
    }

    public void setOriginDataPath(String originDataPath) {
        this.originDataPath = originDataPath;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getSurveyAreaId() {

        return surveyAreaId;
    }

    public void setSurveyAreaId(int surveyAreaId) {
        this.surveyAreaId = surveyAreaId;
    }

    public int getMethodId() {

        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    public int getTypeId() {

        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getProcessor() {

        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getCollector() {

        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getProcessedTime() {

        return processedTime;
    }

    public void setProcessedTime(String processedTime) {
        this.processedTime = processedTime;
    }

    public String getCollectTime() {

        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public int getNumber() {

        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getDistance() {

        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getEndPointLat() {

        return endPointLat;
    }

    public void setEndPointLat(double endPointLat) {
        this.endPointLat = endPointLat;
    }

    public double getEndPointLon() {

        return endPointLon;
    }

    public void setEndPointLon(double endPointLon) {
        this.endPointLon = endPointLon;
    }

    public double getStartPointLat() {

        return startPointLat;
    }

    public void setStartPointLat(double startPonitLat) {
        this.startPointLat = startPonitLat;
    }

    public double getStartPointLon() {

        return startPointLon;
    }

    public void setStartPointLon(double startPointLon) {
        this.startPointLon = startPointLon;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
