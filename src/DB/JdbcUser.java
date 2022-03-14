/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.User;

public class JdbcUser {


    /*********************************************************************************************
     Function Name: getUser
     Input: String username
     Output: ResultSet result
     Description: return user by username
     ********************************************************************************************/
    public static ResultSet getUser(String username) throws SQLException {
        // System.out.println("--->JdbcUser.java:getUser");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM Users WHERE username =?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, username);

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: getUser
     Input: String username, String password
     Output: ResultSet result
     Description: return user by username and password
     ********************************************************************************************/
    public static ResultSet getUser(String username, String password) throws SQLException {
        // System.out.println("--->JdbcUser.java:getUser");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM Users WHERE username=? AND password=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: insertUser
     Input: User user
     Output: void
     Description: add new user to database
     ********************************************************************************************/
    public static void insertUser(User user) throws SQLException {
        // System.out.println("--->JdbcUser.java:insertUser");
        PreparedStatement statement = null;

        try {
            //String query = "INSERT INTO Users (name, username, password, phone_number, type) VALUES (?, ?, ?, ?, ?, ?)";
            String query = "INSERT INTO `users`(`name`, `username`, `password`, `phone_number`, `type`) VALUES (?, ?, ?, ?, ?)";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getType().name());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }


    /*********************************************************************************************
     Function Name: deleteUser
     Input: User user
     Output: void
     Description: delete exist user from users table
     ********************************************************************************************/
    public static void deleteUser(User user) throws SQLException {
        // System.out.println("--->JdbcUser.java:deleteUser");
        PreparedStatement statement = null;

        try {
            String username = user.getUsername();
            String query = "DELETE FROM Users WHERE username =?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, username);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }


    /*********************************************************************************************
     Function Name: deleteUser
     Input: String username
     Output: void
     Description: delete exist user from users table by username
     ********************************************************************************************/
    public static void deleteUser(String username) throws SQLException {
        // System.out.println("--->JdbcUser.java:deleteUser");
        PreparedStatement statement = null;

        try {
            String query = "DELETE FROM Users WHERE username =?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, username);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }
}
