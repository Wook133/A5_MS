package deVilliers_214062813;

import deVilliers_214062813.MotionSimulator.State;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class readCSV {

    private static final String NEW_LINE_SEPARATOR = "\n";

    public static ArrayList<State> readfile(String sfile)
    {
        ArrayList<State> Marks = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(sfile));
            String line = null;
            Scanner scanner = null;
            int index = 0;
            reader.readLine();
            while ((line = reader.readLine()) != null)
            {
                scanner = new Scanner(line);
                while (scanner.hasNext())
                {
                    String data = scanner.next();
                    String[] srow = data.split(",");
                    double x, y, a;
                    x = Double.valueOf(srow[0]);
                    y = Double.valueOf(srow[1]);
                    a = Double.valueOf(srow[2]);
                    State curDS = new State(x, y, a);
                    System.out.println(curDS.toString());
                    Marks.add(curDS);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Marks;
    }

    public static void writeCsvFile(String fileName, int i) throws IOException {
        FileWriter fileWriter = null;
        try
        {
            fileWriter = new FileWriter(fileName, true);
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(String.valueOf(i));
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public static void writeCsvFile(String fileName, String s) throws IOException {
        FileWriter fileWriter = null;
        try
        {
            fileWriter = new FileWriter(fileName, true);
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(s);
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

}
