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
