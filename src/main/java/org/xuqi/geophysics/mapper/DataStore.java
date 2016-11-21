package org.xuqi.geophysics.mapper;

import java.util.List;

public interface DataStore<T> {
	/**
	 * Insert one row into Bus data store
	 * 
	 * @param entity
	 */
	public void insert(T entity) throws Exception;

	/**
	 * Insert a list of rows into Bus data store
	 * 
	 * @param entities
	 */
	public void batchInsert(List<T> entities) throws Exception;
}
