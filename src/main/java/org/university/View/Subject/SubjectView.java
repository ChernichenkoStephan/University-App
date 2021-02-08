package org.university.View.Subject;

import org.university.Model.Subject;
import org.university.View.TableView;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class SubjectView extends TableView {

    public SubjectView(Collection data) {
        super(data);
        name = "SubjectView";
        prepareGUI();
    }

    @Override
    protected void prepareGUI() {
        System.out.println("SubjectView prepareGUI");

        mainPanel.setLayout(new GridLayout(2,1, 10, 10));

        JPanel tablePanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        JPanel controlPanel = new JPanel();

        // adding it to JScrollPane
        table = setTable(data);

        // Initializing the JTable
        tablePanel.add(table);

        tablePanel.setLayout(new CardLayout());

        JButton create = new JButton("Create");
        JButton delete = new JButton("Delete");
        JButton update = new JButton("Update");
        JButton full = new JButton("Full course");
        JButton menu = new JButton("Menu");

        buttonsPanel.setLayout(new GridLayout(2,2));
        buttonsPanel.setPreferredSize(new Dimension(400, 200));
        buttonsPanel.setSize(400, 200);

        buttonsPanel.add(create);
        buttonsPanel.add(delete);
        buttonsPanel.add(update);
        buttonsPanel.add(full);
        buttonsPanel.add(menu);

        controlPanel.setLayout(new FlowLayout());

        JLabel  idLabel = new JLabel("ID: ", JLabel.CENTER);
        JLabel  nameLabel= new JLabel("Name: ", JLabel.RIGHT);
        JLabel  shortLabel = new JLabel("Short name: ", JLabel.RIGHT);
        JLabel  amountLabel = new JLabel("Hours amount: ", JLabel.RIGHT);

        JTextField idText = new JTextField(6);
        JTextField nameText = new JTextField(6);
        JTextField shortText = new JTextField(6);
        JTextField amountText = new JTextField(6);

        controlPanel.add(nameLabel);
        controlPanel.add(nameText);

        controlPanel.add(shortLabel);
        controlPanel.add(shortText);

        controlPanel.add(amountLabel);
        controlPanel.add(amountText);

        controlPanel.add(idLabel);
        controlPanel.add(idText);

        create.addActionListener(e -> {
            String name            = nameText.getText();
            String shortName       = shortText.getText();
            Integer hoursAmount    = open(amountText.getText());
            viewController.createSubject(name, shortName, hoursAmount);
        });

        delete.addActionListener(e -> {
            viewController.deleteSubject(open(idText.getText()));
        });

        update.addActionListener(e -> {
            Integer id             = open(idText.getText());
            String name            = nameText.getText();
            String shortName       = shortText.getText();
            Integer hoursAmount    = open(amountText.getText());
            viewController.updateSubject(id, name, shortName, hoursAmount);
        });

        menu.addActionListener(e -> {
            viewController.main();
        });

        full.addActionListener(e -> viewController.fullCourse());

        controlPanel.add(buttonsPanel);
        mainPanel.add(tablePanel);
        mainPanel.add(controlPanel);

    }

    private JScrollPane setTable(Collection<Subject> subjects) {
        JTable table = parse(subjects);
        JScrollPane stable = new JScrollPane(table);
        stable.setBounds(20,20, 800, 600);
        return stable;

    }

    private JTable parse(Collection<Subject> subjects) {
        String[] columnNames = { "ID", "Name", "Short name", "Hours amount" };
        Object[][] data = new Object[subjects.size()][5];

        int i = 0;
        for (Subject s : subjects) {
            Object[] row = {s.getId(), s.getName(), s.getShortName(), s.getHoursAmount()};
            data[i] = row;
            i++;
        }

        return new JTable(data, columnNames);
    }
}
