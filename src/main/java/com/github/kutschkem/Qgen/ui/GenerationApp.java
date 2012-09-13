package com.github.kutschkem.Qgen.ui;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import org.apache.uima.UIMAException;
import org.apache.uima.util.FileUtils;

import com.github.kutschkem.Qgen.QuestionExtractor;
import com.github.kutschkem.Qgen.ui.model.QuestionTableModelWithCheckButtons;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class GenerationApp {

	private JFrame frame;
	private JTable table;
	private JTextPane txtpane;
	QuestionExtractor extractor = new QuestionExtractor();
	
	QuestionTableModelWithCheckButtons tModel = new QuestionTableModelWithCheckButtons();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenerationApp window = new GenerationApp();
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
	public GenerationApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 761, 675);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_Text = new JPanel();
		tabbedPane.addTab("Text", null, panel_Text, null);
		panel_Text.setLayout(new BorderLayout(0, 0));
		
		txtpane = new JTextPane();
		panel_Text.add(txtpane);
		
		JPanel panel_3 = new JPanel();
		panel_Text.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnGenerateQuestions = new JButton("Generate Questions");
		btnGenerateQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = txtpane.getText();
				try {
					tModel.fill(extractor.extract(text));
				} catch (UIMAException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_3.add(btnGenerateQuestions);
		
		JProgressBar progressBar = new JProgressBar();
		panel_3.add(progressBar);
		
		JPanel panel_Questions = new JPanel();
		tabbedPane.addTab("Questions", null, panel_Questions, null);
		panel_Questions.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_OverTable = new JPanel();
		panel_Questions.add(panel_OverTable,BorderLayout.NORTH);
		
		JButton btnSelectAll = new JButton("Select All");
		btnSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < tModel.getRowCount(); i++){
					tModel.setValueAt(true, i, 0);
				}
				tModel.fireTableDataChanged();
			}
		});
		panel_OverTable.add(btnSelectAll);
		
		JButton btnDeselectAll = new JButton("Deselect All");
		btnDeselectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < tModel.getRowCount(); i++){
					tModel.setValueAt(false, i, 0);
				}
				tModel.fireTableDataChanged();
			}
		});
		panel_OverTable.add(btnDeselectAll);
		
		JPanel panel_Table = new JPanel();
		panel_Questions.add(panel_Table, BorderLayout.SOUTH);
		
		JButton btnQSheetGen = new JButton("Generate Question sheet");
		btnQSheetGen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileFilter(){

					@Override
					public boolean accept(File arg0) {
						return arg0.isDirectory() || arg0.getName().matches(".*\\.[hH][tT][mM][lL]");
					}

					@Override
					public String getDescription() {
						return "HTML files";
					}});
				int returnVal = fc.showSaveDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					//TODO allow different formats
					String out = HTMLGenerator.generateQuestionSheet(tModel.getQuestions());
					try {
						FileUtils.saveString2File(out, file);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(frame, "Saving was unsuccesful due to error: " + e1.getMessage(), "Saving error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		panel_Table.add(btnQSheetGen);
		
		JButton btnAnswerGen = new JButton("Generate Answer sheet");
		btnAnswerGen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileFilter(){

					@Override
					public boolean accept(File arg0) {
						return arg0.isDirectory() || arg0.getName().matches(".*\\.[hH][tT][mM][lL]");
					}

					@Override
					public String getDescription() {
						return "HTML files";
					}});
				int returnVal = fc.showSaveDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					//TODO allow different formats
					String out = HTMLGenerator.generateAnswerSheet(tModel.getQuestions());
					try {
						FileUtils.saveString2File(out, file);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(frame, "Saving was unsuccesful due to error: " + e1.getMessage(), "Saving error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		panel_Table.add(btnAnswerGen);
		
		JButton btnPreview = new JButton("Preview");
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JScrollPane pane = new JScrollPane();
				JEditorPane previewComponent = new JEditorPane();
				previewComponent.setContentType("text/html");
				previewComponent.setText(HTMLGenerator.generateAnswerSheet(tModel.getQuestions()));
				pane.setViewportView(previewComponent);
				JOptionPane.showMessageDialog(frame, pane, "Preview", JOptionPane.PLAIN_MESSAGE);
			}
		});
		panel_Table.add(btnPreview);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_Questions.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(tModel);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChoose = new JFileChooser();
				int returnVal = fileChoose.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fileChoose.getSelectedFile();
					try {
						txtpane.setText(FileUtils.file2String(file));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); //TODO dialog that asks to save
			}
		});
		mnFile.add(mntmClose);
	}

	public JTextPane getTxtpane() {
		return txtpane;
	}
}
