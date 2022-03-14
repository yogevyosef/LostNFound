/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package DB;

import Model.Match;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcMatch {


    /*********************************************************************************************
     Function Name: getMatch
     Input: int serialFound, int serialLost
     Output: ResultSet
     Description: return a match from matches table
     ********************************************************************************************/
    public static ResultSet getMatch(int serialFound, int serialLost) throws SQLException {
        // System.out.println("--->JdbcMatch.java:getMatch");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM `matches` where serial_found=? and serial_lost=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setInt(1, serialFound);
            statement.setInt(2, serialLost);

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: insertMatch
     Input: int serialFound, int serialLost, Match.Status status
     Output: none
     Description: insert new match to matches table
     ********************************************************************************************/
    public static void insertMatch(int serialFound, int serialLost, Match.Status status) throws SQLException {
        // System.out.println("--->JdbcMatch.java:insertMatch");

        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO `matches`(`serial_found`, `serial_lost`, `status`) VALUES (?, ?, ?)";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setInt(1, serialFound);
            statement.setInt(2, serialLost);
            statement.setString(3, status.name());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }


    /*********************************************************************************************
     Function Name: deleteMatch
     Input: int serialFound, int serialLost
     Output: void
     Description: remove exist match from matches table
     ********************************************************************************************/
    public static void deleteMatch(int serialFound, int serialLost) throws SQLException {
        // System.out.println("--->JdbcMatch.java:deleteMatch");
        PreparedStatement statement = null;

        try {
            String query = "DELETE FROM `matches` WHERE serial_found =? AND serial_lost =?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setInt(1, serialFound);
            statement.setInt(2, serialLost);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }


    /*********************************************************************************************
     Function Name: getStatus
     Input: int serial
     Output: ResultSet
     Description: get statuses of all matches with an item
     ********************************************************************************************/
    public static ResultSet getStatus(int serial) throws SQLException {
        // System.out.println("--->JdbcMatch.java:getStatus");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM `matches` WHERE `serial_found`=? OR `serial_lost`=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setInt(1, serial);
            statement.setInt(2, serial);

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: setStatus
     Input: int serialFound, int serialLost, Match.Status status
     Output: none
     Description: set status of a match
     ********************************************************************************************/
    public static void setStatus(int serialFound, int serialLost, Match.Status status) throws SQLException {
        // System.out.println("--->JdbcMatch.java:setStatus");
        PreparedStatement statement = null;

        try {
            String query = "UPDATE `matches` SET `status`=? WHERE `serial_found`=? AND `serial_lost`=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, status.name());
            statement.setInt(2, serialFound);
            statement.setInt(3, serialLost);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }


    /*********************************************************************************************
     Function Name: getAllSerialFounds
     Input: none
     Output: ResultSet
     Description: return all founds from matches table
     ********************************************************************************************/
    public static ResultSet getAllSerialFounds() {
        // System.out.println("--->JdbcMatch.java:getAllSerialFounds");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT `serial_found` FROM `matches` WHERE `status` =?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, Match.Status.Hold.name());

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: getAllSerialLost
     Input: none
     Output: ResultSet
     Description: return all lost from matches table
     ********************************************************************************************/
    public static ResultSet getAllSerialLost() {
        // System.out.println("--->JdbcMatch.java:getAllSerialLost");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT `serial_lost` FROM `matches` WHERE `status` =?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setString(1, Match.Status.Hold.name());

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: getAllFoundsMatches
     Input: none
     Output: ResultSet
     Description: return all the founds matches of a lost
     ********************************************************************************************/
    public static ResultSet checkFoundMatch(int serialNumber) {
        // System.out.println("--->JdbcMatch.java:checkFoundMatch");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT `serial_found` FROM `matches` WHERE `serial_lost`=? And `status`=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setInt(1, serialNumber);
            statement.setString(2, Match.Status.Approved.name());

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    /*********************************************************************************************
     Function Name: getAllLostMatches
     Input: none
     Output: ResultSet
     Description: return all the lost matches of a found
     ********************************************************************************************/
    public static ResultSet checkLostMatch(int serialNumber) {
        // System.out.println("--->JdbcMatch.java:checkLostMatch");
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT `serial_lost` FROM `matches` WHERE `serial_found`=? AND `status`=?";
            statement = JdbcCommon.connection.prepareStatement(query);
            statement.setInt(1, serialNumber);
            statement.setString(2, Match.Status.Approved.name());

            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}



