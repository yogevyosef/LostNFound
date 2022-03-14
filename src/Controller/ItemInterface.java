/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Controller;

import DB.JdbcCommon;
import DB.JdbcItem;
import Model.Address;
import Model.FoundLostItem;
import Model.Item;
import Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Vector;

public class ItemInterface {


    /*********************************************************************************************
     Function Name: getItem
     Input: int serial, User user
     Output: FoundLostItem
     Description: return an item from database according to serial number and user
     ********************************************************************************************/
    public static FoundLostItem getItem(int serial, User user) throws IllegalAccessException, ClassNotFoundException, SQLException {
        JdbcCommon.openConnection();
        ResultSet result = JdbcItem.getItem(serial, user.getUsername());

        FoundLostItem item = new FoundLostItem();
        if (result.next()) {

            item.setSerial(serial);
            item.setItem(new Item(result.getString("category"), result.getString("color")));
            item.setContact(user);      //UserInterface.getUser(result.getString("contact_user")));
            item.setAddress(new Address(result.getString("city"), result.getString("street")));
            item.setItemType(FoundLostItem.ItemType.valueOf(result.getString("type")));

            //extract date from sql
            Timestamp st = result.getTimestamp("date");
            LocalDateTime ldt = st.toLocalDateTime();
            item.setFoundLostDate(ldt);
        } else
            item = null;

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return item;
    }


    /*********************************************************************************************
     Function Name: getItem
     Input: int serial
     Output: FoundLostItem
     Description: return an item from database according to serial number
     ********************************************************************************************/
    public static FoundLostItem getItem(int serial) throws IllegalAccessException, ClassNotFoundException, SQLException {
        JdbcCommon.openConnection();
        ResultSet result = JdbcItem.getItem(serial);

        FoundLostItem item = new FoundLostItem();
        if (result.next()) {

            item.setSerial(serial);     //result.getInt("serial"));
            item.setItem(new Item(result.getString("category"), result.getString("color")));
            item.setContact(UserInterface.getUser(result.getString("contact_user")));
            item.setAddress(new Address(result.getString("city"), result.getString("street")));
            item.setItemType(FoundLostItem.ItemType.valueOf(result.getString("type")));
            item.setDetails(result.getString("details"));
            //extract date from sql
            Timestamp st = result.getTimestamp("date");
            LocalDateTime ldt = st.toLocalDateTime();
            item.setFoundLostDate(ldt);
        } else
            item = null;

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return item;
    }


    /*********************************************************************************************
     Function Name: insertItem
     Input: FoundLostItem item
     Output: none
     Description: insert an item to items table
     ********************************************************************************************/
    public static void insertItem(FoundLostItem item) throws IllegalAccessException, ClassNotFoundException, SQLException {
        JdbcCommon.openConnection();

        JdbcItem.insertItem(item);

        JdbcCommon.closeConnection();
    }


    /*********************************************************************************************
     Function Name: deleteProduct
     Input: String product_name, String product_company
     Output: none
     Description: delete product from database
     ********************************************************************************************/
/*  TODO: (update and) use or erase
    public static void deleteProduct(String product_name, String product_company) throws IllegalAccessException, ClassNotFoundException, SQLException {
        Product product = new Product(product_name, product_company);
        Vector<Supermarket> markets = SupermarketInterface.getAllMarkets();
        JdbcCommon.openConnection();
        JdbcProduct.deleteProduct(product);

        // delete from all dynamic tables of markets

        for (int i = 0; i < markets.size(); i++) {
            Supermarket market = markets.elementAt(i);
            JdbcSupermarket.deleteProductFromMarket(market, product);
        }
        JdbcCommon.closeConnection();
    }
*/


    /*********************************************************************************************
     Function Name: getAllFounds
     Input: none
     Output: Vector<FoundLostItem>
     Description: return all founds from items table
     ********************************************************************************************/
    public static Vector<FoundLostItem> getAllFounds() throws IllegalAccessException, ClassNotFoundException, SQLException {
        System.out.println("--->ItemInterface.java:getAllFounds");
        Vector<FoundLostItem> items = new Vector<FoundLostItem>();
        JdbcCommon.openConnection();
        ResultSet result = JdbcItem.getAllFounds();

        while (result.next()) {
            FoundLostItem currItem = new FoundLostItem();

            currItem.setSerial(result.getInt("serial"));
            currItem.setItem(new Item(result.getString("category"), result.getString("color")));
            currItem.setContact(UserInterface.getUser(result.getString("contact_user")));
            currItem.setAddress(new Address(result.getString("city"), result.getString("street")));
            currItem.setItemType(FoundLostItem.ItemType.Found);

            // extract date from sql
            Timestamp st = result.getTimestamp("date");
            LocalDateTime ldt = st.toLocalDateTime();
            currItem.setFoundLostDate(ldt);

            // adding the new element
            items.addElement(currItem);
        }

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return items;
    }


    /*********************************************************************************************
     Function Name: getAllLost
     Input: none
     Output: Vector<FoundLostItem>
     Description: return all lost from items table
     ********************************************************************************************/
    public static Vector<FoundLostItem> getAllLost() throws IllegalAccessException, ClassNotFoundException, SQLException {
        System.out.println("--->ItemInterface.java:getAllLost");
        Vector<FoundLostItem> items = new Vector<FoundLostItem>();
        JdbcCommon.openConnection();
        ResultSet result = JdbcItem.getAllLost();

        while (result.next()) {
            FoundLostItem currItem = new FoundLostItem();
            currItem.setSerial(result.getInt("serial"));
            currItem.setItem(new Item(result.getString("category"), result.getString("color")));
            currItem.setContact(UserInterface.getUser(result.getString("contact_user")));
            currItem.setAddress(new Address(result.getString("city"), result.getString("street")));
            currItem.setItemType(FoundLostItem.ItemType.Found);

            // extract date from sql
            Timestamp st = result.getTimestamp("date");
            LocalDateTime ldt = st.toLocalDateTime();

            currItem.setFoundLostDate(ldt);

            // adding the new element
            items.addElement(currItem);
        }

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return items;
    }


    /*********************************************************************************************
     Function Name: getAllUserFounds
     Input: String name
     Output: Vector<FoundLostItem>
     Description: return all founds of user from items table
     ********************************************************************************************/
    public static Vector<FoundLostItem> getAllUserFounds(String username) throws IllegalAccessException, ClassNotFoundException, SQLException {
        System.out.println("--->ItemInterface.java:getAllUserFounds");
        Vector<FoundLostItem> items = new Vector<FoundLostItem>();
        JdbcCommon.openConnection();
        ResultSet result = JdbcItem.getAllUserFounds(username);

        while (result.next()) {
            FoundLostItem currItem = new FoundLostItem();

            currItem.setSerial(result.getInt("serial"));
            currItem.setItem(new Item(result.getString("category"), result.getString("color")));
            currItem.setContact(UserInterface.getUser(result.getString("contact_user")));
            currItem.setAddress(new Address(result.getString("city"), result.getString("street")));
            currItem.setItemType(FoundLostItem.ItemType.Found);

            // extract date from sql
            Timestamp st = result.getTimestamp("date");
            LocalDateTime ldt = st.toLocalDateTime();
            currItem.setFoundLostDate(ldt);

            // adding the new element
            items.addElement(currItem);
        }

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return items;
    }


    /*********************************************************************************************
     Function Name: getAllUserLost
     Input: String name
     Output: Vector<FoundLostItem>
     Description: return all lost of user from items table
     ********************************************************************************************/
    public static Vector<FoundLostItem> getAllUserLost(String username) throws IllegalAccessException, ClassNotFoundException, SQLException {
        System.out.println("--->ItemInterface.java:getAllUserLost");
        Vector<FoundLostItem> items = new Vector<FoundLostItem>();
        JdbcCommon.openConnection();
        ResultSet result = JdbcItem.getAllUserLost(username);

        while (result.next()) {
            FoundLostItem currItem = new FoundLostItem();
            currItem.setSerial(result.getInt("serial"));
            currItem.setItem(new Item(result.getString("category"), result.getString("color")));
            currItem.setContact(UserInterface.getUser(result.getString("contact_user")));
            currItem.setAddress(new Address(result.getString("city"), result.getString("street")));
            currItem.setItemType(FoundLostItem.ItemType.Found);

            // extract date from sql
            Timestamp st = result.getTimestamp("date");
            LocalDateTime ldt = st.toLocalDateTime();

            currItem.setFoundLostDate(ldt);

            // adding the new element
            items.addElement(currItem);
        }

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return items;
    }
}