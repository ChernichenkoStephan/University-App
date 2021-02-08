package org.university.View.Class;

public interface ClassEditEventHandler {
    void edit(Integer classID,
              int teacherID,
              int departmentID,
              int groupID,
              int classroomID,
              int subjectID,
              java.sql.Time time,
              java.sql.Date date,
              String type);
}
