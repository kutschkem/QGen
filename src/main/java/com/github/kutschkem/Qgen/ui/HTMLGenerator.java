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
