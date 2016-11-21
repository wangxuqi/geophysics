package org.xuqi.geophysics.mapper;

import org.xuqi.geophysics.entity.GeophysicsProject;

import java.util.List;

public interface GeophysicsProjectMapper extends DataStore<GeophysicsProject> {
    public List<GeophysicsProject> getAllGeophysicsProjects();

    public GeophysicsProject getProjectById(int projectId);

    public void delete(int projectId);
}
