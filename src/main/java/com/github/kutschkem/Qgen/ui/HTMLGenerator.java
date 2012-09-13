package com.github.kutschkem.Qgen.ui;

import java.util.List;

import com.github.kutschkem.Qgen.Question;

public class HTMLGenerator {

	public static String generateAnswerSheet(List<Question> questions) {
		StringBuilder bldr = new StringBuilder();
		bldr.append("<html><body>");
		
		for(Question q: questions){
			bldr.append(q.Question);
			bldr.append("<br/>\n- ");
			bldr.append(q.answer);
			bldr.append("<br/><br/>\n");			
		}
		
		bldr.append("</body></html>");
		return bldr.toString();
	}

	public static String generateQuestionSheet(List<Question> questions) {
		StringBuilder bldr = new StringBuilder();
		bldr.append("<html><body>");
		
		for(Question q: questions){
			bldr.append(q.Question);
			bldr.append("<br/>\n");
			bldr.append("___________________________________________________<br/>\n___________________________________________________");
			bldr.append("<br/><br/>\n");			
		}
		
		bldr.append("</body></html>");
		return bldr.toString();
	}

}
