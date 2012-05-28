package com.github.kutschkem.Qgen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.JCasFactory;
import org.uimafit.pipeline.SimplePipeline;

import com.github.kutschkem.Qgen.annotators.NEQuestionWordAnnotator;
import com.github.kutschkem.Qgen.annotators.QuestionAnnotator;

import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;

public class QuestionExtractor {

    private AnalysisEngine tokenizer;
    private AnalysisEngine ner;
    private AnalysisEngine qwAssigner;
    private AnalysisEngine questionAssembler;
    private JCas jcas;

    public QuestionExtractor() {
        try {
            tokenizer = AnalysisEngineFactory.createPrimitive(StanfordSegmenter.class);
            ner = AnalysisEngineFactory.createPrimitive(StanfordNamedEntityRecognizer.class,
                    StanfordNamedEntityRecognizer.PARAM_MODEL, "english.muc.7class.distsim.crf.ser.gz");
            qwAssigner = AnalysisEngineFactory.createPrimitive(NEQuestionWordAnnotator.class);
            questionAssembler = AnalysisEngineFactory.createPrimitive(QuestionAnnotator.class);
            jcas = JCasFactory.createJCas();
        } catch (ResourceInitializationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UIMAException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Question> extract(String text) throws UIMAException, IOException {
        List<Question> questions = new ArrayList<Question>();

        jcas.reset();
        jcas.setDocumentText(text);

        SimplePipeline.runPipeline(jcas, getPipeline(questions).toArray(new AnalysisEngine[] {}));
        return QuestionListConsumer.questions.get("run");
    }

    private List<AnalysisEngine> getPipeline(List<Question> questions) throws ResourceInitializationException {

        AnalysisEngine dumper = AnalysisEngineFactory.createPrimitive(QuestionListConsumer.class);

        return Arrays.asList(tokenizer, ner, qwAssigner, questionAssembler, dumper);
    }

}
