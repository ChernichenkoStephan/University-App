package org.university.View.Department;

import org.university.Model.Department;
import org.university.Model.Teacher;
import org.university.View.TableView;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class DepartmentView extends TableView {

    public DepartmentView(Collection data) {
        super(data);
        name = "DepartmentView";
        prepareGUI();
    }

    @Override
    protected void prepareGUI() {
        System.out.println("DepartmentView prepareGUI");

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
        JLabel  nameLabel= new JLabel("Name: ", JLabel.RIGHT);
        JLabel  shortnameLabel = new JLabel("Short name: ", JLabel.CENTER);
        JLabel  amountLabel = new JLabel("Amount: ", JLabel.RIGHT);

        JTextField idText = new JTextField(6);
        JTextField nameText       = new JTextField(6);
        JTextField shortnameText   = new JTextField(6);
        JTextField amountText    = new JTextField(6);

        controlPanel.add(nameLabel);
        controlPanel.add(nameText);

        controlPanel.add(shortnameLabel);
        controlPanel.add(shortnameText);

        controlPanel.add(amountLabel);
        controlPanel.add(amountText);

        controlPanel.add(idLabel);
        controlPanel.add(idText);

        create.addActionListener(e -> {
            String  name         = nameText.getText();
            String  shortname   = shortnameText.getText();
            Integer amount   = open(amountText.getText());

            if (amount != -1) viewController.createDepartment(name, shortname, amount);
            else viewController.warning("There is some errors in fields");

        });

        delete.addActionListener(e -> {
            viewController.deleteDepartment(open(idText.getText()));
        });

        update.addActionListener(e -> {
            Integer id           = open(idText.getText());
            String  name         = nameText.getText();
            String  shortname    = shortnameText.getText();
            Integer amount       = open(amountText.getText());
            if (amount != -1 || id != -1) viewController.updateDepartment(id, name, shortname, amount);
            else viewController.warning("There is some errors in fields");
        });

        menu.addActionListener(e -> {
            viewController.main();
        });

        controlPanel.add(buttonsPanel);
        mainPanel.add(tablePanel);
        mainPanel.add(controlPanel);

    }

    private JScrollPane setTable(Collection<Department> departments) {
        JTable table = parse(departments);
        JScrollPane stable = new JScrollPane(table);
        stable.setBounds(20,20, 800, 600);
        return stable;

    }

    private JTable parse(Collection<Department> departments) {
        String[] columnNames = { "ID", "Name", "Short name", "Places amount" };
        Object[][] data = new Object[departments.size()][4];

        int i = 0;
        for (Department d : departments) {
            Object[] row = {d.getId(), d.getName(), d.getShortName(), d.getAmount()};
            data[i] = row;
            i++;
        }

        return new JTable(data, columnNames);
    }
}

/*


 */