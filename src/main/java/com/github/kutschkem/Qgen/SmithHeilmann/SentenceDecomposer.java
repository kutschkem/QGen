package com.github.kutschkem.Qgen.SmithHeilmann;

import java.util.ArrayList;
import java.util.List;


import edu.cmu.ark.AnalysisUtilities;
import edu.cmu.ark.InitialTransformationStep;
import edu.cmu.ark.Question;
import edu.stanford.nlp.trees.Tree;

public class SentenceDecomposer {
	
	InitialTransformationStep trans = new InitialTransformationStep();
	
	public List<String> decompose(String text){
		
		List<Tree> trees = new ArrayList<Tree>();
		for(String sentence : AnalysisUtilities.getSentences(text)){
			trees.add(AnalysisUtilities.getInstance().parseSentence(sentence).parse);
		}
		
		List<Question> transformationOutput = trans.transform(trees);
		
		List<String> decomposed = new ArrayList<String>();
		for(Question q: transformationOutput){
			decomposed.add(AnalysisUtilities.orginialSentence(q.getIntermediateTree().yield()));
		}
		
		return decomposed;
	}

}
