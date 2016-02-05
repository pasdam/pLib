package com.pasdam.utils.file;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Test;

/**
 * Test class of {@link FileRenamer}
 * 
 * @author paco
 * @version 0.1
 */
public class FileRenamerTest {
	
	private static final String TEST_FOLDER = "testData" + File.separator + "utils" + File.separator + "file";
	private static final String TEST_FILE = TEST_FOLDER + File.separator + "fileRename.tst";
	
	private File currentFile;

	@Test
	public void testRenameToFile() {
		File file = new File(TEST_FILE);
		FileRenamer renamer = new FileRenamer(file);

		boolean result;

		// test original file
		assertTrue(file.exists());
		assertFalse(renamer.undoAvailable());
		assertFalse(renamer.undoRename());
		assertEquals(file, renamer.getOriginalFile());
		assertEquals(file, renamer.getCurrentFile());

		result = renamer.renameTo(file);
		assertFalse(result);
		assertTrue(file.exists());
		assertFalse(renamer.undoAvailable());
		assertFalse(renamer.undoRename());
		assertEquals(file, renamer.getOriginalFile());
		assertEquals(file, renamer.getCurrentFile());
		
		// rename to file1
		File file1 = new File(file.getParent(), "file1");
		this.currentFile = file1;
		result = renamer.renameTo(file1);
		assertTrue(result);
		assertTrue(renamer.undoAvailable());
		assertTrue(file1.exists());
		assertFalse(file.exists());
		assertEquals(file, renamer.getOriginalFile());
		assertEquals(file1, renamer.getCurrentFile());
		
		// rename to file2
		File file2 = new File(file.getParent(), "file2");
		result = renamer.renameTo(file2);
		this.currentFile = file2;
		assertTrue(result);
		assertTrue(renamer.undoAvailable());
		assertTrue(file2.exists());
		assertFalse(file1.exists());
		assertFalse(file.exists());
		assertEquals(file, renamer.getOriginalFile());
		assertEquals(file2, renamer.getCurrentFile());

		// undo to file1
		this.currentFile = file1;
		result = renamer.undoRename();
		assertTrue(result);
		assertTrue(renamer.undoAvailable());
		assertTrue(file1.exists());
		assertFalse(file2.exists());
		assertFalse(file.exists());
		assertEquals(file, renamer.getOriginalFile());
		assertEquals(file1, renamer.getCurrentFile());
		
		// undo to original file
		this.currentFile = file;
		result = renamer.undoRename();
		assertTrue(result);
		assertFalse(renamer.undoAvailable());
		assertTrue(file.exists());
		assertFalse(file1.exists());
		assertFalse(file2.exists());
		assertEquals(file, renamer.getOriginalFile());
		assertEquals(file, renamer.getCurrentFile());
	}
	
	@After
	public void restoreOriginalFile() {
		File file = new File(TEST_FILE);
		if (this.currentFile != null && this.currentFile.exists()) {
			if (!this.currentFile.equals(file)) {
				this.currentFile.renameTo(file);
			}
		} else {
			System.err.println("Current file is null or it doesn't exist");
		}
	}
}
