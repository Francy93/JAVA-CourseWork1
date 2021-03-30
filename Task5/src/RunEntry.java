
import java.util.*;



public class RunEntry {
    
    private CharityRun charityRun;
    private Competitor competitor;
    
    RunEntry(CharityRun event, Competitor person){
        this.charityRun = event;
        this.competitor = person;
    }

    


    public CharityRun getEvent() {
        return charityRun;
    }

   
    public Competitor getPerson() {
        return competitor;
    }

}