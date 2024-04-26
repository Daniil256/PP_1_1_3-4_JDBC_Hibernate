package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util = new Util();
    private final String createUsersTableQuery = """
                      CREATE TABLE `test_db`.`users` (
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `name` VARCHAR(45) NOT NULL,
                        `last_name` VARCHAR(45) NOT NULL,
                        `age` INT(5) NOT NULL,
                        PRIMARY KEY (`id`));
                    """;
    private final String dropUsersTableQuery = "DROP TABLE `test_db`.`users`;";
    private final String saveUserQuery = "INSERT INTO `test_db`.`users` (`name`, `last_name`, `age`) VALUES (?, ?, ?);";
    private final String removeUserByIdQuery = "DELETE FROM users WHERE ID = ?;";
    private final String getAllUsersQuery = "SELECT * FROM users;";
    private final String cleanUsersTableQuery = "TRUNCATE users;";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(createUsersTableQuery);
        } catch (SQLException e) {
            System.out.println("createUsersTable " + e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(dropUsersTableQuery);
        } catch (SQLException e) {
            System.out.println("dropUsersTable " + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement state = util.getConnection().prepareStatement(saveUserQuery)) {
            state.setString(1, name);
            state.setString(2, lastName);
            state.setLong(3, age);
            state.execute();
            System.out.printf("User с именем — %s добавлен в базу данных \n", name);
        } catch (SQLException e) {
            System.out.println("saveUser " + e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement state = util.getConnection().prepareStatement(removeUserByIdQuery)) {
            state.setLong(1, id);
            state.execute();
        } catch (SQLException e) {
            System.out.println("removeUserById " + e);
        }
    }

    public List<User> getAllUsers() {
        try (Statement statement = util.getConnection().createStatement()) {
            ResultSet res = statement.executeQuery(getAllUsersQuery);
            List<User> userList = new ArrayList<>();
            while (res.next()) {
                User user = new User();
                user.setId(res.getLong("id"));
                user.setAge(res.getByte("age"));
                user.setName(res.getString("name"));
                user.setLastName(res.getString("last_name"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            System.out.println("getAllUsers " + e);
            return new ArrayList<>();
        }
    }

    public void cleanUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate(cleanUsersTableQuery);
        } catch (SQLException e) {
            System.out.println("cleanUsersTable " + e);
        }
    }
}
