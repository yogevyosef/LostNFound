/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import DB.JdbcCommon;
import DB.JdbcStart;
import Model.*;


public class StartInterface {


    /*********************************************************************************************
     Function Name: firstUse
     Input: none
     Output: none
     Description: create basis database
     ********************************************************************************************/
    public static void firstUse() throws IllegalAccessException, ClassNotFoundException, SQLException {
        JdbcCommon.openConnection();
        JdbcStart.creatUsersTable();
        JdbcStart.creatItemsTable();
        JdbcStart.creatMatchesTable();
        initTables();
        JdbcCommon.closeConnection();
    }


    /*********************************************************************************************
     Function Name: checkFirstUse
     Input: none
     Output: none
     Description: check if there is a basis database
     ********************************************************************************************/
    public static void checkDatabase() throws IllegalAccessException, ClassNotFoundException, SQLException {
        JdbcStart.openConnection();
        String database_name = "lostnfound";
        boolean isExist = false;
        ResultSet result = JdbcStart.connection.getMetaData().getCatalogs();
        while (result.next()) {
            String current_database = result.getString(1);
            if (database_name.equals(current_database))
                isExist = true;
        }

        if (isExist == false) {
            JdbcStart.createDB();
            System.out.println("--> New database established");
            JdbcStart.closeConnection();
            firstUse();
        } else {
            System.out.println("--> The database already exist");
            FoundLostItem.setInstanceCounter(getLastSerial());
        }
        JdbcStart.closeConnection();
    }


    /*********************************************************************************************
     Function Name: getLastSerial
     Input: none
     Output: int
     Description: return serial number of last item inserted to items table
     ********************************************************************************************/
    private static int getLastSerial() throws IllegalAccessException, ClassNotFoundException, SQLException {
        // System.out.println("--->MatchInterface.java:getMatch");
        JdbcCommon.openConnection();
        int lastSerial = 0;

        ResultSet result = JdbcStart.getLastSerial();
        if (result.next())
            lastSerial = result.getInt("MAX(`serial`)");

        // close connection
        result.getStatement().close();
        result.close();
        JdbcCommon.closeConnection();
        return lastSerial;
    }


    /*********************************************************************************************
     Function Name: initTables
     Input: none
     Output: none
     Description: insert first records to data base tables
     ********************************************************************************************/
    private static void initTables() throws IllegalAccessException, ClassNotFoundException, SQLException {
        // users table
        User admin = new User("Admin", "admin", "11111", "051-1111111", User.UserType.Manager);
        UserInterface.insertUser(admin);
        User user1 = new User("Yogev Yosef", "yogev", "312273410", "052-7833665", User.UserType.Manager);
        UserInterface.insertUser(user1);
        User user2 = new User("Eldor Shir", "eldor", "311362461", "054-3374175", User.UserType.Manager);
        UserInterface.insertUser(user2);
        User user3 = new User("Israel Israeli", "israel", "972", "097-2972972", User.UserType.Client);
        UserInterface.insertUser(user3);
        User user4 = new User("Eli Cohen", "elico", "123", "050-0505050", User.UserType.Client);
        UserInterface.insertUser(user4);
        User user5 = new User("Or Levi", "or", "123", "051-2345678", User.UserType.Client);
        UserInterface.insertUser(user5);
        User user6 = new User("Avi Ron", "avi", "123", "059-8765432", User.UserType.Client);
        UserInterface.insertUser(user6);
        User user7 = new User("Dani Din", "dani", "123", "050-6548520", User.UserType.Client);
        UserInterface.insertUser(user7);
        User user8 = new User("Dror Kugler", "dror", "123", "050-0000001", User.UserType.Client);
        UserInterface.insertUser(user8);


        // items table
        Item item1 = new Item("Hat", "black");
        Address address1 = new Address("Even-Yehuda", "HaBanim");
        LocalDateTime date11 = LocalDateTime.of(2019, Month.JULY, 28, 19, 30);
        LocalDateTime date12 = LocalDateTime.of(2019, Month.JULY, 29, 6, 00);
        FoundLostItem lost1 = new FoundLostItem(item1, user4, address1, date11, FoundLostItem.ItemType.Lost, new String("from Zara, with a little sign in the back"));
        FoundLostItem found1 = new FoundLostItem(item1, user3, address1, date12, FoundLostItem.ItemType.Found, new String("Hat from Zara"));
        ItemInterface.insertItem(lost1);
        ItemInterface.insertItem(found1);

        Item item2 = new Item("Dog", "white");
        Address address2 = new Address("Ra'anana", "Ahuza");
        LocalDateTime date21 = LocalDateTime.of(2019, Month.OCTOBER, 10, 18, 30);
        LocalDateTime date22 = LocalDateTime.of(2019, Month.OCTOBER, 10, 20, 00);
        FoundLostItem lost2 = new FoundLostItem(item2, user5, address2, date21, FoundLostItem.ItemType.Lost, new String("called Rexi, with red collar"));
        FoundLostItem found2 = new FoundLostItem(item2, user3, address2, date22, FoundLostItem.ItemType.Found, new String("with red collar"));
        ItemInterface.insertItem(lost2);
        ItemInterface.insertItem(found2);

        Item item3 = new Item("Wallet", "blue");
        Address address3 = new Address("Netanya", "Ha'Tor");
        LocalDateTime date31 = LocalDateTime.of(2020, Month.JANUARY, 1, 11, 11);
        // LocalDateTime date32 = LocalDateTime.of(2020, Month.JANUARY, 1, 15, 00);
        FoundLostItem lost3 = new FoundLostItem(item3, user4, address2, date31, FoundLostItem.ItemType.Lost, new String("with my ID and a 200 NIS"));
        //FoundLostItem found3 = new FoundLostItem(item3, user5, address2, date32, FoundLostItem.ItemType.Found, "with ID");
        ItemInterface.insertItem(lost3);
        //ItemInterface.insertItem(found3);


        // matches table
        MatchInterface.insertMatch(found1.getSerial(), lost1.getSerial());
        MatchInterface.insertMatch(found2.getSerial(), lost2.getSerial());

        Random r = new Random();

        // more users
        Vector<User> users = new Vector<>();
        User user;
        int length;
        String candidateChars = "1234567890abcdefghijklmnopqrstuvwxyz";
        String name;
        StringBuilder username;
        StringBuilder psw;
        StringBuilder phone;
        for (int i = 0; i < 30; i++) {
            name = new String(Name.values()[r.nextInt(Name.values().length)].toString() + " " + Name.values()[r.nextInt(Name.values().length)].toString());
            username = new StringBuilder();
            length = r.nextInt(10) + 10;
            for (int j = 0; j < length; j++)
                username.append(candidateChars.charAt(r.nextInt(26) + 10));
            psw = new StringBuilder();
            for (int j = 0; j < length; j++)
                psw.append(candidateChars.charAt(r.nextInt(candidateChars.length())));
            phone = new StringBuilder("05");
            phone.append(candidateChars.charAt(r.nextInt(10)));
            phone.append("-");
            for (int j = 0; j < 7; j++)
                phone.append(candidateChars.charAt(r.nextInt(10)));

            user = new User(name, username.toString(), psw.toString(), phone.toString(), User.UserType.Client);
            UserInterface.insertUser(user);
            users.add(user);
        }


        // more items
        Item item;
        Address address;
        LocalDate localDate;
        LocalDateTime dateTime;
        FoundLostItem.ItemType itemType;
        long minDay = LocalDate.of(2017, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        int category, color;
        int city, street;
        String details;
        for (int i = 0; i < 100; i++) {
            //item
            category = r.nextInt(Category.values().length);
            color = r.nextInt(Color.values().length);
            item = new Item(Category.values()[category].toString(), Color.values()[color].toString());
            // address
            city = r.nextInt(City.values().length);
            street = r.nextInt(Street.values().length);
            address = new Address(City.values()[city].getRealName(), Street.values()[street].getRealName());
            // date
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            localDate = LocalDate.ofEpochDay(randomDay);
            dateTime = LocalDateTime.of(localDate, LocalTime.of(r.nextInt(24), r.nextInt(60)));
            // type - assume there are usually more lost then founds
            if (r.nextInt(100) + 1 <= 25) // 1/4 probability
                itemType = FoundLostItem.ItemType.Found;
            else itemType = FoundLostItem.ItemType.Lost;
            // user
            user = users.elementAt(r.nextInt(users.size()));
            // details
            details = new String("bla bla bla bla"); //TODO - add some kinds of details strings (as enum ?) ?

            FoundLostItem foundLostItem = new FoundLostItem(item, user, address, dateTime, itemType, details);
            ItemInterface.insertItem(foundLostItem);
        }
    }


    private enum Name {
        Liam,
        Noah,
        William,
        James,
        Oliver,
        Benjamin,
        Elijah,
        Lucas,
        Mason,
        Logan,
        Alexander,
        Ethan,
        Jacob,
        Michael,
        Daniel,
        Henry,
        Jackson,
        Sebastian,
        Aiden,
        Matthew,
        Samuel,
        David,
        Joseph,
        Carter,
        Owen,
        Wyatt,
        John,
        Jack,
        Luke,
        Jayden,
        Dylan,
        Grayson,
        Levi,
        Isaac,
        Gabriel,
        Julian,
        Mateo,
        Anthony,
        Jaxon,
        Lincoln,
        Joshua,
        Christopher,
        Andrew,
        Theodore,
        Caleb,
        Ryan,
        Asher,
        Nathan,
        Thomas,
        Leo,
        Isaiah,
        Charles,
        Josiah,
        Hudson,
        Christian,
        Hunter,
        Connor,
        Eli,
        Ezra,
        Aaron,
        Landon,
        Adrian,
        Jonathan,
        Nolan,
        Jeremiah,
        Easton,
        Elias,
        Colton,
        Cameron,
        Carson,
        Robert,
        Angel,
        Maverick,
        Nicholas,
        Dominic,
        Jaxson,
        Greyson,
        Adam,
        Ian,
        Austin,
        Santiago,
        Jordan,
        Cooper,
        Brayden,
        Roman,
        Evan,
        Ezekiel,
        Xavier,
        Jose,
        Jace,
        Jameson,
        Leonardo,
        Bryson,
        Axel,
        Everett,
        Parker,
        Kayden,
        Miles,
        Sawyer,
        Jason,
        Declan,
        Weston,
        Micah,
        Ayden,
        Wesley,
        Luca,
        Vincent,
        Damian,
        Zachary,
        Silas,
        Gavin,
        Chase,
        Kai,
        Emmett,
        Harrison,
        Nathaniel,
        Kingston,
        Cole,
        Tyler,
        Bennett,
        Bentley,
        Ryker,
        Tristan,
        Brandon,
        Kevin,
        Luis,
        George,
        Ashton,
        Rowan,
        Braxton,
        Ryder,
        Gael,
        Ivan,
        Diego,
        Maxwell,
        Max,
        Carlos,
        Kaiden,
        Juan,
        Maddox,
        Justin,
        Waylon,
        Calvin,
        Giovanni,
        Jonah,
        Abel,
        Jayce,
        Jesus,
        Amir,
        King,
    }

    private enum Category {
        Wallet,
        Phone,
        Bag,
        Bottle,
        Dog,
        Cat,
        Book,
        Hat,
        Jacket;
    }

    private enum Color {
        black,
        green,
        red,
        white,
        yellow,
        purple
    }

    private enum City {
        Jerusalem("Jerusalem"),
        Tel_Aviv("Tel-Aviv"),
        Even_Yehuda("Even-Yehuda"),
        Netanya("Netanya"),
        Raanana("Ra'anana"),
        Ramat_Gan("Ramat-Gan"),
        Haifa("Haifa"),
        Ashdod("Ashdod");

        City(String realName) {
            this.realName = realName;
        }

        public String getRealName() {
            return realName;
        }

        private final String realName;
    }

    private enum Street {
        Begin("Begin"),
        Rabin("Rabin"),
        Ahuza("Ahuza"),
        Kaplan("Kaplan"),
        King_George("King-George"),
        Hillel("Hillel"),
        Tzahal("Tzahal"),
        HaLehi("Ha'Lehi"),
        Etzel("Etzel"),
        HaHagana("Ha'Hagana"),
        HaShalom("Ha'Shalom");

        Street(String realName) {
            this.realName = realName;
        }

        public String getRealName() {
            return realName;
        }

        private final String realName;
    }

}
