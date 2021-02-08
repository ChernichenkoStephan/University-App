package org.university.View.Group;

import org.university.Model.Group;
import org.university.View.TableView;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class GroupView extends TableView {
    public GroupView(Collection data) {
        super(data);
        name = "GroupView";
        prepareGUI();
    }

    @Override
    protected void prepareGUI() {
        System.out.println("GroupView prepareGUI");

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
        JButton menu = new JButton("Menu");

        buttonsPanel.setLayout(new GridLayout(2,2));
        buttonsPanel.setPreferredSize(new Dimension(400, 200));
        buttonsPanel.setSize(400, 200);

        buttonsPanel.add(create);
        buttonsPanel.add(delete);
        buttonsPanel.add(update);
        buttonsPanel.add(menu);

        controlPanel.setLayout(new FlowLayout());

        JLabel  idLabel = new JLabel("ID: ", JLabel.CENTER);
        JLabel  facultyLabel= new JLabel("Faculty: ", JLabel.RIGHT);
        JLabel  amountLabel = new JLabel("Students amount: ", JLabel.RIGHT);

        JTextField idText = new JTextField(6);
        JTextField facultyext       = new JTextField(6);
        JTextField amountText    = new JTextField(6);

        controlPanel.add(facultyLabel);
        controlPanel.add(facultyext);

        controlPanel.add(amountLabel);
        controlPanel.add(amountText);

        controlPanel.add(idLabel);
        controlPanel.add(idText);

        create.addActionListener(e -> {
            String  name         = facultyext.getText();
            Integer amount       = open(amountText.getText());

            if (amount != -1) viewController.createGroup(name, amount);
            else viewController.warning("There is some errors in fields");

        });

        delete.addActionListener(e -> {
            viewController.deleteGroup(open(idText.getText()));
        });

        update.addActionListener(e -> {
            Integer id           = open(idText.getText());
            String  name         = facultyext.getText();
            Integer amount       = open(amountText.getText());

            if (amount != -1 || id != -1) viewController.updateGroup(id, name, amount);
            else viewController.warning("There is some errors in fields");
        });

        menu.addActionListener(e -> {
            viewController.main();
        });

        controlPanel.add(buttonsPanel);
        mainPanel.add(tablePanel);
        mainPanel.add(controlPanel);


    }


    private JScrollPane setTable(Collection<Group> groups) {
        JTable table = parse(groups);
        JScrollPane stable = new JScrollPane(table);
        stable.setBounds(20,20, 800, 600);
        return stable;

    }

    private JTable parse(Collection<Group> groups) {
        String[] columnNames = { "ID", "Faculty", "Students amount" };
        Object[][] data = new Object[groups.size()][3];

        int i = 0;
        for (Group g : groups) {
            Object[] row = {g.getId(), g.getFaculty(), g.getAmount()};
            data[i] = row;
            i++;
        }

        return new JTable(data, columnNames);
    }
}
