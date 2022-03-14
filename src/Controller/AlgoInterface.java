/***********************
 Yogev Yosef - 312273410
 Eldor Shir  - 311362461
 ***********************/

package Controller;

import Model.FoundLostItem;
import Model.Match;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.*;

public class AlgoInterface {

    /*********************************************************************************************
     Function Name: updateMatches
     Input: none
     Output: none
     Description: update the matches table using the hungarian algorithm and our algorithm of priority
     ********************************************************************************************/
    public static void updateMatches() throws IllegalAccessException, SQLException, ClassNotFoundException {
        // System.out.println("--->AlgoInterface.java:updateMatches");
        Vector<FoundLostItem> allFounds = ItemInterface.getAllFounds();
        Vector<FoundLostItem> allLost = ItemInterface.getAllLost();

        for (Iterator<FoundLostItem> iterator = allFounds.iterator(); iterator.hasNext(); ) {
            FoundLostItem found = iterator.next();
            if (MatchInterface.isApproved(found.getSerial()))
                iterator.remove();
        }

        for (Iterator<FoundLostItem> iterator = allLost.iterator(); iterator.hasNext(); ) {
            FoundLostItem lost = iterator.next();
            if (MatchInterface.isApproved(lost.getSerial()))
                iterator.remove();
        }

        double[][] priorityTable = new double[allFounds.size()][allLost.size()];

        // calculate priorityTable
        for (int found = 0; found < allFounds.size(); found++)
            for (int lost = 0; lost < allLost.size(); lost++)
                priorityTable[found][lost] = calcPriority(allFounds.get(found), allLost.get(lost));


        HungarianAlgo hungarianAlgo = new HungarianAlgo(priorityTable);
        int[] matches = hungarianAlgo.execute();


        // insert good matches to DB in sorted by priority
        HashMap<Match, Double> matchHashMap = new HashMap<>();
        for (int i = 0; i < matches.length; i++)
            if (priorityTable[i][matches[i]] > 0 && priorityTable[i][matches[i]] < 100)
                matchHashMap.put(new Match(allFounds.get(i), allLost.get(matches[i]), Match.Status.Hold), priorityTable[i][matches[i]]);
        HashMap<Match, Double> sortedMatchHashMap = sortByValue(matchHashMap);
        for (Map.Entry<Match, Double> en : sortedMatchHashMap.entrySet())
            MatchInterface.insertMatch(en.getKey().getFound().getSerial(), en.getKey().getLost().getSerial());
    }

    /*********************************************************************************************
     Function Name: calcPriority
     Input: FoundLostItem found, FoundLostItem lost
     Output: double
     Description: calculate a priority factor between a lost and a found
     ********************************************************************************************/
    private static double calcPriority(FoundLostItem found, FoundLostItem lost) throws IllegalAccessException, SQLException, ClassNotFoundException {
        double rank = 97; // "high" primary number
        // must conditions
        if (!found.getItem().getCategory().toLowerCase().equals(lost.getItem().getCategory().toLowerCase()))
            return Double.MAX_VALUE;

        // match status
        Match match = MatchInterface.getMatch(found.getSerial(), lost.getSerial());
        if (match != null) {// match denied or in hold
            if (match.getStatus() == Match.Status.Denied) // match already denied
                return Double.MAX_VALUE;
            else { // match in hold - give good priority
                rank /= 5;
                MatchInterface.deleteMatch(found.getSerial(), lost.getSerial());
            }
        }

        // color
        rank = found.getItem().getColor().toLowerCase().equals(lost.getItem().getColor().toLowerCase()) ? rank / 2 : rank * 2;

        // address
        if (found.getAddress().getCity().toLowerCase().equals(lost.getAddress().getCity().toLowerCase())) {
            rank /= 2;
            if (found.getAddress().getStreet().toLowerCase().equals(lost.getAddress().getStreet().toLowerCase()))
                rank /= 2;
        } else rank *= 2;

        // date - within 3 months the rank will decrease.
        long diffSec = (found.getFoundLostDate().toEpochSecond(OffsetDateTime.now().getOffset()) - lost.getFoundLostDate().toEpochSecond(OffsetDateTime.now().getOffset()));
        double diffParameter = ((double) diffSec) / (60 * 60 * 24 * 12 * 30); // the parameter is by div by 3months = 60s * 60m * 24d * 12mon * 30other parameter
        if (diffParameter < 0) // if found date is before lost date -> give less priority
            diffParameter = (-diffParameter) * 100;
        if (diffParameter == 0)
            diffParameter = 0.0001;
        rank *= diffParameter;

        return rank;
    }

    /*********************************************************************************************
     Function Name: sortByValue
     Input: HashMap<Match, Double> hm
     Output: HashMap<Match, Double>
     Description: sorted HashMap by value
     ********************************************************************************************/
    private static HashMap<Match, Double> sortByValue(HashMap<Match, Double> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Match, Double>> list =
                new LinkedList<Map.Entry<Match, Double>>(hm.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Match, Double>>() {
            public int compare(Map.Entry<Match, Double> o1,
                               Map.Entry<Match, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hash map
        HashMap<Match, Double> temp = new LinkedHashMap<Match, Double>();
        for (Map.Entry<Match, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
