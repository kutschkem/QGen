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

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.github.kutschkem.Qgen.Question;

public class QuestionTableModel extends AbstractTableModel {

	protected List<Question> table = new ArrayList<Question>();

	public void fill(List<Question> questions) {

		table = new ArrayList<Question>(questions);
		fireTableDataChanged();
	}

	public int getRowCount() {
		return table.size();
	}

	public int getColumnCount() {
		return 3;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Question q = table.get(rowIndex);
		switch(columnIndex){
		case 0 : return q.original_text;
		case 1 : return q.Question;
		case 2 : return q.answer;
		default: throw new IndexOutOfBoundsException("invalid column index " + columnIndex);
		}
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
	
	public List<Question> getQuestions(){
		return table;
	}

}
