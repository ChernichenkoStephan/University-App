package org.university.Model;

/*
CREATE TABLE CLASS (
  CLASS_ID                    INT PRIMARY KEY   NOT NULL,
  TeacherID                               INT   NOT NULL,
  DepartmentID                            INT   NOT NULL,
  GroupID                                 INT   NOT NULL,
  ClassroomID                             INT   NOT NULL,
  SubjectID                               INT   NOT NULL,

  CLASS_TIME                              TIME  NOT NULL,
  CLASS_DATE                              DATE  NOT NULL,
  TYPE                                    TEXT  NOT NULL,
);
 */

import java.sql.Date;
import java.sql.Time;

public class Event {
    private Integer id;
    private Teacher teacher;
    private Department department;
    private Group group;
    private Classroom classroom;
    private Subject subject;
    private Time time;
    private Date date;
    private String type;

    public Event(Integer id, Teacher teacher, Department department, Group group, Classroom classroom, Subject subject, Time time, Date date, String type) {
        this.id = id;
        this.teacher = teacher;
        this.department = department;
        this.group = group;
        this.classroom = classroom;
        this.subject = subject;
        this.time = time;
        this.date = date;
        this.type = type;
    }

    public Event(Teacher teacher, Department department, Group group, Classroom classroom, Subject subject, Time time, Date date, String type) {
        this.teacher = teacher;
        this.department = department;
        this.group = group;
        this.classroom = classroom;
        this.subject = subject;
        this.time = time;
        this.date = date;
        this.type = type;
    }

    public Event(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", teacher=" + teacher +
                ", department=" + department +
                ", group=" + group +
                ", classroom=" + classroom +
                ", subject=" + subject +
                ", time=" + time +
                ", date=" + date +
                ", type='" + type + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
