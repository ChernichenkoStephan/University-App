package org.university.View.Request;

import org.university.View.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class RequestView extends View {

    private final String labelText;
    private final Vector<String> columnNames;
    private final Vector<Vector<Object>> data;

    public RequestView(String label, Vector<String> columnNames, Vector<Vector<Object>> data) {
        super();
        this.columnNames = columnNames;
        this.data = data;
        this.labelText = label;
        prepareGUI();
    }

    @Override
    protected void prepareGUI() {
        System.out.println(labelText + " prepareGUI");

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel tablePanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        tablePanel.setLayout(new CardLayout());

        JLabel mainLabel = new JLabel(labelText, SwingConstants.CENTER);

        // adding it to JScrollPane
        JScrollPane table = makeTable(columnNames, data);

        // Initializing the JTable
        table.setBounds(30, 40, 800, 400);

        tablePanel.add(table);

        JButton menu = new JButton("Menu");
        JButton back = new JButton("Back");
        menu.addActionListener(e -> viewController.main());
        back.addActionListener(e -> viewController.main());
        buttonsPanel.add(back);
        buttonsPanel.add(menu);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridy = 0;
        c.insets = new Insets(5, 0, 5, 0);
        mainPanel.add(mainLabel, c);
        c.ipady = 20;
        c.gridy = 1;
        mainPanel.add(tablePanel, c);
        c.ipady = 5;
        c.gridy = 2;
        mainPanel.add(buttonsPanel, c);
    }

    protected JScrollPane makeTable(Vector<String> columnNames, Vector<Vector<Object>> data) {
        DefaultTableModel dm = new DefaultTableModel(data, columnNames);
        JTable contentTable = new JTable(dm);
        return new JScrollPane(contentTable);
    }
}
