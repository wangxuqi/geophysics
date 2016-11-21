package org.xuqi.geophysics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;
import org.xuqi.geophysics.batch.ModelConfig;
import org.xuqi.geophysics.entity.GeophysicsProject;
import org.xuqi.geophysics.entity.GeophysicsSurveyLine;
import org.xuqi.geophysics.mapper.GeophysicsProjectMapper;
import org.xuqi.geophysics.mapper.GeophysicsSurveyLineMapper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangxuqi on 2016/3/28.
 */
@ContextConfiguration(classes = {ModelConfig.class})
@WebAppConfiguration
public class GeophysicsSurveyLineMapperTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private GeophysicsSurveyLineMapper geophysicsSurveyLineMapper;

    @Autowired
    private GeophysicsProjectMapper geophysicsProjectMapper;


    @Test
    public void testGetLineByLineId() throws Exception{
        long id =1L;
        GeophysicsSurveyLine geophysicsSurveyLine = null;
        geophysicsSurveyLine = geophysicsSurveyLineMapper.getSurveyLineById(id);
        System.out.println(geophysicsSurveyLine.getAnomaly3DModelPath());
    }

    @Test
    public void testGetLinesByProjectIdAndAreaId() throws Exception{
        List<GeophysicsSurveyLine> geophysicsSurveyLines= new LinkedList<GeophysicsSurveyLine>();
        geophysicsSurveyLines = geophysicsSurveyLineMapper.getSurveyLineBySurveyAreaIdAndProjectId(1L,1);
        for (GeophysicsSurveyLine geophysicsSurveyLine : geophysicsSurveyLines) {
            System.out.println(geophysicsSurveyLine.getAnomaly3DModelPath());
        }
    }

    @Test
    public void testGetAllProjects() throws Exception{
        List<GeophysicsProject> geophysicsProjects = new LinkedList<GeophysicsProject>();
        geophysicsProjects = geophysicsProjectMapper.getAllGeophysicsProjects();
        for (GeophysicsProject geophysicsProject : geophysicsProjects) {
            System.out.println(geophysicsProject.getName());
        }
    }

    @Test
    public void testGetProjectById() throws Exception{
        GeophysicsProject geophysicsProject = null;
        geophysicsProject = geophysicsProjectMapper.getProjectById(1);
        System.out.println(geophysicsProject.getName());
    }
}

