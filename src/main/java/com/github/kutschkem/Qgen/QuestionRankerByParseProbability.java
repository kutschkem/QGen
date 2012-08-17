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

import static org.apache.commons.io.IOUtils.closeQuietly;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.lexparser.LexicalizedParserQuery;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.ScoredObject;

public class QuestionRankerByParseProbability {

	LexicalizedParser parser;

	public QuestionRankerByParseProbability(URL parserModel) throws IOException {

		parser = getParserDataFromSerializedFile(parserModel);
	}

	public List<Question> rank(List<Question> questions) {

		System.out.println("afterPipeline:" + questions.size());

		final Map<Question, Double> scores = new HashMap<Question, Double>();
		for (Question q : questions) {
			List<HasWord> tokens = new DocumentPreprocessor(new StringReader(
					q.Question)).iterator().next();

			LexicalizedParserQuery query = parser.parserQuery();
			query.parse(tokens);
			scores.put(q, average(query.getKBestPCFGParses(3)));
		}

		List<Question> result = new ArrayList<Question>(questions);

		Collections.sort(result, new Comparator<Question>() {

			public int compare(Question o1, Question o2) {
				return -scores.get(o1).compareTo(scores.get(o2));
			}

		});

		for (Question q : result) {
			System.out.println(q.Question + " " + scores.get(q));
		}

		return result;
	}

	private Double average(List<ScoredObject<Tree>> kBestPCFGParses) {
		double result = 0;
		int norm = 0;
		for (ScoredObject<Tree> s : kBestPCFGParses) {
			if (s.object().firstChild().label().value().equals("SBARQ")) {
				result += s.score();
				norm++;
			}
		}
		if (result == 0) return Double.NEGATIVE_INFINITY;
		return result / norm;
	}

	/**
	 * Load the parser from the given location within the classpath.
	 * 
	 * @param aUrl
	 *            URL of the parser file.
	 */
	// copied from DKPro's StanfordParser
	private LexicalizedParser getParserDataFromSerializedFile(URL aUrl)
			throws IOException {
		ObjectInputStream in;
		InputStream is = null;
		try {
			is = aUrl.openStream();

			if (aUrl.toString().endsWith(".gz")) {
				// it's faster to do the buffering _outside_ the gzipping as
				// here
				in = new ObjectInputStream(new BufferedInputStream(
						new GZIPInputStream(is)));
			} else {
				in = new ObjectInputStream(new BufferedInputStream(is));
			}
			LexicalizedParser pd = LexicalizedParser.loadModel(in);
			// Numberer.setNumberers(pd.numbs); // will happen later in
			// makeParsers()
			in.close();
			return pd;
		} finally {
			closeQuietly(is);
		}
	}

}
