package deVilliers_214062813.Controller;

import deVilliers_214062813.MotionSimulator.Command;
import deVilliers_214062813.MotionSimulator.State;
import deVilliers_214062813.MotionSimulator.TimedCommand;
import deVilliers_214062813.MotionSimulator.VisualFrame;
import deVilliers_214062813.Randomness;
import deVilliers_214062813.readCSV;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class population {
    ArrayList<Controller> Population = new ArrayList<>();
    ArrayList<State> targetStates = new ArrayList<>();
    String sfunction;

    Double ShuffleRate;
    Double ScaleRate;
    Double ShiftRate;
    Double replaceMutation = 0.15;
    Double crossoverRate, MutationRate;
    Integer PopulationSize, Generations;

    public population(Integer populationsize, Double cr, Double mr, Double sr, Double shift, Double scale, Integer generations, String s, String func) {
        PopulationSize = populationsize;
        ShuffleRate = sr;
        ShiftRate = shift;
        crossoverRate = cr;
        ScaleRate = scale;
        MutationRate = mr;
        Generations = generations;
        readCSV rcv = new readCSV();
        targetStates = rcv.readfile(s);
        VisualFrame vf = new VisualFrame(targetStates);
        sfunction = func;

    }
    public void InitializePopulation()
    {
        Population = new ArrayList<>();
        for (int i = 0; i <= PopulationSize - 1; i++)
        {
            Controller cur = new Controller(targetStates.get(0), targetStates);
            cur.evaluateFitness(targetStates);
            Population.add(cur);
        }
    }
    public ArrayList<Pair<Integer, Integer>> ElitistSelection()
    {
        Collections.sort(Population, new sortController());
        ArrayList<Pair<Integer, Integer>> breedingPairs = new ArrayList<>();
        Randomness r = new Randomness();
        Integer Half = (Population.size()-1)/2;
        Pair<Integer, Integer> cur1 = Pair.create(0, 0);
        breedingPairs.add(cur1);
        for (int i = 1; i <= Half; i++)
        {
            Pair<Integer, Integer> cur = Pair.create(i, r.UniformPositiveRandomInteger(Half*1.0));
            breedingPairs.add(cur);
        }
        return breedingPairs;
    }
    public ArrayList<Controller> Breed(ArrayList<Pair<Integer, Integer>> breedingpairs)
    {
        ArrayList<Controller> temp = new ArrayList<>();
        Controller c = Population.get(breedingpairs.get(0).getFirst());
        Controller cc = Population.get(breedingpairs.get(0).getSecond());
        Controller ccc = createClone(c);
        Controller cccc = createClone(cc);
        temp.add(ccc);
        temp.add(cccc);
        for (int i = 2; i <= breedingpairs.size() - 1; i++)
        {
            Controller A = Population.get(breedingpairs.get(i).getFirst());
            Controller B = Population.get(breedingpairs.get(i).getSecond());
            Controller cur = createAdvancedLife(A, B);
            cur.evaluateFitness(targetStates);
            temp.add(cur);
            //System.out.println(cur.TotalFitness);
        }
        return temp;
    }
    public Controller createClone(Controller A)
    {
        Controller tem = new Controller(targetStates.get(0), A.listChromosomes, targetStates);
        return tem;
    }
    public Controller createLife(Controller A, Controller B)
    {
        ArrayList<TimedCommand> temp = new ArrayList<>();
        for (int j = 0; j <= A.listChromosomes.size()-1; j++)
        {
            temp.add(new TimedCommand(new Command(0,0),1));
        }

        Randomness r = new Randomness();
        if (A.listChromosomes.size() == B.listChromosomes.size()) {
            for (int i = 0; i <= A.listChromosomes.size() - 1; i++) {
                Double curCR = r.UniformPositiveRandomNumber(1.0);
                if (curCR >= crossoverRate)
                {
                    Double curMR = r.UniformPositiveRandomNumber(1.0);
                    if (curMR >= MutationRate)
                    {
                        int cl = r.normalRandomIntegerN(20.0) * A.listChromosomes.get(i).getC().getLeft();

                        int cleft = B.listChromosomes.get(i).c.getLeft() + cl;
                        int cright = B.listChromosomes.get(i).c.getRight() + (r.normalRandomIntegerN(20.0) * A.listChromosomes.get(i).getC().getRight());
                        if ((cleft >= 700) || (cleft <= -700))
                        {
                            cleft = r.UniformRandomInteger(700.0);
                        }
                        //System.out.println("Left "  + cleft);
                        if ((cright >= 700) || (cright <= -700))
                        {
                            cright = r.UniformRandomInteger(700.0);
                        }
                        TimedCommand tc = new TimedCommand(new Command(cleft, cright), 1);
                        temp.set(i, tc);
                    }
                    else
                    {
                        temp.set(i, B.listChromosomes.get(i));
                    }
                }
                else
                {
                    Double curMR = r.UniformPositiveRandomNumber(1.0);
                    if (curMR >= MutationRate)
                    {
                        int cleft = A.listChromosomes.get(i).c.getLeft() + (r.normalRandomIntegerN(20.0) * B.listChromosomes.get(i).getC().getLeft());
                        int cright = A.listChromosomes.get(i).c.getRight() + (r.normalRandomIntegerN(20.0) * B.listChromosomes.get(i).getC().getRight());
                        if ((cleft >= 700) || (cleft <= -700))
                        {
                            cleft = r.UniformRandomInteger(700.0);
                        }
                        if ((cright >= 700) || (cright <= -700))
                        {
                            cright = r.UniformRandomInteger(700.0);
                        }
                        TimedCommand tc = new TimedCommand(new Command(cleft, cright), 1);
                        temp.set(i, tc);
                    }
                    else
                    {
                        temp.set(i, A.listChromosomes.get(i));
                    }
                }
            }
            Controller c = new Controller(targetStates.get(0), temp, targetStates);
            //System.out.println(temp.toString());
           // System.out.println(c.toString());
            return c ;
        }
        else
        {
            ArrayList<TimedCommand> listChromosomes = new ArrayList<>();
            for (int i = 0; i <= 76; i++)
            {

                int left = r.UniformRandomInteger(700.0);
                int right = r.UniformRandomInteger(700.0);
                int time = 1;
                listChromosomes.add(new TimedCommand(new Command(left, right), time));
            }
            Controller c = new Controller(targetStates.get(0), listChromosomes, targetStates);
            return c;
        }
    }

    public Controller createAdvancedLife(Controller A, Controller B)
    {
        ArrayList<TimedCommand> temp = new ArrayList<>();
        for (int j = 0; j <= A.listChromosomes.size()-1; j++)
        {
            temp.add(new TimedCommand(new Command(0,0),1));
        }

        Randomness r = new Randomness();
        if (A.listChromosomes.size() == B.listChromosomes.size()) {
            for (int i = 0; i <= A.listChromosomes.size() - 1; i++) {
                Double curCR = r.UniformPositiveRandomNumber(1.0);
                if (curCR >= crossoverRate)
                {
                    Double curMR = r.UniformPositiveRandomNumber(1.0);
                    if (curMR >= MutationRate)
                    {
                        int cl = r.normalRandomIntegerN(20.0) * A.listChromosomes.get(i).getC().getLeft();
                        int cleft = B.listChromosomes.get(i).c.getLeft() + cl;
                        int cright = B.listChromosomes.get(i).c.getRight() + (r.normalRandomIntegerN(20.0) * A.listChromosomes.get(i).getC().getRight());
                        if ((cleft >= 700) || (cleft <= -700))
                        {
                            cleft = r.UniformRandomInteger(700.0);
                        }
                        //System.out.println("Left "  + cleft);
                        if ((cright >= 700) || (cright <= -700))
                        {
                            cright = r.UniformRandomInteger(700.0);
                        }
                        TimedCommand tc = new TimedCommand(new Command(cleft, cright), 1);
                        temp.set(i, tc);
                    }
                    else
                    {
                        temp.set(i, B.listChromosomes.get(i));
                    }
                }
                else
                {
                    Double curMR = r.UniformPositiveRandomNumber(1.0);
                    if (curMR >= MutationRate)
                    {
                        int cleft = A.listChromosomes.get(i).c.getLeft() + (r.normalRandomIntegerN(20.0) * B.listChromosomes.get(i).getC().getLeft());
                        int cright = A.listChromosomes.get(i).c.getRight() + (r.normalRandomIntegerN(20.0) * B.listChromosomes.get(i).getC().getRight());
                        if ((cleft >= 700) || (cleft <= -700))
                        {
                            cleft = r.UniformRandomInteger(700.0);
                        }
                        if ((cright >= 700) || (cright <= -700))
                        {
                            cright = r.UniformRandomInteger(700.0);
                        }
                        TimedCommand tc = new TimedCommand(new Command(cleft, cright), 1);
                        temp.set(i, tc);
                    }
                    else
                    {
                        temp.set(i, A.listChromosomes.get(i));
                    }
                }
            }
            /*for (int i = 0; i <= temp.size() - 1; i++)
            {
                Double curShift = r.UniformPositiveRandomNumber(1.0);
                /*if (curShift >= ShiftRate)
                {
                    int ipos = r.UniformPositiveRandomNaturalNumber(temp.size() - 1.0);
                    if ((ipos < 0) || (ipos >= temp.size()))
                    {
                        ipos = 0;
                    }
                    TimedCommand tc = temp.remove(i);
                    temp.add(ipos, tc);
                }*/
               /* if (curShift >= replaceMutation)
                {
                    TimedCommand tc = getRandomGoodGene();
                    temp.set(i, tc);
                }
            }/*
            for (int i = 0; i <= temp.size() - 1; i++)
            {
                Double scaleRate = r.UniformPositiveRandomNumber(1.0);
                if (scaleRate >= ScaleRate)
                {
                    TimedCommand tc = temp.remove(i);
                    Double left = tc.getC().left * r.NormalRandomNumber(2.0);
                    Integer l = (int)Math.round(left);
                    Double right = tc.getC().right * r.NormalRandomNumber(2.0);
                    Integer ri = (int)Math.round(right);
                    TimedCommand tnew = new TimedCommand(new Command(l, ri), 1);
                    temp.add(i, tnew);
                }
            }*/
            if (ShuffleRate >= r.UniformPositiveRandomNumber(1.0))
            {
                Collections.shuffle(temp);
            }



            Controller c = new Controller(targetStates.get(0), temp, targetStates);
            //System.out.println(temp.toString());
            // System.out.println(c.toString());
            return c ;
        }
        else
        {
            ArrayList<TimedCommand> listChromosomes = new ArrayList<>();
            for (int i = 0; i <= targetStates.size()-3; i++)
            {

                int left = r.UniformRandomInteger(700.0);
                int right = r.UniformRandomInteger(700.0);
                int time = 1;
                listChromosomes.add(new TimedCommand(new Command(left, right), time));
            }
            Controller c = new Controller(targetStates.get(0), listChromosomes, targetStates);
            return c;
        }
    }

    public void Evolve()
    {
        Collections.sort(Population, new sortController());
        VisualFrame vf = new VisualFrame(Population.get(0).listStates);
        int igen =0;
        while (igen <= Generations)
        {
            Controller curMin = Collections.min(Population, new sortController());
            Controller curMax = Collections.max(Population, new sortController());
            System.out.println("Generation " + igen);
            System.out.println("Size " + Population.size());
            System.out.println("Min : " + curMin.TotalFitness);
            System.out.println("Max : " + curMax.TotalFitness);
            System.out.println("Number States : " + curMin.listChromosomes.size());
            ArrayList<Pair<Integer, Integer>> breeders = new ArrayList<>();
            breeders = ElitistSelection();
            ArrayList<Controller> children = new ArrayList<>();
            ArrayList<Controller> children2 = new ArrayList<>();
            children = Breed(breeders);
            children2 = Breed(breeders);
            Population = new ArrayList<>();
            Population.addAll(children);
            Population.addAll(children2);
            //breeders = selectBest10Percent();
            //BreedToPop(breeders);
            igen = igen + 1;
        }
        Collections.sort(Population, new sortController());
        vf = new VisualFrame(Population.get(0).listStates);
        System.out.println(Population.get(0).TotalFitness);
        for (State s : Population.get(0).listStates)
        {
            System.out.println(s.getX() + "; " + s.getY() + "; " +s.getA());
        }
    }

    public void EvolvePrint(String s)
    {
        Collections.sort(Population, new sortController());
        VisualFrame vf = new VisualFrame(Population.get(0).listStates);
        int igen = 0;
        Controller firstMin = Collections.min(Population, new sortController());
        while (igen <= Generations)
        {
            Controller curMin = Collections.min(Population, new sortController());
            Controller curMax = Collections.max(Population, new sortController());
            System.out.println("Generation " + igen);
            if ((igen % 50 ==0)) {
                System.out.println("Size " + Population.size());
                System.out.println("Min : " + curMin.TotalFitness + " : " + curMin.fitnessEuclid(targetStates));
                System.out.println("Max : " + curMax.TotalFitness + " : " + curMax.fitnessEuclid(targetStates));
            }
            ArrayList<Pair<Integer, Integer>> breeders = new ArrayList<>();
            breeders = ElitistSelection();
            ArrayList<Controller> children = new ArrayList<>();
            ArrayList<Controller> children2 = new ArrayList<>();
            children = Breed(breeders);
            children2 = Breed(breeders);
            Population = new ArrayList<>();
            Population.addAll(children);
            Population.addAll(children2);
            igen = igen + 1;
        }
        //   (Double ybeststart, Double ybestend, String function, String optimizationMethod, Integer popSize, Integer generation, String range, Double crossoverrate, Double mutationrate, Double shufflerate) {
        try {
            Collections.sort(Population, new sortController());
            Double dE1 = firstMin.TotalFitness;
            Controller lastMin = Collections.min(Population, new sortController());
            Double dE2 = lastMin.TotalFitness;
            Experiment e = new Experiment(dE1, dE2, sfunction, "GA", PopulationSize, Generations, "Random Initialize", crossoverRate, MutationRate, ShuffleRate);

            readCSV.writeCsvFile(s, e.print());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
}


    public ArrayList<TimedCommand> goodGenes()
    {
        ArrayList<TimedCommand> listCommands = new ArrayList<>();
        TimedCommand tc = new TimedCommand(new Command(100,100), 1);
        listCommands.add(tc);
      /*  for (int i = 0; i <= 9; i++)
        {*/
            tc = new TimedCommand(new Command(100,200), 1);
            listCommands.add(tc);
       // }
        tc = new TimedCommand(new Command(100,100), 1);
        listCommands.add(tc);
       /* for (int i = 0; i <= 9; i++)
        {*/
            tc = new TimedCommand(new Command(200,100), 1);
            listCommands.add(tc);
        //}
        tc = new TimedCommand(new Command(200,100), 1);
        listCommands.add(tc);
        tc = new TimedCommand(new Command(200,150), 1);
        listCommands.add(tc);
        return listCommands;
    }

    public TimedCommand getRandomGoodGene()
    {
        ArrayList<TimedCommand> gg = new ArrayList<>();
        gg =goodGenes();
        Randomness r = new Randomness();
        Integer i = r.UniformPositiveRandomInteger(gg.size() -0.0) - 1;
        if ((i < 0) || (i >= gg.size()))
        {
            i = 0;
        }
        return gg.get(i);
    }

}
class sortController implements Comparator<Controller>
{
    @Override
    public int compare(Controller o1, Controller o2) {
        return Double.compare(o1.TotalFitness, o2.TotalFitness);
    }
}
