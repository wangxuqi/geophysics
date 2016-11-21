package org.xuqi.geophysics.entity;

public class GeophysicsProject {
    private int projectId;
    private String name;
    private String polygon;
    private String startTime;
    private String finishTime;
    private String principal;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getFinishTime() {

        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getStartTime() {

        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPolygon() {

        return polygon;
    }

    public void setPolygon(String polygon) {
        this.polygon = polygon;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProjectId() {

        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
