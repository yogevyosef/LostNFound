/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Model;

public class Address {
    private String city;
    private String street;


    /*********************************************************************************************
     C'tors
     ********************************************************************************************/
    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }


    /*********************************************************************************************
     Getters
     ********************************************************************************************/
    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }


    /*********************************************************************************************
     Setters
     ********************************************************************************************/
    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
