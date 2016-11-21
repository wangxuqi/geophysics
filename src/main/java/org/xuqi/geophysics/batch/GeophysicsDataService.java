package org.xuqi.geophysics.batch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xuqi.geophysics.mapper.GeophysicsAnomalyMapper;
import org.xuqi.geophysics.mapper.GeophysicsProjectMapper;
import org.xuqi.geophysics.mapper.GeophysicsSurveyAreaMapper;
import org.xuqi.geophysics.mapper.GeophysicsSurveyLineMapper;

import com.google.common.collect.Lists;

@Service("geophysicsDataService")
public class GeophysicsDataService extends BaseService{
    @Autowired
    private GeophysicsSurveyLineMapper geophysicsSurveyLineMapper;

    @Autowired
    private GeophysicsSurveyAreaMapper geophysicsSurveyAreaMapper;

    @Autowired
    private GeophysicsProjectMapper geophysicsProjectMapper;

    @Autowired
    private GeophysicsAnomalyMapper geophysicsAnomalyMapper;
    @Override
    public List<Handler> getHandles() {
        List<Handler> handlers = Lists.newLinkedList();
        //handlers.add(new GeophysicsProcessor.SurveyLineHandler(geophysicsSurveyLineMapper, getBatchSize()));
        handlers.add(new GeophysicsProcessor.SurveyAreaHandler(geophysicsSurveyAreaMapper,getBatchSize()));
        //handlers.add(new GeophysicsProcessor.ProjectHandler(geophysicsProjectMapper,getBatchSize()));
        //handlers.add(new GeophysicsProcessor.AnomalyHandler(geophysicsAnomalyMapper,getBatchSize()));
        return handlers;
    }
}
