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
import org.uimafit.component.ViewCreatorAnnotator;
import org.uimafit.util.JCasUtil;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import edu.cmu.ark.AnalysisUtilities;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.trees.tregex.tsurgeon.Tsurgeon;
import edu.stanford.nlp.trees.tregex.tsurgeon.TsurgeonPattern;

public class SimpleSentenceDecompositionAnnotator extends JCasAnnotator_ImplBase {
	
	public static final String VIEW_NAME = "Simple_decomposed_view";

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		StringBuilder bldr = new StringBuilder();
		for(String s: decompose(aJCas.getDocumentText())){
			bldr.append(s);
		}
		
		JCas view = ViewCreatorAnnotator.createViewSafely(aJCas, VIEW_NAME);
		if(JCasUtil.exists(aJCas, DocumentMetaData.class)){
		DocumentMetaData.copy(aJCas, view);
		}
		view.setDocumentText(bldr.toString());
		view.setDocumentLanguage(aJCas.getDocumentLanguage());
	}

	private List<String> decompose(String documentText) {
		List<Tree> trees = new ArrayList<Tree>();
		for(String sentence : AnalysisUtilities.getSentences(documentText)){
			trees.add(AnalysisUtilities.getInstance().parseSentence(sentence).parse);
		}
		
		List<String> result = new ArrayList<String>();
		
		for(Tree t: trees){
			TregexPattern p = TregexPattern.compile("ROOT << (NP=np $++ VP=vp) ");
			TregexMatcher m = p.matcher(t);
			while(m.find()){
				Tree np = m.getNode("np");
				Tree vp = m.getNode("vp");

					Tree np2 = np.deepCopy();
					TregexPattern p2 = TregexPattern.compile("NP << (/^S.*/=sbarq ?. /,/=c1 ?, /,/=c2)");
					List<TsurgeonPattern> ps = new ArrayList<TsurgeonPattern>();
					ps.add(Tsurgeon.parseOperation("prune sbarq"));
					ps.add(Tsurgeon.parseOperation("prune c1"));
					ps.add(Tsurgeon.parseOperation("prune c2"));
				
					Tsurgeon.processPattern(p2, Tsurgeon.collectOperations(ps), np2);
					np = np2;

				Tree newTree = Tree.valueOf("(S "+ np + vp + "(. .))");
				result.add(AnalysisUtilities.orginialSentence(newTree.yield()));
			}
			
		}

		return result;
	}

}
