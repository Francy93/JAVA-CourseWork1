
import java.util.*;
import java.io.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Francy
 */
public class Data extends DummyGen{
    
    //constructor
    Data(){
        eventsGen();
        competitorsGen();
        //parameter below determines the entries amount
        entriesGen(500);
    }
        

    
    //generating table
    private String rowGen(String[][] data){
        String row = "|";
        
        for(String[] element: data){
            String word = element[0];
            int longest = Integer.valueOf(element[1]);
            
            int spaces = longest < word.length()? 0: longest - word.length();
            word += new String(new char[spaces]).replace("\0", " ");

            row += " " + word + " |" ;
        }

        return row;
    }
    //find the longest of an array
    private int longest(String[] array){
        int theLongest = 0;
        for(String str: array){
            theLongest = str.length() > theLongest? str.length(): theLongest;
        }
        return theLongest;
    }
    
    //array of longest values generator
    private ArrayList<String> arrayOfLongest(ArrayList<ArrayList<String>> elements){
        ArrayList<String> longest = new ArrayList<>();
        //getting array of longest
        for(int j = 0; j < elements.size(); j++){

            if(longest.size() <= j){
                longest.add("0");
            }
            int lgstINT = longest(elements.get(j).toArray(new String[0]));
            String lgstSTR = String.valueOf(lgstINT);
            longest.set(j, lgstSTR); 
        }
        return longest;
    }
    
    //table body
    private String[] table(ArrayList<ArrayList<String>> elements, ArrayList<String> longest){
        
        String delimiter = "";
        String table ="";
        int elemSizeI = elements.get(0).size();
        for(int i=0; i < elemSizeI ;i++){
            ArrayList<String[]> row = new ArrayList<>();
            int elemSizeJ = elements.size();
            
            for (int j=0; j < elemSizeJ; j++){
                //indexOutOfBound guard
                if(j >= elements.size()){
                    elements.add(new ArrayList<>(Arrays.asList("")));
                }//indexOutOfBound guard
                if(i >= elements.get(j).size()){
                    elements.get(j).add("");
                }//indexOutOfBound guard
                if(j >= longest.size()){
                    longest.add("0");
                }
                String[] elem = {elements.get(j).get(i),longest.get(j)};
                row.add(elem);
            }
            
            String[][] line = row.toArray(new String[0][0]);
            String spacedRow = rowGen(line);
            
            
            //delimiter
            if(i == 0){
                for(int j=0; j<spacedRow.length(); j++ ){
                    delimiter += "-";
                }
            }
            table += spacedRow + "\r\n";
        }
        String[] body = {table, delimiter};
        return body;
    }
    
    //table body
    private String[] tableBody(ArrayList<ArrayList<String>> elements){
        //getting array of longest
        ArrayList<String>longest = arrayOfLongest(elements);
        
        return table(elements, longest);
    }
    
    //table generator
    private String tableGen(ArrayList<ArrayList<String>> elements){
        String title="", body="", table="";

        String[] bodyAndDelimiter = tableBody(elements);
        //getting the delimiter
        String delimiter = bodyAndDelimiter[1];
        //getting the title and body
        String[] array = bodyAndDelimiter[0].split("(?<=\r\n)");
        for(int i=0; i< array.length; i++){
            if(i>0){ body+=array[i]; }
            else{ title=array[i]; }
        }
        //the title
        table += delimiter + "\r\n";
        table += title;
        table += delimiter + "\r\n";

        //the body
        table += body;
        table += delimiter + "\r\n";
                
        return table;
    }
    
    
    
    
    //getting list events
    public Integer eventsList(){
        String[] title = {"No.", "Events"};
        ArrayList<ArrayList<String>> elements = new ArrayList<>();
        
        //adding the title into the list
        for (String title1 : title) {
            ArrayList<String> elem = new ArrayList<>();
            elem.add(title1);
            elements.add(elem);
        }
        //counting amount of titles rows
        int titlesRows = elements.get(0).size();
                
        //fulling array of elements
        for(int i=0; i < events.size(); i++){
            for(int j=elements.size(); j < title.length ;j++){
                elements.add(new ArrayList<>());
            }
            elements.get(0).add(String.valueOf(getEvent(i).getNumber()));
            elements.get(1).add(getEvent(i).getClass().getSimpleName());
        }
        

        //printing the table
        System.out.println(tableGen(elements));
        
        return elements.get(0).size() - titlesRows;
    }
    
    //getting list venues
    public ArrayList<ArrayList<String>>  venuesList(boolean mode){
        String[] title = {"No.", "Venues"};
        ArrayList<ArrayList<String>> elements = new ArrayList<>();
        
        //adding the title into the list
        for (String title1 : title) {
            ArrayList<String> elem = new ArrayList<>();
            elem.add(title1);
            elements.add(elem);
        }
        
        if (mode){
            //fulling array of entries venues
            for(int i=0; i < events.size(); i++){
                for(int j=elements.size(); j < title.length ;j++){
                    elements.add(new ArrayList<>());
                }

                String venueName = getEvent(i).getVenue().getName();
                boolean nameExisting = false;
                //ignoring copies
                for(String element  :elements.get(1)){
                    nameExisting = element.equals(venueName)? true: nameExisting;
                }
                //if not a copy, then store it into the array and increase printable list index by 1
                if(!nameExisting){
                    String lastIndex = i == 0? i+"": elements.get(0).get(elements.get(0).size() - 1);
                    String index = lastIndex.matches("\\d+")? (Integer.valueOf(lastIndex)+1)+"": lastIndex;
                    elements.get(0).add(index+"");
                    elements.get(1).add(venueName);
                }
            }
        }else{        
            //fulling array of venues
            for(int i=0; i<getVenues().size(); i++){
                elements.get(0).add(i+1+"");
                elements.get(1).add(getVenue(i));
            }
        }

        //printing the table
        System.out.println(tableGen(elements));
        
        return elements;
    }
    
    
    public void eventDetailes(int numEvent){
        boolean HalfM = false;
        
        String eventVenue = "";
        int entriesAmount = 0;
        //optional if event is HalfMarathon
        int waterStations = 0;
        
        //event details finder
        for(CharityRun event: this.events){
            if(event.getNumber() == numEvent){
                eventVenue = event.getVenue().getName();
                boolean waterCond = event instanceof HalfMarathon;
                HalfM = waterCond;
                waterStations = waterCond? ((HalfMarathon)event).getNumWaterStation(): 0;
            }
        }
        //event entries counter
        for(RunEntry entry: this.entries){
            if(entry.getEvent().getNumber() == numEvent){
                entriesAmount++;
            }
        }
        
        ArrayList<String> rowElem = new ArrayList<>(Arrays.asList(numEvent+"", eventVenue, entriesAmount+"", waterStations+""));
        
        ArrayList<String> title = new ArrayList<>(Arrays.asList("No.", "Venue", "Entries"));

        ArrayList<ArrayList<String>> elements = new ArrayList<>();
        
        if(HalfM){
            title.add("WaterS");
        }
        //adding the title into the list
        for (int i=0; i<title.size(); i++) {
            ArrayList<String> elem = new ArrayList<>(Arrays.asList(title.get(i), rowElem.get(i)));

            elements.add(elem);
        }
        
        
        
        //printing the table
        System.out.println(tableGen(elements));
    }
    
    public void venueDetails(String nameVenue){
        nameVenue = nameVenue.trim();
        boolean optionalCF = false;
        ArrayList<String> title = new ArrayList<>(Arrays.asList("Date", "StartTime"));
        ArrayList<ArrayList<String>> datasTimes = new ArrayList<>();
        //optional if venue is a park
        
        //adding the title into the list
        for (String title1 : title) {
            ArrayList<String> elem = new ArrayList<>();
            elem.add(title1);
            datasTimes.add(elem);
        }
        
        boolean elementAdded = false;
        for(CharityRun event: this.events){
            if(event.getVenue().getName().equals(nameVenue)){
                for(int i=datasTimes.size(); i < title.size() ;i++){
                    datasTimes.add(new ArrayList<>());
                }
                optionalCF = event.getVenue() instanceof Park;
                if(optionalCF && !elementAdded){
                    elementAdded = true;
                    datasTimes.add(new ArrayList<>());
                    datasTimes.get(datasTimes.size()-1).add("ChangingF");
                }
                ArrayList<String> row = new ArrayList<>(Arrays.asList(event.getDate(), event.getStartTime()));
                if(optionalCF){
                    //optional if venue is a park
                    row.add(((Park)event.getVenue()).getNumChangingFacilities()+"");
                }
                //fulling the dataTimes array
                for(int i=0; i<row.size() ;i++){
                    datasTimes.get(i).add(row.get(i));
                }
            }
        }
        
        if(datasTimes.get(0).size() <= 1){
            System.out.println("\r\n--------------------------------------");
            System.out.println("NO EVENTS FOUND FOR THE SELECTED VENUE");
            System.out.println("--------------------------------------");
        }else{
            //printing the table
            System.out.println(tableGen(datasTimes));
        }
    }
    
    //searching Competitors
    public Integer searchCompetitor(String search){
        int quantity = 0;
        search = search.toLowerCase();
        //great data
        ArrayList<ArrayList<ArrayList<String>>> data = new ArrayList<>();
        //horizontal
        ArrayList<String> mainTitle = new ArrayList<>(Arrays.asList("COMPETITORS", "EVENTS"));
        //vertical
        ArrayList<String> personTitle = new ArrayList<>(Arrays.asList("Name:", "Age:", "Entries:"));
        //horizontal
        ArrayList<String> eventsTitle = new ArrayList<>(Arrays.asList("No.", "Event","Date"));
        
        
        for(int i=0; i<competitors.size(); i++){
            String currentName = getCompetitor(i).getName().toLowerCase();
            boolean found = currentName.matches("(\\s*\\S*\\s)*"+search+"(\\s*\\S*)*");
            
            if(found){
                quantity++;
                
                String personName = getCompetitor(i).getName();
                ArrayList<ArrayList<String>> competitor = new ArrayList<>();
                ArrayList<String> compAttr = new ArrayList<>();
                compAttr.add(personName);
                compAttr.add(getCompetitor(i).getAge()+"");
                compAttr.add("00");
                
                // making array person's titles and attributes
                ArrayList<String> formattedCP = new ArrayList<>();
                ArrayList<ArrayList<String>> elements = new ArrayList<>(Arrays.asList(personTitle, compAttr));
                for(int j=0; j<elements.size(); j++){
                    competitor.add(new ArrayList<>(elements.get(j)));
                }
                
                //adding events titles bar
                ArrayList<ArrayList<String>> personEvents = new ArrayList<>();
                for(String title: eventsTitle){
                    personEvents.add(new ArrayList<>(Arrays.asList(title)));
                }
                
                //fulling the events table
                int eventQuantity = 0;
                for(int j=0; j<entries.size(); j++){
                    if(getEntry(j).getPerson().getName().equals(personName)){
                        eventQuantity++;
                        
                        personEvents.get(0).add(getEntry(j).getEvent().getNumber()+"");
                        personEvents.get(1).add(getEntry(j).getEvent().getClass().getSimpleName());
                        personEvents.get(2).add(getEntry(j).getEvent().getDate());
                        
                        //add a new blanck row to Person data section
                        if(competitor.get(0).size() < personEvents.get(0).size()+1){
                            for(ArrayList<String> section: competitor){
                                section.add("");
                            }
                        }
                    }
                }
                
                //formatting person details formattedCP
                competitor.get(1).set(2, eventQuantity+"");
                for(String row: tableBody(competitor)[0].split("\r\n")){
                    String newRow = row.replace("| ", "").replace(" |", "");
                    formattedCP.add(newRow.trim());
                }
                
                //formatting title and adding delimiter formattedPE
                ArrayList<String> formattedPE = new ArrayList<>();
                String tabBody = tableBody(personEvents)[0];
                String delimiter = tableBody(personEvents)[1];
                String[] rows = tabBody.split("\r\n");
                for(int j=0; j<rows.length; j++){
                    String trimmedRow = rows[j].substring(1).substring(0, rows[j].length() - 2);
                    formattedPE.add(trimmedRow.trim());
                    if(j == 0){
                        formattedPE.add(delimiter.substring(0, (delimiter.length() - 3)/2).replace("", " ").trim());
                    }
                }
                
                // fulling event table with place holders when no data was found
                if(personEvents.get(0).size() <= 1){
                    formattedPE.add("No events entries!");
                    
                    //adding an ampty value to person's table
                    if(formattedCP.size() < formattedPE.size()){
                        formattedCP.add("");
                    }
                }
                
                //making slot of a person and storing it into "data" array 
                ArrayList<ArrayList<String>> slot = new ArrayList<>();
                slot.add(formattedCP);
                slot.add(formattedPE);
                
                //storing it into "data" array 
                data.add(slot);
            }
        }
        
        
        //getting longest
        ArrayList<String> longest = new ArrayList<>();
        for(ArrayList<ArrayList<String>> singleSlot: data){
            ArrayList<String> tempLong = arrayOfLongest(singleSlot);
            
            for(int i=0; i<tempLong.size(); i++){
                String elem = tempLong.get(i);
                
                if(longest.size() <= i){
                    longest.add("0");
                }
                
                boolean cond = ((elem + longest.get(i)).matches("\\d+"));
                boolean greater = cond? Integer.valueOf(elem) > Integer.valueOf(longest.get(i)): false;
                if(greater){
                    longest.set(i, elem);
                }
            }
        }
        
        //bulding table
        String table = "";
        for(int i=0; i<data.size(); i++){
            ArrayList<ArrayList<String>> singleSlot = data.get(i);
            
            if(i == 0){
                //merging the titles to the rest of the data
                ArrayList<ArrayList<String>> titeled = new ArrayList<>();
                for(int j=0; j<mainTitle.size(); j++){
                    String title = mainTitle.get(j);
                    int numSpaces = (Integer.valueOf(longest.get(j))-title.length())/2;
                    String centeredTitle = new String(new char[numSpaces]).replace("\0", " ")+title;
                    titeled.add(new ArrayList<>(Arrays.asList(centeredTitle)));
                    titeled.get(j).addAll(singleSlot.get(j));
                }
                
                String delimiter = this.table(titeled, longest)[1];
                String[] array = this.table(titeled, longest)[0].split("(?<=\r\n)");
                String body="", title="";
                for(int j=0; j< array.length; j++){
                    if(j>0){ body+=array[j]; }
                    else{ title=array[j]; }
                }
                
                table += delimiter+"\r\n";
                table += title;
                table += delimiter+"\r\n";
                table += body;
                table += delimiter+"\r\n";
            }else{
                //table body
                table += this.table(singleSlot, longest)[0];
                //table delimiter
                table += this.table(singleSlot, longest)[1]+"\r\n";
            }
        }
        
        //printing the table
        System.out.println(table);
        
        return quantity;
    }
    
}






abstract class DummyGen {
    
    protected ArrayList<String>   venues     = new ArrayList<>();
    protected ArrayList<CharityRun> events      = new ArrayList<>();
    protected ArrayList<Competitor> competitors = new ArrayList<>();
    protected ArrayList<RunEntry>   entries     = new ArrayList<>();
            
    protected void venuesGen(){
        addVenue("Richmond Park");
        addVenue("Bushy Park");
        addVenue("Regent's Park");
        addVenue("Hyde Park");
        addVenue("Kensington Gardens");
        addVenue("Greenwich Park");
        addVenue("St. James's Park");
        addVenue("Green Park");
        addVenue("Romford");
        addVenue("Ilford");
        addVenue("Loughton");
        addVenue("Barking");
        addVenue("Dagenham");
        addVenue("West Ham");
        //no events venues
        addVenue("Woolwich");
        addVenue("Greenwich");
        addVenue("Plumstead");
    }
    
    protected void eventsGen(){
        venuesGen();
        
        addEvent(new FiveKmRun(getVenue(0),     12,   "01/02/2021", "8 AM"   ));
        addEvent(new FiveKmRun(getVenue(1),     10,   "01/03/2021", "9 AM"   ));
        addEvent(new FiveKmRun(getVenue(2),     8,    "01/04/2021", "10 AM"  ));
        addEvent(new FiveKmRun(getVenue(3),     6,    "01/05/2021", "11 AM"  ));
        addEvent(new FiveKmRun(getVenue(4),     4,    "01/06/2021", "12 PM"  ));
        addEvent(new FiveKmRun(getVenue(5),     2,    "01/07/2021", "1 PM"   ));
        addEvent(new FiveKmRun(getVenue(5),     3,    "01/08/2021", "1:30 PM"));
        
        addEvent(new HalfMarathon(getVenue(6),  1, 3, "01/08/2021", "2 PM"   ));
        addEvent(new HalfMarathon(getVenue(7),  2, 1, "01/09/2021", "3 PM"   ));
        addEvent(new HalfMarathon(getVenue(7),  2, 1, "01/10/2021", "3 PM"   ));
        
        addEvent(new HalfMarathon(getVenue(8),  3,    "01/10/2021", "4 PM"   ));
        addEvent(new HalfMarathon(getVenue(9),  4,    "01/11/2021", "5 PM"   ));
        addEvent(new HalfMarathon(getVenue(10), 5,    "01/12/2021", "6 PM"   ));
        addEvent(new HalfMarathon(getVenue(11), 6,    "01/01/2022", "7 PM"   ));
        addEvent(new HalfMarathon(getVenue(12), 7,    "01/02/2022", "8 PM"   ));
        addEvent(new HalfMarathon(getVenue(13), 8,    "01/03/2022", "8 AM"   ));
        addEvent(new HalfMarathon(getVenue(13), 8,    "01/04/2022", "9 AM"   ));
    }
    
    protected void competitorsGen(){
        addCompetitor (new Competitor("John One",       14));
        addCompetitor (new Competitor("Dodo Two",       22));
        addCompetitor (new Competitor("Frnacy Three",   28));
        addCompetitor (new Competitor("Mike Four",      22));
        addCompetitor (new Competitor("Daisy Five",     27));
        addCompetitor (new Competitor("Ortensa Six",    21));
        addCompetitor (new Competitor("Dede Seven",     21));
        addCompetitor (new Competitor("Simo Eight",     22));
        addCompetitor (new Competitor("Harry Nine",     25));
        addCompetitor (new Competitor("Max Ten",        50));
        addCompetitor (new Competitor("Sandy Eleven",   15));
        addCompetitor (new Competitor("Alex Twelve",    23));
        addCompetitor (new Competitor("Frank Thirteen", 65));
        addCompetitor (new Competitor("Mary Fourteen",  55));
        addCompetitor (new Competitor("Daniel Fifteen", 20));
        addCompetitor (new Competitor("Nelson Sixteen", 12));
        addCompetitor (new Competitor("Anna Seventeen", 38));
        addCompetitor (new Competitor("David Eighteen", 45));
        addCompetitor (new Competitor("Bob Nineteen",   45));
        addCompetitor (new Competitor("Tatiana Twenty", 38));
    }
    
    protected void entriesGen(int size){
        int overCicles = 1;
        int lastSize = size;
        if(size > competitors.size()){
            overCicles = Math.floorDiv(size, competitors.size())+1;
            lastSize = size%competitors.size();
            size = competitors.size();
        }
        //extra loop to repeat entries but inverted
        for(int j=0; j< overCicles ;j++){
            Collections.shuffle(competitors);
            Collections.reverse(competitors);
            
            if(j == overCicles-1){ size = lastSize; }
            for(int i=0; i< size ;i++){

                int es = events.size();
                int cs = competitors.size();

                double percC = (i*100.0)/cs;
                double percE = (es/100.0)*percC;
                int e = (Integer) Math.round((float)percE);

                //filtering entries
                if(!(getEvent(e) instanceof HalfMarathon && getCompetitor(i).getAge()<16)){
                    int currentNumber = getEvent(e).getNumber();
                    String currentPerson = getCompetitor(i).getName();
                    boolean registred = false;

                    //checking if conpetitor is registered
                    for(RunEntry entry :entries){
                        String personName = entry.getPerson().getName();
                        int eventNumber = entry.getEvent().getNumber();

                        if(personName.equals(currentPerson) && eventNumber == currentNumber){
                            registred = true;
                        }
                    }

                    //registering competitor if not yet registered
                    if(!registred){
                        addEntry(new RunEntry(getEvent(e), getCompetitor(i)));
                    }
                }
            }
        }
    }
    
    
    
    public CharityRun getEvent(int index){
        return events.get(index);
    }
    protected void addEvent(CharityRun event){
        events.add(event);
    }
    
    
    public Competitor getCompetitor(int index){
        return competitors.get(index);
    }
    protected void addCompetitor(Competitor person){
        competitors.add(person);
    }
    
    
    public RunEntry getEntry(int index){
        return entries.get(index);
    }
    protected void addEntry(RunEntry entry){
        entries.add(entry);
    }
    
    public ArrayList<String> getVenues(){
        return venues;
    }
    public String getVenue(int index){
        return venues.get(index);
    }
    protected void addVenue(String entry){
        venues.add(entry);
    }
}