
package task3;
import java.util.Scanner;         // Import the Scanner class
import java.text.SimpleDateFormat;// Import the DateFormat class
import java.util.Date;            // Import the Date class

// Interface class
interface Device {
    // Interface methods (do not have a body)
    public String get();
    public void set(int param);
}
// Subclass (inherit from Device)
class Altimeter implements Device{
    private String metres = "1500";
    @Override
    public String get() {
        return metres;
    }

    @Override //method for a possible future implementation of an altimeter setter
    public void set(int param) { 
        metres = metres;
    }
}
// Subclass (inherit from Device)
class Clock implements Device{
    private String time;    //formatted current time storage
    //constructor method
    Clock() {
        //getting the actual current time
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); //setting a format
        final Date now = new Date();                //getting the current time
        final String currentTime = sdf.format(now); //formatting the current time
        this.time = currentTime;                    //storing formatted current time
    }
    
    @Override
    public String get() {
        return this.time;
    }

    @Override
    public void set(int param) {
        //array of 3 parameters (hours, minutes and seconds)
        String array[] = time.split(":");
        //if the mode button has been pressed for the first time
        if(param == 2){
            //adding hours
            int hour = Integer.parseInt(array[0]);            //parsing the hours string to integer
            hour = hour < 23? ++hour : 0;                     //if greater then 23 set back to 0
            String newHour = String.format("%02d", hour);     //parsing the hours integer to string
            time = newHour +":"+ array[1] +":"+ array[2];     //storing the new clock value
        }//if the button mode has been pressed for the second time
        else if(param == 1){
            // adding minutes
            int minutes = Integer.parseInt(array[1]);          //parsing the minute string to integer
            minutes = minutes < 59? ++minutes: 0;              //if greater then 59 set back to 0
            String newMinutes = String.format("%02d", minutes);//parsing the minute integer to string
            time = array[0] +":"+ newMinutes +":"+ array[2];   //storing the new clock value
        }
    }
}




class HillWalker {
    // button: 0 stands for mode
    // button: 1 stands for set
    private int button = 1;
    private int setParam = 0;
    private final Clock hwClock = new Clock();
    private final Altimeter hwAltimeter = new Altimeter();

    private void display() {
        String editing = setParam >= 2? "(Hours)": "(Minutes)";   //print "Hours" or "Minutes" beside the "Edit mode text"
        String modeMessge = "Edit mode " +editing+ "\r\n";        //merge "Edit mode" and the what is going to be edited
        String timeText = "Time\r\n"+hwClock.get();               //setting time label
        String altitudeText = "Altitude\r\n"+hwAltimeter.get()+" (metres)";//setting altitude label
        String printable = button == 0? timeText: altitudeText;   //set the "printable" variable with the current mode value
        printable = setParam > 0? modeMessge+printable: printable;//merge "printable" and "modeMessage" values
        System.out.println(printable);                            //printing the "printable" result
    }

    private void mode() {
        //if set button has not been pressed then switch between clock and altimeter
        button = button == 0 && setParam == 0? 1:0;
        //If the mode is to time and set button has been pressed,
        // decrease "setParam" value so that to switch from setting hour to minutes
        setParam = button == 0 && setParam > 0? --setParam: setParam;
    }

    private void set() {
        //if the mode is to time, then set it
        if(button == 0){
            //setting the "setParam" to 3 so that no case below will be ran the first time set button is pressed
            setParam = setParam == 0? 3: setParam;
            hwClock.set(setParam);
            setParam = setParam == 3? 2: setParam;
        }
    }

    public void press(String button) {
        switch (button) {
            case "":
                break;
            case "0": case "O": case "o":
                mode();
                break;
            case "1": case "L": case "l": case "I": case "i":
                set();
                break;
            default:
                System.out.println("Button not contemplated");
                break;
        }
        display();
    }
}




public class ApplicationRunner {
    public static void main(String[] args) {
        // 0 stands for mode
        // 1 stands for set
        HillWalker myHW = new HillWalker();         // Create a HW object
        Scanner myScann = new Scanner(System.in);   // Create a Scanner object
        String text = "Press a button: ('E' to Exit) \r\n  0 for mode \r\n  1 to set \r\n";
        System.out.println(text);
        myHW.press("0");
        
        while(true){                                // neverending loop
            String userInput = myScann.nextLine();  // Read user input
            System.out.println(text);
            //condition to exit the infinite loop
            if ("e".equals(userInput)||"E".equals(userInput)){
                System.out.println("Successfully exited");
                break;
            }
            myHW.press(userInput);                  // sending the coice to the HW
        }
    }
}