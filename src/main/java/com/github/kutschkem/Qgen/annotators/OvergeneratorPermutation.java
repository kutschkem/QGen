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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;

public class OvergeneratorPermutation {

	public List<String> overgenerate(String question) {
		List<String> result = new ArrayList<String>();

		Iterator<CoreLabel> it = PTBTokenizerFactory.newCoreLabelTokenizerFactory(
				null).getIterator(new StringReader(question));
		List<CoreLabel> tokens = new ArrayList<CoreLabel>();

		while (it.hasNext()) {
			tokens.add(it.next());
		}

		List<String> permutations = permutations(tokens);

		for (String p : permutations) {
			if (!p.equals(question) && p.matches("[wW].+\\?")) {
				result.add(p);
			}
		}
		return result;

	}

	/**
	 * generates all permutations of a sentence
	 * 
	 * @param tokens
	 * @return
	 */
	private List<String> permutations(List<CoreLabel> tokens) {
		List<List<CoreLabel>> permutations = permute(tokens);
		List<String> result = new ArrayList<String>();

		for (List<CoreLabel> l : permutations) {
			result.add(joinWithWhiteSpaces(l));
		}

		System.out.println(result.size());
		return result;
	}

	private List<List<CoreLabel>> permute(List<CoreLabel> tokens) {
		List<List<CoreLabel>> result = new ArrayList<List<CoreLabel>>();

		if (tokens.size() <= 1) {
			result.add(tokens);
			return result;
		}

		for (int i = 0; i < tokens.size(); i++) {
			List<CoreLabel> lHelper = new ArrayList<CoreLabel>(tokens);
			lHelper.remove(i);
			for (List<CoreLabel> l2 : permute(lHelper)) {
				List<CoreLabel> l = new ArrayList<CoreLabel>();
				l.add(tokens.get(i));
				l.addAll(l2);
				result.add(l);
			}
		}
		return result;
	}

	private String joinWithWhiteSpaces(List<CoreLabel> labels) {
		StringBuilder buf = new StringBuilder();

		for (CoreLabel l : labels) {
			buf.append(' ');
			buf.append(l.word());
		}

		String result = buf.toString().trim().replaceAll("\\s+", " ");
		result = result.replaceAll("\\s(?=\\p{Punct})", ""); // remove leading whitespaces of punctuation
		result = result.replaceFirst("^.", result.substring(0, 1).toUpperCase()); //First letter uppercase

		return result;
	}

}
