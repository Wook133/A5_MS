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
        /**ArrayList<TimedCommand> listCommands = new ArrayList<>();
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
        listStates = ms.getPath(new State(-10,-10, 270), listCommands);
        VisualFrame vf = new VisualFrame(listStates);
        for (State s : listStates)
        {
            System.out.println(s.getX() + "; " + s.getY() + "; " +s.getA());
        }*/
        ArrayList<TimedCommand> listCommands = new ArrayList<>();
         TimedCommand tc = new TimedCommand(new Command(100,100), 5);
         listCommands.add(tc);
         for (int i = 0; i <= 9; i++)
         {
         tc = new TimedCommand(new Command(700,200), 1);
         listCommands.add(tc);
         }
         tc = new TimedCommand(new Command(100,200), 3);
         listCommands.add(tc);
         for (int i = 0; i <= 5; i++)
         {
         tc = new TimedCommand(new Command(0,-700), 1);
         listCommands.add(tc);
         }
         tc = new TimedCommand(new Command(500,200), 3);
         listCommands.add(tc);
         tc = new TimedCommand(new Command(600,300), 5);
         listCommands.add(tc);
     tc = new TimedCommand(new Command(300,600), 15);
     listCommands.add(tc);
         MotionSimulator ms = new MotionSimulator();
         listStates = ms.getPath(new State(-10,-10, 45), listCommands);
         VisualFrame vf = new VisualFrame(listStates);
         for (State s : listStates)
         {
         System.out.println(s.getX() + "; " + s.getY() + "; " +s.getA());
         }
       //    public populationControl(Double crossoverRate, Double mutationRate, Double growthMutation, Double shrinkMutation, Double swapMutation, Double replaceMutation, Double parameterMutation, Integer populationSize, Integer generations, Integer dimensions, Double xstart, Double xend, Double ystart, Double yend, Double angle) {
       /*populationControl pc = new populationControl (0.5, 0.6, 0.30, 0.2, 0.2, 0.10, 0.10, 100, 100, -50.0, 0.0, 0.0, 0.0, 270.0);
       pc.InitializePopulation();
       pc.Evolve();*/
       //pc.visualTest();
	// write your code here
    }
}
