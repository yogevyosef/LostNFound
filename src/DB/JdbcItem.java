/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package DB;

import Model.FoundLostItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class JdbcItem {


    /*********************************************************************************************
     Function Name: getItem
     Input: int serial number, User username
     Output: ResultSet
     Description: return an item from items table
     ********************************************************************************************/
    public static ResultSet getItem(int serial, String username) throws SQLException {
        // System.out.println("--->JdbcItem.java:getItem");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM ITEMS where serial=? and contact_user=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setInt(1, serial);
            statement.setString(2, username);

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: getItem
     Input: int serial number
     Output: ResultSet
     Description: return an item from items table
     ********************************************************************************************/
    public static ResultSet getItem(int serial) throws SQLException {
        // System.out.println("--->JdbcItem.java:getItem");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM ITEMS where serial=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setInt(1, serial);

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: insertItem
     Input: FoundLostItem item
     Output: none
     Description: insert new found/lost to items table
     ********************************************************************************************/
    public static void insertItem(FoundLostItem item) throws SQLException {
        // System.out.println("--->JdbcItem.java:insertItem");
        PreparedStatement statement = null;

        try {
            String query = "INSERT INTO `items`(`serial`, `category`, `color`, `contact_user`, `city`, `street`, `date`, `type`, `details`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setInt(1, item.getSerial());
            statement.setString(2, item.getItem().getCategory());
            statement.setString(3, item.getItem().getColor());
            statement.setString(4, item.getContact().getUsername());
            statement.setString(5, item.getAddress().getCity());
            statement.setString(6, item.getAddress().getStreet());
            statement.setString(7, item.getFoundLostDate().toString());
            //convert LocalDateTime to Timestamp
            Timestamp ts = Timestamp.valueOf(item.getFoundLostDate());
            statement.setTimestamp(7,ts);
            statement.setString(8, item.getItemType().name());
            statement.setString(9, item.getDetails());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }


    /*********************************************************************************************
     Function Name: deleteItem
     Input: int serialItem
     Output: none
     Description: delete item from items table
     ********************************************************************************************/
    public static void deleteItem(int serialItem) throws SQLException {
        // System.out.println("--->JdbcItem.java:deleteItem");
        PreparedStatement statement = null;

        try {

            String query = "DELETE FROM ITEMS WHERE serial =?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setInt(1, serialItem);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }


    /*********************************************************************************************
     Function Name: getAllFounds
     Input: none
     Output: ResultSet
     Description: return all founds from items table
     ********************************************************************************************/
    public static ResultSet getAllFounds() {
        // System.out.println("--->JdbcItem.java:getAllFounds");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM ITEMS WHERE type=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, FoundLostItem.ItemType.Found.name());

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: getAllLost
     Input: none
     Output: ResultSet
     Description: return all lost from items table
     ********************************************************************************************/
    public static ResultSet getAllLost() {
        // System.out.println("--->JdbcItem.java:getAllLost");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM ITEMS WHERE type=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, FoundLostItem.ItemType.Lost.name());

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: getAllUserFounds
     Input: String username
     Output: ResultSet
     Description: return all the founds of user from items table
     ********************************************************************************************/
    public static ResultSet getAllUserFounds(String username) {
        // System.out.println("--->JdbcItem.java:getAllUserFounds");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM ITEMS WHERE type=? AND contact_user=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, FoundLostItem.ItemType.Found.name());
            statement.setString(2, username);

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: getAllUserLost
     Input: String username
     Output: ResultSet
     Description: return all the lost of user from items table
     ********************************************************************************************/
    public static ResultSet getAllUserLost(String username) {
        // System.out.println("--->JdbcItem.java:getAllUserLost");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM ITEMS WHERE type=? AND contact_user=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, FoundLostItem.ItemType.Lost.name());
            statement.setString(2, username);

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}



