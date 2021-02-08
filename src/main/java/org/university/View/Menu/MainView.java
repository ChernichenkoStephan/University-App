package org.university.View.Menu;

import org.university.View.View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionListener;

public class MainView extends View {

    public MainView() {
        super();
        prepareGUI();
    }

    @Override
    protected void prepareGUI() {
        System.out.println("MainView prepareGUI");
        name = "MainView";

        mainPanel.setLayout(new GridLayout(1,2));

        JPanel entitiesButtons = new JPanel();
        JPanel requestsButtons = new JPanel();

        entitiesButtons.setLayout(new GridLayout(6,1));
        requestsButtons.setLayout(new GridLayout(6,1));

        JButton teacher     = new JButton("Teachers");
        JButton department  = new JButton("Departments");
        JButton group       = new JButton("Groups");
        JButton subject     = new JButton("Subjects");
        JButton classroom   = new JButton("Classrooms");
        JButton event       = new JButton("Classes");

        JButton allPlaces           = new JButton("Places");
        JButton universityUnits     = new JButton("Units");
        JButton universityObjects   = new JButton("Objects");
        JButton mostClasses         = new JButton("Best");
        JButton aboveAvgTeachers    = new JButton("Above AVG");
        JButton enoughClass         = new JButton("Enough");

        teacher.addActionListener(e -> viewController.teacher());
        department.addActionListener(e -> viewController.department());
        group.addActionListener(e -> viewController.group());
        subject.addActionListener(e -> viewController.subject());
        classroom.addActionListener(e -> viewController.classroom());
        event.addActionListener(e -> viewController.event());
        allPlaces.addActionListener( e -> viewController.allPlaces());
        universityUnits.addActionListener(e -> viewController.universityUnits());
        universityObjects.addActionListener(e -> viewController.universityObjects());
        mostClasses.addActionListener(e -> viewController.mostClasses());
        aboveAvgTeachers.addActionListener(e -> viewController.aboveAvgTeachers());
        enoughClass.addActionListener(e -> viewController.enoughClass());

        entitiesButtons.add(teacher);
        entitiesButtons.add(department);
        entitiesButtons.add(group);
        entitiesButtons.add(subject);
        entitiesButtons.add(classroom);
        entitiesButtons.add(event);

        requestsButtons.add(allPlaces);
        requestsButtons.add(universityUnits);
        requestsButtons.add(universityObjects);
        requestsButtons.add(mostClasses);
        requestsButtons.add(aboveAvgTeachers);
        requestsButtons.add(enoughClass);

        mainPanel.add(entitiesButtons);
        mainPanel.add(requestsButtons);

    }
}
