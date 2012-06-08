package com.github.kutschkem.Qgen.ui;

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

public class GUI {

    private JFrame frame;
    private JTextField txtInput;
    private JTable table;

    private QuestionTableModel tableModel = new QuestionTableModel();

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
        frame.getContentPane().setLayout(springLayout);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 0, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, 0, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, tabbedPane, 0, SpringLayout.EAST, frame.getContentPane());
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
        sl_panel.putConstraint(SpringLayout.NORTH, txtInput, 0, SpringLayout.NORTH, panel);
        sl_panel.putConstraint(SpringLayout.WEST, txtInput, 110, SpringLayout.WEST, panel);
        sl_panel.putConstraint(SpringLayout.SOUTH, txtInput, 39, SpringLayout.NORTH, panel);
        sl_panel.putConstraint(SpringLayout.EAST, txtInput, -109, SpringLayout.EAST, panel);
        panel.add(txtInput);
        txtInput.setColumns(10);

        table = new JTable();
        sl_panel.putConstraint(SpringLayout.EAST, table, 0, SpringLayout.EAST, panel);
        table.setModel(tableModel);
        sl_panel.putConstraint(SpringLayout.NORTH, table, 0, SpringLayout.SOUTH, txtInput);
        sl_panel.putConstraint(SpringLayout.WEST, table, 0, SpringLayout.WEST, panel);
        sl_panel.putConstraint(SpringLayout.SOUTH, table, 0, SpringLayout.SOUTH, panel);
        panel.add(table);

        JLabel lblEnterText = new JLabel("Enter text:");
        sl_panel.putConstraint(SpringLayout.NORTH, lblEnterText, 12, SpringLayout.NORTH, txtInput);
        sl_panel.putConstraint(SpringLayout.EAST, lblEnterText, -20, SpringLayout.WEST, txtInput);
        panel.add(lblEnterText);
    }
}
