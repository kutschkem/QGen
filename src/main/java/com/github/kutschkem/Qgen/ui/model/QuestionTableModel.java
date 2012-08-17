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

package com.github.kutschkem.Qgen.ui.model;

import java.util.Collection;

import javax.swing.table.AbstractTableModel;

import com.github.kutschkem.Qgen.Question;

public class QuestionTableModel extends AbstractTableModel {

	private String[][] table = new String[][] {};

	public void fill(Collection<Question> questions) {

		table = new String[questions.size()][3];

		int i = 0;
		for (Question q : questions) {
			table[i][0] = q.original_text;
			table[i][1] = q.Question;
			table[i][2] = q.answer;
			i++;
		}
		fireTableDataChanged();
	}

	public int getRowCount() {
		return table.length;
	}

	public int getColumnCount() {
		return 3;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return table[rowIndex][columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Text";
		case 1:
			return "Question";
		case 2:
			return "Answer";
		default:
			return super.getColumnName(column);
		}
	}

}
