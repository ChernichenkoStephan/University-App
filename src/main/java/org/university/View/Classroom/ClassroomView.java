package org.university.View.Classroom;

import org.university.Model.Classroom;
import org.university.View.TableView;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ClassroomView extends TableView {

    public ClassroomView(Collection data) {
        super(data);
        name = "ClassroomView";
        prepareGUI();
    }

    @Override
    protected void prepareGUI() {
        System.out.println("ClassroomView prepareGUI");

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
        JButton lectures = new JButton("Lectures");
        JButton practices = new JButton("Practices");
        JButton laboratories = new JButton("Laboratories");
        JButton computers = new JButton("Computers");
        JButton smalls = new JButton("Smalls");
        JButton menu = new JButton("Menu");

        buttonsPanel.setLayout(new GridLayout(2,2));
        buttonsPanel.setPreferredSize(new Dimension(400, 200));
        buttonsPanel.setSize(400, 200);

        buttonsPanel.add(create);
        buttonsPanel.add(delete);
        buttonsPanel.add(update);
        buttonsPanel.add(lectures);
        buttonsPanel.add(practices);
        buttonsPanel.add(laboratories);
        buttonsPanel.add(computers);
        buttonsPanel.add(smalls);
        buttonsPanel.add(menu);

        controlPanel.setLayout(new FlowLayout());

        JLabel  idLabel = new JLabel("ID: ", JLabel.CENTER);
        JLabel  buildingLabel= new JLabel("Building: ", JLabel.RIGHT);
        JLabel  floorLabel = new JLabel("Building floor: ", JLabel.RIGHT);
        JLabel  typeLabel = new JLabel("Type: ", JLabel.RIGHT);
        JLabel  capacityLabel = new JLabel("Capacity: ", JLabel.RIGHT);

        JTextField idText = new JTextField(6);
        JTextField buildingText = new JTextField(6);
        JTextField floorText = new JTextField(6);
        JTextField typeText = new JTextField(6);
        JTextField capacityText = new JTextField(6);

        controlPanel.add(buildingLabel);
        controlPanel.add(buildingText);

        controlPanel.add(floorLabel);
        controlPanel.add(floorText);

        controlPanel.add(typeLabel);
        controlPanel.add(typeText);

        controlPanel.add(capacityLabel);
        controlPanel.add(capacityText);

        controlPanel.add(idLabel);
        controlPanel.add(idText);

        create.addActionListener(e -> {
            Integer building = open(buildingText.getText());
            Integer floor    = open(floorText.getText());
            String type      = typeText.getText();
            Integer capacity = open(capacityText.getText());
            viewController.createClassroom(building, floor, type, capacity);
        });

        delete.addActionListener(e -> {
            viewController.deleteClassroom(open(idText.getText()));
        });

        update.addActionListener(e -> {
            Integer id       = open(idText.getText());
            Integer building = open(buildingText.getText());
            Integer floor    = open(floorText.getText());
            String type      = typeText.getText();
            Integer capacity = open(capacityText.getText());
            viewController.updateClassroom(id, building, floor, type, capacity);
        });

        menu.addActionListener(e -> {
            viewController.main();
        });

        lectures.addActionListener(e -> viewController.lectures());
        practices.addActionListener(e -> viewController.practices());
        laboratories.addActionListener(e -> viewController.laboratories());
        computers.addActionListener(e -> viewController.computers());
        smalls.addActionListener(e -> viewController.classroomsSmall());

        controlPanel.add(buttonsPanel);
        mainPanel.add(tablePanel);
        mainPanel.add(controlPanel);

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
