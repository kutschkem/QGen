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

import java.util.List;

import com.github.kutschkem.Qgen.Question;

public class QuestionTableModelWithCheckButtons extends QuestionTableModel {
	
	boolean[] selected;

	@Override
	public void fill(List<Question> questions) {
		super.fill(questions);
		selected = new boolean[questions.size()];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0) return selected[rowIndex];
		return super.getValueAt(rowIndex, columnIndex-1);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(columnIndex == 0){
			selected[rowIndex] = (Boolean) aValue;
			
		}else{
		super.setValueAt(aValue, rowIndex, columnIndex-1);
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 0) return true;
		return super.isCellEditable(rowIndex, columnIndex-1);
	}

	@Override
	public int getColumnCount() {
		return super.getColumnCount() +1;
	}

	@Override
	public String getColumnName(int column) {
		if(column == 0){
			return "";
		}
		return super.getColumnName(column - 1);
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		if(arg0 == 0) return Boolean.class;
		return super.getColumnClass(arg0 -1);
	}



}
