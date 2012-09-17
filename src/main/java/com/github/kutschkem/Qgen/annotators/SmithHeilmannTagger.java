// Copyright (c) 2012 Michael Kutschke. All Rights Reserved.
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
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
