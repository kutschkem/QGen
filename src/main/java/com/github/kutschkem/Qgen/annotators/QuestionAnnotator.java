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

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.util.JCasUtil;

import com.github.kutschkem.Qgen.annotations.Question;
import com.github.kutschkem.Qgen.annotations.Questionword;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

/**
 * this annotator transforms Questionword annotations to Question annotations. It needs sentence
 * segmenting (and question word annotation, of course) as preprocessing step(s).
 * 
 * @author Michael Kutschke
 */
public class QuestionAnnotator extends JCasAnnotator_ImplBase {

	@Override
	public void process(JCas cas) throws AnalysisEngineProcessException {
		OvergeneratorPermutation perm = new OvergeneratorPermutation();
		for (Sentence s : JCasUtil.select(cas, Sentence.class)) {
			for (Questionword qw : JCasUtil.selectCovered(cas,
					Questionword.class, s)) {

				String questionword = qw.getQuestionword();

				String sentence = s.getCoveredText();
				int from = qw.getBegin() - s.getBegin();
				int to = qw.getEnd() - s.getBegin();

				String quest = sentence.substring(0, from) + questionword
						+ sentence.substring(to);

				quest = quest.replaceAll("\\.$", "?");

				for (String overgenerated : perm.overgenerate(quest)) {

					Question question = new Question(cas);
					question.setBegin(s.getBegin());
					question.setEnd(s.getEnd());
					question.setAnswer(qw.getCoveredText());

					question.setText(overgenerated);
					question.addToIndexes();
				}
			}
		}

	}

}
