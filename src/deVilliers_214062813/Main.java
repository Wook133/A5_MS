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
        TimedCommand tc = new TimedCommand(new Command(100,100), 5);
        listCommands.add(tc);
        for (int i = 0; i <= 9; i++)
        {
            tc = new TimedCommand(new Command(100,200), 3);
            listCommands.add(tc);
        }
        tc = new TimedCommand(new Command(100,100), 3);
        listCommands.add(tc);
        for (int i = 0; i <= 9; i++)
        {
            tc = new TimedCommand(new Command(200,100), 3);
            listCommands.add(tc);
        }
        tc = new TimedCommand(new Command(200,100), 3);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(200,150), 5);
        listCommands.add(tc);
        MotionSimulator ms = new MotionSimulator();
        listStates = ms.getPath(new State(-50,-0, 270), listCommands);
        VisualFrame vf = new VisualFrame(listStates);
        for (State s : listStates)
        {
            System.out.println(s.getX() + "; " + s.getY() + "; " +s.getA());
        }
	// write your code here
    }
}
