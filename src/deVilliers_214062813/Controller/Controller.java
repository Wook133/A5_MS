package deVilliers_214062813.Controller;

import deVilliers_214062813.Distance;
import deVilliers_214062813.EuclideanDistance;
import deVilliers_214062813.MotionSimulator.Command;
import deVilliers_214062813.MotionSimulator.MotionSimulator;
import deVilliers_214062813.MotionSimulator.State;
import deVilliers_214062813.MotionSimulator.TimedCommand;
import deVilliers_214062813.Randomness;

import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    public ArrayList<TimedCommand> listChromosomes = new ArrayList<>();
    public ArrayList<State> listStates = new ArrayList<>();
    public ArrayList<Double> fitness = new ArrayList<>();
    public int timeSteps;
    public Double TotalFitness;
    public State initial;

    public Controller(State initial, ArrayList<State> targetStates) {
        this.initial = initial;
        listStates.add(initial);
        initialize();
        evaluateFitness(targetStates);
    }

    public Controller(State initial, ArrayList<TimedCommand> chromes, ArrayList<State> targetStates)
    {
        this.initial = initial;
        listChromosomes = new ArrayList<>();
        for (int i = 0; i <= chromes.size() -1; i++)
        {
            listChromosomes.add(chromes.get(i));
        }
        MotionSimulator ms = new MotionSimulator();
        listStates = new ArrayList<>();
        listStates = ms.getPath(initial, listChromosomes);
        countTimesteps();
        evaluateFitness(targetStates);
    }

    public void initialize()
    {
        listChromosomes = new ArrayList<>();
        Randomness r = new Randomness();
        for (int i = 0; i <= 90; i++)
        {
            int left = r.UniformRandomInteger(700.0);
            int right = r.UniformRandomInteger(700.0);
            int time = 1;
            System.out.println(left + " : " + right + " : " + time);
            listChromosomes.add(new TimedCommand(new Command(left, right), time));
        }
        MotionSimulator ms = new MotionSimulator();
        countTimesteps();
        listStates = ms.getPath(initial, listChromosomes);
        System.out.println(listStates.toString());
    }

    /*public void DistributeChromosomes()
    {
        ArrayList<TimedCommand> temp = new ArrayList<>();
        for (int i = 0; i <= listChromosomes.size() - 1; i ++)
        {
            int icounter = 1;
            while (icounter <= listChromosomes.get(i).getTime())
            {
                Command c = new Command(listChromosomes.get(i).getC().getLeft(), listChromosomes.get(i).getC().getRight());
                temp.add(new TimedCommand(c, 1));
                icounter = icounter +1;
            }
        }
        distributedChromosomes.clear();
        distributedChromosomes.addAll(temp);
    }*/

    public void countTimesteps()
    {
        timeSteps = 0;
        for (TimedCommand tc : listChromosomes)
        {
            timeSteps = timeSteps + tc.getTime();
        }
    }

    public void evaluateFitness(ArrayList<State> targetStates)
    {
        fitness = new ArrayList<>();
        for (int k = 0; k <= targetStates.size() - 1; k++)
        {
            if (k <= listStates.size() - 1) {
                Distance D = new EuclideanDistance(targetStates.get(k).getX(), listStates.get(k).getX(), targetStates.get(k).getY(), listStates.get(k).getY());
                fitness.add(D.calculateDistance());
            }
            else
            {
                fitness.add(1000.0);
            }
        }
        Double d = 0.0;
        for (Double f : fitness)
        {
            d = d + f;
        }
        TotalFitness = d;


       /* if (listStates.size() >= targetStates.size()) {
            for (int i = 0; i <= targetStates.size() - 1; i++) {
                double x1 = targetStates.get(i).getX();
                double y1 = targetStates.get(i).getY();
                double x2 = listStates.get(i).getX();
                double y2 = listStates.get(i).getY();
                Distance d = new EuclideanDistance(x1, x2, y1, y2);
                fitness.add(d.calculateDistance());
            }
            for (int j = targetStates.size(); j <= listStates.size() - 1; j++)
            {
                fitness.add(10000.0);
            }
        }
        else
        {
            for (int i = 0; i <= listStates.size() - 1; i++) {
                double x1 = targetStates.get(i).getX();
                double y1 = targetStates.get(i).getY();
                double x2 = listStates.get(i).getX();
                double y2 = listStates.get(i).getY();
                Distance d = new EuclideanDistance(x1, x2, y1, y2);
                fitness.add(d.calculateDistance());
            }
            for (int j = listStates.size(); j <= targetStates.size() - 1; j++)
            {
                fitness.add(10000.0);
            }
        }*/

    }

    @Override
    public String toString() {
        return "Controller{" +
               // "listChromosomes=" + listChromosomes +
                ", listStates=" + listStates +
                ", fitness=" + fitness +
                ", timeSteps=" + timeSteps +
                ", TotalFitness=" + TotalFitness +
                ", initial=" + initial +
                '}';
    }

    public int positionFittest()
    {
        int n = 0;
        Double d = Double.MAX_VALUE;
        for (int i = 0; i <= fitness.size() - 1; i++)
        {
            if (fitness.get(i) < d)
            {
                n = i;
            }
        }
        return n;
    }

    public int positionFittest(int i)
    {
        ArrayList<Double> listTemp = new ArrayList<>();
        listTemp.addAll(fitness);
        Collections.sort(listTemp);
        double temp = listTemp.get(i);
        return fitness.indexOf(temp);
    }
}
