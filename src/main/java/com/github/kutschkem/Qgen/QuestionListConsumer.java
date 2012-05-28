package com.github.kutschkem.Qgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasConsumer_ImplBase;
import org.uimafit.descriptor.ConfigurationParameter;
import org.uimafit.factory.ConfigurationParameterFactory;
import org.uimafit.util.JCasUtil;

public class QuestionListConsumer extends JCasConsumer_ImplBase {

    public static Map<String, List<Question>> questions = new HashMap<String, List<Question>>();

    public static final String PARAM_RUN_ID = ConfigurationParameterFactory.createConfigurationParameterName(
            QuestionListConsumer.class, "runId");

    @ConfigurationParameter(defaultValue = "run")
    private String runId;

    @Override
    public void process(JCas arg0) throws AnalysisEngineProcessException {
        List<Question> questionList = new ArrayList<Question>();
        for (com.github.kutschkem.Qgen.annotations.Question q : JCasUtil.select(arg0,
                com.github.kutschkem.Qgen.annotations.Question.class)) {
            Question question = new Question();
            question.original_text = q.getCoveredText();
            question.Question = q.getText();
            question.answer = q.getAnswer();

            questionList.add(question);
        }
        questions.put(runId, questionList);
    }

    public void clear() {
        questions.clear();
    }

}
