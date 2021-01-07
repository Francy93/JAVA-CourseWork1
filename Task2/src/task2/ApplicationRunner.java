package task2;

import java.io.*;
import java.util.*;
import java.lang.reflect.Array;

public class ApplicationRunner {

    public static Scanner scan = new Scanner(System.in);                   //setting the briliant java System.input from terminal
    public static ArrayList<String> names = new ArrayList<String>();       //this is the list where strings from "scan" get stored
    public static ArrayList<String> namesnames = new ArrayList<String>();  //temp arraylist to be randomized
    public static String[][] coveredMatrix;                                //this is the array of the XXXXX matrixGen
    public static String[][] realMatrix;                                   //this is the array of the hidden matrixGen
    public static String[][] tempMatrix;                                   //this is the temp matrixGen to show just up to the last 2 selected cells 
    public static String axisYrow = "";                                    // string of Y row guide line
    public static String wrapper = "";                                     // string of "---------- ....."
    public static String strX = "";                                        // string made of "XXXXXXX...."
    public static int longest;                                             // here will be store the longestth of the longest word of a file
    public static int attempts = 0;                                        // number of attempts

    public static String firstStart() {
        System.out.println("---------------------------------");
        System.out.println("Welcome to the memory square game");
        System.out.println("---------------------------------" + "\r\n");
        System.out.println("Easy................1");
        System.out.println("Intermediate........2");
        System.out.println("Difficult...........3");
        System.out.println("Just give up now....0" + "\r\n");
        System.out.print("Select your preferred level > ");
        String StartExit = scan.next();
        System.out.println();
        return StartExit;
    }

    public static void reminder() {
        System.out.println("Remember to enter selections (square) as two digits representing row and column \r\nFor example, to select the square at row two and column three, enter \"23\" at the prompt (without quotes)\r\n\r\nStart the game by selecting 'Make a guess' below ...\r\n");
    }

    public static void list(int size) {
        names.clear();                      //this deletes the whole strings list every time this function is called
        Scanner scann = null;

        switch (size) {
            case 4:
                try {
                    scann = new Scanner(new File("small.txt"));
                } catch (IOException e) {
                }
                break;
            case 6:
                try {
                    scann = new Scanner(new File("medium.txt"));
                } catch (IOException e) {
                }
                break;
            case 8:
                try {
                    scann = new Scanner(new File("large.txt"));
                } catch (IOException e) {
                }
                break;
        }
        //------------------scanning the file line by line----------------------
        longest = 0;
        for (int i = 0; i < size * size / 2; i++) {         //while(scann.hasNextLine()){
            String line = scann.hasNextLine() ? scann.nextLine() : "EMPTY!";
            longest = line.length() > longest ? line.length() : longest;
            names.add(line);
        }
        scann.close();
        //------------------------names spaces adder----------------------------        
        for (int i = 0; i < names.size(); i++) {
            int lengthSpaces = (longest - (names.get(i).length()));
            for (int j = 0; j < lengthSpaces; j++) {
                names.set(i, names.get(i) + " ");                       //adding spaces to the name
            }
        }
    }

    public static void wrapAndSet() {
        int sizeSqrt = (int) Math.round(Math.sqrt(names.size() * 2));   //squert root of "name" list length
        namesnames.clear();
        namesnames.addAll(names);                                       //temp arraylist initialized
        namesnames.addAll(namesnames);                                  //temp arraylist doubled
        Collections.shuffle(namesnames, new Random());                  //temp arraylistnow randomized

        coveredMatrix = new String[sizeSqrt][sizeSqrt];
        realMatrix = new String[sizeSqrt][sizeSqrt];
        tempMatrix = new String[sizeSqrt][sizeSqrt];

        //--------------------------XXXs string maker---------------------------        
        strX = "";
        for (int i = 0; i < longest; i++) {
            strX = strX + "X";
        }

        //---------------------------axisYrow maker-----------------------------
        axisYrow = "  ";
        for (int i = 0; i < coveredMatrix.length; i++) {
            axisYrow += " ";
            for (int j = 0; j < longest - 2; j++) {
                axisYrow += " ";
            }
            axisYrow += (i + " |");
        }

        //----------------------------wrapper maker-----------------------------
        wrapper = "";
        for (int j = 0; j < (longest + 2) * coveredMatrix.length + 2; j++) {
            wrapper += "-";
        }
    }

    public static boolean choice(String number) {
        boolean dontExit = true;
        int num = 0;
        if (number.matches("^-?\\d+$")) {
            num = (Integer.parseInt(number) + 1) * 2;
        }

        switch (num) {
            case 2:
                System.out.print("Successfully ");
                dontExit = false;
                break;
            case 4:
                System.out.println("Easy/novice level selected \r\n--------------------------\r\n");
                reminder();
                list(num);   //"4" ... ((num+1)*2) means 4
                wrapAndSet();
                break;
            case 6:
                System.out.println("Intermediate level selected \r\n---------------------------\r\n");
                reminder();
                list(num);   //"6" ... ((num+1)*2) means 6
                wrapAndSet();
                break;
            case 8:
                System.out.println("Challenging level selected \r\n--------------------------\r\n");
                reminder();
                list(num);   //"8" ... ((num+1)*2) means 8
                wrapAndSet();
                break;
            default:
                System.out.println("Selection not contemplated. Please try again");
                choice(scan.next());
        }
        return dontExit;
    }

    public static void matrixGen() {
        for (int x = 0; x < coveredMatrix.length; x++) {
            for (int y = 0; y < coveredMatrix.length; y++) {
                Array.set(coveredMatrix[y], x, "[" + strX + "]");
                Array.set(tempMatrix[y], x, "[" + strX + "]");
                Array.set(realMatrix[y], x, "[" + namesnames.get(x * coveredMatrix.length + y) + "]");   //assign elements of the doubled list to realMatrix
            }
        }//matrixPrint(realMatrix); this is a commented debuggin function. It would immediately disclose the real matrix
    }

    public static void matrixPrint(String[][] array) {
        System.out.println(axisYrow);
        System.out.println(wrapper);
        for (int x = 0; x < coveredMatrix.length; x++) {
            System.out.print(x + " ");
            for (int y = 0; y < coveredMatrix.length; y++) {
                System.out.print(array[y][x]);
            }
            System.out.println("");
        }
        System.out.println(wrapper);
    }

    public static void matrixClone(String[][] array1, String[][] array2) {
        for (int x = 0; x < array1.length; x++) {
            for (int y = 0; y < array1.length; y++) {
                Array.set(array1[y], x, (String) Array.get(array2[y], x));
            }
        }
    }

    public static boolean noWin() {                            //this functions returns true if there is not eny XXXs string
        boolean notfool = false;
        for (String[] coveredX : coveredMatrix) {              //this is the X axis foreach
            for (String coveredY : coveredX) {                 //this is the Y axis foreach
                if (coveredY.equals("[" + strX + "]")) {       //check if the cell contains the XXXs string
                    notfool = true;
                }
            }
        }
        return notfool;
    }

    public static boolean makeGuess() {
        System.out.println("Make a guess........1");
        System.out.println("Exit................0\r\n");
        System.out.print("Enter choice:> ");
        String StartExit = scan.next();
        System.out.println();

        if ("0".equals(StartExit)) {
            System.out.print("Successfully ");
            return false;
        } else if ("1".equals(StartExit)) {
            return true;
        } else {
            System.out.println("Selection not contemplated, please try again\r\n");
            return makeGuess();
        }
    }

    public static void matrixReveal(String coordinates, String revealedCell) {
        int coordX = Integer.parseInt(Character.toString(coordinates.charAt(0)));
        int coordY = Integer.parseInt(Character.toString(coordinates.charAt(1)));

        if (tempMatrix[coordY][coordX].equals("[" + strX + "]")) {
            tempMatrix[coordY][coordX] = (String) Array.get(realMatrix[coordY], coordX);
            matrixPrint(tempMatrix);  //tempMatrix = coveredMatrix;
            if ("".equals(revealedCell)) {
                matrixReveal(getInput(2, ""), realMatrix[coordY][coordX]);
            } else if (revealedCell.equals(realMatrix[coordY][coordX])) {
                matrixClone(coveredMatrix, tempMatrix);
                System.out.println("Found a match\r\n");
                attempts++;
            } else {
                matrixClone(tempMatrix, coveredMatrix);
                System.out.println("Not a match\r\n");
                attempts++;
            }
        } else {
            System.out.println("Wrong selection, select one covered cell\r\n");
            matrixReveal(getInput(2, ""), revealedCell);
        }
    }

    public static String getInput(int step, String square) {
        if (null == square || "".equals(square)) {
            square = "";
        }
        switch (square.length()) {
            case 0:
                System.out.print("Select the " + step + "° square > ");
                square = square += scan.next();
                System.out.println();
                return getInput(step, square);
            case 1:
                if (square.matches("^-?\\d+$")) {
                    System.out.println("Almost there! You have to enter just one more digit now for 'Y' coordinate");
                    System.out.print("Enter the last digit > ");
                    square = square += scan.next();
                    System.out.println();
                    return getInput(step, square);
                } else {
                    System.out.println("Incorrect coordinate format entered. Please try again");
                    return getInput(step, "");
                }
            case 2:
                if (square.matches("^-?\\d+$") && Character.toString(square.charAt(0)).matches("^-?\\d+$")) {     //if square corrispond to a number
                    int numb0 = Integer.parseInt(Character.toString(square.charAt(0)));
                    int numb1 = Integer.parseInt(Character.toString(square.charAt(1)));
                    if (numb0 > coveredMatrix.length - 1 || numb0 < 0 || numb1 > coveredMatrix.length - 1 || numb1 < 0) {
                        System.out.println("Selection outside grid range. Please try again");
                        return getInput(step, "");
                    } else {
                        return square;
                    }
                } else {
                    System.out.println("Incorrect coordinate format entered. Please try again");
                    return getInput(step, "");
                }
            default:
                System.out.println("Wrong amount of digits. Please try again");
                return getInput(step, "");
        }
    }

    //---------------------------------MAIN-------------------------------------
    public static void main(String[] args) {
        if (choice(firstStart())) {                   //starting by printing first lines
            matrixGen();                              //Genereting matrixes
            while (noWin() && makeGuess()) {          //while noWin returns true and "makeGuess returns true (0 for false and 1 for true)
                matrixPrint(coveredMatrix);
                matrixReveal(getInput(1, ""), "");
                System.out.println("Number of guesses so far ... " + attempts + "\r\n");
            }
            if (!noWin()) {
                System.out.println("You completed the memory square in " + attempts + " guesses!!\r\n");
                System.out.print("Do you wanna try a different level? \r\nEnter Y to conferm or any other key to exit ");
                String restart = scan.next();
                System.out.println();
                if ("y".equals(restart) || "Y".equals(restart)) {
                    attempts = 0;
                    main(null);
                }
            }
        }
        System.out.println("exited!");
    }

}
