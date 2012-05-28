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
        Collection<NamedEntity> entities = JCasUtil.select(cas, NamedEntity.class);

        for (NamedEntity entity : entities) {
            Questionword qw = new Questionword(cas);

            qw.setBegin(entity.getBegin());
            qw.setEnd(entity.getEnd());
            if (entity instanceof Date) {
                qw.setQuestionword("when");
            }
            if (entity instanceof Person) {
                qw.setQuestionword("who");
            }
            if (entity instanceof Location) {
                qw.setQuestionword("where");
            }
            if (entity instanceof Organization) {
                qw.setQuestionword("which organisation");
            }
            if (entity instanceof Money) {
                qw.setQuestionword("how much money");
            }
            // TODO figure out how to use Time and Percent annotations
            qw.addToIndexes();
        }

    }

}
