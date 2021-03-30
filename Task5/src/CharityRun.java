
import java.util.*;



abstract class CharityRun {
    
    static protected int index;
    private int number = index+1;
    protected String date;
    protected String startTime;
    protected Venue venue;

    
    
    public Integer getNumber(){
        return this.number;
    }
    
    
    public String getDate() {
        return date;
    }
    

    public String getStartTime() {
        return startTime;
    }

   
    public Venue getVenue() {
        return venue;
    }
}