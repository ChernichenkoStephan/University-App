package org.university.DBLayer.Repositories;

import org.university.DBLayer.JdbcConnection;
import org.university.Model.Department;
import org.university.DBLayer.Repositories.Interfaces.DepartmentDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentRepository  implements DepartmentDatabase {

    private static final Logger LOGGER =
            Logger.getLogger(TeacherRepository.class.getName());

    private final Optional<Connection> connection;

    public DepartmentRepository() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Department> get(int id) {
        return connection.flatMap(dp -> {
            Optional<Department> department = Optional.empty();
            String sql = "SELECT * FROM UniversitySchema.DEPARTMENT WHERE DEPARTMENT_ID = " + id;

            try (Statement statement = dp.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String name = resultSet.getString("NAME");
                    String shortName = resultSet.getString("SHORT_NAME");
                    Integer amount = resultSet.getInt("PLACES_AMOUNT");

                    department = Optional.of(new Department(id, name, shortName, amount));

                    LOGGER.log(Level.INFO, "Found {0} in database", department.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return department;
        });
    }

    @Override
    public Collection<Department> getAll() {
        Collection<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.DEPARTMENT";

        connection.ifPresent(dp -> {
            try (Statement statement = dp.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Integer id = resultSet.getInt("DEPARTMENT_ID");
                    String name = resultSet.getString("NAME");
                    String shortName = resultSet.getString("SHORT_NAME");
                    Integer amount = resultSet.getInt("PLACES_AMOUNT");

                    departments.add(new Department(id, name, shortName, amount));


                }
                LOGGER.log(Level.INFO, "Found {0} in database", departments);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return departments;
    }

    @Override
    public Optional<Integer> save(Department department) {
        String message = "The department to be added should not be null";
        Department nonNullentity = Objects.requireNonNull(department, message);

        String sql = "INSERT INTO "
                + "UniversitySchema.DEPARTMENT(NAME, SHORT_NAME, PLACES_AMOUNT) "
                + "VALUES(?, ?, ?)";

        return connection.flatMap(dp -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         dp.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullentity.getName());
                statement.setString(2, nonNullentity.getShortName());
                statement.setInt(3, nonNullentity.getAmount());

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
    public void update(Department department) {
        String message = "The department to be updated should not be null";
        Department nonNullentity = Objects.requireNonNull(department, message);
        String sql = "UPDATE UniversitySchema.DEPARTMENT "
                + "SET "
                + "NAME = ?, "
                + "SHORT_NAME = ?, "
                + "PLACES_AMOUNT = ? "
                + "WHERE "
                + "DEPARTMENT_ID = ?";

        connection.ifPresent(dp -> {
            try (PreparedStatement statement = dp.prepareStatement(sql)) {

                statement.setString(1, nonNullentity.getName());
                statement.setString(2, nonNullentity.getShortName());
                statement.setInt(3, nonNullentity.getAmount());
                statement.setInt(4, nonNullentity.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the department updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Department department) {
        String message = "The teacher to be deleted should not be null";
        Department nonNullentity = Objects.requireNonNull(department, message);

        String sql = "DELETE FROM UniversitySchema.DEPARTMENT WHERE DEPARTMENT_ID = ?";

        connection.ifPresent(dp -> {
            try (PreparedStatement statement = dp.prepareStatement(sql)) {

                statement.setInt(1, nonNullentity.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the department deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public Collection<Department> getByName(String name) {
        Collection<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM UniversitySchema.DEPARTMENT WHERE NAME = ?;";

        connection.ifPresent(tchr -> {

            try (PreparedStatement statement = tchr.prepareStatement(sql)) {

                statement.setString(1, name);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {

                    Integer id = resultSet.getInt("DEPARTMENT_ID");
                    String shortName = resultSet.getString("SHORT_NAME");
                    Integer amount = resultSet.getInt("PLACES_AMOUNT");

                    departments.add(new Department(id, name, shortName, amount));
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        });
        return departments;
    }
}
