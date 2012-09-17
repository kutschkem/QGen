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
