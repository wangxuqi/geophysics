package org.xuqi.geophysics.batch;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateFileNameFilter implements FilenameFilter {
	// Date regex pattern
	private final Pattern regex = Pattern.compile("^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}$");

	@Override
	public boolean accept(File dir, String name) {
		Matcher m = regex.matcher(name);
		if (m.find()) {
			return true;
		}
		return false;
	}
}
