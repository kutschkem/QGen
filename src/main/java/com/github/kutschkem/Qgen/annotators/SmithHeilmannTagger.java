package com.github.kutschkem.Qgen.annotators;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.util.JCasUtil;

import com.github.kutschkem.Qgen.SmithHeilmann.QuestionAsker;
import com.github.kutschkem.Qgen.annotations.Question;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import edu.cmu.ark.AnalysisUtilities;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.trees.tregex.tsurgeon.Tsurgeon;
import edu.stanford.nlp.trees.tregex.tsurgeon.TsurgeonPattern;

public class SmithHeilmannTagger extends JCasAnnotator_ImplBase {

	QuestionAsker asker = new QuestionAsker();

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		List<Sentence> sentences = new ArrayList<Sentence>(JCasUtil.select(
				aJCas, Sentence.class));
		List<String> strings = new ArrayList<String>();
		for (Sentence s : sentences) {
			strings.add(s.getCoveredText());
		}
		List<edu.cmu.ark.Question> questions = asker.ask(strings);

		for (edu.cmu.ark.Question q : questions) {
			Sentence s = sentences.get(q.getSourceSentenceNumber());
			Question question = new Question(aJCas);
			question.setBegin(s.getBegin());
			question.setEnd(s.getEnd());
			Tree answerPhraseTree = q.getAnswerPhraseTree();
			if (answerPhraseTree != null) {
				question.setAnswer(AnalysisUtilities.orginialSentence(answerPhraseTree.yield()));
				relabelWH(JCasUtil.selectCovered(NamedEntity.class, s),
						question.getAnswer(), q.getTree());
			} else {
				question.setAnswer("Yes");
			}
			String text = AnalysisUtilities.orginialSentence(q.getTree()
					.yield());
			question.setText(text);
			question.addToIndexes();
		}

	}

	/**
	 * Smith-Heilmann's system doesn't seem to always decide on the right
	 * Question word, this is enhance here by a rule-based system based on the
	 * Named Entity Recognizer
	 * 
	 * @param listNE
	 * @param answer
	 * @param tree
	 */
	private void relabelWH(List<NamedEntity> listNE, String answer, Tree tree) {
		// Step 1: identify NamedEntity corresponding to answer
		String newWH = null;
		for (NamedEntity ent : listNE) {
			if (ent.getCoveredText().equals(answer)) {
				newWH = NEQuestionWordAnnotator.getQuestionWord(ent);
				break;
			}
		}
		if (newWH == null)
			return;
		// Step 2: identify WH-node and replace the question word
		String tregex = String.format(
				"ROOT << (/^WH.*$/ << /^[Ww]h.*$/=qw ! << %1$s)", newWH);
		TregexPattern p = TregexPattern.compile(tregex);

		TsurgeonPattern sp = Tsurgeon.parseOperation(String.format(
				"relabel qw %1$s", newWH));

		Tsurgeon.processPattern(p, sp, tree);

		AnalysisUtilities.upcaseFirstToken(tree);
	}

}
