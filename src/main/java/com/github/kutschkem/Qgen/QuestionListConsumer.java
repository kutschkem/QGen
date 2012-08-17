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

package com.github.kutschkem.Qgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasConsumer_ImplBase;
import org.uimafit.descriptor.ConfigurationParameter;
import org.uimafit.factory.ConfigurationParameterFactory;
import org.uimafit.util.JCasUtil;

public class QuestionListConsumer extends JCasConsumer_ImplBase {

	public static Map<String, List<Question>> questions = new HashMap<String, List<Question>>();

	public static final String PARAM_RUN_ID = ConfigurationParameterFactory.createConfigurationParameterName(
			QuestionListConsumer.class, "runId");

	@ConfigurationParameter(defaultValue = "run")
	private String runId;

	@Override
	public void process(JCas arg0) throws AnalysisEngineProcessException {
		List<Question> questionList = new ArrayList<Question>();
		for (com.github.kutschkem.Qgen.annotations.Question q : JCasUtil.select(
				arg0, com.github.kutschkem.Qgen.annotations.Question.class)) {
			Question question = new Question();
			question.original_text = q.getCoveredText();
			question.Question = q.getText();
			question.answer = q.getAnswer();

			questionList.add(question);
		}
		questions.put(runId, questionList);
	}

	public void clear() {
		questions.clear();
	}

}
