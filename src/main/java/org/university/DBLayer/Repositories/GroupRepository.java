package org.university.DBLayer.Repositories;

import org.university.DBLayer.JdbcConnection;
import org.university.Model.Group;
import org.university.DBLayer.Repositories.Interfaces.GroupDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
new
SELECT STUDENTS_AMOUNT FROM UniversitySchema.STUDENTS_GROUP GROUP BY STUDENTS_AMOUNT HAVING STUDENTS_AMOUNT IN ( 20, 30 ) ;
 */

public class GroupRepository  implements GroupDatabase {

    private static final Logger LOGGER =
            Logger.getLogger(TeacherRepository.class.getName());

    private final Optional<Connection> connection;

    public GroupRepository() { this.connection = JdbcConnection.getConnection(); }


    @Override
    public Optional<Group> get(int id) {
        return connection.flatMap(gr -> {
            Optional<Group> group = Optional.empty();
            String sql = "SELECT * FROM UniversitySchema.STUDENTS_GROUP WHERE GROUP_ID = " + id;

            try (Statement statement = gr.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String faculty = resultSet.getString("FACULTY");
                    Integer amount = resultSet.getInt("STUDENTS_AMOUNT");

                    group = Optional.of(
                            new Group(id, faculty, amount));

                    LOGGER.log(Level.INFO, "Found {0} in database", group.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return group;
        });
    }

    @Override
    public Collection<Group> getAll() {
        Collection<Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.STUDENTS_GROUP";

        connection.ifPresent(grs -> {
            try (Statement statement = grs.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Integer id = resultSet.getInt("GROUP_ID");
                    String faculty = resultSet.getString("FACULTY");
                    Integer amount = resultSet.getInt("STUDENTS_AMOUNT");

                    groups.add(new Group(id, faculty, amount));

                }

                LOGGER.log(Level.INFO, "Found {0} in database", groups);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return groups;
    }

    @Override
    public Optional<Integer> save(Group group) {
        String message = "The group to be added should not be null";
        Group nonNullentity = Objects.requireNonNull(group, message);

        String sql = "INSERT INTO "
                + "UniversitySchema.STUDENTS_GROUP(FACULTY, STUDENTS_AMOUNT) "
                + "VALUES(?, ?)";

        return connection.flatMap(grp -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         grp.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullentity.getFaculty());
                statement.setInt(2, nonNullentity.getAmount());

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
    public void update(Group group) {
        String message = "The group to be updated should not be null";
        Group nonNullentity = Objects.requireNonNull(group, message);
        String sql = "UPDATE UniversitySchema.STUDENTS_GROUP "
                + "SET "
                + "FACULTY = ?, "
                + "STUDENTS_AMOUNT = ? "
                + "WHERE "
                + "GROUP_ID = ?";

        connection.ifPresent(tchr -> {
            try (PreparedStatement statement = tchr.prepareStatement(sql)) {

                statement.setString(1, nonNullentity.getFaculty());
                statement.setInt(2, nonNullentity.getAmount());
                statement.setInt(3, nonNullentity.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the group updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Group group) {
        String message = "The group to be deleted should not be null";
        Group nonNullentity = Objects.requireNonNull(group, message);

        String sql = "DELETE FROM UniversitySchema.STUDENTS_GROUP WHERE GROUP_ID = ?";

        connection.ifPresent(gr -> {
            try (PreparedStatement statement = gr.prepareStatement(sql)) {

                statement.setInt(1, nonNullentity.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the group deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public Collection<Group> getByFaculty(String faculty) {
        Collection<Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.STUDENTS_GROUP WHERE FACULTY = ?;";

        connection.ifPresent(tchr -> {

            try (PreparedStatement statement = tchr.prepareStatement(sql)) {

                statement.setString(1, faculty);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Integer id = resultSet.getInt("GROUP_ID");
                    Integer amount = resultSet.getInt("STUDENTS_AMOUNT");
                    groups.add(new Group(id, faculty, amount));
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
        return groups;
    }
}
