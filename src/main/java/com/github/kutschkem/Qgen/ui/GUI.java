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

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import org.apache.uima.UIMAException;

import com.github.kutschkem.Qgen.QuestionExtractor;
import com.github.kutschkem.Qgen.ui.model.QuestionTableModel;

import de.tudarmstadt.ukp.wikipedia.api.exception.WikiApiException;
import javax.swing.JScrollPane;

public class GUI {

	private JFrame frame;
	private JTextField txtInput;
	private JTable table;

	private QuestionTableModel tableModel = new QuestionTableModel();
	private JTextField textField;
	private final JScrollPane scrollPane = new JScrollPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.SOUTH, tabbedPane);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 0,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 0,
				SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, 100,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, 0,
				SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Question Entering", null, panel, null);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);

		txtInput = new JTextField();
		txtInput.addActionListener(new ActionListener() {
			QuestionExtractor questionExtractor = new QuestionExtractor();

			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						try {
							tableModel.fill(questionExtractor.extract(txtInput.getText()));
						} catch (UIMAException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				});

			}
		});
		sl_panel.putConstraint(SpringLayout.NORTH, txtInput, 0,
				SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, txtInput, 110,
				SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, txtInput, 39,
				SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, txtInput, -109,
				SpringLayout.EAST, panel);
		panel.add(txtInput);
		txtInput.setColumns(10);

		JLabel lblEnterText = new JLabel("Enter text:");
		sl_panel.putConstraint(SpringLayout.NORTH, lblEnterText, 12,
				SpringLayout.NORTH, txtInput);
		sl_panel.putConstraint(SpringLayout.EAST, lblEnterText, -20,
				SpringLayout.WEST, txtInput);
		panel.add(lblEnterText);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Wikipedia", null, panel_1, null);
		SpringLayout sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			QuestionExtractor questionExtractor = new QuestionExtractor();

			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						try {
							tableModel.fill(questionExtractor.extractFromWikipedia(textField.getText()));
						} catch (UIMAException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (WikiApiException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});
			}
		});
		sl_panel_1.putConstraint(SpringLayout.NORTH, textField, 0,
				SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, textField, 110,
				SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, textField, 39,
				SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, textField, -109,
				SpringLayout.EAST, panel_1);
		textField.setColumns(10);
		panel_1.add(textField);

		JLabel label = new JLabel("Enter text:");
		sl_panel_1.putConstraint(SpringLayout.NORTH, label, 12,
				SpringLayout.NORTH, textField);
		sl_panel_1.putConstraint(SpringLayout.EAST, label, -20,
				SpringLayout.WEST, textField);
		panel_1.add(label);
		
				table = new JTable();
				springLayout.putConstraint(SpringLayout.NORTH, table, 0,
						SpringLayout.NORTH, scrollPane);
				springLayout.putConstraint(SpringLayout.WEST, table, 0,
						SpringLayout.WEST, scrollPane);
				springLayout.putConstraint(SpringLayout.SOUTH, table, 0,
						SpringLayout.SOUTH, scrollPane);
				springLayout.putConstraint(SpringLayout.EAST, table, 0,
						SpringLayout.EAST, scrollPane);
				sl_panel.putConstraint(SpringLayout.NORTH, table, 0,
						SpringLayout.SOUTH, panel);
				sl_panel.putConstraint(SpringLayout.WEST, table, 0, SpringLayout.WEST,
						panel);
				sl_panel.putConstraint(SpringLayout.SOUTH, table, 0,
						SpringLayout.SOUTH, frame.getContentPane());
				sl_panel.putConstraint(SpringLayout.EAST, table, 0, SpringLayout.EAST,
						panel);
				scrollPane.setViewportView(table);
				table.setModel(tableModel);
		frame.getContentPane().add(scrollPane);
	}
}
