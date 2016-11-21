package org.xuqi.geophysics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;
import org.xuqi.geophysics.batch.ModelConfig;
import org.xuqi.geophysics.entity.GeophysicsSurveyArea;
import org.xuqi.geophysics.mapper.GeophysicsSurveyAreaMapper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangxuqi on 2016/3/28.
 */
@ContextConfiguration(classes = {ModelConfig.class})
@WebAppConfiguration
public class GeophysicsSurveyAreaMapperTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private GeophysicsSurveyAreaMapper geophysicsSurveyAreaMapper;

    @Test
    public void testGetGeophysicsAreaByProjectFId(){
        List<GeophysicsSurveyArea> geophysicsSurveyAreas = new LinkedList<GeophysicsSurveyArea>();
        geophysicsSurveyAreas = geophysicsSurveyAreaMapper.getSurveyAreasByProjectId(1);
        for(GeophysicsSurveyArea geophysicsSurveyArea : geophysicsSurveyAreas){
            System.out.println(geophysicsSurveyArea.getName());
        }

    }

    @Test
    public void testGetAreasByProjectIdAndMethodId(){
        int projectId =1;
        int methodId=1;
        List<GeophysicsSurveyArea> geophysicsSurveyAreas = new LinkedList<GeophysicsSurveyArea>();
        geophysicsSurveyAreas = geophysicsSurveyAreaMapper.getSurveyAreasByProjectIdAndMethodId(projectId,methodId);
        for(GeophysicsSurveyArea geophysicsSurveyArea : geophysicsSurveyAreas){
            System.out.println(geophysicsSurveyArea.getName());
        }
    }
}
