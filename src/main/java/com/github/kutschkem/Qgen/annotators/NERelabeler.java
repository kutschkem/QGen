package com.github.kutschkem.Qgen.annotators;

import java.util.List;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import edu.cmu.ark.AnalysisUtilities;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.trees.tregex.tsurgeon.Tsurgeon;
import edu.stanford.nlp.trees.tregex.tsurgeon.TsurgeonPattern;

public class NERelabeler {
	
	/**
	 * Smith-Heilmann's system doesn't seem to always decide on the right
	 * Question word, this is enhance here by a rule-based system based on the
	 * Named Entity Recognizer
	 * 
	 * @param listNE
	 * @param answer
	 * @param tree
	 */
	public static void relabelWH(List<NamedEntity> listNE, String answer, Tree tree) {
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
