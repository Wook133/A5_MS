package deVilliers_214062813.Controller;

import deVilliers_214062813.*;
import deVilliers_214062813.MotionSimulator.Command;
import deVilliers_214062813.MotionSimulator.MotionSimulator;
import deVilliers_214062813.MotionSimulator.State;
import deVilliers_214062813.MotionSimulator.TimedCommand;

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
        //Biasedinitialize();
        //CopyInitialize();
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
        for (int i = 0; i <= 74; i++)
        {
            int left = r.UniformRandomInteger(250.0);
            int right = r.UniformRandomInteger(250.0);
            int time = 1;
            System.out.println(left + " : " + right + " : " + time);
            listChromosomes.add(new TimedCommand(new Command(left, right), time));
        }
        MotionSimulator ms = new MotionSimulator();
        countTimesteps();
        listStates = ms.getPath(initial, listChromosomes);
        System.out.println(listStates.toString());
    }
    public void Biasedinitialize()
    {
        ArrayList<TimedCommand> listgood = new ArrayList<>();
        listgood = goodGenes();
        Randomness r = new Randomness();
        while (listChromosomes.size() <= 74)
        {
            Integer pos = r.UniformPositiveRandomNaturalNumber(listgood.size()-1.0);
            listChromosomes.add(listgood.get(pos));
        }
       /* for (int i = 0; i <= 76; i++)
        {
            int left = r.UniformRandomInteger(250.0);
            int right = r.UniformRandomInteger(250.0);
            int time = 1;
            System.out.println(left + " : " + right + " : " + time);
            listChromosomes.add(new TimedCommand(new Command(left, right), time));
        }*/
        MotionSimulator ms = new MotionSimulator();
        countTimesteps();
        listStates = ms.getPath(initial, listChromosomes);
        System.out.println(listStates.toString());
    }
    public void CopyInitialize()
    {
        ArrayList<TimedCommand> listgood = new ArrayList<>();
        TimedCommand tc = new TimedCommand(new Command(100,100), 5);
        listChromosomes.add(tc);
        for (int i = 0; i <= 9; i++)
        {
            tc = new TimedCommand(new Command(100,200), 3);
            listChromosomes.add(tc);
        }
        tc = new TimedCommand(new Command(100,100), 3);
        listgood.add(tc);
        for (int i = 0; i <= 9; i++)
        {
            tc = new TimedCommand(new Command(200,100), 3);
            listChromosomes.add(tc);
        }
        tc = new TimedCommand(new Command(200,100), 3);
        listChromosomes.add(tc);
        tc = new TimedCommand(new Command(200,150), 5);
        listChromosomes.add(tc);
       /* for (int i = 0; i <= 76; i++)
        {
            int left = r.UniformRandomInteger(250.0);
            int right = r.UniformRandomInteger(250.0);
            int time = 1;
            System.out.println(left + " : " + right + " : " + time);
            listChromosomes.add(new TimedCommand(new Command(left, right), time));
        }*/
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
    public Double fitnessEuclid(ArrayList<State> targetStates)
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
        return d;
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

    public ArrayList<TimedCommand> goodGenes()
    {
        ArrayList<TimedCommand> listCommands = new ArrayList<>();
        TimedCommand tc = new TimedCommand(new Command(100,100), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(100,200), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(100,100), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(200,100), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(200,100), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(200,150), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(0,0), 1);


        listCommands.add(tc);
        tc = new TimedCommand(new Command(200,200), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(100,100), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(200,200), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(200,200), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(300,300), 1);





        /*tc = new TimedCommand(new Command(700,200), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(100,200), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(0,-700), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(500,200), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(600,300), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(300,600), 1);
        listCommands.add(tc);*/



        return listCommands;
    }
}
