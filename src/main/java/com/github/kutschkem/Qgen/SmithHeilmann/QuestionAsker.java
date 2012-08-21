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
		
		for(Question q: transformationOutput){
			qtd.generateQuestionsFromParse(q);
			outputQuestionList.addAll(qtd.getQuestions());
		}
		
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
