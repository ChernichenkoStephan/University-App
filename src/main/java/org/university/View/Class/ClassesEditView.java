package org.university.View.Class;

import org.university.Model.*;
import org.university.View.View;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

public class ClassesEditView extends View {

    private final Collection teachers;
    private final Collection departments;
    private final Collection groups;
    private final Collection classrooms;
    private final Collection subjects;
    private final ClassEditEventHandler eventHandler;

    private Integer teacherID = -1;
    private Integer departmentID = -1;
    private Integer groupID = -1;
    private Integer classroomID = -1;
    private Integer subjectID = -1;

    private final JLabel resTLabel = new JLabel("Teacher was skipped");
    private final JLabel resDLabel = new JLabel("Department was skipped");
    private final JLabel resGLabel = new JLabel("Group was skipped");
    private final JLabel resCLabel = new JLabel("Classroom was skipped");
    private final JLabel resSLabel = new JLabel("Subject was skipped");

    public ClassesEditView(Collection teachers,
                           Collection departments,
                           Collection groups,
                           Collection classrooms,
                           Collection subjects,
                           ClassEditEventHandler eventHandler)
    {
        this.teachers = teachers;
        this.departments = departments;
        this.groups = groups;
        this.classrooms = classrooms;
        this.subjects = subjects;
        this.eventHandler = eventHandler;

        name = "ClassesEditView";
        prepareGUI();
    }

    @Override
    protected void prepareGUI() {
        System.out.println("ClassesEditView prepareGUI");

        JScrollPane teachersTable = parseTeachers();
        JLabel teacherLabel = new JLabel("Entered teacher id: ", JLabel.RIGHT);
        JTextField teacherText = new JTextField(6);
        JButton toDepartment = new JButton("next");
        JPanel addTeacherPanel = makeEntityPanel(teachersTable, "Teachers", teacherLabel, teacherText, toDepartment);

        JScrollPane departmentsTable = parseDepartments();
        JLabel departmentLabel = new JLabel("Entered department id: ", JLabel.RIGHT);
        JTextField departmentText = new JTextField(6);
        JButton toGroup = new JButton("next");
        JPanel addDepartmentPanel = makeEntityPanel(departmentsTable, "Departments", departmentLabel, departmentText, toGroup);

        JScrollPane groupsTable = parseGroups();
        JLabel groupLabel = new JLabel("Entered group id: ", JLabel.RIGHT);
        JTextField groupText = new JTextField(6);
        JButton toClassroom = new JButton("next");
        JPanel addGroupPanel = makeEntityPanel(groupsTable, "Groups", groupLabel, groupText, toClassroom);

        JScrollPane classroomsTable = parseClassrooms();
        JLabel classroomLabel = new JLabel("Entered classroom id: ", JLabel.RIGHT);
        JTextField classroomText = new JTextField(6);
        JButton toSubject = new JButton("next");
        JPanel addClassroomPanel = makeEntityPanel(classroomsTable, "Classrooms", classroomLabel, classroomText, toSubject);

        JScrollPane subjectsTable = parseSubjects();
        JLabel subjectLabel = new JLabel("Entered subject id: ", JLabel.RIGHT);
        JTextField subjectText = new JTextField(6);
        JButton toRes = new JButton("next");
        JPanel addSubjectPanel = makeEntityPanel(subjectsTable, "Subjects", subjectLabel, subjectText, toRes);

        CardLayout layout = new CardLayout();
        mainPanel.setLayout(layout);

        mainPanel.add("Teacher", addTeacherPanel);
        mainPanel.add("Department", addDepartmentPanel);
        mainPanel.add("Group", addGroupPanel);
        mainPanel.add("Classroom", addClassroomPanel);
        mainPanel.add("Subject", addSubjectPanel);

        mainPanel.add("Final", makeFinalPanel());

        toDepartment.addActionListener(e -> {
            this.teacherID = open(teacherText.getText());
            layout.show(mainPanel, "Department");
        });
        toGroup.addActionListener(e -> {
            this.departmentID = open(departmentText.getText());
            layout.show(mainPanel, "Group");
        });
        toClassroom.addActionListener(e -> {
            this.groupID = open(groupText.getText());
            layout.show(mainPanel, "Classroom");
        });
        toSubject.addActionListener(e -> {
            this.classroomID = open(classroomText.getText());
            layout.show(mainPanel, "Subject");
        });
        toRes.addActionListener(e -> {
            this.subjectID = open(subjectText.getText());
            if (teacherID != -1) resTLabel.setText("Teacher id: " + teacherID.toString());
            if (departmentID != -1) resDLabel.setText("Department id: " + departmentID.toString());
            if (groupID != -1) resGLabel.setText("Group id: " + groupID.toString());
            if (classroomID != -1) resCLabel.setText("Classroom id: " + classroomID.toString());
            if (subjectID != -1) resSLabel.setText("Subject id: " + subjectID.toString());
            layout.show(mainPanel, "Final");
        });
    }

    private JPanel makeEntityPanel(JScrollPane table,
                                   String labelText,
                                   JLabel propertyLabel,
                                   JTextField propertyText,
                                   JButton next) {
        JPanel resPanel = new JPanel();

        resPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel tablePanel = new JPanel();
        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        tablePanel.setLayout(new CardLayout());

        JLabel mainLabel = new JLabel(labelText, SwingConstants.CENTER);

        // Initializing the JTable
        table.setBounds(30, 40, 800, 400);

        tablePanel.add(table);

        JButton cancel = new JButton("Сancel");
        cancel.addActionListener(e -> viewController.event());

        controlsPanel.add(propertyLabel);
        controlsPanel.add(propertyText);

        buttonsPanel.add(cancel);
        buttonsPanel.add(next);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridy = 0;
        c.insets = new Insets(5, 0, 5, 0);
        resPanel.add(mainLabel, c);
        c.ipady = 300;
        c.ipadx = 800;
        c.gridy = 1;
        resPanel.add(tablePanel, c);
        c.ipady = 5;
        c.ipadx = 10;
        c.gridy = 2;
        resPanel.add(controlsPanel, c);
        c.gridy = 3;
        resPanel.add(buttonsPanel, c);

        return resPanel;
    }

    private JPanel makeFinalPanel() {

        JPanel finalPanel = new JPanel();
        JPanel enteredPanel = new JPanel();
        JPanel datePanel = new JPanel();
        JPanel timePanel = new JPanel();
        JPanel controlPanel = new JPanel();

        finalPanel.setLayout(new GridLayout(4,1, 10, 10));

        JLabel  idLabel = new JLabel("ID: ", JLabel.CENTER);

        JTextField idText = new JTextField(6);

        JLabel  secondsLabel = new JLabel("Seconds: ", JLabel.LEFT);
        JLabel  minutesLabel = new JLabel("Minutes: ", JLabel.LEFT);
        JLabel  hoursLabel = new JLabel("Hours: ", JLabel.LEFT);
        JLabel  dayLabel = new JLabel("Days: ", JLabel.LEFT);
        JLabel  monthLabel = new JLabel("Month: ", JLabel.LEFT);
        JLabel  yearLabel = new JLabel("Year: ", JLabel.LEFT);
        JLabel  typeLabel = new JLabel("Type: ", JLabel.RIGHT);

        JTextField secondsText = new JTextField(6);
        JTextField minutesText = new JTextField(6);
        JTextField hoursText = new JTextField(6);
        JTextField dayText = new JTextField(6);
        JTextField monthText = new JTextField(6);
        JTextField yearText = new JTextField(6);
        JTextField typeText = new JTextField(6);

        JButton cancel = new JButton("Cancel");
        JButton apply = new JButton("Apply");

        enteredPanel.add(resTLabel);
        enteredPanel.add(resDLabel);
        enteredPanel.add(resGLabel);
        enteredPanel.add(resCLabel);
        enteredPanel.add(resSLabel);

        timePanel.add(secondsLabel);
        timePanel.add(secondsText);
        timePanel.add(minutesLabel);
        timePanel.add(minutesText);
        timePanel.add(hoursLabel);
        timePanel.add(hoursText);

        datePanel.add(dayLabel);
        datePanel.add(dayText);
        datePanel.add(monthLabel);
        datePanel.add(monthText);
        datePanel.add(yearLabel);
        datePanel.add(yearText);

        controlPanel.add(typeLabel);
        controlPanel.add(typeText);
        controlPanel.add(idLabel);
        controlPanel.add(idText);
        controlPanel.add(cancel);
        controlPanel.add(apply);

        finalPanel.add(enteredPanel);
        finalPanel.add(datePanel);
        finalPanel.add(timePanel);
        finalPanel.add(controlPanel);

        cancel.addActionListener(e -> viewController.event());
        apply.addActionListener(e -> {
            Integer ID           = open(idText.getText());
            Integer teacherID    = this.teacherID;
            Integer departmentID = this.departmentID;
            Integer groupID      = this.groupID;
            Integer classroomID  = this.classroomID;
            Integer subjectID    = this.subjectID;

            String timeStr = hoursText.getText() + ":" + minutesText.getText() + ":" + secondsText.getText();
            Time time = openTime(timeStr);
            String dateStr = yearText.getText() + "-" + monthText.getText() + "-" + dayText.getText();
            Date date = openDate(dateStr);
            String type = typeText.getText();

            this.eventHandler.edit(ID, teacherID, departmentID, groupID, classroomID, subjectID, time, date, type);
        });

        return finalPanel;
    }

    private Date openDate(String str) {
        try {
            return Date.valueOf(str);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Time openTime(String str) {
        try {
            return Time.valueOf(str);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JScrollPane parseTeachers() {
        Collection<Teacher> teachers = this.teachers;
        String[] columnNames = { "ID", "Name", "Lastname", "Surname", "Position", "Experience" };
        Object[][] data = new Object[teachers.size()][6];
        int i = 0;
        for (Teacher t : teachers) {
            Object[] row = {t.getId(), t.getName(), t.getLastName(), t.getMiddleName(), t.getPosition(), t.getExperience()};
            data[i] = row;
            i++;
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane stable = new JScrollPane(table);
        stable.setBounds(20,20, 800, 600);
        return stable;
    }

    private JScrollPane parseDepartments() {
        Collection<Department> departments = this.departments;
        String[] columnNames = { "ID", "Name", "Short name", "Places amount" };
        Object[][] data = new Object[departments.size()][4];

        int i = 0;
        for (Department d : departments) {
            Object[] row = {d.getId(), d.getName(), d.getShortName(), d.getAmount()};
            data[i] = row;
            i++;
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane stable = new JScrollPane(table);
        stable.setBounds(20,20, 800, 600);
        return stable;
    }

    private JScrollPane parseGroups() {
        Collection<Group> groups = this.groups;
        String[] columnNames = { "ID", "Faculty", "Students amount" };
        Object[][] data = new Object[groups.size()][3];

        int i = 0;
        for (Group g : groups) {
            Object[] row = {g.getId(), g.getFaculty(), g.getAmount()};
            data[i] = row;
            i++;
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane stable = new JScrollPane(table);
        stable.setBounds(20,20, 800, 600);
        return stable;
    }

    private JScrollPane parseClassrooms() {
        Collection<Classroom> classrooms = this.classrooms;
        String[] columnNames = { "ID", "Building", "Floor", "Type", "Capacity" };
        Object[][] data = new Object[classrooms.size()][5];

        int i = 0;
        for (Classroom c : classrooms) {
            Object[] row = {c.getId(), c.getBuilding(), c.getFloor(), c.getType(), c.getCapacity()};
            data[i] = row;
            i++;
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane stable = new JScrollPane(table);
        stable.setBounds(20,20, 800, 600);
        return stable;
    }

    private JScrollPane parseSubjects() {
        Collection<Subject> subjects = this.subjects;
        String[] columnNames = { "ID", "Name", "Short name", "Hours amount" };
        Object[][] data = new Object[subjects.size()][5];

        int i = 0;
        for (Subject s : subjects) {
            Object[] row = {s.getId(), s.getName(), s.getShortName(), s.getHoursAmount()};
            data[i] = row;
            i++;
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane stable = new JScrollPane(table);
        stable.setBounds(20,20, 800, 600);
        return stable;
    }

}
