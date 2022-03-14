/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Model;

public class Client extends User {


    /*********************************************************************************************
     C'tors
     ********************************************************************************************/
    public Client(String name, String username, String phoneNumber, String password) {
        super(name, username, password, phoneNumber, UserType.Client);
    }
}
