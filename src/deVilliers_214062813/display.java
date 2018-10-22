package deVilliers_214062813;

import deVilliers_214062813.MotionSimulator.State;
import deVilliers_214062813.MotionSimulator.VisualFrame;

import java.util.ArrayList;

public class display {
    public static void main(String[] args) {
        ArrayList<State> targetStates = new ArrayList<>();
        readCSV rcv = new readCSV();
        targetStates = rcv.readfile("book2.csv");
        VisualFrame vf = new VisualFrame(targetStates);
        ArrayList<State> generatedStates = new ArrayList<>();
        readCSV rcv2 = new readCSV();
        generatedStates = rcv.readfile("s_random_197.csv");
        VisualFrame vf2 = new VisualFrame(generatedStates);
    }
}
