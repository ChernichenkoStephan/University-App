package org.university.DBLayer.Repositories;

import org.university.Controller.DatabaseService;
import org.university.DBLayer.JdbcConnection;

import java.sql.*;
import java.util.Optional;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestsRepository {

    private static final Logger LOGGER =
            Logger.getLogger(TeacherRepository.class.getName());

    private final Optional<Connection> connection;

    public RequestsRepository() {
        this.connection = JdbcConnection.getConnection();
    }

    public DatabaseService.CustomData allPlaces() {
        String sql = "SELECT SHORT_NAME, PLACES_AMOUNT FROM UniversitySchema.DEPARTMENT " +
                "UNION SELECT FACULTY, STUDENTS_AMOUNT FROM UniversitySchema.STUDENTS_GROUP;";

        return request(sql);
    }

    public DatabaseService.CustomData universityUnits() {
        String sql = "SELECT NAME AS \"DepartmentsSubjects\" FROM UniversitySchema.DEPARTMENT " +
                "UNION SELECT NAME FROM UniversitySchema.SUBJECT;";

        return request(sql);
    }

    public DatabaseService.CustomData universityObjects() {
        String sql = "SELECT BUILDING AS \"ObjectsIDs\" FROM UniversitySchema.CLASSROOM " +
                "UNION SELECT CLASSROOM_ID FROM UniversitySchema.CLASSROOM;";

        return request(sql);
    }

    public DatabaseService.CustomData mostClasses() {
        String sql = "SELECT CLASS_TIME, CLASS_DATE, TYPE FROM UniversitySchema.CLASS CLASS " +
                "WHERE EXISTS (" +
                "  SELECT * FROM UniversitySchema.TEACHER " +
                "  WHERE EXPERIENCE = (SELECT MAX(EXPERIENCE) FROM UniversitySchema.TEACHER) " +
                "  AND TEACHER_ID = CLASS.TeacherID " +
                ");";

        return request(sql);
    }

    public DatabaseService.CustomData aboveAvgTeachers() {
        String sql = "SELECT * FROM UniversitySchema.TEACHER " +
                "WHERE EXPERIENCE > (SELECT AVG(EXPERIENCE) FROM UniversitySchema.TEACHER);";

        return request(sql);
    }

    public DatabaseService.CustomData enoughClass() {
        String sql = "SELECT * FROM UniversitySchema.CLASSROOM " +
                "WHERE CAPACITY >= ALL (SELECT SUM(STUDENTS_AMOUNT) FROM UniversitySchema.STUDENTS_GROUP );";

        return request(sql);
    }

    private DatabaseService.CustomData request(String sql) {
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<String> columnNames = new Vector<String>();

        connection.ifPresent(req -> {
            try (Statement statement = req.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnName(column));
                }

                while (resultSet.next()) {
                    Vector<Object> vector = new Vector<Object>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        vector.add(resultSet.getObject(columnIndex));
                    }
                    data.add(vector);

                }

                LOGGER.log(Level.INFO, "Found {0} in database", data.size());
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        });

        return new DatabaseService.CustomData(columnNames, data);
    }



}
