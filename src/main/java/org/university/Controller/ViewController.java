package org.university.Controller;

import jdk.jshell.spi.ExecutionControlProvider;
import org.university.DBLayer.Exeptions.NonExistentEntityException;
import org.university.Model.*;
import org.university.Model.Event;
import org.university.View.Class.ClassEditEventHandler;
import org.university.View.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class ViewController {

    public static ViewController defaultController = new ViewController();

    private ViewController(){
        mainFrame = new JFrame("University");
        mainFrame.setSize(900,600);
        mainFrame.setPreferredSize(new Dimension(900, 600));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        mainFrame.setLayout(new GridLayout(1,1));

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(800, 500));

        mainFrame.add(mainPanel);
    }

    private JFrame mainFrame;
    private AssemblyBuilder builder = new AssemblyBuilder();
    private View view;
    private JPanel mainPanel;

    public void start() {
        view = builder.makeMainView();

        mainPanel.add(view.panel());

        mainFrame.setVisible(true);
    }

    private void update(View newView) {
        // Clear view
        view.panel().setVisible(false);

        // Remove from view
        mainPanel.remove(view.panel());

        // Change current
        view = newView;

        // Add view
        mainPanel.add(view.panel());

        // Refresh window
        mainFrame.pack();
    }

    // ---------------------- Main menu ----------------------

    public void main() {
        View newView = builder.makeMainView();
        update(newView);
    }

    // ---------------------- Teacher ----------------------

    public void teacher() {
        Collection<Teacher> teachers = DatabaseService.getAllTeachers();
        View newView = builder.makeTeacherView(teachers);
        update(newView);
    }

    public void createTeacher(String name, String lastName, String middleName, String position, Integer experience) {
        System.out.println("createTeacher");
        if (name.equalsIgnoreCase("") ||
            lastName.equalsIgnoreCase("") ||
            middleName.equalsIgnoreCase("") ||
            position.equalsIgnoreCase("") ||
            experience == -1)
        {
            warning("There is empty fields. Please fill them");
        } else {
            Teacher newt = new Teacher(name, lastName, middleName, position, experience);
            DatabaseService.addTeacher(newt);
            teacher();
        }
    }

    public void deleteTeacher(Integer id) {
        System.out.println("deleteTeacher");
        try {
            if (id != -1) {
                DatabaseService.deleteTeacher(new Teacher(id));
                teacher();
            }
        } catch (Exception e) {
            e.printStackTrace();
            warning("Something went wrong with this operation");
        }
    }

    public void updateTeacher(Integer id, String nName, String nLastName, String nMiddleName, String nPosition, Integer nExperience) {
        System.out.println("updateTeacher");
        if (id == -1) warning("Enter ID");
        try {
            Teacher teacher = DatabaseService.getTeacher(id);

            if (!nName.equalsIgnoreCase("")) teacher.setName(nName);
            if (!nLastName.equalsIgnoreCase("")) teacher.setLastName(nLastName);
            if (!nMiddleName.equalsIgnoreCase("")) teacher.setMiddleName(nMiddleName);
            if (!nPosition.equalsIgnoreCase("")) teacher.setPosition(nPosition);
            if (nExperience != -1) teacher.setExperience(nExperience);

            DatabaseService.updateTeacher(teacher);

            teacher();
        } catch (NonExistentEntityException e) {
            e.printStackTrace();
            warning("Teacher not found");
        }
    }

    public void teacherMost() {
        Collection<Teacher> teachers = DatabaseService.mostExperienced();
        View newView = builder.makeMostExperienced(teachers);
        update(newView);
    }

    public void teacherMaxExp() {
        Optional<Teacher> max = DatabaseService.maxExperienced();
        String FIO = "";
        String data = "";
        String value = "";
        if (max.isPresent()) {
            FIO = max.get().getLastName() + " " + max.get().getName() + " " + max.get().getMiddleName();
            data = max.get().getPosition();
            value = max.get().getExperience().toString();
        }
        JFrame frame = builder.makeTextFrame("Max experience", FIO, data, value);
        frame.setVisible(true);
    }

    public void teacherAmount() {
        String amount = DatabaseService.teachersAmount().toString();
        JFrame frame = builder.makeTextFrame("Teachers amount", "Teachers in university", "", amount);
        frame.setVisible(true);
    }

    public void professorize() {
        if (DatabaseService.professorize()) {
            teacher();
            warning("Success");
        } else {
            warning("Ops! Something bad happened...");
        }
    }

    // ---------------------- Department ----------------------

    public void department() {
        Collection<Department> departments = DatabaseService.getAllDepartments();
        View newView = builder.makeDepartmentView(departments);
        update(newView);
    }

    public void createDepartment(String name, String shortName, Integer amount) {
        System.out.println("createDepartment");
        if (name.equalsIgnoreCase("") ||
                shortName.equalsIgnoreCase("") ||
                amount == -1)
        {
            warning("There is empty fields. Please fill them");
        } else {
            Department newd = new Department(name, shortName, amount);
            DatabaseService.addDepartment(newd);
            department();
        }
    }

    public void updateDepartment(Integer id, String nName, String nShortName, Integer nAmount) {
        System.out.println("deleteDepartment");
        if (id == -1) warning("Enter ID");
        try {
            Department department = DatabaseService.getDepartment(id);

            if (!nName.equalsIgnoreCase("")) department.setName(nName);
            if (!nShortName.equalsIgnoreCase("")) department.setShortName(nShortName);
            if (nAmount != -1) department.setAmount(nAmount);

            DatabaseService.updateDepartment(department);

            department();
        } catch (NonExistentEntityException e) {
            e.printStackTrace();
            warning("Department not found");
        }
    }

    public void deleteDepartment(Integer id) {
        System.out.println("deleteDepartment");
        try {
            if (id != -1) {
                DatabaseService.deleteDepartment(new Department(id));
                department();
            }
            else warning("Error int id field");

        } catch (Exception e) {
            e.printStackTrace();
            warning("Something went wrong with this operation");
        }
    }

    // ---------------------- Group ----------------------

    public void group() {
        Collection<Group> groups = DatabaseService.getAllGroups();
        View newView = builder.makeGroupView(groups);
        update(newView);
    }

    public void createGroup(String faculty, Integer amount) {
        System.out.println("createGroup");
        if (faculty.equalsIgnoreCase("") || amount == -1)
        {
            warning("There is empty fields. Please fill them");
        } else {
            Group newg = new Group(faculty, amount);
            DatabaseService.addGroup(newg);
            group();
        }
    }

    public void updateGroup(Integer id, String nFaculty, Integer nAmount) {
        System.out.println("updateGroup");
        if (id == -1) warning("Enter ID");
        try {
            Group group = DatabaseService.getGroup(id);

            if (!nFaculty.equalsIgnoreCase("")) group.setFaculty(nFaculty);
            if (nAmount != -1) group.setAmount(nAmount);

            DatabaseService.updateGroup(group);

            group();
        } catch (NonExistentEntityException e) {
            e.printStackTrace();
            warning("Group not found");
        }
    }

    public void deleteGroup(Integer id) {
        System.out.println("deleteGroup");
        try {
            if (id != -1) {
                DatabaseService.deleteGroup(new Group(id));
                group();
            }
            else warning("Error int id field");

        } catch (Exception e) {
            e.printStackTrace();
            warning("Something went wrong with this operation");
        }
    }

    // ---------------------- Classroom ----------------------

    public void classroom() {
        Collection<Classroom> classrooms = DatabaseService.getAllClassrooms();
        View newView = builder.makeClassroomView(classrooms);
        update(newView);
    }

    public void createClassroom(Integer building, Integer floor, String type, Integer capacity) {
        System.out.println("createClassroom");
        if ( building == -1 || floor == -1 || type.equalsIgnoreCase("") || capacity == -1)
        {
            warning("There is empty fields. Please fill them");
        } else {
            Classroom newc = new Classroom(building, floor, type, capacity);
            DatabaseService.addClassroom(newc);
            classroom();
        }
    }

    public void updateClassroom(Integer id, Integer nBuilding, Integer nFloor, String nType, Integer nCapacity) {
        System.out.println("updateClassroom");
        if (id == -1) warning("Enter ID");
        try {

            Classroom classroom = DatabaseService.getClassroom(id);

            if (nBuilding != -1) classroom.setBuilding(nBuilding);
            if (nFloor != -1) classroom.setFloor(nFloor);
            if (!nType.equalsIgnoreCase("")) classroom.setType(nType);
            if (nCapacity != -1) classroom.setCapacity(nCapacity);

            DatabaseService.updateClassroom(classroom);

            classroom();
        } catch (NonExistentEntityException e) {
            e.printStackTrace();
            warning("Classroom not found");
        }
    }

    public void deleteClassroom(Integer id) {
        System.out.println("deleteClassroom");
        try {
            if (id != -1) {
                DatabaseService.deleteClassroom(new Classroom(id));
                classroom();
            }
            else warning("Error int id field");

        } catch (Exception e) {
            e.printStackTrace();
            warning("Something went wrong with this operation");
        }
    }

    public void lectures() {
        Collection<Classroom> lectures = DatabaseService.getLectures();
        View newView = builder.makeTypeClassroom(lectures);
        update(newView);
    }

    public void practices() {
        Collection<Classroom> practices = DatabaseService.getPractices();
        View newView = builder.makeTypeClassroom(practices);
        update(newView);
    }

    public void laboratories() {
        Collection<Classroom> laboratories = DatabaseService.getLaboratories();
        View newView = builder.makeTypeClassroom(laboratories);
        update(newView);
    }

    public void computers() {
        Collection<Classroom> computers = DatabaseService.getComputers();
        View newView = builder.makeTypeClassroom(computers);
        update(newView);
    }

    public void classroomsSmall() {
        Collection<Classroom> smalls = DatabaseService.getSmall();
        View newView = builder.makeTypeClassroom(smalls);
        update(newView);
    }

    // ---------------------- Subject ----------------------

    public void subject() {
        Collection<Subject> subjects = DatabaseService.getAllSubjects();
        View newView = builder.makeSubjectView(subjects);
        update(newView);
    }

    public void createSubject(String name, String shortName, Integer hoursAmount) {
        System.out.println("createSubject");
        if (name.equalsIgnoreCase("") || shortName.equalsIgnoreCase("") || hoursAmount == -1)
        {
            warning("There is empty fields. Please fill them");
        } else {
            Subject news = new Subject(name, shortName, hoursAmount);
            DatabaseService.addSubject(news);
            subject();
        }
    }

    public void updateSubject(Integer id, String nName, String nShortName, Integer nHoursAmount) {
        System.out.println("updateSubject");
        if (id == -1) warning("Enter ID");
        try {
            Subject subject = DatabaseService.getSubject(id);

            if (!nName.equalsIgnoreCase("")) subject.setName(nName);
            if (!nShortName.equalsIgnoreCase("")) subject.setShortName(nShortName);
            if (nHoursAmount != -1) subject.setHoursAmount(nHoursAmount);

            DatabaseService.updateSubject(subject);

            subject();
        } catch (NonExistentEntityException e) {
            e.printStackTrace();
            warning("Classroom not found");
        }
    }

    public void deleteSubject(Integer id) {
        System.out.println("deleteSubject");
        try {
            if (id != -1) {
                DatabaseService.deleteSubject(new Subject(id));
                subject();
            }
            else warning("Error int id field");

        } catch (Exception e) {
            e.printStackTrace();
            warning("Something went wrong with this operation");
        }
    }

    public void fullCourse() {
        Integer res = DatabaseService.fullCourse();
        JFrame frame = builder.makeTextFrame("fullCourse",
                "Hours amount",
                "Summ of all hours amounts of all courses",
                res.toString());
        frame.setVisible(true);
    }

    // ---------------------- Class ----------------------

    public void event() {
        Collection<Event> events            = DatabaseService.getAllClasses();
        Collection<Teacher> teachers        = DatabaseService.getAllTeachers();
        Collection<Department> departments  = DatabaseService.getAllDepartments();
        Collection<Group> groups            = DatabaseService.getAllGroups();
        Collection<Classroom> classrooms    = DatabaseService.getAllClassrooms();
        Collection<Subject> subjects        = DatabaseService.getAllSubjects();

        View newView = builder.makeClassView(events, teachers, departments, groups, classrooms, subjects);
        update(newView);
    }

    public void eventCreate(Collection<Teacher> teachers,
                            Collection<Department> departments,
                            Collection<Group> groups,
                            Collection<Classroom> classrooms,
                            Collection<Subject> subjects) {

        ClassEditEventHandler eventHandler = this::createEvent;
        View newView = builder.makeClassEditView(teachers,
                departments,
                groups,
                classrooms,
                subjects,
                eventHandler
        );
        update(newView);

    }

    public void eventUpdate(Collection<Teacher> teachers,
                            Collection<Department> departments,
                            Collection<Group> groups,
                            Collection<Classroom> classrooms,
                            Collection<Subject> subjects) {
        ClassEditEventHandler eventHandler = this::updateEvent;
        View newView = builder.makeClassEditView(teachers,
                departments,
                groups,
                classrooms,
                subjects,
                eventHandler
        );
        update(newView);
    }

    public void createEvent(Integer classID,
                            int teacherID,
                            int departmentID,
                            int groupID,
                            int classroomID,
                            int subjectID,
                            java.sql.Time time,
                            java.sql.Date date,
                            String type)
    {
        System.out.println("createEvent");
        if (time == null || date == null) warning("There is empty fields. Please fill them");
        else {
            try {
                Teacher teacher = DatabaseService.getTeacher(teacherID);
                Department department = DatabaseService.getDepartment(departmentID);
                Group group = DatabaseService.getGroup(groupID);
                Classroom classroom = DatabaseService.getClassroom(classroomID);
                Subject subject = DatabaseService.getSubject(subjectID);

                Event newe = new Event(teacher, department, group, classroom, subject, time, date, type);
                DatabaseService.addClass(newe);
                event();
            } catch (NonExistentEntityException e) {
                e.printStackTrace();
                warning("One of entities was wrong");
            }
        }
    }

    public void updateEvent(Integer classID,
                            int teacherID,
                            int departmentID,
                            int groupID,
                            int classroomID,
                            int subjectID,
                            java.sql.Time time,
                            java.sql.Date date,
                            String type) {
        System.out.println("updateEvent");
        if (classID == -1) warning("Wrong ID");
        else {
            try {
                Event event = DatabaseService.getClass(classID);
                if (teacherID != -1) {
                    Teacher teacher = DatabaseService.getTeacher(teacherID);
                    event.setTeacher(teacher);
                }
                if (departmentID != -1) {
                    Department department = DatabaseService.getDepartment(departmentID);
                    event.setDepartment(department);
                }
                if (groupID != -1) {
                    Group group = DatabaseService.getGroup(groupID);
                    event.setGroup(group);
                }
                if (classroomID != -1) {
                    Classroom classroom = DatabaseService.getClassroom(classroomID);
                    event.setClassroom(classroom);
                }
                if (subjectID != -1) {
                    Subject subject = DatabaseService.getSubject(subjectID);
                    event.setSubject(subject);
                }
                if (time != null) event.setTime(time);
                if (date != null) event.setDate(date);
                if (type != null) event.setType(type);

                DatabaseService.updateClass(event);
                event();
            } catch (NonExistentEntityException e) {
                e.printStackTrace();
                warning("One of entities was wrong");
            }
        }
    }

    public void deleteEvent(Integer id) {
        System.out.println("deleteEvent");
        try {
            if (id != -1) {
                DatabaseService.deleteClass(new Event(id));
                event();
            }
            else warning("Error int id field");

        } catch (Exception e) {
            e.printStackTrace();
            warning("Something went wrong with this operation");
        }
    }

    // ---------------------- Other ----------------------

    public void warning(String text) {
        JFrame frame = builder.makeTextFrame("Warning", "Warning", text, "");
        frame.setVisible(true);
    }

    public void allPlaces() {
        DatabaseService.CustomData resp = DatabaseService.allPlaces();
        Vector<String> columnNames = new Vector<>(List.of("Unit name", "Places amount"));
        viewRequest("University units places", columnNames, resp.getBody());
    }

    public void universityUnits() {
        DatabaseService.CustomData resp = DatabaseService.universityUnits();
        Vector<String> columnNames = new Vector<>(List.of("Unit name"));
        viewRequest("University units", columnNames, resp.getBody());
    }

    public void universityObjects() {
        DatabaseService.CustomData resp = DatabaseService.universityObjects();
        Vector<String> columnNames = new Vector<>(List.of("Object ID"));
        viewRequest("University objects", columnNames, resp.getBody());
    }

    public void mostClasses() {
        DatabaseService.CustomData resp = DatabaseService.mostClasses();
        Vector<String> columnNames = new Vector<>(List.of("Time", "Date"));
        viewRequest("Classes of most experienced teacher", columnNames, resp.getBody());
    }

    public void aboveAvgTeachers() {
        DatabaseService.CustomData resp = DatabaseService.aboveAvgTeachers();
        Vector<String> columnNames = new Vector<>(List.of("ID", "Name", "Lastname", "Surname", "Position", "Experience"));
        viewRequest("Teachers with experience above average", columnNames, resp.getBody());
    }

    public void enoughClass() {
        DatabaseService.CustomData resp = DatabaseService.enoughClass();
        Vector<String> columnNames = new Vector<>(List.of("ID", "Building", "Floor", "Type", "Capacity"));
        viewRequest("Classes with enough capacity for all students", columnNames, resp.getBody());
    }

    private void viewRequest (String name, Vector<String> columnNames, Vector<Vector<Object>> data) {
        View newView = builder.makeRequestView(name, columnNames, data);
        update(newView);
    }

}