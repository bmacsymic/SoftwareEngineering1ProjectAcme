package acme.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCopier {
	public static boolean copyTextFile(String src, String dest) {
		File srcFile = null;
		File destFile = null;
		boolean result = false;
		
		if (src != null && dest != null && !src.equals(dest))
		{
			try {
				destFile = new File(dest);
				if (destFile.exists())
				{
					destFile.delete();
				}

				srcFile = new File(src);
				destFile = new File(dest);

				FileReader in = new FileReader(srcFile);
				FileWriter out = new FileWriter(destFile);
				int c;

				while ((c = in.read()) != -1)
				{
					out.write(c);
				}

				in.close();
				out.close();
				result = true;
			} catch (IOException ioe) {
				result = false;
			}
		}
		
		return result;
	}
	
	public static boolean copyBinaryFile(String src, String dest) {
		File srcFile;
		boolean result = false;

		try {
			if (src != null && dest != null) {
				srcFile = new File(src);

				if (srcFile.exists()) {
					File destFile = new File(dest);
					if (!destFile.exists()) {
						FileInputStream fin = new FileInputStream(srcFile);
						FileOutputStream fout = new FileOutputStream(destFile);

						byte[] b = new byte[1024];
						int numBytes = 0;

						while ((numBytes = fin.read(b)) != -1) {
							fout.write(b, 0, numBytes);
						}

						fin.close();
						fout.close();
						result = true;
					} else {
						result = true;
					}
				}
			}
		} catch (IOException ioe) {
			result = false;
		}

		return result;
	}
}
