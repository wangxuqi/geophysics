package org.xuqi.geophysics.entity;

public class GeophysicsAnomalyPoint {
    private long pointId;
    private double lon;
    private double lat;
    private double depth;
    private double value;
    private long surveyLineId;
    private int anomalyId;

    public int getAnomalyId() {
        return anomalyId;
    }

    public void setAnomalyId(int anomalyId) {
        this.anomalyId = anomalyId;
    }

    public long getSurveyLineId() {

        return surveyLineId;
    }

    public void setSurveyLineId(long surveyLineId) {
        this.surveyLineId = surveyLineId;
    }

    public double getValue() {

        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getDepth() {

        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getLat() {

        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public long getPointId() {

        return pointId;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
    }

    public double getLon() {

        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
