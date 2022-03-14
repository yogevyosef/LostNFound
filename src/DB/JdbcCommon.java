/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package DB;

import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcCommon {
    static java.sql.Connection connection = null;


    /*********************************************************************************************
     Function Name: openConnection
     Input: none
     Output: void
     Description: open connection with the DB
     ********************************************************************************************/
    public static void openConnection() throws IllegalAccessException, ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbUrl = "jdbc:mysql://localhost/lostnfound";
            connection = DriverManager.getConnection(dbUrl, "root", "");
            // System.out.println("Database connection established");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    /*********************************************************************************************
     Function Name: closeConnection
     Input: none
     Output: void
     Description: close connection with the DB
     ********************************************************************************************/
    public static void closeConnection() throws SQLException {
        connection.close();
        // System.out.println("Database connection disconnected");
    }

}
