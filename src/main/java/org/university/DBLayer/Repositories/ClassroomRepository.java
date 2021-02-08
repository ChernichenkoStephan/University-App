package org.university.DBLayer.Repositories;

import org.university.DBLayer.JdbcConnection;
import org.university.Model.Classroom;
import org.university.DBLayer.Repositories.Interfaces.ClassroomDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassroomRepository implements ClassroomDatabase {

    private static final Logger LOGGER =
            Logger.getLogger(TeacherRepository.class.getName());

    private final Optional<Connection> connection;

    public ClassroomRepository() { this.connection = JdbcConnection.getConnection(); }

    @Override
    public Optional<Classroom> get(int id) {
        return connection.flatMap(clsr -> {
            Optional<Classroom> classroom = Optional.empty();
            String sql = "SELECT * FROM UniversitySchema.CLASSROOM WHERE CLASSROOM_ID = " + id;

            try (Statement statement = clsr.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    Integer building = resultSet.getInt("BUILDING");
                    Integer floor = resultSet.getInt("BUILDING_FLOOR");
                    String type = resultSet.getString("TYPE");
                    Integer capacity = resultSet.getInt("CAPACITY");

                    classroom = Optional.of(new Classroom(id, building, floor, type, capacity));

                    LOGGER.log(Level.INFO, "Found {0} in database", classroom.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return classroom;
        });
    }

    @Override
    public Collection<Classroom> getAll() {
        Collection<Classroom> classrooms = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.CLASSROOM";

        connection.ifPresent(clsr -> {
            try (Statement statement = clsr.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Integer id = resultSet.getInt("CLASSROOM_ID");
                    Integer building = resultSet.getInt("BUILDING");
                    Integer floor = resultSet.getInt("BUILDING_FLOOR");
                    String type = resultSet.getString("TYPE");
                    Integer capacity = resultSet.getInt("CAPACITY");

                    classrooms.add(new Classroom(id, building, floor, type, capacity));
                }

                LOGGER.log(Level.INFO, "Found {0} in database", classrooms);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return classrooms;

    }

    @Override
    public Optional<Integer> save(Classroom classroom) {
        String message = "The classroom to be added should not be null";
        Classroom nonNullentity = Objects.requireNonNull(classroom, message);
        String sql = "INSERT INTO "
                + "UniversitySchema.CLASSROOM(BUILDING, BUILDING_FLOOR, TYPE, CAPACITY) "
                + "VALUES(?, ?, ?, ?)";

        return connection.flatMap(clsr -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         clsr.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, nonNullentity.getBuilding());
                statement.setInt(2, nonNullentity.getFloor());
                statement.setString(3, nonNullentity.getType());
                statement.setInt(4, nonNullentity.getCapacity());

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
    public void update(Classroom classroom) {
        String message = "The classroom to be updated should not be null";
        Classroom nonNullentity = Objects.requireNonNull(classroom, message);
        String sql = "UPDATE UniversitySchema.CLASSROOM "
                + "SET "
                + "BUILDING = ?, "
                + "BUILDING_FLOOR = ?, "
                + "TYPE = ?, "
                + "CAPACITY = ? "
                + "WHERE "
                + "CLASSROOM_ID = ?";

        connection.ifPresent(clsr -> {
            try (PreparedStatement statement = clsr.prepareStatement(sql)) {

                statement.setInt(1, nonNullentity.getBuilding());
                statement.setInt(2, nonNullentity.getFloor());
                statement.setString(3, nonNullentity.getType());
                statement.setInt(4, nonNullentity.getCapacity());
                statement.setInt(5, nonNullentity.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the classroom updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Classroom classroom) {
        String message = "The classroom to be deleted should not be null";
        Classroom nonNullentity = Objects.requireNonNull(classroom, message);

        String sql = "DELETE FROM UniversitySchema.CLASSROOM WHERE CLASSROOM_ID = ?";

        connection.ifPresent(clsr -> {
            try (PreparedStatement statement = clsr.prepareStatement(sql)) {

                statement.setInt(1, nonNullentity.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the classroom deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    private Collection<Classroom> getByType(String type) {
        Collection<Classroom> classrooms = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.CLASSROOM WHERE TYPE LIKE ? ;";

        connection.ifPresent(cls -> {

            try (PreparedStatement statement = cls.prepareStatement(sql)) {

                statement.setString(1, type);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {

                    Integer id = resultSet.getInt("CLASSROOM_ID");
                    Integer building = resultSet.getInt("BUILDING");
                    String buildingType = resultSet.getString("TYPE");
                    Integer floor = resultSet.getInt("BUILDING_FLOOR");
                    Integer capacity = resultSet.getInt("CAPACITY");

                    classrooms.add(new Classroom(id, building, floor, buildingType, capacity));
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
        return classrooms;
    }

    @Override
    public Collection<Classroom> getLectures() {
        return getByType("Lecture%");
    }

    @Override
    public Collection<Classroom> getPractices() {
        return getByType("Practice%");
    }

    @Override
    public Collection<Classroom> getLaboratories() { return getByType("Laboratory%"); }

    @Override
    public Collection<Classroom> getComputers() {
        return getByType("Computer%");
    }

    @Override
    public Collection<Classroom> getSmall() {
        Collection<Classroom> classrooms = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.CLASSROOM WHERE CAPACITY > 1 AND CAPACITY < 27;";

        connection.ifPresent(cls -> {

            try (Statement statement = cls.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {

                    Integer id = resultSet.getInt("CLASSROOM_ID");
                    Integer building = resultSet.getInt("BUILDING");
                    Integer floor = resultSet.getInt("BUILDING_FLOOR");
                    String type = resultSet.getString("TYPE");
                    Integer capacity = resultSet.getInt("CAPACITY");

                    classrooms.add(new Classroom(id, building, floor, type, capacity));
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
        return classrooms;
    }
}
