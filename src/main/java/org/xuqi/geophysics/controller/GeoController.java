package org.xuqi.geophysics.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.xuqi.geophysics.batch.MyFileReader;
import org.xuqi.geophysics.entity.GeophysicsProject;
import org.xuqi.geophysics.entity.GeophysicsSurveyArea;
import org.xuqi.geophysics.ResponseWrapper;
import org.xuqi.geophysics.entity.GeophysicsSurveyLine;
import org.xuqi.geophysics.mapper.GeophysicsProjectMapper;
import org.xuqi.geophysics.mapper.GeophysicsSurveyAreaMapper;
import org.xuqi.geophysics.mapper.GeophysicsSurveyLineMapper;

@Controller
public class GeoController {

    @Autowired
    private GeophysicsProjectMapper geophysicsProjectMapper;

    @Autowired
    private GeophysicsSurveyAreaMapper geophysicsSurveyAreaMapper;

    @Autowired
    private GeophysicsSurveyLineMapper geophysicsSurveyLineMapper;


    @ResponseBody
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResponseWrapper<List<GeophysicsProject>> getAllProjects() {
        List<GeophysicsProject> projects = geophysicsProjectMapper.getAllGeophysicsProjects();
        return new ResponseWrapper<List<GeophysicsProject>>("success", 200, projects);
    }

    @ResponseBody
    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.GET)
    public ResponseWrapper<GeophysicsProject> getProjectByProjectId(
            @PathVariable("projectId") int projectId) {
        GeophysicsProject geophysicsProject = geophysicsProjectMapper.getProjectById(projectId);
        return new ResponseWrapper<GeophysicsProject>("success", 200, geophysicsProject);
    }

    @ResponseBody
    @RequestMapping(value = "/projects/{projectId}/areas", method = RequestMethod.GET)
    public ResponseWrapper<List<GeophysicsSurveyArea>> getSurveyAreasByProjectId(
            @PathVariable("projectId") int projectId) {
        List<GeophysicsSurveyArea> areas = geophysicsSurveyAreaMapper.getSurveyAreasByProjectId(projectId);
        return new ResponseWrapper<List<GeophysicsSurveyArea>>("success", 200, areas);
    }

    @ResponseBody
    @RequestMapping(value = "/projects/{projectId}/method/{methodId}/areas", method = RequestMethod.GET)
    public ResponseWrapper<List<GeophysicsSurveyArea>> getSurveyAreasByProjectIdAndMethodId(
            @PathVariable("projectId") int projectId,
            @PathVariable("methodId") int methodId) {
        List<GeophysicsSurveyArea> areas = geophysicsSurveyAreaMapper.getSurveyAreasByProjectIdAndMethodId(projectId, methodId);
        return new ResponseWrapper<List<GeophysicsSurveyArea>>("success", 200, areas);
    }

    @ResponseBody
    @RequestMapping(value = "/projects/{projectId}/areas/{areaId}/lines", method = RequestMethod.GET)
    public ResponseWrapper<List<GeophysicsSurveyLine>> getSurveylinesByProjectId(
            @PathVariable("projectId") int projectId,
            @PathVariable("areaId") long areaId) {
        List<GeophysicsSurveyLine> surveyLines = new LinkedList<GeophysicsSurveyLine>();
        surveyLines = geophysicsSurveyLineMapper.getSurveyLineBySurveyAreaIdAndProjectId(areaId, projectId);
        return new ResponseWrapper<List<GeophysicsSurveyLine>>("success", 200, surveyLines);
    }

    @ResponseBody
    @RequestMapping(value = "/projects/{projectId}/areas/{areaId}/lines/query", method = RequestMethod.GET)
    public ResponseWrapper<GeophysicsSurveyLine> getSurveyLineById(@RequestParam("id") long id) {
        GeophysicsSurveyLine geophysicsSurveyLine = null;
        geophysicsSurveyLine = geophysicsSurveyLineMapper.getSurveyLineById(id);
        return new ResponseWrapper<GeophysicsSurveyLine>("success", 200, geophysicsSurveyLine);
    }

    @ResponseBody
    @RequestMapping(value = "/projects/{projectId}/lines", method = RequestMethod.GET)
    public ResponseWrapper<List<GeophysicsSurveyLine>> getSurveylinesByProjectId(
            @PathVariable("projectId") int projectId) {
        List<GeophysicsSurveyLine> surveyLines = geophysicsSurveyLineMapper.getSurveyLineByProjectId(projectId);
        return new ResponseWrapper<List<GeophysicsSurveyLine>>("success", 200, surveyLines);
    }

    @ResponseBody
    @RequestMapping(value = "/projects/{projectId}/lines/query", method = RequestMethod.GET)
    public ResponseWrapper<GeophysicsSurveyLine> getSurveyLineBylineId(@RequestParam("id") long id) {
        GeophysicsSurveyLine geophysicsSurveyLine = null;
        geophysicsSurveyLine = geophysicsSurveyLineMapper.getSurveyLineById(id);
        return new ResponseWrapper<GeophysicsSurveyLine>("success", 200, geophysicsSurveyLine);
    }


    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseWrapper<List<double[]>> getData() throws IOException {
        List<double[]> list = MyFileReader.readFileData("F:\\1234.txt");
        double min =99999;
        double max = 0;
        for (double[] array : list ) {
            if (array[2] < min) {
                min = array[2];
            }
            if (array[2] > max){
                max = array[2];
            }
        }
        for (double[] array : list ) {
            array[0] = array[0] * 40;
            array[1] = array[1] * -40;
            array[2] = (array[2]-min) * 10;
        }
        return new ResponseWrapper<List<double[]>>("success", 200, list);
    }
}
