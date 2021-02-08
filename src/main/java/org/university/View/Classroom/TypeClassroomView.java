package org.university.View.Classroom;

import org.university.Model.Classroom;
import org.university.View.TableView;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class TypeClassroomView extends TableView {

    public TypeClassroomView(Collection data) {
        super(data);
        name = "TypeClassroomView";
        prepareGUI();
    }

    @Override
    protected void prepareGUI() {
        System.out.println("TypeClassroomView prepareGUI");

        mainPanel.setLayout(new GridLayout(2,1));

        JPanel tablePanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        // adding it to JScrollPane
        table = setTable(data);

        // Initializing the JTable
        tablePanel.add(table);
        tablePanel.setLayout(new CardLayout());

        JButton menu = new JButton("Menu");
        JButton back = new JButton("Back");
        menu.addActionListener(e -> viewController.main());
        back.addActionListener(e -> viewController.classroom());
        buttonsPanel.add(back);
        buttonsPanel.add(menu);

        mainPanel.add(tablePanel);
        mainPanel.add(buttonsPanel);
    }

    private JScrollPane setTable(Collection<Classroom> classrooms) {
        JTable table = parse(classrooms);
        JScrollPane stable = new JScrollPane(table);
        stable.setBounds(20,20, 800, 600);
        return stable;

    }

    private JTable parse(Collection<Classroom> classrooms) {
        String[] columnNames = { "ID", "Building", "Floor", "Type", "Capacity" };
        Object[][] data = new Object[classrooms.size()][5];

        int i = 0;
        for (Classroom c : classrooms) {
            Object[] row = {c.getId(), c.getBuilding(), c.getFloor(), c.getType(), c.getCapacity()};
            data[i] = row;
            i++;
        }

        return new JTable(data, columnNames);
    }
}
