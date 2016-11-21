package org.xuqi.geophysics.batch;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FilesOperation {

	public File doZip(String sourceDir, String zipFilePath) {

		File file = new File(sourceDir);
		File zipFile = new File(zipFilePath);
		ZipOutputStream zos = null;
		try {
			// ???????
			OutputStream os = new FileOutputStream(zipFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			zos = new ZipOutputStream(bos);

			String basePath = null;

			// ????
			if (file.isDirectory()) {
				basePath = file.getPath();
			} else {
				basePath = file.getParent();
			}

			zipFile(file, basePath, zos);
			zos.closeEntry();
			zos.close();
			bos.close();
			os.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return zipFile;
	}

	/**
	 * @param source
	 *            ???
	 * @param basePath
	 * @param zos
	 */
	private void zipFile(File source, String basePath, ZipOutputStream zos) throws IOException {
		File[] files = null;
		if (source.isDirectory()) {
			files = source.listFiles();
		} else {
			files = new File[1];
			files[0] = source;
		}

		InputStream is = null;
		String pathName;
		byte[] buf = new byte[1024];
		int length = 0;
		try {
			for (File file : files) {
				if (file.isDirectory()) {
					pathName = file.getPath().substring(basePath.length() + 1) + "/";
					zos.putNextEntry(new ZipEntry(pathName));
					zipFile(file, basePath, zos);
				} else {
					pathName = file.getPath().substring(basePath.length() + 1);
					is = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(is);
					zos.putNextEntry(new ZipEntry(pathName));
					while ((length = bis.read(buf)) > 0) {
						zos.write(buf, 0, length);
					}
					is.close();
					bis.close();
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void deleteFile(File file) {
		if (!file.exists())
			throw new IllegalArgumentException(String.format(" the file %s not exist", file.getAbsolutePath()));
		if (file.isDirectory()) {
			String[] files = file.list();
			int index = 0;
			while (index < files.length) {
				File deleteFile = new File(file.getAbsolutePath() + "/" + files[index]);
				if (deleteFile.isDirectory())
					deleteFile(deleteFile);
				boolean result = deleteFile.getAbsoluteFile().delete();
				index++;
			}

		} else {
			file.getAbsoluteFile().delete();
		}
		file.getAbsoluteFile().delete();
	}

	public  void copyFile(File sourcefile, File targetFile) throws IOException {

		// ??????????????
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);

		// ??????????????
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);

		// ????
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}

		// ?????????
		outbuff.flush();

		// ???
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();

	}
	public  void copyDirectory(String sourceDir, String targetDir) throws IOException {

		// ??????

		(new File(targetDir)).mkdirs();

		// ??????????????
		File[] file = (new File(sourceDir)).listFiles();

		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// ???
				File sourceFile = file[i];
				// ????
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());

				copyFile(sourceFile, targetFile);

			}

			if (file[i].isDirectory()) {
				// ?????????
				String dir1 = sourceDir + file[i].getName();
				// ??????????
				String dir2 = targetDir + "/" + file[i].getName();

				copyDirectory(dir1, dir2);
			}
		}

	}
}
