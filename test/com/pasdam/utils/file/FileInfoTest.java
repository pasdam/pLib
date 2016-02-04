package com.pasdam.utils.file;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.pasdam.test.TestFileReader;

/**
 * {@link FileInfo} test class
 * 
 * @author paco
 * @version 0.1
 */
public class FileInfoTest {

	private static final String TEST_FILE_NAME 			= "fileInfoNames";
	private static final String TEST_FILE_SIZE_FORMAT 	= "fileInfoFormatSizes";
	private static final String TEST_FILE_SIZE_PARSE 	= "fileInfoParseSizes";
	
	@Test
	public void testParseFileInfo() throws Exception {
		File testData = new File(
				"testData" + File.separator + 
				"utils" + File.separator + 
				"file" + File.separator + 
				TEST_FILE_NAME + "." + TestFileReader.EXTENSION);
		FileInfo fileInfo = FileInfo.parseFileInfo(testData);
		
		// test testData's info
		assertEquals(TEST_FILE_NAME, fileInfo.getName());
		assertEquals(TEST_FILE_NAME, FileInfo.parseName(testData.getName()));
		assertEquals(TestFileReader.EXTENSION, fileInfo.getExtension());
		assertEquals(TestFileReader.EXTENSION, FileInfo.parseExtension(testData.getName()));
		
		// test info from testData
		TestFileReader reader = new TestFileReader(testData);
		String[] parts;
		
		while ((parts = reader.readAndSplitLine()).length > 0) {
			assertEquals("Data file is not well formed. ", true, parts.length >= 2 && parts.length <= 3);
			assertEquals("Filename=\"" + parts[0] + "\". Name => ", parts[1], FileInfo.parseName(parts[0]));
			assertEquals("Filename=\"" + parts[0] + "\". Extension => ", (parts.length == 3 ? parts[2] : ""), FileInfo.parseExtension(parts[0]));
		}
	}

	@Test
	public void testFormatSize() throws Exception {
		TestFileReader reader = new TestFileReader(new File(
				"testData" + File.separator + 
				"utils" + File.separator + 
				"file" + File.separator + 
				TEST_FILE_SIZE_FORMAT + "." + TestFileReader.EXTENSION)
		);
		
		String[] parts;
		while ((parts = reader.readAndSplitLine()).length > 0) {
			assertEquals("Data file is not well formed. ", 4, parts.length);
			assertEquals(parts[0] + " => " + parts[3] + ". ", parts[3], FileInfo.formatSize(Long.parseLong(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
		}
	}

	@Test
	public void testParseSize() throws Exception {
		TestFileReader reader = new TestFileReader(new File(
				"testData" + File.separator + 
				"utils" + File.separator + 
				"file" + File.separator + 
				TEST_FILE_SIZE_PARSE + "." + TestFileReader.EXTENSION)
		);
		
		String[] parts;
		while ((parts = reader.readAndSplitLine()).length > 0) {
			assertEquals("Data file is not well formed. ", 2, parts.length);
			assertEquals(parts[1] + " => " + parts[0] + ". ", Long.parseLong(parts[0]), FileInfo.parseSize(parts[1]));
		}
	}
}
