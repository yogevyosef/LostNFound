/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Controller;

import DB.JdbcCommon;
import DB.JdbcMatch;
import Model.FoundLostItem;
import Model.Match;
import Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MatchInterface {


    /*********************************************************************************************
     Function Name: getMatch
     Input: String int serialFound, int serialLost
     Output: Match
     Description: return user by username and password
     ********************************************************************************************/
    public static Match getMatch(int serialFound, int serialLost) throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->MatchInterface.java:getMatch");
        JdbcCommon.openConnection();
        Match match;

        ResultSet result = JdbcMatch.getMatch(serialFound, serialLost);
        if (result.next()) {
            String status = result.getString("status");
            match = new Match(ItemInterface.getItem(serialFound), ItemInterface.getItem(serialLost), Match.Status.valueOf(status));
        } else
            match = null;

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return match;
    }


    /*********************************************************************************************
     Function Name: insertMatch
     Input: int serialFound, int serialLost
     Output: void
     Description: add new match to matches table
     ********************************************************************************************/
    public static void insertMatch(int serialFound, int serialLost) throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->MatchInterface.java:insertMatch");
        JdbcCommon.openConnection();

        ResultSet result = JdbcMatch.getMatch(serialFound, serialLost);
        if (!result.next())
            JdbcMatch.insertMatch(serialFound, serialLost, Match.Status.Hold);

        JdbcCommon.closeConnection();
    }


    /*********************************************************************************************
     Function Name: deleteMatch
     Input: int serialFound, int serialLost
     Output: none
     Description: delete match from matches table
     ********************************************************************************************/
    public static void deleteMatch(int serialFound, int serialLost) throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->MatchInterface.java:deleteMatch");
        JdbcCommon.openConnection();

        JdbcMatch.deleteMatch(serialFound, serialLost);

        JdbcCommon.closeConnection();
    }


    /*********************************************************************************************
     Function Name: denyMatch
     Input: int serialFound, int serialLost
     Output: void
     Description: set match status to denied
     ********************************************************************************************/
    public static void denyMatch(int serialFound, int serialLost) throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->MatchInterface.java:denyMatch");
        JdbcCommon.openConnection();

        JdbcMatch.setStatus(serialFound, serialLost, Match.Status.Denied);

        JdbcCommon.closeConnection();
    }


    /*********************************************************************************************
     Function Name: isApproved
     Input: int serial
     Output: boolean
     Description: return true if the item is have an approved match
     ********************************************************************************************/
    public static boolean isApproved(int serial) throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->MatchInterface.java:isApproved");
        JdbcCommon.openConnection();
        ResultSet result = JdbcMatch.getStatus(serial);

        JdbcMatch.getStatus(serial);
        while (result.next()) {
            String tempStatus = result.getString("status");
            Match.Status status = Match.Status.valueOf(tempStatus);
            if (status == Match.Status.Approved) {
                JdbcCommon.closeConnection();
                return true;
            }
        }
        JdbcCommon.closeConnection();
        return false;
    }


    /*********************************************************************************************
     Function Name: approveMatch
     Input: int serialFound, int serialLost
     Output: void
     Description: set match status to approved
     ********************************************************************************************/
    public static void approveMatch(int serialFound, int serialLost) throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->MatchInterface.java:approveMatch");
        JdbcCommon.openConnection();
        JdbcMatch.setStatus(serialFound, serialLost, Match.Status.Approved);
        JdbcCommon.closeConnection();
    }


    /*********************************************************************************************
     Function Name: getAllFounds
     Input: none
     Output: Vector<FoundLostItem>
     Description: return all founds in matches table
     ********************************************************************************************/
    public static Vector<FoundLostItem> getAllFounds() throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->MatchInterface.java:getAllFounds");
        Vector<FoundLostItem> founds = new Vector<FoundLostItem>();
        JdbcCommon.openConnection();
        ResultSet result = JdbcMatch.getAllSerialFounds();

        while (result.next()) {
            founds.addElement(ItemInterface.getItem(result.getInt("serial_found")));
        }

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return founds;
    }


    /*********************************************************************************************
     Function Name: getAllLost
     Input: none
     Output: Vector<FoundLostItem>
     Description: return all lost in matches table
     ********************************************************************************************/
    public static Vector<FoundLostItem> getAllLost() throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->MatchInterface.java:getAllLost");
        Vector<FoundLostItem> lost = new Vector<FoundLostItem>();
        JdbcCommon.openConnection();
        ResultSet result = JdbcMatch.getAllSerialLost();

        while (result.next()) {
            lost.addElement(ItemInterface.getItem(result.getInt("serial_lost")));
        }

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return lost;
    }


    /*********************************************************************************************
     Function Name: checkFoundMatch
     Input: int serialNumber
     Output: User
     Description: return a user match if there is
     ********************************************************************************************/
    public static User checkFoundMatch(int serialNumber) throws IllegalAccessException, ClassNotFoundException, SQLException {
        System.out.println("--->MatchInterface.java:checkFoundMatch");
        JdbcCommon.openConnection();
        ResultSet result = JdbcMatch.checkFoundMatch(serialNumber);

        //case there is match
        if (result.next()) {
            FoundLostItem matchItem = ItemInterface.getItem(result.getInt("serial_found"));

            // close connection
            result.getStatement().close();
            result.close();
            JdbcCommon.closeConnection();
            return matchItem.getContact();
        }

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return null;
    }

    /*********************************************************************************************
     Function Name: checkLostMatch
     Input: int serialNumber
     Output: User match
     Description: return a user match if there is
     ********************************************************************************************/
    public static User checkLostMatch(int serialNumber) throws IllegalAccessException, ClassNotFoundException, SQLException {
        System.out.println("--->MatchInterface.java:checkLostMatch");
        JdbcCommon.openConnection();
        ResultSet result = JdbcMatch.checkLostMatch(serialNumber);

        //case there is a match
        if (result.next()) {
            FoundLostItem matchItem = ItemInterface.getItem(result.getInt("serial_lost"));

            // close connection
            result.getStatement().close();
            result.close();
            JdbcCommon.closeConnection();
            return matchItem.getContact();
        }

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return null;
    }
}
