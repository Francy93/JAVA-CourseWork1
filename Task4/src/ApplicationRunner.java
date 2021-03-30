import java.io.*;
import java.util.*;


//------------------------------------------- Class AlbumMaker---------------------------------------

class AlbumMaker{
    
    private final String ranking;
    private final String title;
    private final String artist;
    private final int    year;
    private final int    sales;
    private ArrayList<String[]> tracks = new ArrayList<>();
    
    //constructor
    AlbumMaker(String[] album){
        
        //splitting album data
        String[] arrSplit = album[0].split(":");
        
        this.ranking = arrSplit[0];
        this.title   = arrSplit[1];
        this.artist  = arrSplit[2];
        this.year    = Integer.parseInt(arrSplit[3]);
        String unit  = String.valueOf(arrSplit[4].charAt(arrSplit[4].length() -1));
        double amount;
        if(!unit.matches("[-+]?\\d*\\.?\\d+")){
            double number = Double.parseDouble(arrSplit[4].replaceFirst(".$",""));
            amount = "k".equals(unit.toLowerCase())? number*1000: number*1000000;
        }else{ amount = Double.parseDouble(arrSplit[4]); }
        this.sales = (int)amount;
        
        //getting the tracks
        for(int i = 1; i < album.length ;i++){
            //splitting track title from duration
            String[] trackDataSplit = album[i].split("(?=\\([-+]?\\d*\\.?\\d+)");
            
            trackDataSplit[0] = trackDataSplit[0].trim();
            tracks.add(trackDataSplit);
        }
    }
    
    public String getRanking(){
        return ranking;
    }
    public String getTitle(){
        return title;
    }
    public String getArtist(){
        return artist;
    }
    public int getYear(){
        return year;
    }
    public int getSales(){
        return sales;
    }
    public ArrayList<String[]> getTracks(){
        return tracks;
    }
    //generating track row to be printed
    private String trackPrint(String[] track, int longestTitle, int counter){
        String titleTrack = track[0];
        int fulling = longestTitle - titleTrack.length();
        String titleTrackComplete = titleTrack + new String(new char[fulling]).replace("\0", " ");
        
        String time = track[1].replace("(", "").replace(")", "");
        String[] timeArr = time.split(":");
        String minutes = timeArr[0];
        minutes = new String(new char[4 - minutes.length()]).replace("\0", " ") + minutes;
        String seconds = timeArr[1];
        seconds = new String(new char[4 - seconds.length()]).replace("\0", " ") + seconds;
        String index = String.valueOf(counter+1);
        index += new String(new char[4 - index.length()]).replace("\0", " ");
                    
        return "|"+index+"|"+titleTrackComplete+" |"+minutes+"|"+seconds+"|";
    }
    
    //generating and printing album data
    @Override
    public String toString(){
        int longestTitle = 0;
        String printable = "";
        String frame = "";
        
        //printing album data
        System.out.println("Ranking: "+getRanking());
        System.out.println("Album title: "+getTitle());
        System.out.println("Artist: "+getArtist());
        System.out.println("Year of release: "+getYear());
        System.out.println("Sales to date: "+getSales());
        System.out.println("\r\nTrack list:");
        
        //generating printable data
        for(int i =0; i < 2; i++){
            int counter = 0;
            for(String[] track: getTracks()){
                if(i == 0){
                    int titleLength = track[0].length();
                    longestTitle = titleLength > longestTitle? titleLength: longestTitle;
                    
                }else if(counter == 0 && i > 0){
                    String title = "Title";
                    int fulling = longestTitle - title.length();
                          
                    String titleComplete = title + new String(new char[fulling]).replace("\0", " ");
                    String titleBar ="|No. |"+titleComplete+" |Mins|Secs|\r\n";
                    frame = new String(new char[titleBar.length()-2]).replace("\0", "-")+"\r\n";
                        
                    printable += frame;
                    printable += titleBar;
                    printable += frame;
                    printable += trackPrint(track, longestTitle, counter)+"\r\n";
                    
                }else{
                    printable += trackPrint(track, longestTitle, counter)+"\r\n";
                }
                counter++;
            }
        }
        printable += frame;

        return printable;
    }
}

//------------------------------------------- Class CollectionMaker---------------------------------------


class CollectionMaker{
    
    public static ArrayList<AlbumMaker> collection = new ArrayList<AlbumMaker>();
    
    //constructor
    CollectionMaker(String fileName){
        String[][] data = fileReader(fileName);
        //fulling the collection array
        collectionGen(data);
    }
    
    //txt file reader
    private static String[][] fileReader(String fileName){
        Scanner scann = null;
        ArrayList<String[]> listColl = new ArrayList<>();
        ArrayList<String> tempData = new ArrayList<>();
        String delimiter = "----------------------------------------------------------------------------------";
        String[][] allData;

        try {
            scann = new Scanner(new File(fileName));
        } catch (IOException e) {
            System.out.println("WARNING, Exception while reading the file!");
        }
        
        while(scann.hasNextLine()){
            String line = scann.nextLine();
            if(delimiter.equals(line)){
                listColl.add(tempData.toArray(new String[0]));
                tempData.clear();
            }else{ tempData.add(line); }
            
        }
        scann.close();
        
        allData = listColl.toArray(new String[0][0]);
        
        return allData;
    }
    
    //generating a collection/array of albums/objects
    private static void collectionGen(String[][] allData){
        
        for(String[] singleData : allData){
        
        collection.add(new AlbumMaker(singleData));   
        }
    }
    
    //generating the rows of an album
    private static String rowsAlbums(int[] longest, String elementRow[]){
        String completeRow = "|";
        
        for(int i=0; i<elementRow.length; i++){
            int length = String.valueOf(elementRow[i]).length();
            longest[i] = longest[i] < 4? 4 : longest[i]; 
            int spacesLength = longest[i] > length? longest[i]-length: 0;
            
            //making a string of spaces to fit the table
            String spaces = new String(new char[spacesLength]).replace("\0", " ");
            //adding the spaces string to the titles string
            elementRow[i] = elementRow[i].equals(elementRow[0])?spaces+elementRow[i]:elementRow[i]+spaces;
            //making the row
            completeRow += " "+elementRow[i]+" |";
        }
        return completeRow;
    }
    //function to print a list of all the collection albums
    private static String listAlbums(){
        String table = "";
        int rankLonghest  = 0;
        int titleLongest  = 0;
        int artistLongest = 0;
        int yearLongest   = 0;
        int salesLongest  = 0;
        
        String yDelimiter  = "|";
        String xDelimiter  = "";
        String titleBar    = yDelimiter;
        String titleBarComponents[] = {"Rank", "Title", "Artist", "Year", "Sales"};
        
        //getting the longest elements
        for(AlbumMaker album: collection){
            int rankLength = String.valueOf(album.getRanking()).length();
            int titleLength = album.getTitle().length();
            int artistLength = album.getArtist().length();
            int yearLength = String.valueOf(album.getYear()).length();
            int salesLength = String.valueOf(album.getSales()).length();
           
            rankLonghest = rankLonghest < rankLength? rankLength: rankLonghest;
            titleLongest = titleLongest < titleLength? titleLength: titleLongest;
            artistLongest = artistLongest < artistLength? artistLength: artistLongest;
            yearLongest = yearLongest < yearLength? yearLength: yearLongest;
            salesLongest = salesLongest < salesLength? salesLength: salesLongest;
        }
        //intefer array of longhest elements size
        int titleBarLength[] = {rankLonghest, titleLongest, artistLongest, yearLongest, salesLongest};
        //getting the titleBar line
        titleBar = rowsAlbums(titleBarLength, titleBarComponents);
        //setting the delimeter
        xDelimiter = new String(new char[titleBar.length()]).replace("\0", "-");
        
        //printing the titleBar
        table += xDelimiter + "\r\n";
        table += titleBar + "\r\n";
        table += xDelimiter + "\r\n";
        
        //getting the rows
        for(AlbumMaker album: collection){
            String elementRow[] =  {album.getRanking(), album.getTitle(), album.getArtist(),
                                String.valueOf(album.getYear()),
                                String.valueOf(album.getSales())};
            //printing the Row
            table += rowsAlbums(titleBarLength, elementRow) + "\r\n";
        }
        //printing the last horizontal delimeter
        return table += xDelimiter + "\r\n\r\n";
    }
    
    @Override
    public String toString(){
        return listAlbums();
    }
}


//------------------------------------------- Main Class ---------------------------------------


public class ApplicationRunner {
    
    //loading the collection into the ram
    public static CollectionMaker albums;
    //reading the chosen file and storing the content into "albums"
    public static void fileName(String name){
        albums = new CollectionMaker(name);
    }
    
    //function to print a list of all the collection albums
    public static void listAlbums(){
       System.out.println(albums.toString());
    }
    
    //function to print album by ranking choice
    public static void selectAlbums(){
        System.out.println("\r\nNow enter the album rank from list [1 - "+albums.collection.size()+"]");
        System.out.println("Back to menu........0");
        System.out.print  ("\r\nEnter choice:>");
        
        //getting user choice
        Scanner input = new Scanner(System.in); 
        String number = input.next();
        System.out.println();
        
        //verifying the value is numeric
        if( number.matches("[-+]?\\d*\\.?\\d+") && 
            Integer.parseInt(number) <= albums.collection.size() &&
            Integer.parseInt(number) > 0){
            
            //iterating through all the albums
            for(AlbumMaker album: albums.collection){
                if(Integer.parseInt(album.getRanking()) == Integer.parseInt(number)){
                    //printing the album toString() outcome
                    System.out.println(album.toString());
                    break;
                }
            }
            
        }else if(!number.matches("[-+]?\\d*\\.?\\d+") || 
            Integer.parseInt(number) != 0){ 
            System.out.println("Wrong selection. Try again!");
            selectAlbums();
        }
        System.out.println();
    }
    //function to search titles
    public static void searchTitles(){
        System.out.println("\r\nNow enter a search word or phrase ");
        System.out.println("Back to menu........0");
        System.out.print  ("\r\nEnter word:>");
        
        String title="";
        Scanner scan = new Scanner(System.in);
        //getting multiple words including related spaces
        title+=scan.nextLine();
        System.out.println();
        
        if(!title.equals("0")){
            int counter = 0;
            int albumCounter = 0;
            //searching through all the albums
            for(AlbumMaker album: albums.collection){
                int tracksCounter = 0;
                boolean albumFound = false;
                
                //searching into the album
                for(String[]track :album.getTracks()){
                    String lowTitle = title.toLowerCase().trim();
                    String lowTrack = track[0].toLowerCase();
                    tracksCounter++;
                    
                    if(lowTrack.matches(".*\\b"+lowTitle+"\\b.*")){
                        if(!albumFound){
                            albumCounter++;
                            String delimiter = "-----";
                        
                            //printing the element found
                            System.out.println(delimiter);
                            System.out.println("ARTIST: "+album.getArtist()+" ALBUM: "+album.getTitle());
                            System.out.println("Matching song title(s):");
                            System.out.println(delimiter);
                        }
                        counter++;

                        System.out.println("Track "+tracksCounter+". "+track[0]);
                        albumFound = true;
                    }
                }
                if (albumFound){ System.out.println(); albumFound = false; }
            }
            if(counter == 0){
                System.out.println("\r\nNothing found. Try again!");
                searchTitles();
            }else{
                String tracKS = counter == 1? "TRACK": "TRACKS";
                String albuMS = albumCounter == 1? "ALBUM": "ALBUMS";
                System.out.println("\r\n"+counter+" "+tracKS+" IN "+albumCounter+" "+albuMS+" FOUND!");
            }
        }
        System.out.println();
    }
    
    //printing the menu
    public static void menu(){
        System.out.println("List albums.........1");
        System.out.println("Select album........2");
        System.out.println("Search titles.......3");
        System.out.println("Exit................0");
        System.out.print  ("\r\nEnter choice:>");
        
        Scanner input = new Scanner(System.in); 
        String choice = input.next();
        
        switch(choice.toLowerCase()){
            case "0": case "exit": case "Exit": case "EXIT":
                System.out.println("\r\nSuccessfully exited. Bye!\r\n");
                break;
            case "1":
                listAlbums();
                menu();
                break;
            case "2":
                selectAlbums();
                menu();
                break;
            case "3":
                searchTitles();
                menu();
                break;
            default: 
                System.out.println("\r\nWrong selection. Try again!\r\n");
                menu();
        }
    }
    
    
    //----------------------Main method-----------------------

    public static void main(String[] args) {
        
        //specify the txt file name
        fileName("albums.txt");
        
        //starting the program from menu
        menu();
    }  
}