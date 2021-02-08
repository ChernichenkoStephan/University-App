package org.university.View.Teacher;

import org.university.Model.Teacher;
import org.university.View.TableView;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class TeachersView extends TableView {

    public TeachersView(Collection data) {
        super(data);
        name = "TeachersView";
        prepareGUI();
    }

    @Override
    protected void prepareGUI() {
        System.out.println("TeachersView prepareGUI");

        mainPanel.setLayout(new GridLayout(3,1));

        JPanel tablePanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        // adding it to JScrollPane
        table = parse(data);

        // Initializing the JTable
        table.setBounds(30, 40, 800, 400);

        tablePanel.add(table);

        JButton menu = new JButton("Menu");
        JButton back = new JButton("Back");
        menu.addActionListener(e -> viewController.main());
        back.addActionListener(e -> viewController.teacher());
        buttonsPanel.add(back);
        buttonsPanel.add(menu);

        mainPanel.add(tablePanel);
        mainPanel.add(buttonsPanel);
    }

    protected JScrollPane parse(Collection<Teacher> teachers) {
        String[] columnNames = { "ID", "Name", "Lastname", "Surname", "Position", "Experience" };
        Object[][] data = new Object[teachers.size()][6];
        int i = 0;
        for (Teacher t : teachers) {
            Object[] row = {t.getId(), t.getName(), t.getLastName(), t.getMiddleName(), t.getPosition(), t.getExperience()};
            data[i] = row;
            i++;
        }
        return new JScrollPane(new JTable(data, columnNames));
    }
}
