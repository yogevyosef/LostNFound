/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package DB;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcStart {
    public static java.sql.Connection connection = null;


    /*********************************************************************************************
     Function Name: openConnection
     Input: none
     Output: void
     Description: open connection to the DB
     ********************************************************************************************/
    public static void openConnection() throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->JdbcStart.java:openConnection");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbUrl = "jdbc:mysql://localhost";
            connection = DriverManager.getConnection(dbUrl, "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    /*********************************************************************************************
     Function Name: closeConnection
     Input: none
     Output: void
     Description: close connection to the DB
     ********************************************************************************************/
    public static void closeConnection() throws SQLException {
        // System.out.println("--->JdbcStart.java:closeConnection");
        connection.close();
    }


    /*********************************************************************************************
     Function Name: createDB
     Input: none
     Output: none
     Description: create new database
     ********************************************************************************************/
    public static void createDB() throws SQLException {
        // System.out.println("--->JdbcStart.java:createDB");
        PreparedStatement statement = null;

        try {
            String query = "CREATE DATABASE lostnfound";
            statement = JdbcStart.connection.prepareStatement(query);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }


    /*********************************************************************************************
     Function Name: creatUsersTable
     Input: none
     Output: none
     Description: create new table - users
     ********************************************************************************************/
    public static void creatUsersTable() throws SQLException {
        // System.out.println("--->JdbcStart.java:creatUsersTable");
        PreparedStatement statement = null;

        try {
            String query = "CREATE TABLE `lostnfound`.`users` ( " +
                    "`name` VARCHAR(30) NOT NULL , " +
                    "`username` VARCHAR(30) NOT NULL , " +
                    "`password` VARCHAR(30) NOT NULL , " +
                    "`phone_number` VARCHAR(30) NOT NULL , " +
                    "`type` ENUM('Manager','Client') NOT NULL , " +
                    "PRIMARY KEY (`username`)) ENGINE = InnoDB;";
            statement = JdbcCommon.connection.prepareStatement(query);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }


    /*********************************************************************************************
     Function Name: creatItemsTable
     Input: none
     Output: none
     Description: create new table - items
     ********************************************************************************************/
    public static void creatItemsTable() throws SQLException {
        // System.out.println("--->JdbcStart.java:creatItemsTable");
        PreparedStatement statement = null;

        try {
            String query = "CREATE TABLE `lostnfound`.`items` ( " +
                    "`serial` INT NOT NULL , " +
                    "`category` VARCHAR(30) NOT NULL , " +
                    "`color` VARCHAR(30) NOT NULL , " +
                    "`contact_user` VARCHAR(30) NOT NULL , " +
                    "`city` VARCHAR(30) NOT NULL , " +
                    "`street` VARCHAR(30) NOT NULL , " +
                    "`date` DATETIME NOT NULL , " +
                    "`type` ENUM('Lost','Found') NOT NULL , " +
                    "`details` VARCHAR(300) NOT NULL , " +
                    "PRIMARY KEY (`serial`)) ENGINE = InnoDB;";
            statement = JdbcCommon.connection.prepareStatement(query);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }


    /*********************************************************************************************
     Function Name: creatMatchesTable
     Input: none
     Output: none
     Description: create new table - matches
     ********************************************************************************************/
    public static void creatMatchesTable() throws SQLException {
        // System.out.println("--->JdbcStart.java:creatMatchesTable");
        PreparedStatement statement = null;

        try {
            String query = "CREATE TABLE `lostnfound`.`matches` ( " +
                    "`serial_found` INT NOT NULL , " +
                    "`serial_lost` INT NOT NULL , " +
                    "`status` ENUM('Approved','Denied','Hold') NOT NULL) ENGINE = InnoDB;";
            statement = JdbcCommon.connection.prepareStatement(query);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }


    /*********************************************************************************************
     Function Name: getLastSerial
     Input: none
     Output: ResultSet
     Description: return last serial number
     ********************************************************************************************/
    public static ResultSet getLastSerial() {
        // System.out.println("--->JdbcStart.java:getLastSerial");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT MAX(`serial`) FROM `items` WHERE 1";
            statement = JdbcCommon.connection.prepareStatement(query);

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}