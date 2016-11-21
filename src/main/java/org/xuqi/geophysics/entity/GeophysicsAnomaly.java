package org.xuqi.geophysics.entity;

public class GeophysicsAnomaly {
	private int anomalyId;
	private String polygon;
	private String createTime;
	private String approver;
	private String suspectedThing;
	private String anomaly3DModelPath;

	public String getAnomaly3DModelPath() {
		return anomaly3DModelPath;
	}

	public void setAnomaly3DModelPath(String anomaly3DModelPath) {
		this.anomaly3DModelPath = anomaly3DModelPath;
	}

	public String getSuspectedThing() {

		return suspectedThing;
	}

	public void setSuspectedThing(String suspectedThing) {
		this.suspectedThing = suspectedThing;
	}

	public String getApprover() {

		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getCreateTime() {

		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPolygon() {

		return polygon;
	}

	public void setPolygon(String polygon) {
		this.polygon = polygon;
	}

	public int getAnomalyId() {

		return anomalyId;
	}

	public void setAnomalyId(int anomalyId) {
		this.anomalyId = anomalyId;
	}
}
