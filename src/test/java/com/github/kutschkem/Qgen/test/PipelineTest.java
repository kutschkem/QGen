package com.github.kutschkem.Qgen.test;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.junit.Test;

import com.github.kutschkem.Qgen.Question;
import com.github.kutschkem.Qgen.QuestionExtractor;

public class PipelineTest {

	
	@Test
	public void test() throws UIMAException, IOException{
		String sentence = "Napoleon Bonaparte died in 1821.";
		QuestionExtractor ex = new QuestionExtractor();
		for(Question q: ex.extract(sentence)){
			System.out.println(q.Question);
		}
		
	}
}
