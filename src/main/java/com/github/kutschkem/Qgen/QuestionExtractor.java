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

import static org.uimafit.factory.CollectionReaderFactory.createCollectionReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.JCasFactory;
import org.uimafit.pipeline.SimplePipeline;

import com.github.kutschkem.Qgen.annotators.NEQuestionWordAnnotator;
import com.github.kutschkem.Qgen.annotators.OvergeneratorPermutation;
import com.github.kutschkem.Qgen.annotators.QuestionAnnotator;
import com.github.kutschkem.Qgen.annotators.SmithHeilmannTagger;
import com.github.kutschkem.Qgen.util.WikipediaUtil;

import de.tudarmstadt.ukp.dkpro.core.io.jwpl.WikipediaArticleReader;
import de.tudarmstadt.ukp.dkpro.core.io.jwpl.WikipediaReaderBase;
import de.tudarmstadt.ukp.dkpro.core.io.jwpl.WikipediaStandardReaderBase;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.tudarmstadt.ukp.wikipedia.api.DatabaseConfiguration;

public class QuestionExtractor {

	private AnalysisEngine tokenizer;
	private AnalysisEngine ner;
	private AnalysisEngine qwAssigner;
	private AnalysisEngine questionAssembler;
	private AnalysisEngine smithHeilmann;
	private QuestionRankerByParseProbability ranker;
	private JCas jcas;

	public QuestionExtractor() {
		try {
			tokenizer = AnalysisEngineFactory.createPrimitive(StanfordSegmenter.class);
			ner = AnalysisEngineFactory.createPrimitive(
					StanfordNamedEntityRecognizer.class,
					StanfordNamedEntityRecognizer.PARAM_LANGUAGE,
					"en",
					StanfordNamedEntityRecognizer.PARAM_VARIANT,
					"muc.7class.distsim.crf");
			qwAssigner = AnalysisEngineFactory.createPrimitive(NEQuestionWordAnnotator.class);
			questionAssembler = AnalysisEngineFactory.createPrimitive(QuestionAnnotator.class);
			ranker = new QuestionRankerByParseProbability(
					ClassLoader.getSystemResource("de/tudarmstadt/ukp/dkpro/core/stanfordnlp/lib/parser-en-pcfg.ser.gz"));
			smithHeilmann = AnalysisEngineFactory.createPrimitive(SmithHeilmannTagger.class);
			jcas = JCasFactory.createJCas();
			jcas.setDocumentLanguage("en");
		} catch (ResourceInitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UIMAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Question> extractFromWikipedia(String title)
			throws FileNotFoundException, IOException, UIMAException {
		DatabaseConfiguration dbconfig = WikipediaUtil.loadDbConfig();
		CollectionReader rdr = createCollectionReader(
				WikipediaArticleReader.class, WikipediaReaderBase.PARAM_HOST,
				dbconfig.getHost(), WikipediaReaderBase.PARAM_DB,
				dbconfig.getDatabase(), WikipediaReaderBase.PARAM_USER,
				dbconfig.getUser(), WikipediaReaderBase.PARAM_PASSWORD,
				dbconfig.getPassword(), WikipediaReaderBase.PARAM_LANGUAGE,
				dbconfig.getLanguage().toString(),
				WikipediaStandardReaderBase.PARAM_PAGE_ID_LIST,
				new String[] { title });
		SimplePipeline.runPipeline(rdr,
				getPipeline().toArray(new AnalysisEngine[0]));
		return ranker.rank(QuestionListConsumer.questions.get("run"));
	}

	public List<Question> extract(String text) throws UIMAException,
			IOException {
		jcas.reset();
		jcas.setDocumentText(text);

		SimplePipeline.runPipeline(jcas,
				getPipeline().toArray(new AnalysisEngine[] {}));
		return ranker.rank(QuestionListConsumer.questions.get("run"));
	}

	private List<AnalysisEngine> getPipeline()
			throws ResourceInitializationException {

		AnalysisEngine dumper = AnalysisEngineFactory.createPrimitive(QuestionListConsumer.class);

		return Arrays.asList(tokenizer, ner, qwAssigner, questionAssembler, smithHeilmann, dumper);
	}

}
