package com.github.kutschkem.Qgen.annotators;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.util.JCasUtil;

import com.github.kutschkem.Qgen.annotations.Question;
import com.github.kutschkem.Qgen.annotations.Questionword;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

public class QuestionAnnotator extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas cas) throws AnalysisEngineProcessException {
        for (Sentence s : JCasUtil.select(cas, Sentence.class)) {
            for (Questionword qw : JCasUtil.selectCovered(cas, Questionword.class, s)) {
                Question question = new Question(cas);
                question.setBegin(s.getBegin());
                question.setEnd(s.getEnd());
                question.setAnswer(qw.getCoveredText());

                String questionword = qw.getQuestionword();

                String sentence = s.getCoveredText();
                int from = qw.getBegin();
                int to = qw.getEnd();

                String quest = sentence.substring(0, from) + questionword + sentence.substring(to);

                quest = quest.replaceAll("\\.$", "?");

                question.setText(quest);
                question.addToIndexes();
            }
        }

    }

}
