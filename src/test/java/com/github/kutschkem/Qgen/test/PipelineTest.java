package com.github.kutschkem.Qgen.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.uimafit.component.xwriter.CASDumpWriter;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.JCasFactory;
import org.uimafit.pipeline.SimplePipeline;
import com.github.kutschkem.Qgen.Question;
import com.github.kutschkem.Qgen.QuestionExtractor;
import com.github.kutschkem.Qgen.annotators.SmithHeilmannDecompositionAnnotator;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;

public class PipelineTest {

	
	@Test
	public void test() throws UIMAException, IOException{
		String sentence = "Napoleon Bonaparte died in 1821.";
		QuestionExtractor ex = new QuestionExtractor();
		for(Question q: ex.extract(sentence)){
			System.out.println(q.Question);
		}
		
	}
	
	@Test
	public void testModularSHComponents() throws UIMAException, IOException{
		String text = "Napoleon Bonaparte, who died in 1821, shouted at his dog.";
		
		List<AnalysisEngine> engines = new ArrayList<AnalysisEngine>();
		
		engines.add(AnalysisEngineFactory.createPrimitive(StanfordNamedEntityRecognizer.class,
					StanfordNamedEntityRecognizer.PARAM_LANGUAGE,
					"en",
					StanfordNamedEntityRecognizer.PARAM_VARIANT,
					"muc.7class.distsim.crf"));
		engines.add(AnalysisEngineFactory.createPrimitive(SmithHeilmannDecompositionAnnotator.class));
		engines.add(AnalysisEngineFactory.createAnalysisEngine(AnalysisEngineFactory.createPrimitiveDescription(StanfordSegmenter.class),SmithHeilmannDecompositionAnnotator.VIEW_NAME));
		engines.add(AnalysisEngineFactory.createPrimitive(CASDumpWriter.class));

		JCas cas = JCasFactory.createJCas();
		cas.setDocumentLanguage("en");
		cas.setDocumentText(text);
		
		SimplePipeline.runPipeline(cas, engines.toArray(new AnalysisEngine[0]));
		
		
	}
}
