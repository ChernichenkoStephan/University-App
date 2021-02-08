package org.university.Controller;

import org.university.View.Class.ClassEditEventHandler;
import org.university.View.Class.ClassView;
import org.university.View.Class.ClassesEditView;
import org.university.View.Classroom.ClassroomView;
import org.university.View.Classroom.TypeClassroomView;
import org.university.View.Department.DepartmentView;
import org.university.View.Group.GroupView;
import org.university.View.Menu.MainView;
import org.university.View.Request.RequestView;
import org.university.View.Subject.SubjectView;
import org.university.View.Teacher.TeachersView;
import org.university.View.Teacher.TeacherView;
import org.university.View.View;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Vector;
import java.util.function.Consumer;


public class AssemblyBuilder {

    /**
     * Contains text, title, body & value
     * @return text window
     */
    JFrame makeTextFrame(String label, String title, String body, String value) {
        JFrame frame = new JFrame(label);
        JPanel panel = new JPanel();
        panel.add(new JLabel(title, JLabel.CENTER));
        panel.add(new JLabel(body, JLabel.CENTER));
        panel.add(new JLabel(value, JLabel.CENTER));
        panel.setLayout(new GridLayout(3,1));
        frame.setSize(400,300);
        frame.add(panel);
        return frame;
    }

    /**
     * Main menu
     * @return view to present
     */
    public View makeMainView() { return new MainView(); }

    // ---------------------- Teacher ----------------------

    /**
     * Teacher entity
     * @return view to present
     */
    public View makeTeacherView(Collection data) {
        return new TeacherView(data);
    }

    /**
     * Teachers most exp
     * @return view to present
     */
    public View makeMostExperienced(Collection data) {
        return new TeachersView(data);
    }

    // ---------------------- Department ----------------------

    /**
     * Main department view
     * @param data collection of all departments from db
     * @return view to present
     */
    public View makeDepartmentView(Collection data) {
        return new DepartmentView(data);
    }

    // ---------------------- Group ----------------------

    /**
     * Main groups view
     * @param data collection of all groups from db
     * @return view to present
     */
    public View makeGroupView(Collection data) {
        return new GroupView(data);
    }

    // ---------------------- Classroom ----------------------

    /**
     * Main classrooms view
     * @param data collection of all classrooms from db
     * @return view to present
     */
    public View makeClassroomView(Collection data) {
        return new ClassroomView(data);
    }

    /**
     * View for showing custom request results
     * @param data collection from db
     * @return view to present
     */
    public View makeTypeClassroom(Collection data) {
        return new TypeClassroomView(data);
    }

    // ---------------------- Subject ----------------------

    /**
     * Main subject view
     * @param data collection of all subjects from db
     * @return view to present
     */
    public View makeSubjectView(Collection data) {
        return new SubjectView(data);
    }

    // ---------------------- Class ----------------------

    /**
     * Main class view
     * @param data collection of all subjects from db
     * @return view to present
     */
    public View makeClassView(Collection data,
                              Collection teachers,
                              Collection departments,
                              Collection groups,
                              Collection classrooms,
                              Collection subjects) {
        return new ClassView(data, teachers, departments, groups, classrooms, subjects);
    }

    /**
     * View for create and update
     * @return view to present
     */
    public View makeClassEditView(Collection teachers,
                                  Collection departments,
                                  Collection groups,
                                  Collection classrooms,
                                  Collection subjects,
                                  ClassEditEventHandler eventHandler) {
        return new ClassesEditView(teachers, departments, groups, classrooms, subjects, eventHandler);
    }

    // ---------------------- Requests ----------------------

    /**
     * Flexible view for custom requests
     * @param label Name of request
     * @param columnNames Names for table columns
     * @param data Tables data
     * @return view to present
     */
    public View makeRequestView(String label, Vector<String> columnNames, Vector<Vector<Object>> data) {
        return new RequestView(label, columnNames, data);
    }

}
