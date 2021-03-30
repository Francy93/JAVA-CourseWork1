
import java.util.*;


public class HalfMarathon extends CharityRun {

    private int numWaterStation;
    
    //Park case
    HalfMarathon(String name, int waterS, int changingF, String date, String time){
        this.index += 1;
        this.venue = new Park(name, changingF);
        this.numWaterStation = waterS;
        this.date = date;
        this.startTime = time;
    }
    //Town case
    HalfMarathon(String name, int waterS, String date, String time){
        this.index += 1;
        this.venue = new Town(name);
        this.numWaterStation = waterS;
        this.date = date;
        this.startTime = time;
    }
    
    

    public Integer getNumWaterStation() {
        return numWaterStation;
    }

}