/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Controller;

import Model.*;
import DB.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInterface {


    /*********************************************************************************************
     Function Name: getUser
     Input: String username
     Output: User
     Description: return user by username
     ********************************************************************************************/
    public static User getUser(String username) throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->UserInterface.java:getUser");
        User user = new User(username);
        JdbcCommon.openConnection();

        ResultSet result = JdbcUser.getUser(username);
        if (result.next()) {
            user.setName(result.getString("name"));
            user.setPhoneNumber(result.getString("phone_number"));
            String tempType = result.getString("type");
            user.setType(User.UserType.valueOf(tempType));
        } else
            user = null;

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return user;
    }


    /*********************************************************************************************
     Function Name: getUser
     Input: String username, String password
     Output: User
     Description: return user by username and password
     ********************************************************************************************/
    public static User getUser(String username, String password) throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->UserInterface.java:getUser");
        User user = new User(username, password);
        JdbcCommon.openConnection();

        ResultSet result = JdbcUser.getUser(username, password);
        if (result.next()) {
            user.setName(result.getString("name"));
            user.setPhoneNumber(result.getString("phone_number"));
            String tempType = result.getString("type");
            user.setType(User.UserType.valueOf(tempType));
        } else
            user = null;

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return user;
    }


    /*********************************************************************************************
     Function Name: insertUser
     Input: User user
     Output: void
     Description: add new user to users table
     ********************************************************************************************/
    public static void insertUser(User user) throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->UserInterface.java:insertUser");
        JdbcCommon.openConnection();

        JdbcUser.insertUser(user);

        JdbcCommon.closeConnection();
    }


    /*********************************************************************************************
     Function Name: deleteUser
     Input: String username
     Output: void
     Description: delete user from users table
     ********************************************************************************************/
    public static void deleteUser(String username) throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->UserInterface.java:deleteUser");
        User user = new User(username);
        JdbcCommon.openConnection();

        JdbcUser.deleteUser(user);

        JdbcCommon.closeConnection();
    }
}
