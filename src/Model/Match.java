/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Model;

public class Match {
    private FoundLostItem found;
    private FoundLostItem lost;
    private Match.Status status;

    public enum Status {
        Approved,
        Denied,
        Hold
    }


    /*********************************************************************************************
     C'tors
     ********************************************************************************************/
    public Match(FoundLostItem found, FoundLostItem lost, Status status) {
        this.found = found;
        this.lost = lost;
        this.status = status;
    }


    /*********************************************************************************************
     Getters
     ********************************************************************************************/
    public FoundLostItem getFound() {
        return found;
    }

    public FoundLostItem getLost() {
        return lost;
    }

    public Status getStatus() {
        return status;
    }


    /*********************************************************************************************
     Setters
     ********************************************************************************************/
    public void setFound(FoundLostItem found) {
        this.found = found;
    }

    public void setLost(FoundLostItem lost) {
        this.lost = lost;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
