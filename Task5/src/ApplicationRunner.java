import java.util.*;
import java.io.*;


public class ApplicationRunner {
    //getting data collections
    protected static Data data = new Data();
    
    //printing events list
    protected static void eventsList(){
        int max = data.eventsList();
        
        System.out.println("Select one by entering its number");
        System.out.println("Or just go BACK by entering.....0");
        System.out.print  ("\r\nEnter choice:>");
        
        Scanner input = new Scanner(System.in); 
        final String choice = input.next();
        String cond = choice.trim().chars().allMatch(x -> Character.isDigit(x))? choice.trim(): "";
        cond = !"".equals(cond)? Integer.valueOf(cond) > max? "": Integer.valueOf(cond)+"": cond;
        
        switch(cond){
            case "0":
                return;
            case "":
                System.out.println("\r\nWRONG CHOICE!");
                System.out.print("Enter any key to go back and Try again :>");
                input.next();
                System.out.println();
                eventsList();
                break;
            default:
                data.eventDetailes(Integer.valueOf(cond));
                System.out.print("\r\nEnter any key to go BACK :>");
                input.next();
                System.out.println();
                eventsList();
                break;
        }
    }
    //printing venues list
    protected static void venuesList(){
        ArrayList<ArrayList<String>> elements = data.venuesList(false);
        
        System.out.println("Select one by entering its number");
        System.out.println("Or just go BACK by entering.....0");
        System.out.print  ("\r\nEnter choice:>");
        
        Scanner input = new Scanner(System.in); 
        final String choice = input.next();
        String cond = choice.trim().chars().allMatch(x -> Character.isDigit(x))? choice.trim(): "";
        cond = cond.matches("\\d+")? Integer.valueOf(cond)+"": cond;
        String positives = "0*[1-9]+\\d*";
        
        if(cond.matches(positives)){
            for(int i=0; i<elements.get(0).size(); i++){
                String elemN = elements.get(0).get(i).trim();
                int elemNum = elemN.matches(positives)? Integer.valueOf(elemN): -1;
                int condNum = Integer.valueOf(cond);
                
                if(condNum == elemNum){
                    cond = elements.get(1).get(i);
                    break;
                }
            }
        }
        cond = cond.matches(positives)? "": cond;
        
        switch(cond){
            case "0":
                return;
            case "":
                System.out.println("\r\nWRONG CHOICE!");
                System.out.print("Enter any key to go back and Try again :>");
                input.next();
                System.out.println();
                venuesList();
                break;
            default:
                data.venueDetails(cond);
                System.out.print("\r\nEnter any key to go BACK :>");
                input.next();
                System.out.println();
                venuesList();
                break;
        }
    }
    protected static void searchEntries(){
        
        System.out.println("\r\nPlease, Enter the Name to be searched");
        System.out.println("Or just go BACK by entering.........0");
        System.out.print  ("\r\nEnter a name:>");
        
        String name="";
        Scanner scan = new Scanner(System.in);
        //getting multiple words including related spaces
        name+=scan.nextLine();
        System.out.println();
        
        if(!name.matches("0*")){
            int found = data.searchCompetitor(name);
            
            if(found == 0){
                System.out.println("No competitors found. Try again!");
            }else{
                System.out.println("\r\nEnter any key to go back :>");
                scan.nextLine();
            }
        }else{ return; }
        
        searchEntries();
    }
    //printing the menu
    protected static void menu(){
        System.out.println("\r\n\r\nPLEASE, SELECT A CHOICE:\r\n");
        System.out.println("List Event Information..............1");
        System.out.println("List Venue Details..................2");
        System.out.println("Search Competitor’s Event Entries...3");
        System.out.println("Exit................................0");
        System.out.print  ("\r\nEnter choice:>");
        
        Scanner input = new Scanner(System.in); 
        String choice = input.next();
        
        switch(choice.toLowerCase()){
            case "0": case "exit":
                System.out.println("\r\nSuccessfully exited. Bye!\r\n");
                break;
            case "1":
                eventsList();
                menu();
                break;
            case "2":
                venuesList();
                menu();
                break;
            case "3":
                searchEntries();
                menu();
                break;
            default: 
                System.out.println("\r\nWrong selection. Try again!\r\n");
                menu();
        }
    }
 
    
    public static void main(String[] args) {
        
        System.out.println("Welcome to the CharityRun dashboard");
        menu();
    }
}
