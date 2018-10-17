package deVilliers_214062813;

import deVilliers_214062813.MotionSimulator.Command;
import deVilliers_214062813.MotionSimulator.TimedCommand;

import java.util.ArrayList;

public class Agent {
    ArrayList<TimedCommand> listChromosomes = new ArrayList<>();
    int timeSteps;

    public void initialize()
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
