package com.github.kutschkem.Qgen.annotators;

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.util.JCasUtil;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

import edu.cmu.ark.AnalysisUtilities;
import edu.cmu.ark.Question;
import edu.cmu.ark.QuestionTransducer;
import edu.stanford.nlp.trees.Tree;


public class SmithHeilmannQuestionAnnotator extends JCasAnnotator_ImplBase {

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		QuestionTransducer trans = new QuestionTransducer();
		trans.setAvoidPronounsAndDemonstratives(true);
		for(Sentence sentence : JCasUtil.select(aJCas, Sentence.class)){
			Tree t = AnalysisUtilities.getInstance().parseSentence(sentence.getCoveredText()).parse;
			
			trans.generateQuestionsFromParse(t);
			List<Question> questions = trans.getQuestions();
			for(Question q: questions){
				com.github.kutschkem.Qgen.annotations.Question qanno = new com.github.kutschkem.Qgen.annotations.Question(aJCas);
				qanno.setBegin(sentence.getBegin());
				qanno.setEnd(sentence.getEnd());
				if(q.getAnswerPhraseTree() != null){
				qanno.setAnswer(AnalysisUtilities.orginialSentence(q.getAnswerPhraseTree().yield()));
				NERelabeler.relabelWH(JCasUtil.selectCovered(aJCas, NamedEntity.class,sentence), qanno.getAnswer(), q.getTree());
				}else{
					qanno.setAnswer("Yes");
				}
				qanno.setText(AnalysisUtilities.orginialSentence(q.getTree().yield()));
				qanno.addToIndexes();
			}
		}
		
		
	}

}
