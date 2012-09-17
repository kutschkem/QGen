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

import arkref.parsestuff.AnalysisUtilities;

import edu.cmu.ark.GlobalProperties;
import edu.cmu.ark.InitialTransformationStep;
import edu.cmu.ark.Question;
import edu.cmu.ark.QuestionRanker;
import edu.cmu.ark.QuestionTransducer;
import edu.stanford.nlp.trees.Tree;

public class QuestionAsker {
	
	QuestionTransducer qtd = new QuestionTransducer();
	InitialTransformationStep trans = new InitialTransformationStep();
	QuestionRanker ranker = new QuestionRanker();
	private final String rankerModel = "models/linear-regression-ranker-reg500.ser.gz";
	
	static{
		GlobalProperties.loadProperties(ClassLoader.getSystemResource("config/QuestionTransducer.properties").getFile());
	}
	
	public QuestionAsker(){
		qtd.setAvoidPronounsAndDemonstratives(true);
		trans.setDoPronounNPC(true);
		trans.setDoNonPronounNPC(true);
		ranker.loadModel(ClassLoader.getSystemResource(rankerModel).getFile());
	}
	
	public List<Question> ask(List<String> sentences){
		
		List<Tree> parses = new ArrayList<Tree>();
		for(String sentence: sentences){
		Tree parse = AnalysisUtilities.getInstance().parseSentence(sentence).parse;
		parses.add(parse);
		}
		
		//step 1 transformation
		List<Question> transformationOutput = trans.transform(parses);
		
		//step 2 question transducer
		List<Question> outputQuestionList = new ArrayList<Question>();
		
		System.out.println();
		int i = 0;
		for(Question q: transformationOutput){
			qtd.generateQuestionsFromParse(q);
			outputQuestionList.addAll(qtd.getQuestions());
			i++;
			System.out.print("\rTransducer Progress: " + i + "/" + transformationOutput.size());
		}
		System.out.println();
		
		//remove duplicates
		QuestionTransducer.removeDuplicateQuestions(outputQuestionList);
		
		//step 3 ranking
		ranker.scoreGivenQuestions(outputQuestionList);
//		boolean doStemming = true;
//		QuestionRanker.adjustScores(outputQuestionList, inputTrees, avoidFreqWords, preferWH, downweightPronouns, doStemming);
		QuestionRanker.sortQuestions(outputQuestionList, false);
		
		return outputQuestionList;
	}


}
