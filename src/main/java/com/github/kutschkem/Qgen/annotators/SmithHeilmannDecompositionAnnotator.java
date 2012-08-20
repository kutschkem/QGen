package com.github.kutschkem.Qgen.annotators;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.component.ViewCreatorAnnotator;
import org.uimafit.util.JCasUtil;

import com.github.kutschkem.Qgen.SmithHeilmann.SentenceDecomposer;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;

public class SmithHeilmannDecompositionAnnotator extends JCasAnnotator_ImplBase {
	
	public static final String VIEW_NAME = "SH_decomposed_view";

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		SentenceDecomposer decomp = new SentenceDecomposer();
		StringBuilder bldr = new StringBuilder();
		for(String s: decomp.decompose(aJCas.getDocumentText())){
			bldr.append(s);
		}
		
		JCas view = ViewCreatorAnnotator.createViewSafely(aJCas, VIEW_NAME);
		if(JCasUtil.exists(aJCas, DocumentMetaData.class)){
		DocumentMetaData.copy(aJCas, view);
		}
		view.setDocumentText(bldr.toString());
		view.setDocumentLanguage(aJCas.getDocumentLanguage());
	}

}
