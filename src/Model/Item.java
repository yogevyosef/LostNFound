/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Model;

public class Item {
    private String category;
    private String color;


    /*********************************************************************************************
     C'tors
     ********************************************************************************************/
    public Item(String category, String color) {
        this.category = category;
        this.color = color;
    }


    /*********************************************************************************************
     Getters
     ********************************************************************************************/
    public String getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }


    /*********************************************************************************************
     Setters
     ********************************************************************************************/
    public void setCategory(String category) {
        this.category = category;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
