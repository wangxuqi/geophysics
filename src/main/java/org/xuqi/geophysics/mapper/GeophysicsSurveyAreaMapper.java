package org.xuqi.geophysics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xuqi.geophysics.entity.GeophysicsSurveyArea;

public interface GeophysicsSurveyAreaMapper extends DataStore<GeophysicsSurveyArea> {
	public void delete(int surveyAreaId);

	public List<GeophysicsSurveyArea> getSurveyAreasByProjectId(int projectId);

	public List<GeophysicsSurveyArea> getAllSurveyAreas();

	public List<GeophysicsSurveyArea> getSurveyAreasByProjectIdAndMethodId(@Param("projectId")int projectId,
																		   @Param("methodId") int methodId);
}
