package org.university.View.Class;

import org.university.Model.Event;
import org.university.View.TableView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

public class ClassView extends TableView {

    private final Collection teachers;
    private final Collection departments;
    private final Collection groups;
    private final Collection classrooms;
    private final Collection subjects;

    public ClassView(Collection data,
                     Collection teachers,
                     Collection departments,
                     Collection groups,
                     Collection classrooms,
                     Collection subjects)
    {
        super(data);

        this.teachers = teachers;
        this.departments = departments;
        this.groups = groups;
        this.classrooms = classrooms;
        this.subjects = subjects;

        name = "ClassView";
        prepareGUI();
    }

    @Override
    protected void prepareGUI() {
        System.out.println("ClassView prepareGUI");

        mainPanel.setLayout(new GridLayout(2,1, 10, 10));

        JPanel tablePanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        JPanel controlPanel = new JPanel();

        // JCheckBox(String text, Icon icon, boolean selected)
        JCheckBox isDeletable = new JCheckBox("Delete fuse", true);
        JComboBox<Integer> idBox = new JComboBox<Integer>(makeIdsVector(data));

        controlPanel.add(isDeletable);
        controlPanel.add(idBox);

        // adding it to JScrollPane
        table = setTable(data);

        // Initializing the JTable
        tablePanel.add(table);

        tablePanel.setLayout(new CardLayout());

        JButton create = new JButton("Create");
        JButton delete = new JButton("Delete");
        JButton update = new JButton("Update");
        JButton menu = new JButton("Menu");

        buttonsPanel.setLayout(new GridLayout(2,2));
        buttonsPanel.setPreferredSize(new Dimension(400, 200));
        buttonsPanel.setSize(400, 200);

        buttonsPanel.add(create);
        buttonsPanel.add(delete);
        buttonsPanel.add(update);
        buttonsPanel.add(menu);

        create.addActionListener(e -> viewController.eventCreate(teachers, departments, groups, classrooms, subjects));
        update.addActionListener(e -> viewController.eventUpdate(teachers, departments, groups, classrooms, subjects));
        menu.addActionListener(e -> viewController.main());


        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isDeletable.isSelected()) {
                    Integer selectedIndex =  idBox.getSelectedIndex();
                    Integer id = idBox.getItemAt(selectedIndex);
                    viewController.deleteEvent(id);
                }
            }
        });

        mainPanel.add(tablePanel);
        controlPanel.add(buttonsPanel);
        mainPanel.add(controlPanel);
    }

    private JScrollPane setTable(Collection<Event> events) {
        JTable table = parse(events);
        JScrollPane stable = new JScrollPane(table);
        stable.setBounds(20,20, 800, 600);
        return stable;

    }

    private JTable parse(Collection<Event> events) {
        String[] columnNames = { "ID", "Teacher", "Department", "Group", "Classroom", "Subject", "Time", "Date", "Type" };
        Object[][] data = new Object[events.size()][9];

        int i = 0;
        for (Event e : events) {
            Object[] row = {e.getId(),
                    e.getTeacher().getLastName(),
                    e.getDepartment().getShortName(),
                    e.getGroup().getId(),
                    e.getClassroom().getId(),
                    e.getSubject().getShortName(),
                    e.getTime(),
                    e.getDate(),
                    e.getType()
            };
            data[i] = row;
            i++;
        }

        return new JTable(data, columnNames);
    }

    private Vector<Integer> makeIdsVector(Collection<Event> events) {
        Vector<Integer> res = new Vector<>();
        for (Event e : events) {
            res.add(e.getId());
        }
        return res;
    }
}
