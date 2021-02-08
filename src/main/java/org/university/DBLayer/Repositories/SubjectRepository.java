package org.university.DBLayer.Repositories;

import org.university.DBLayer.JdbcConnection;
import org.university.Model.Subject;
import org.university.DBLayer.Repositories.Interfaces.SubjectDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SubjectRepository implements SubjectDatabase {

    private static final Logger LOGGER =
            Logger.getLogger(TeacherRepository.class.getName());

    private final Optional<Connection> connection;

    public SubjectRepository() { this.connection = JdbcConnection.getConnection(); }

    @Override
    public Optional<Subject> get(int id) {
        return connection.flatMap(sb -> {
            Optional<Subject> subject = Optional.empty();
            String sql = "SELECT * FROM UniversitySchema.SUBJECT WHERE SUBJECT_ID = " + id;

            try (Statement statement = sb.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String firstName = resultSet.getString("NAME");
                    String shortName = resultSet.getString("SHORT_NAME");
                    Integer amount = resultSet.getInt("HOURS_AMOUNT");

                    subject = Optional.of(
                            new Subject(id, firstName, shortName, amount));

                    LOGGER.log(Level.INFO, "Found {0} in database", subject.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return subject;
        });
    }

    @Override
    public Collection<Subject> getAll() {
        Collection<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.SUBJECT";

        connection.ifPresent(sbs -> {
            try (Statement statement = sbs.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Integer id = resultSet.getInt("SUBJECT_ID");
                    String firstName = resultSet.getString("NAME");
                    String shortName = resultSet.getString("SHORT_NAME");
                    Integer amount = resultSet.getInt("HOURS_AMOUNT");

                    subjects.add(new Subject(id, firstName, shortName, amount));
                }
                LOGGER.log(Level.INFO, "Found {0} in database", subjects);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return subjects;
    }

    @Override
    public Optional<Integer> save(Subject subject) {
        String message = "The subject to be added should not be null";
        Subject nonNullentity = Objects.requireNonNull(subject, message);

        String sql = "INSERT INTO "
                + "UniversitySchema.SUBJECT(NAME, SHORT_NAME, HOURS_AMOUNT) "
                + "VALUES(?, ?, ?)";

        return connection.flatMap(tchr -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         tchr.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullentity.getName());
                statement.setString(2, nonNullentity.getShortName());
                statement.setInt(3, nonNullentity.getHoursAmount());

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
    public void update(Subject subject) {
        String message = "The subject to be updated should not be null";
        Subject nonNullentity = Objects.requireNonNull(subject, message);

        String sql = "UPDATE UniversitySchema.SUBJECT "
                + "SET "
                + "NAME = ?, "
                + "SHORT_NAME = ?, "
                + "HOURS_AMOUNT = ? "
                + "WHERE "
                + "SUBJECT_ID = ?";

        connection.ifPresent(tchr -> {
            try (PreparedStatement statement = tchr.prepareStatement(sql)) {

                statement.setString(1, nonNullentity.getName());
                statement.setString(2, nonNullentity.getShortName());
                statement.setInt(3, nonNullentity.getHoursAmount());
                statement.setInt(4, nonNullentity.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the subject updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Subject subject) {
        String message = "The subject to be deleted should not be null";
        Subject nonNullentity = Objects.requireNonNull(subject, message);

        String sql = "DELETE FROM UniversitySchema.SUBJECT WHERE SUBJECT_ID = ?";

        connection.ifPresent(tchr -> {
            try (PreparedStatement statement = tchr.prepareStatement(sql)) {

                statement.setInt(1, nonNullentity.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the subject deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public Collection<Subject> getByName(String name) {

        Collection<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.SUBJECT WHERE NAME = ?";

        connection.ifPresent(tchr -> {

            try (PreparedStatement statement = tchr.prepareStatement(sql)) {

                statement.setString(1, name);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Integer id = resultSet.getInt("SUBJECT_ID");
                    String firstName = resultSet.getString("NAME");
                    String shortName = resultSet.getString("SHORT_NAME");
                    Integer amount = resultSet.getInt("HOURS_AMOUNT");

                    subjects.add(new Subject(id, firstName, shortName, amount));
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
        return subjects;
    }

    @Override
    public Integer getFullCourse() {
        AtomicReference<Integer> res = new AtomicReference<>(0);
        String sql = "SELECT SUM(HOURS_AMOUNT) AS \"AMOUNT\" FROM UniversitySchema.SUBJECT;\n";

        connection.ifPresent(sub -> {
            try (Statement statement = sub.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    res.set(resultSet.getInt("AMOUNT"));
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
        return res.get();
    }
}
