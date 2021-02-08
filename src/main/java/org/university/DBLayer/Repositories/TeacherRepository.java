package org.university.DBLayer.Repositories;

import org.university.DBLayer.JdbcConnection;
import org.university.Model.Teacher;
import org.university.DBLayer.Repositories.Interfaces.TeacherDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherRepository implements TeacherDatabase {

    private static final Logger LOGGER =
            Logger.getLogger(TeacherRepository.class.getName());

    private final Optional<Connection> connection;

    public TeacherRepository() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Teacher> get(int id) {
        return connection.flatMap(tchr -> {
            Optional<Teacher> teacher = Optional.empty();
            String sql = "SELECT * FROM UniversitySchema.TEACHER WHERE TEACHER_ID = " + id;

            try (Statement statement = tchr.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String firstName = resultSet.getString("NAME");
                    String lastName = resultSet.getString("LAST_NAME");
                    String middleName = resultSet.getString("MIDDLE_NAME");
                    String position = resultSet.getString("POSITION");
                    Integer experience = resultSet.getInt("EXPERIENCE");

                    teacher = Optional.of(
                            new Teacher(id, firstName, lastName, middleName, position, experience));

                    LOGGER.log(Level.INFO, "Found {0} in database", teacher.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }

            return teacher;
        });
    }

    @Override
    public Collection<Teacher> getAll() {
        Collection<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.TEACHER ORDER BY NAME, LAST_NAME ASC;";

        connection.ifPresent(tchr -> {
            try (Statement statement = tchr.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Integer id = resultSet.getInt("TEACHER_ID");
                    String firstName = resultSet.getString("NAME");
                    String lastName = resultSet.getString("LAST_NAME");
                    String middleName = resultSet.getString("MIDDLE_NAME");
                    String position = resultSet.getString("POSITION");
                    Integer experience = resultSet.getInt("EXPERIENCE");

                    teachers.add(new Teacher(id, firstName, lastName, middleName, position, experience));

                }
                LOGGER.log(Level.INFO, "Found {0} in database", teachers);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });

        return teachers;
    }

    @Override
    public Optional<Integer> save(Teacher teacher) {
        String message = "The teacher to be added should not be null";
        Teacher nonNullentity = Objects.requireNonNull(teacher, message);

        String sql = "INSERT INTO "
                + "UniversitySchema.TEACHER(NAME, LAST_NAME, MIDDLE_NAME, POSITION, EXPERIENCE) "
                + "VALUES(?, ?, ?, ?, ?)";

        return connection.flatMap(tchr -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         tchr.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullentity.getName());
                statement.setString(2, nonNullentity.getLastName());
                statement.setString(3, nonNullentity.getMiddleName());
                statement.setString(4, nonNullentity.getPosition());
                statement.setInt(5, nonNullentity.getExperience());


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
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Teacher teacher) {
        String message = "The teacher to be updated should not be null";
        Teacher nonNullentity = Objects.requireNonNull(teacher, message);
        String sql = "UPDATE UniversitySchema.TEACHER "
                + "SET "
                + "NAME = ?, "
                + "LAST_NAME = ?, "
                + "MIDDLE_NAME = ?, "
                + "POSITION = ?, "
                + "EXPERIENCE = ? "
                + "WHERE "
                + "TEACHER_ID = ?";

        connection.ifPresent(tchr -> {
            try (PreparedStatement statement = tchr.prepareStatement(sql)) {

                statement.setString(1, nonNullentity.getName());
                statement.setString(2, nonNullentity.getLastName());
                statement.setString(3, nonNullentity.getMiddleName());
                statement.setString(4, nonNullentity.getPosition());
                statement.setInt(5, nonNullentity.getExperience());
                statement.setInt(6, nonNullentity.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the teacher updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
    }

    @Override
    public void delete(Teacher teacher) {
        String message = "The teacher to be deleted should not be null";
        Teacher nonNullentity = Objects.requireNonNull(teacher, message);

        String sql = "DELETE FROM UniversitySchema.TEACHER WHERE TEACHER_ID = ?;";

        connection.ifPresent(tchr -> {
            try (PreparedStatement statement = tchr.prepareStatement(sql)) {

                statement.setInt(1, nonNullentity.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the teacher deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
    }


    @Override
    public Collection<Teacher> getByName(String name) {
        Collection<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.TEACHER WHERE NAME = ?;";

        connection.ifPresent(tchr -> {

            try (PreparedStatement statement = tchr.prepareStatement(sql)) {

                statement.setString(1, name);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {

                    Integer id = resultSet.getInt("TEACHER_ID");
                    String firstName = resultSet.getString("NAME");
                    String lastName = resultSet.getString("LAST_NAME");
                    String middleName = resultSet.getString("MIDDLE_NAME");
                    String position = resultSet.getString("POSITION");
                    Integer experience = resultSet.getInt("EXPERIENCE");

                    Teacher teacher = new Teacher(id, firstName, lastName, middleName, position, experience);

                    teachers.add(teacher);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
        return teachers;
    }

    @Override
    public Collection<Teacher> getMostExperienced() {
        Collection<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.TEACHER WHERE EXPERIENCE >= 40;";

        connection.ifPresent(tchr -> {

            try (Statement statement = tchr.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {

                    Integer id = resultSet.getInt("TEACHER_ID");
                    String firstName = resultSet.getString("NAME");
                    String lastName = resultSet.getString("LAST_NAME");
                    String middleName = resultSet.getString("MIDDLE_NAME");
                    String position = resultSet.getString("POSITION");
                    Integer experience = resultSet.getInt("EXPERIENCE");

                    Teacher teacher = new Teacher(id, firstName, lastName, middleName, position, experience);

                    teachers.add(teacher);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
        return teachers;
    }

    @Override
    public Integer getAmount() {
        AtomicReference<Integer> res = new AtomicReference<>(0);
        String sql = "SELECT COUNT(*) AS \"TEACHERS_AMOUNT\" FROM UniversitySchema.TEACHER;";
        connection.ifPresent(tchr -> {
            try (Statement statement = tchr.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    res.set(resultSet.getInt("TEACHERS_AMOUNT"));
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
        return res.get();
    }

    @Override
    public Optional<Teacher> getMaxExperienced() {
        AtomicReference<Teacher> res = new AtomicReference<>();

        String sql = "SELECT TEACHER_ID, NAME, LAST_NAME, MIDDLE_NAME, POSITION, EXPERIENCE"
                + " FROM UniversitySchema.TEACHER WHERE EXPERIENCE = (SELECT MAX(EXPERIENCE) FROM UniversitySchema.TEACHER);";

        connection.ifPresent(tchr -> {
            try (Statement statement = tchr.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    Integer id = resultSet.getInt("TEACHER_ID");
                    String firstName = resultSet.getString("NAME");
                    String lastName = resultSet.getString("LAST_NAME");
                    String middleName = resultSet.getString("MIDDLE_NAME");
                    String position = resultSet.getString("POSITION");
                    Integer experience = resultSet.getInt("EXPERIENCE");

                    Teacher teacher = new Teacher(id, firstName, lastName, middleName, position, experience);
                    res.set(teacher);
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });

        return Optional.of(res.get());
    }

    @Override
    public Boolean professorize() {
        AtomicReference<Boolean> res = new AtomicReference<>();
        String sql = "UPDATE UniversitySchema.TEACHER SET POSITION = 'Professor' WHERE EXPERIENCE >= 40;";
        connection.ifPresent(tchr -> {
            try (PreparedStatement statement = tchr.prepareStatement(sql)) {
                statement.executeUpdate();
                res.set(true);
            } catch (SQLException e) {
                e.printStackTrace();
                res.set(false);
            }
        });
        return res.get();
    }

}
