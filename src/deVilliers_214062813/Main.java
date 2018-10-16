package deVilliers_214062813;

import deVilliers_214062813.MotionSimulator.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<State> listStates = new ArrayList<>();
        /*for (int i = 0; i <= 10; i++)
        {
            State cur = new State(i, i, 0);
            listStates.add(cur);
        }*/
        ArrayList<TimedCommand> listCommands = new ArrayList<>();
        for (int i = 0; i <= 1000; i++)
        {
            TimedCommand tc = new TimedCommand(new Command(200,75), 3);
            listCommands.add(tc);
        }
        MotionSimulator ms = new MotionSimulator();
        listStates = ms.getPath(new State(0,0, 30), listCommands);
        VisualFrame vf = new VisualFrame(listStates);
	// write your code here
    }
}
