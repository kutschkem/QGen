package com.github.kutschkem.Qgen.test;

import static org.junit.Assert.*;

import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.uimafit.factory.JCasFactory;

import com.github.kutschkem.Qgen.annotators.SimpleSentenceDecompositionAnnotator;

public class SimpleDecompositionTest {

	@Test
	public void testSimpleDecomposition() throws UIMAException{
		JCas cas = JCasFactory.createJCas();
		cas.setDocumentText("Napoleon Bonaparte, who was a french emperor, died in 1821.");
		SimpleSentenceDecompositionAnnotator ann = new SimpleSentenceDecompositionAnnotator();
		ann.process(cas);
		
		assertEquals(cas.getView(SimpleSentenceDecompositionAnnotator.VIEW_NAME).getDocumentText(), "Napoleon Bonaparte died in 1821.");
	}
	
}
