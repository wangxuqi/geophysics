package org.xuqi.geophysics.entity;

public class GeophysicsSurveyArea {
	  private long surveyAreaId;
	  private String name;
	  private String polygon;
	  private int methodId;
	  private int projectId;
	  private String startTime;
	  private String finishTime;

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

	  public int getProjectId() {

	    return projectId;
	  }

	  public void setProjectId(int projectId) {
	    this.projectId = projectId;
	  }

	  public int getMethodId() {

	    return methodId;
	  }

	  public void setMethodId(int methodId) {
	    this.methodId = methodId;
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

	  public long getSurveyAreaId() {

	    return surveyAreaId;
	  }

	  public void setSurveyAreaId(long surveyAreaId) {
	    this.surveyAreaId = surveyAreaId;
	  }
	}