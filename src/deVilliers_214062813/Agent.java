package deVilliers_214062813;

import deVilliers_214062813.MotionSimulator.Command;
import deVilliers_214062813.MotionSimulator.MotionSimulator;
import deVilliers_214062813.MotionSimulator.State;
import deVilliers_214062813.MotionSimulator.TimedCommand;

import java.util.ArrayList;

public class Agent {
    ArrayList<TimedCommand> listChromosomes = new ArrayList<>();
    ArrayList<State> listStates = new ArrayList<>();
    double xstart, xend, ystart, yend, angle;


    int timeSteps;

    public void initialize(double x1, double y1, double x2, double y2, double a)
    {
        Randomness r = new Randomness();
        int size = r.UniformPositiveRandomNaturalNumber(100.0);
        for (int i = 0; i <= size - 1; i++)
        {
            int left = r.UniformRandomInteger(700.0);
            int right = r.UniformRandomInteger(700.0);
            int time = r.UniformPositiveRandomNaturalNumber(8.0);
            listChromosomes.add(new TimedCommand(new Command(left, right), time));
        }
        pruneAgent();
        MotionSimulator ms = new MotionSimulator();
        xstart = x1;
        xend = x2;
        ystart = y1;
        yend = y2;
        angle = a;
        listStates = ms.getPath(new State(xstart, ystart, angle), listChromosomes);

    }

    public void pruneAgent(int maxSize)
    {
        numberSteps();
        while (timeSteps > maxSize)
        {
            Randomness r = new Randomness();
            double size = (listChromosomes.size()*1.0) - 1.0;
            int posremove = r.UniformPositiveRandomNaturalNumber(size);
            listChromosomes.remove(posremove);
            numberSteps();
        }
    }
    public void pruneAgent()
    {
        numberSteps();
        while (timeSteps > 100)
        {
            Randomness r = new Randomness();
            double size = (listChromosomes.size()*1.0) - 1.0;
            int posremove = r.UniformPositiveRandomNaturalNumber(size);
            listChromosomes.remove(posremove);
            numberSteps();
        }
    }


    public void numberSteps()
    {
        int timesteps = 0;
        for (TimedCommand cur : listChromosomes)
        {
            timesteps = timesteps + cur.getTime();
        }
        timeSteps = timesteps;
    }



}
