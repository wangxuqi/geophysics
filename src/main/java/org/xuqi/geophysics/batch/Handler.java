package org.xuqi.geophysics.batch;

import java.io.File;

public interface Handler {
	  /**
	   * Handler the input directory
	   * @param dir
	   * @throws Exception
	   */
	  public void handle(File dir) throws Exception;

	  /**
	   * Get the name of handler
	   * @return
	   */
	  public String getName();

	  /**
	   * Return the process count of data
	   * @return
	   */
	  public int getHandlerCount();
	}
