package task1;

import java.io.*;

public class ApplicationRunner {

    public static String[] consonant
            = {"",  "b", "B", "c", "C", "d", "D", "f", "F", "g", "G",
                    "h", "H", "j", "J", "k", "K", "l", "L", "m", "M",
                    "n", "N", "p", "P", "q", "Q", "r", "R", "s", "S",
                    "t", "T", "v", "V", "w", "W", "x", "X", "z", "Z"};

    public static String[] vowel = {"", "a", "A", "e", "E", "i", "I",
                                        "o", "O", "u", "U", "y", "Y"};

    public static String sNumb =       "0";       //here will be collected the index number coordinate
    public static char   arrayCoord =  '0';       //here will be temporarly stored one coordinate letter at a time
    public static String translaction = "";       //here will be stored the whole decripted text
    
    



    public static void printer(char array, String index) {
        if (array == 'V' || array == 'v') {
            System.out.print(vowel[Integer.parseInt(index)]);
            translaction +=  vowel[Integer.parseInt(index)];
        } else {
            System.out.print(consonant[Integer.parseInt(index)]);
            translaction +=  consonant[Integer.parseInt(index)];
        }
    }

    public static void decripter(char X) {

        if (Character.isDigit(X)) {
            sNumb += Character.toString(X);
        } else {
            switch (X) {
                case 'C': case 'c': case 'V': case 'v':
                    if (!"0".equals(sNumb)) {
                        printer(arrayCoord, sNumb);
                        sNumb = "0";
                    }
                    arrayCoord = X;
                    break;
                default:
                    if (!"0".equals(sNumb)) {
                        printer(arrayCoord, sNumb);
                        sNumb = "0";
                    }
                    System.out.print(X);
                    translaction+= X;
                    break;
            }
        }
    }

    public static void funReader(String f) {

        try {
            FileReader reader = new FileReader(f);
            int character;

            while ((character = reader.read()) != -1) {
                decripter((char) character);

            }
            decripter((char) 0);
            reader.close();
            System.out.println("\r\n");

        } catch (IOException e) {
            System.out.println("error while reading the file.");
            e.printStackTrace();
        }
    }

    public static void funWriter(String fileName) {
        //----------------------file creating---------------------------
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("The file \""+fileName+"\" already exists.");
            }
        } catch (IOException e) {
            System.out.println("error while creating the file.");
            e.printStackTrace();
        }

        //----------------------text writing---------------------------
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(translaction);
            myWriter.close();
            System.out.println("Successfully wrote to the file: "+fileName);
        } catch (IOException e) {
            System.out.println("error while writing.");
            e.printStackTrace();
        }
    }
    
        
        
    public static void main(String[] args) {
        String inputFile  = "datafile.txt";    //enter here the name of the file you'd like to decript
        String outputFile = "translation.txt"; //enter here the name of the file wherebe to store the translation

        funReader(inputFile);                  //starting the decripting process
        funWriter(outputFile);                 //creating a file and storing the translaction in it
    }

}