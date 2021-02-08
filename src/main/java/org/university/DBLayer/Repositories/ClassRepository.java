package org.university.DBLayer.Repositories;

import org.university.DBLayer.Repositories.Interfaces.Dao;
import org.university.DBLayer.JdbcConnection;
import org.university.Model.*;
import org.university.DBLayer.Repositories.Interfaces.ClassDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClassRepository  implements ClassDatabase {

    private static final Logger LOGGER =
            Logger.getLogger(ClassRepository.class.getName());
    private final Optional<Connection> connection;

    public ClassRepository(Dao<Teacher, Integer> teacherRepository,
                           Dao<Department, Integer> departmentRepository,
                           Dao<Group, Integer> groupRepository,
                           Dao<Classroom, Integer> classroomRepository,
                           Dao<Subject, Integer> subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.groupRepository = groupRepository;
        this.classroomRepository = classroomRepository;
        this.subjectRepository = subjectRepository;
        this.connection = JdbcConnection.getConnection();
    }

    private final Dao<Teacher, Integer> teacherRepository;
    private final Dao<Department, Integer> departmentRepository;
    private final Dao<Group, Integer> groupRepository;
    private final Dao<Classroom, Integer> classroomRepository;
    private final Dao<Subject, Integer> subjectRepository;



    private Optional<Event> toClass (SQLClass sqlClass) {
        Optional<Teacher> teacher = teacherRepository.get(sqlClass.teacherId);
        Optional<Department> department = departmentRepository.get(sqlClass.departmentId);
        Optional<Group> group = groupRepository.get(sqlClass.groupId);
        Optional<Classroom> classroom = classroomRepository.get(sqlClass.classroomId);
        Optional<Subject> subject = subjectRepository.get(sqlClass.subjectId);

        if (teacher.isPresent() &&
        department.isPresent() &&
        group.isPresent() &&
        classroom.isPresent() &&
        subject.isPresent()) {
            return Optional.of(
                    new Event(sqlClass.id,
                                teacher.get(),
                                department.get(),
                                group.get(),
                                classroom.get(),
                                subject.get(),
                                sqlClass.time,
                                sqlClass.date,
                                sqlClass.type
            ));
        }
        return Optional.empty();
    }

    private SQLClass toSqlClass (Event event) {
        return new SQLClass(event.getId(),
                event.getTeacher().getId(),
                event.getDepartment().getId(),
                event.getGroup().getId(),
                event.getClassroom().getId(),
                event.getSubject().getId(),
                event.getTime(),
                event.getDate(),
                event.getType());
    }

    @Override
    public Optional<Event> get(int id) {

        Optional<SQLClass> resp = connection.flatMap(cl -> {

            String sql = "SELECT * FROM UniversitySchema.CLASS WHERE CLASS_ID = " + id;

            try (Statement statement = cl.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {

                    Integer teacher = resultSet.getInt("TeacherID");
                    Integer department = resultSet.getInt("DepartmentID");
                    Integer group = resultSet.getInt("GroupID");
                    Integer classroom = resultSet.getInt("ClassroomID");
                    Integer subject = resultSet.getInt("SubjectID");

                    Time time =  resultSet.getTime("CLASS_TIME");
                    Date date = resultSet.getDate("CLASS_DATE");
                    String type = resultSet.getString("TYPE");

                    return Optional.of(new SQLClass(id, teacher, department, group, classroom, subject, time, date, type));

                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return Optional.empty();
        });


        if (resp.isPresent()) {
            Optional<Event> res = toClass(resp.get());
            res.ifPresent( r -> LOGGER.log(Level.INFO, "Found {0} in database", r));
            return res;
        }

        return Optional.empty();
    }

    @Override
    public Collection<Event> getAll() {
        Collection<Event> events = new ArrayList<>();
        Collection<SQLClass> resp = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.CLASS ORDER BY CLASS_DATE, CLASS_TIME ASC";

        connection.ifPresent(tchr -> {
            try (Statement statement = tchr.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {

                    Integer id = resultSet.getInt("CLASS_ID");
                    Integer teacher = resultSet.getInt("TeacherID");
                    Integer department = resultSet.getInt("DepartmentID");
                    Integer group = resultSet.getInt("GroupID");
                    Integer classroom = resultSet.getInt("ClassroomID");
                    Integer subject = resultSet.getInt("SubjectID");

                    Time time =  resultSet.getTime("CLASS_TIME");
                    Date date = resultSet.getDate("CLASS_DATE");
                    String type = resultSet.getString("TYPE");

                    resp.add(new SQLClass(id, teacher, department, group, classroom, subject, time, date, type));
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        resp.forEach( sqc -> {
            Optional<Event> res = toClass(sqc);
            res.ifPresent(events::add);
        });

        LOGGER.log(Level.INFO, "Found {0} in database", events);

        return events;
    }

    @Override
    public Optional<Integer> save(Event aClass) {
        String message = "The class to be added should not be null";
        Event nonNullentity = Objects.requireNonNull(aClass, message);

        String sql = "INSERT INTO "
                + "UniversitySchema.CLASS(TeacherID, DepartmentID, GroupID, ClassroomID, SubjectID, CLASS_TIME, CLASS_DATE, TYPE)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        return connection.flatMap(cls -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         cls.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, nonNullentity.getTeacher().getId());
                statement.setInt(2, nonNullentity.getDepartment().getId());
                statement.setInt(3, nonNullentity.getGroup().getId());
                statement.setInt(4, nonNullentity.getClassroom().getId());
                statement.setInt(5, nonNullentity.getSubject().getId());
                statement.setTime(6, nonNullentity.getTime());
                statement.setDate(7, nonNullentity.getDate());
                statement.setString(8, nonNullentity.getType());

                int numberOfInsertedRows = statement.executeUpdate();

                // Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                LOGGER.log(
                        Level.INFO,
                        "{0} created successfully? {1}",
                        new Object[]{nonNullentity,
                                (numberOfInsertedRows > 0)});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Event aClass) {
        String message = "The class to be updated should not be null";
        Event nonNullentity = Objects.requireNonNull(aClass, message);
        String sql = "UPDATE UniversitySchema.CLASS "
                + "SET "
                + "TeacherID = ?,"
                + "DepartmentID = ?,"
                + "GroupID = ?,"
                + "ClassroomID = ?,"
                + "SubjectID = ?,"
                + "CLASS_TIME = ?,"
                + "CLASS_DATE = ?,"
                + "TYPE = ?"
                + "WHERE "
                + "CLASS_ID = ?";

        connection.ifPresent(cls -> {
            try (PreparedStatement statement = cls.prepareStatement(sql)) {

                statement.setInt(1, nonNullentity.getTeacher().getId());
                statement.setInt(2, nonNullentity.getDepartment().getId());
                statement.setInt(3, nonNullentity.getGroup().getId());
                statement.setInt(4, nonNullentity.getClassroom().getId());
                statement.setInt(5, nonNullentity.getSubject().getId());
                statement.setTime(6, nonNullentity.getTime());
                statement.setDate(7, nonNullentity.getDate());
                statement.setString(8, nonNullentity.getType());
                statement.setInt(9, nonNullentity.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the class updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Event aClass) {
        String message = "The class to be deleted should not be null";
        Event nonNullentity = Objects.requireNonNull(aClass, message);

        String sql = "DELETE FROM UniversitySchema.CLASS WHERE CLASS_ID = ?";

        connection.ifPresent(tchr -> {
            try (PreparedStatement statement = tchr.prepareStatement(sql)) {

                statement.setInt(1, nonNullentity.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the class deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public Collection<Event> classesByTime() {
        Collection<Event> events = new ArrayList<>();
        Collection<SQLClass> resp = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.CLASS ORDER BY CLASS_DATE, CLASS_TIME ASC;";

        connection.ifPresent(tchr -> {
            try (Statement statement = tchr.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {

                    Integer id = resultSet.getInt("CLASS_ID");
                    Integer teacher = resultSet.getInt("TeacherID");
                    Integer department = resultSet.getInt("DepartmentID");
                    Integer group = resultSet.getInt("GroupID");
                    Integer classroom = resultSet.getInt("ClassroomID");
                    Integer subject = resultSet.getInt("SubjectID");

                    Time time =  resultSet.getTime("CLASS_TIME");
                    Date date = resultSet.getDate("CLASS_DATE");
                    String type = resultSet.getString("TYPE");

                    resp.add(new SQLClass(id, teacher, department, group, classroom, subject, time, date, type));
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        resp.forEach( sqc -> {
            Optional<Event> res = toClass(sqc);
            res.ifPresent(events::add);
        });

        LOGGER.log(Level.INFO, "Found {0} in database", events);

        return events;
    }

    private static class SQLClass {
        Integer id;
        Integer teacherId;
        Integer departmentId;
        Integer groupId;
        Integer classroomId;
        Integer subjectId;
        Time time;
        Date date;
        String type;

        public SQLClass(Integer id, Integer teacherId, Integer departmentId, Integer groupId, Integer classroomId, Integer subjectId, Time time, Date date, String type) {
            this.id = id;
            this.teacherId = teacherId;
            this.departmentId = departmentId;
            this.groupId = groupId;
            this.classroomId = classroomId;
            this.subjectId = subjectId;
            this.time = time;
            this.date = date;
            this.type = type;
        }

        public SQLClass(Integer teacherId, Integer departmentId, Integer groupId, Integer classroomId, Integer subjectId, Time time, Date date, String type) {
            this.teacherId = teacherId;
            this.departmentId = departmentId;
            this.groupId = groupId;
            this.classroomId = classroomId;
            this.subjectId = subjectId;
            this.time = time;
            this.date = date;
            this.type = type;
        }
    }
}
