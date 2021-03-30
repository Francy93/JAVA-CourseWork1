
import java.util.*;



public class FiveKmRun extends CharityRun {
    
    
    
   FiveKmRun(String name, int changingF, String date, String time){
        this.index += 1;
        this.venue = new Park(name, changingF);
        this.date = date;
        this.startTime = time;
   }


}