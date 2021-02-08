package org.university.View.Teacher;

import org.university.Model.Teacher;
import org.university.View.TableView;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;


public class TeacherView extends TableView {

    public TeacherView(Collection data) {
        super(data);
        name = "TeacherView";
        prepareGUI();
    }

    @Override
    protected void prepareGUI() {
        System.out.println("TeacherView prepareGUI");


        mainPanel.setLayout(new GridLayout(2,1, 10, 10));
        JPanel tablePanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        JPanel controlPanel = new JPanel();

        // adding it to JScrollPane
        table = setTable(data);

        // Initializing the JTable
        table.setBounds(30, 40, 800, 400);

        tablePanel.setLayout(new CardLayout());

        tablePanel.add(table);

        JButton create = new JButton("Create");
        JButton delete = new JButton("Delete");
        JButton update = new JButton("Update");
        JButton mostExp = new JButton("Most exp");
        JButton maxExp = new JButton("Max exp");
        JButton amount = new JButton("Amount");
        JButton professorize = new JButton("Professorize");
        JButton menu = new JButton("Menu");

        buttonsPanel.setLayout(new GridLayout(3,3));
        buttonsPanel.setPreferredSize(new Dimension(700, 200));
        buttonsPanel.setSize(700, 200);

        buttonsPanel.add(create);
        buttonsPanel.add(delete);
        buttonsPanel.add(update);
        buttonsPanel.add(mostExp);
        buttonsPanel.add(maxExp);
        buttonsPanel.add(amount);
        buttonsPanel.add(professorize);
        buttonsPanel.add(menu);

        controlPanel.setLayout(new FlowLayout());

        JLabel  nameLabel= new JLabel("Name: ", JLabel.RIGHT);
        JLabel  lastnameLabel = new JLabel("Last name: ", JLabel.CENTER);
        JLabel  midlenameLabel = new JLabel("Mid name: ", JLabel.RIGHT);
        JLabel  positionLabel = new JLabel("Position: ", JLabel.CENTER);
        JLabel  experienceLabel = new JLabel("Experience: ", JLabel.RIGHT);
        JLabel  idLabel = new JLabel("ID: ", JLabel.CENTER);

        JTextField nameText       = new JTextField(6);
        JTextField lastnameText   = new JTextField(6);
        JTextField midnameText    = new JTextField(6);
        JTextField positionText   = new JTextField(6);
        JTextField experienceText = new JTextField(6);
        JTextField idText = new JTextField(6);

        controlPanel.add(nameLabel);
        controlPanel.add(nameText);

        controlPanel.add(lastnameLabel);
        controlPanel.add(lastnameText);

        controlPanel.add(midlenameLabel);
        controlPanel.add(midnameText);

        controlPanel.add(positionLabel);
        controlPanel.add(positionText);

        controlPanel.add(experienceLabel);
        controlPanel.add(experienceText);

        controlPanel.add(idLabel);
        controlPanel.add(idText);

        create.addActionListener(e -> {
            String  name         = nameText.getText();
            String  lastName     = lastnameText.getText();
            String  middleName   = midnameText.getText();
            String  position     = positionText.getText();
            Integer experience   = open(experienceText.getText());
            viewController.createTeacher(name, lastName, middleName, position, experience);
        });

        update.addActionListener(e -> {
            Integer id           = open(idText.getText());
            String  name         = nameText.getText();
            String  lastName     = lastnameText.getText();
            String  middleName   = midnameText.getText();
            String  position     = positionText.getText();
            Integer experience   = open(experienceText.getText());
            viewController.updateTeacher(id, name, lastName, middleName, position, experience);
        });

        delete.addActionListener(e -> {
            viewController.deleteTeacher(open(idText.getText()));
        });

        mostExp.addActionListener(e -> viewController.teacherMost());
        maxExp.addActionListener(e -> viewController.teacherMaxExp());
        amount.addActionListener(e -> viewController.teacherAmount());

        professorize.addActionListener(e -> viewController.professorize());
        menu.addActionListener(e -> viewController.main());

        controlPanel.add(buttonsPanel);
        mainPanel.add(tablePanel);
        mainPanel.add(controlPanel);

    }

    private JScrollPane setTable(Collection<Teacher> teachers) {
        JTable table = parse(teachers);
        JScrollPane stable = new JScrollPane(table);
        stable.setBounds(20,20, 800, 600);
        return stable;

    }

    private JTable parse(Collection<Teacher> teachers) {
        String[] columnNames = { "ID", "Name", "Lastname", "Surname", "Position", "Experience" };
        Object[][] data = new Object[teachers.size()][6];
        int i = 0;
        for (Teacher t : teachers) {
            Object[] row = {t.getId(), t.getName(), t.getLastName(), t.getMiddleName(), t.getPosition(), t.getExperience()};
            data[i] = row;
            i++;
        }
        return new JTable(data, columnNames);
    }
}
