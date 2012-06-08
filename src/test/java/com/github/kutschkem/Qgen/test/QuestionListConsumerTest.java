package com.github.kutschkem.Qgen.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.JCasFactory;

import com.github.kutschkem.Qgen.QuestionListConsumer;
import com.github.kutschkem.Qgen.annotations.Question;

public class QuestionListConsumerTest {

    @Test
    public void test() throws UIMAException {
        AnalysisEngine dumper = AnalysisEngineFactory.createPrimitive(QuestionListConsumer.class);

        JCas cas = JCasFactory.createJCas();
        String text = "This is a question.";
        cas.setDocumentText(text);

        Question q = new Question(cas);
        q.setBegin(0);
        q.setEnd(text.length());
        q.setText("This is what?");
        q.setAnswer("a question");
        q.addToIndexes();

        dumper.process(cas);

        List<com.github.kutschkem.Qgen.Question> questions = QuestionListConsumer.questions.get("run");

        assertTrue(questions.size() == 1);
        assertTrue(questions.get(0).original_text.equals(text));

    }

}
