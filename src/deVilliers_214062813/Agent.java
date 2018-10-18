package deVilliers_214062813;

import deVilliers_214062813.MotionSimulator.Command;
import deVilliers_214062813.MotionSimulator.MotionSimulator;
import deVilliers_214062813.MotionSimulator.State;
import deVilliers_214062813.MotionSimulator.TimedCommand;

import java.util.ArrayList;

public class Agent {
    ArrayList<TimedCommand> listChromosomes = new ArrayList<>();
    //ArrayList<State> listStates = new ArrayList<>();
    int timeSteps;
    Double TotalFitness;

    public Agent() {
        listChromosomes = new ArrayList<>();
        timeSteps = 0;
        TotalFitness = 100000000.0;
    }

    public void addCommand(TimedCommand tc)
    {
        listChromosomes.add(tc);
        pruneAgent();
    }
    public void setCommand(int i, TimedCommand tc)
    {
        if (i <= listChromosomes.size() - 1)
        {
            listChromosomes.set(i, tc);
        }
        pruneAgent();
    }

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
        pruneAgent();
        //MotionSimulator ms = new MotionSimulator();
       // listStates = ms.getPath(new State(xstart, ystart, angle), listChromosomes);

    }

    public void initializeBlank()
    {
        listChromosomes = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            listChromosomes.add(new TimedCommand(new Command(0, 0), 2));
        }
        pruneAgent();
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

    public ArrayList<TimedCommand> getListChromosomes() {
        return listChromosomes;
    }
    public void getRemoveChromosomes(int i) {
        if ((i >=0) && (i <= listChromosomes.size() -1))
        {
            listChromosomes.remove(i);
        }
    }

    public void setListChromosomes(ArrayList<TimedCommand> listChromosomes) {
        this.listChromosomes = listChromosomes;
    }

   /* public ArrayList<State> getListStates() {
        return listStates;
    }

    public void setListStates(ArrayList<State> listStates) {
        this.listStates = listStates;
    }*/

    public int getTimeSteps() {
        return timeSteps;
    }

    public void setTimeSteps(int timeSteps) {
        this.timeSteps = timeSteps;
    }
    public void setTotalFitness(Double totalFitness) {
        TotalFitness = totalFitness;
    }

    public Double getTotalFitness() {
        return TotalFitness;
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
