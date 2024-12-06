/**************************
 * @file Proj4.java
 * @brief This program includes the main for timing and printing insert, search, and delete functions using Hash Table class to screen and to analysis.txt file
 * @author John Kaelber
 * @date December 4, 2024
 **************************/
import java.io.FileInputStream;//token test
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.io.*;

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        //ArrayList to store the Sleep data
        ArrayList<SleepData> sleepDataArrayList = new ArrayList<SleepData>();

        // FINISH ME
        // Read the file line by line
        while (inputFileNameScanner.hasNext() && sleepDataArrayList.size() < numLines) {
            String line = inputFileNameScanner.nextLine();
            String[] parts = line.split(","); // split the string into multiple parts


            //User ID, Age, Gender, Sleep Quality, Bedtime, Wake-up Time, Daily Steps, Calories Burned, Physical Activity, Dietary Habits, Sleep Disorders, Medication Usage
            // New sleep data object
            SleepData data = new SleepData(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    parts[2],
                    Integer.parseInt(parts[3]),
                    parts[4],
                    parts[5],
                    Integer.parseInt(parts[6]),
                    Integer.parseInt(parts[7]),
                    parts[8],
                    parts[9],
                    parts[10],
                    parts[11]
            );

            sleepDataArrayList.add(data); // add the data onto the ArrayList
        }
        inputFileNameStream.close();

        Collections.sort(sleepDataArrayList); //sort data

        PrintWriter analysispw = new PrintWriter(new FileWriter("analysis.txt", true)); //appends to analysis.txt file each time

        analysispw.println("Printing: Number of Lines Evaluated, Insert Time, Search Time, Delete Time for Already-Sorted, Shuffled, and Reversed Lists...");
        analysispw.append("\n");

        //creating sorted, shuffled, and reversed array lists of SleepData objects
        ArrayList<SleepData> sortedArrayList = new ArrayList<>(sleepDataArrayList);
        ArrayList<SleepData> shuffledArrayList = new ArrayList<>(sleepDataArrayList);
        ArrayList<SleepData> reversedArrayList = new ArrayList<>(sleepDataArrayList);

        //creating sorted, shuffled, and reverse lists
        Collections.sort(sortedArrayList); //sorting
        Collections.shuffle(shuffledArrayList); //shuffling
        Collections.sort(reversedArrayList, Collections.reverseOrder()); //reversing

        //new SeparateChainingHashTable Object
        SeparateChainingHashTable<SleepData> SleepDataHashTable = new SeparateChainingHashTable<>();

        //call method for sorted, shuffled, reversed
        calculatingData(sortedArrayList, SleepDataHashTable, "Sorted", analysispw);
        calculatingData(shuffledArrayList, SleepDataHashTable, "Shuffled", analysispw);
        calculatingData(reversedArrayList, SleepDataHashTable, "Reversed", analysispw);
        analysispw.append("\n");

        analysispw.close();
    }
    //method for timing and printing
    private static void calculatingData(ArrayList<SleepData> sleepDataArrayList, SeparateChainingHashTable<SleepData> SleepDataHashTable, String listName, PrintWriter analysispw) {
        //insertion calculation time
        long insertStart = System.nanoTime();
        for (SleepData data : sleepDataArrayList) {
            SleepDataHashTable.insert(data); //insert time in ns
        }

        long insertEnd = System.nanoTime();
        long insertTime = insertEnd - insertStart;

        //search calculation time
        long searchStart = System.nanoTime();
        for (SleepData data : sleepDataArrayList){
            SleepDataHashTable.contains(data); //remove time in ns
        }
        long searchEnd = System.nanoTime();
        long searchTime = searchEnd - searchStart;

        //deletion calculation time
        long deleteStart = System.nanoTime();
        for (SleepData data : sleepDataArrayList){
            SleepDataHashTable.remove(data); //search time in ns
        }
        long deleteEnd = System.nanoTime();
        long deleteTime = deleteEnd - deleteStart;

        SleepDataHashTable.makeEmpty(); //makes Hash completely empty after each run

        //printing data to screen
        System.out.println("NumLines: " + sleepDataArrayList.size() + " for List Type: " + listName + "...");
        System.out.println("Insert Time: " + insertTime + " ns");
        System.out.println("Search Time: " + searchTime + " ns");
        System.out.println("Delete Time: " + deleteTime + " ns");
        System.out.println("");

        //appending data to analysispw
        analysispw.append("Number of Lines Evaluated: " + sleepDataArrayList.size() + ", ");
        analysispw.append("List Type: " + listName + ", ");
        analysispw.append("Insert Time: " + insertTime + " ns, ");
        analysispw.append("Search Time: " + searchTime + " ns, ");
        analysispw.append("Delete Time: " + deleteTime + " ns");
        analysispw.append("\n");
    }
}
