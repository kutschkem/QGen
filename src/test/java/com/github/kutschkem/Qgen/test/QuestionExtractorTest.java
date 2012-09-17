package com.github.kutschkem.Qgen.test;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.junit.Test;

import com.github.kutschkem.Qgen.QuestionExtractor;

public class QuestionExtractorTest {

	@Test
	public void testSingularForm() throws UIMAException, IOException{
		// tests a bug that occurred in the sentence "You went to school."
		String text = "You went to school.";
		QuestionExtractor ex = new QuestionExtractor();
		ex.extract(text);
	}
	
}
