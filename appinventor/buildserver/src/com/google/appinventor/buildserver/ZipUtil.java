package com.google.appinventor.buildserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	public static void recursiveZip(File dir, URI dirBase, ZipOutputStream out) throws IOException {
		File[] files = dir.listFiles();
		byte[] tmpBuf = new byte[1024];

		for (File file : files) {
			if (file.isDirectory()) {
				out.putNextEntry(new ZipEntry(dirBase.relativize(file.toURI()).getPath()));
				out.closeEntry();
				recursiveZip(file,dirBase,out);
				continue;
			}
			
			FileInputStream in = new FileInputStream(file.getAbsolutePath());
			out.putNextEntry(new ZipEntry(dirBase.relativize(file.toURI()).getPath()));

			int len;
			while ((len = in.read(tmpBuf)) != -1) {
				out.write(tmpBuf, 0, len);
			}

			out.closeEntry();
			in.close();
		}
	}

}
