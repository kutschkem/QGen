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

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.uimafit.component.JCasAnnotator_ImplBase;
import org.uimafit.util.JCasUtil;

import com.github.kutschkem.Qgen.annotations.Questionword;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.Date;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.Location;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.Money;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.Organization;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.Person;

public class NEQuestionWordAnnotator extends JCasAnnotator_ImplBase {

	@Override
	public void process(JCas cas) throws AnalysisEngineProcessException {
		Collection<NamedEntity> entities = JCasUtil.select(cas,
				NamedEntity.class);

		for (NamedEntity entity : entities) {
			Questionword qw = new Questionword(cas);

			qw.setBegin(entity.getBegin());
			qw.setEnd(entity.getEnd());
			if (entity.getValue().equals("DATE")) {
				qw.setQuestionword("when");
			}
			if (entity.getValue().equals("PERSON")) {
				qw.setQuestionword("who");
			}
			if (entity.getValue().equals("LOCATION")) {
				qw.setQuestionword("where");
			}
			if (entity.getValue().equals("ORGANIZATION")) {
				qw.setQuestionword("which organisation");
			}
			if (entity.getValue().equals("MONEY")) {
				qw.setQuestionword("how much money");
			}
			// TODO figure out how to use Time and Percent annotations
			qw.addToIndexes();
		}

	}

}
