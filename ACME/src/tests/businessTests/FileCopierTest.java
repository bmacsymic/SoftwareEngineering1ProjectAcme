package tests.businessTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

import acme.business.FileCopier;

public class FileCopierTest {
	@Test
	public void testEmpty() {
		assertFalse(FileCopier.copyBinaryFile("", ""));
		assertFalse(FileCopier.copyTextFile("", ""));
		System.out.println("Finished FileCopierTest(1/4).");
	}

	@Test
	public void testNull() {
		assertFalse(FileCopier.copyBinaryFile(null, null));
		assertFalse(FileCopier.copyTextFile(null, null));
		System.out.println("Finished FileCopierTest(2/4).");
	}

	@Test
	public void testSuppliedText()
	{
		String src = "TextFile1.txt";
		String dest = "TextFile2.txt";

		File file = new File(src);
		File file2 = new File(dest);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!file2.exists()) {
			try {
				file2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			pw.println("This is a test: line 1");
			pw.println("This is a test: line 2");
			pw.close();
		} catch (IOException e) {
			assertTrue(false);
			System.out.println("Failed to write test data to test file in FileCopierTest(3/4)");
		}
		
		src = file.getAbsolutePath();
		dest = file2.getAbsolutePath();

		assertTrue(FileCopier.copyTextFile(src, dest));
		
		file.delete();
		file2.delete();
		System.out.println("Finished FileCopierTest(3/4).");
	}
	
	@Test
	public void testSuppliedBinary() {
		String src = "TextFile1.txt";
		String dest = "TextFile2.txt";

		File file = new File(src);
		File file2 = new File(dest);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!file2.exists()) {
			try {
				file2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		src = file.getAbsolutePath();
		dest = file2.getAbsolutePath();

		assertTrue(FileCopier.copyBinaryFile(src, dest));

		file.delete();
		file2.delete();
		System.out.println("Finished FileCopierTest(4/4).");
	}

}
