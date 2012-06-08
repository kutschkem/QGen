package com.github.kutschkem.Qgen.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.JCasFactory;
import org.uimafit.util.JCasUtil;

import com.github.kutschkem.Qgen.annotations.Question;
import com.github.kutschkem.Qgen.annotations.Questionword;
import com.github.kutschkem.Qgen.annotators.QuestionAnnotator;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

public class QuestionAnnotatorTest {

    @Test
    public void test() throws UIMAException {
        String text = "I went home.";
        JCas cas = JCasFactory.createJCas();

        cas.setDocumentText(text);

        Sentence sent = new Sentence(cas);
        sent.setBegin(0);
        sent.setEnd(text.length());
        sent.addToIndexes();

        Questionword w1 = new Questionword(cas);
        w1.setBegin(0);
        w1.setEnd(1);
        w1.setQuestionword("who");
        w1.addToIndexes();

        Questionword w2 = new Questionword(cas);
        w2.setBegin(7);
        w2.setEnd(11);
        w2.setQuestionword("where");
        w2.addToIndexes();

        AnalysisEngine questionAssembler = AnalysisEngineFactory.createPrimitive(QuestionAnnotator.class);
        questionAssembler.process(cas);

        Collection<Question> questions = JCasUtil.select(cas, Question.class);

        List<String> questionStrings = new ArrayList<String>();
        for (Question q : questions) {
            questionStrings.add(q.getText());
        }

        List<String> answerStrings = new ArrayList<String>();
        for (Question q : questions) {
            answerStrings.add(q.getAnswer());
        }

        assertEquals(2, questions.size());
        assertTrue(questionStrings.contains("who went home?"));
        assertTrue(questionStrings.contains("I went where?"));
        assertTrue(answerStrings.contains("I"));
        assertTrue(answerStrings.contains("home"));
    }

}
