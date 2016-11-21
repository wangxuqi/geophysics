package org.xuqi.geophysics.mapper;

import org.xuqi.geophysics.entity.GeophysicsAnomaly;

public interface GeophysicsAnomalyMapper extends DataStore<GeophysicsAnomaly> {
	public String get3DModelByAnomalyId(int anomalyId);

	public void delete(int anomalyId);

	public GeophysicsAnomaly getAnomalyByAnomalyId(int anomalyId);
}