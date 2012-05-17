package com.github.kutschkem.Qgen;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.component.xwriter.CASDumpWriter;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.JCasFactory;
import org.uimafit.pipeline.SimplePipeline;

import com.github.kutschkem.Qgen.annotators.NEQuestionWordAnnotator;
import com.github.kutschkem.Qgen.annotators.QuestionAnnotator;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;

public class QuestionExtractor {

    public Question extract(String text) throws UIMAException, IOException {
        // TODO
        JCas jcas = JCasFactory.createJCas();
        jcas.setDocumentText(text);

        SimplePipeline.runPipeline(jcas, getPipeline().toArray(new AnalysisEngine[] {}));
        return null;
    }

    private List<AnalysisEngine> getPipeline() throws ResourceInitializationException {

        AnalysisEngine tokenizer = AnalysisEngineFactory.createPrimitive(StanfordSegmenter.class);
        AnalysisEngine ner = AnalysisEngineFactory.createPrimitive(StanfordNamedEntityRecognizer.class,
                StanfordNamedEntityRecognizer.PARAM_MODEL, "english.muc.7class.distsim.crf.ser.gz");

        AnalysisEngine qwAssigner = AnalysisEngineFactory.createPrimitive(NEQuestionWordAnnotator.class);
        AnalysisEngine questionAssembler = AnalysisEngineFactory.createPrimitive(QuestionAnnotator.class);

        AnalysisEngine dumper = AnalysisEngineFactory.createPrimitive(CASDumpWriter.class);

        return Arrays.asList(tokenizer, ner, qwAssigner, questionAssembler, dumper);
    }

}
