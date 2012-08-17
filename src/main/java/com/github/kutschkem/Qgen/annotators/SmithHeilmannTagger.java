package com.github.kutschkem.Qgen.annotators;

import java.util.Arrays;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.util.JCasUtil;

import com.github.kutschkem.Qgen.SmithHeilmann.QuestionAsker;
import com.github.kutschkem.Qgen.annotations.Question;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.wikipedia.util.StringUtils;
import edu.cmu.ark.AnalysisUtilities;
import edu.cmu.ark.GlobalProperties;
import edu.stanford.nlp.trees.Tree;

public class SmithHeilmannTagger extends JCasAnnotator_ImplBase {
	
	QuestionAsker asker = new QuestionAsker();
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		for(Sentence s : JCasUtil.select(aJCas, Sentence.class) ){
			String sentence = s.getCoveredText();
			List<edu.cmu.ark.Question> questions = asker.ask(sentence);
			
			for(edu.cmu.ark.Question q : questions){
				Question question = new Question(aJCas);
				question.setBegin(s.getBegin());
				question.setEnd(s.getEnd());
				Tree answerPhraseTree = q.getAnswerPhraseTree();
				if(answerPhraseTree != null){
				question.setAnswer(StringUtils.join(Arrays.asList(AnalysisUtilities.stringArrayFromLabels(answerPhraseTree.yield()))," "));
				}else{
					question.setAnswer("Yes");
				}
				String text = StringUtils.join(Arrays.asList(AnalysisUtilities.stringArrayFromLabels(q.getTree().yield())), " ");
				text = text.replaceAll("\\s(?=\\p{Punct})", "");
				question.setText(text);
				question.addToIndexes();
			}
		}
	}

}
