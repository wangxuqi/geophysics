package org.xuqi.geophysics.batch;

import org.xuqi.geophysics.entity.GeophysicsAnomaly;
import org.xuqi.geophysics.entity.GeophysicsProject;
import org.xuqi.geophysics.entity.GeophysicsSurveyArea;
import org.xuqi.geophysics.entity.GeophysicsSurveyLine;
import org.xuqi.geophysics.mapper.GeophysicsAnomalyMapper;
import org.xuqi.geophysics.mapper.GeophysicsProjectMapper;
import org.xuqi.geophysics.mapper.GeophysicsSurveyAreaMapper;
import org.xuqi.geophysics.mapper.GeophysicsSurveyLineMapper;

public class GeophysicsProcessor {
	  public static class AnomalyHandler extends BaseHandler<GeophysicsAnomaly> {
	    public AnomalyHandler(GeophysicsAnomalyMapper geophysicsAnomalyMapper, int batchSize) {
	      super(batchSize, "^survey_anomalies$", geophysicsAnomalyMapper);
	    }

	    @Override
	    public GeophysicsAnomaly convert(FileRow row, String datetime) {
	      String[] rowData = row.getRowData();
	      GeophysicsAnomaly geophysicsAnomaly = new GeophysicsAnomaly();
	      geophysicsAnomaly.setAnomalyId(Integer.valueOf(rowData[0]));
	      geophysicsAnomaly.setPolygon(rowData[1]);
	      geophysicsAnomaly.setCreateTime(rowData[2]);
	      geophysicsAnomaly.setApprover(rowData[3]);
	      geophysicsAnomaly.setSuspectedThing(rowData[4]);
	      geophysicsAnomaly.setSuspectedThing(rowData[5]);
	      geophysicsAnomaly.setAnomaly3DModelPath(rowData[6]);
	      return geophysicsAnomaly;
	    }
	  }

	  public static class ProjectHandler extends BaseHandler<GeophysicsProject> {
	    public ProjectHandler(GeophysicsProjectMapper geophysicsProjectMapper, int batchSize) {
	      super(batchSize, "^survey_projects$", geophysicsProjectMapper);
	    }

	    @Override
	    public GeophysicsProject convert(FileRow row, String datetime) {
	      String[] rowData = row.getRowData();
	      GeophysicsProject geophysicsProject = new GeophysicsProject();
	      geophysicsProject.setProjectId(Integer.valueOf(rowData[0]));
	      geophysicsProject.setName(rowData[1]);
	      geophysicsProject.setPolygon(rowData[2]);
	      geophysicsProject.setStartTime(rowData[3]);
	      geophysicsProject.setFinishTime(rowData[4]);
	      geophysicsProject.setPrincipal(rowData[5]);
	      return geophysicsProject;
	    }
	  }

	  public static class SurveyAreaHandler extends BaseHandler<GeophysicsSurveyArea> {
	    public SurveyAreaHandler(GeophysicsSurveyAreaMapper geophysicsSurveyAreaMapper, int batchSize) {
	      super(batchSize, "^survey_areas$", geophysicsSurveyAreaMapper);
	    }


	    @Override
	    public GeophysicsSurveyArea convert(FileRow row, String datetime) {
	      String[] rowData = row.getRowData();
	      GeophysicsSurveyArea geophysicsSurveyArea = new GeophysicsSurveyArea();
	      geophysicsSurveyArea.setSurveyAreaId(Long.valueOf(rowData[0]));
	      geophysicsSurveyArea.setName(rowData[1]);
	      geophysicsSurveyArea.setPolygon(rowData[2]);
	      geophysicsSurveyArea.setMethodId(Integer.valueOf(rowData[3]));
	      geophysicsSurveyArea.setProjectId(Integer.valueOf(rowData[4]));
	      geophysicsSurveyArea.setStartTime(rowData[5]);
	      geophysicsSurveyArea.setFinishTime(rowData[6]);
	      return geophysicsSurveyArea;
	    }
	  }

	  public static class SurveyLineHandler extends BaseHandler<GeophysicsSurveyLine> {
	    public SurveyLineHandler(GeophysicsSurveyLineMapper geophysicsSurveyLineMapper, int batchSize) {
	      super(batchSize, "^survey_lines$", geophysicsSurveyLineMapper);
	    }

	    @Override
	    public GeophysicsSurveyLine convert(FileRow row, String datetime) {
	      String[] rowData = row.getRowData();
	      GeophysicsSurveyLine geophysicsSurveyLine = new GeophysicsSurveyLine();
	      geophysicsSurveyLine.setId(Long.valueOf(rowData[0]));
	      geophysicsSurveyLine.setStartPointLon(Double.valueOf(rowData[1]));
	      geophysicsSurveyLine.setStartPointLat(Double.valueOf(rowData[2]));
	      geophysicsSurveyLine.setEndPointLon(Double.valueOf(rowData[3]));
	      geophysicsSurveyLine.setEndPointLat(Double.valueOf(rowData[4]));
	      geophysicsSurveyLine.setDistance(Double.valueOf(rowData[5]));
	      geophysicsSurveyLine.setNumber(Integer.valueOf(rowData[6]));
	      geophysicsSurveyLine.setTypeId(Integer.valueOf(rowData[7]));
	      geophysicsSurveyLine.setMethodId(Integer.valueOf(rowData[8]));
	      geophysicsSurveyLine.setCollectTime(rowData[9]);
	      geophysicsSurveyLine.setProcessedTime(rowData[10]);
	      geophysicsSurveyLine.setCollector(rowData[11]);
	      geophysicsSurveyLine.setProcessor(rowData[12]);
	      geophysicsSurveyLine.setSurveyAreaId(Integer.valueOf(rowData[13]));
	      geophysicsSurveyLine.setProjectId(Integer.valueOf(rowData[14]));
	      geophysicsSurveyLine.setOriginDataPath(rowData[15]);
	      geophysicsSurveyLine.setProcessedDataPath(rowData[16]);
	      geophysicsSurveyLine.setProcessedDataImagePath(rowData[17]);
	      geophysicsSurveyLine.setAnomaly3DModelPath(rowData[18]);
	      return geophysicsSurveyLine;
	    }
	  }
	}
