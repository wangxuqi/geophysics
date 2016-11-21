package org.xuqi.geophysics.mapper;

import org.apache.ibatis.annotations.Param;
import org.xuqi.geophysics.entity.GeophysicsSurveyLine;

import java.util.List;

public interface GeophysicsSurveyLineMapper extends DataStore<GeophysicsSurveyLine> {

    public GeophysicsSurveyLine getSurveyLineById(long surveyLineId);

    public void delete(int lineId);

    public List<GeophysicsSurveyLine> getSurveyLineBySurveyAreaIdAndProjectId(
            @Param("surveyAreaId") long surveyAreaId, @Param("projectId") int projectId);

    public List<GeophysicsSurveyLine> getSurveyLineByProjectIdAndMethodId(int projectId, int methodId);

    public List<GeophysicsSurveyLine> getSurveyLineByProjectId(int projectId);
}
