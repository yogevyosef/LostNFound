/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Model;

public class User {
    private String name;
    private String username;
    private String password;
    private String phoneNumber;
    private UserType type;

    public enum UserType{
        Manager,
        Client
    }


    /*********************************************************************************************
     C'tors
     ********************************************************************************************/
    public User(String name, String username, String password, String phoneNumber, UserType type) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username) {
        this.username = username;
    }


    /*********************************************************************************************
     Getters
     ********************************************************************************************/
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserType getType() {
        return type;
    }


    /*********************************************************************************************
     Setters
     ********************************************************************************************/
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}


