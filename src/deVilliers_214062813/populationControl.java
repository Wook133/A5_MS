package deVilliers_214062813;

import deVilliers_214062813.MotionSimulator.*;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.atomic.DoubleAccumulator;

public class populationControl {
    ArrayList<Triple<Agent, ArrayList<State>, ArrayList<Double>>> population = new ArrayList<>();
    ArrayList<State> targetStates = new ArrayList<>();
    Double crossoverRate, MutationRate;
    Double growthMutation, shrinkMutation, swapMutation, replaceMutation, parameterMutation;
    Integer PopulationSize, Generations;
    Double xstart, xend, ystart, yend, angle;

    public populationControl(Double crossoverRate, Double mutationRate, Double growthMutation, Double shrinkMutation, Double swapMutation, Double replaceMutation, Double parameterMutation, Integer populationSize, Integer generations, Double xstart, Double xend, Double ystart, Double yend, Double angle) {
        this.crossoverRate = crossoverRate;
        MutationRate = mutationRate;
        this.growthMutation = growthMutation;
        this.shrinkMutation = shrinkMutation;
        this.swapMutation = swapMutation;
        this.replaceMutation = replaceMutation;
        this.parameterMutation = parameterMutation;
        PopulationSize = populationSize;
        Generations = generations;
        this.xstart = xstart;
        this.xend = xend;
        this.ystart = ystart;
        this.yend = yend;
        this.angle = angle;
        readCSV rcv = new readCSV();
        targetStates = rcv.readfile("scaledS.csv");
    }

    public void InitializePopulation()
    {
        population = new ArrayList<>();
        ArrayList<Agent> temppopulation = new ArrayList<>();

        for (int i = 0; i <= PopulationSize - 1; i++)
        {
            Agent cur = new Agent();
            cur.initialize();
            temppopulation.add(cur);
        }
        MotionSimulator ms = new MotionSimulator();

        for (int i = 0; i <= temppopulation.size() - 1; i++)
        {
            ArrayList<State> tempStates = new ArrayList<>();
            tempStates = ms.getPath(new State(xstart,ystart, angle), temppopulation.get(i).getListChromosomes());
            ArrayList<Double> tempFitness = calcIndividualFitness(tempStates);
            Double tf = calcTotalFitness(tempFitness);
            temppopulation.get(i).setTotalFitness(tf);
            Triple<Agent, ArrayList<State>, ArrayList<Double>> pcur = Triple.of(temppopulation.get(i), tempStates, tempFitness);
            population.add(pcur);
        }
    }

    public void visualTest()
    {
        VisualFrame vf = new VisualFrame(population.get(0).getMiddle());
        System.out.println(population.get(0).getLeft().TotalFitness);
    }

    public ArrayList<Pair<Integer, Integer>> ElitistSelection()
    {
        Collections.sort(population, new sortTripleGA());
        ArrayList<Pair<Integer, Integer>> breedingPairs = new ArrayList<>();
        Randomness r = new Randomness();
        Integer Half = (population.size()-1)/2;
        Pair<Integer, Integer> cur1 = Pair.create(0, 0);
        breedingPairs.add(cur1);
        for (int i = 1; i <= Half; i++)
        {
            Pair<Integer, Integer> cur = Pair.create(i, r.UniformPositiveRandomInteger(Half*1.0));
            breedingPairs.add(cur);
        }
        return breedingPairs;
    }

    public Triple<Agent, ArrayList<State>, ArrayList<Double>> createAgent(Agent A, Agent B, ArrayList<Double> fB)
    {
        Randomness r = new Randomness();
        int aCh = A.timeSteps;
        int bCh = B.timeSteps;
        int size = 0;
        Agent temp = new Agent();
       // temp.setListChromosomes(A.getListChromosomes());
        temp.initializeBlank();
        if (aCh > bCh)
        {
            size = B.listChromosomes.size();
        }
        else
        {
            size = A.listChromosomes.size();
        }
        for (int i = 0; i <= size - 1; i++)
        {
            Double curCR = r.UniformPositiveRandomNumber(1.0);
            Double curMR = r.UniformPositiveRandomNumber(1.0);
            Double curGM = r.UniformPositiveRandomNumber(1.0);
            Double curShM = r.UniformPositiveRandomNumber(1.0);
            /*Double curSwR = r.UniformPositiveRandomNumber(1.0);
            Double curRR = r.UniformPositiveRandomNumber(1.0);
            Double curPM = r.UniformPositiveRandomNumber(1.0);*/
            if (curCR >= crossoverRate)
            {
                if (i >= B.listChromosomes.size())
                {
                    TimedCommand tc = randomTimedCommand();
                    temp.setCommand(i, tc);
                }
                else
                {
                    temp.setCommand(i, B.listChromosomes.get(i));
                }
            }
            else
            {
                if (i >= A.listChromosomes.size())
                {
                    TimedCommand tc = randomTimedCommand();
                    temp.setCommand(i, tc);
                }
                else
                {
                    temp.setCommand(i, A.listChromosomes.get(i));
                }
            }
            if (curMR >= MutationRate)
            {
                if (i >= B.listChromosomes.size())
                {
                    TimedCommand tc = randomTimedCommand();
                    int left = (int) (tc.getC().getLeft() * r.NormalRandomNumber(3.0));
                    int right = (int) (tc.getC().getRight() * r.NormalRandomNumber(3.0));
                    int t = (int) (tc.getTime() * r.NormalRandomNumber(2.0));
                    TimedCommand mutatedTC = new TimedCommand(new Command(left, right), t);
                    temp.setCommand(i, mutatedTC);
                }
                else if (i >= A.listChromosomes.size())
                {
                    int pos = r.UniformPositiveRandomInteger((A.getListChromosomes().size() - 1)*1.0);
                    //TimedCommand tc = randomTimedCommand();
                    int left = (int) (B.getListChromosomes().get(i).getC().getLeft() * r.NormalRandomNumber(3.0)) + A.getListChromosomes().get(pos).getC().getLeft();
                    int right = (int) (B.getListChromosomes().get(i).getC().getRight() * r.NormalRandomNumber(3.0)) + A.getListChromosomes().get(pos).getC().getRight();
                    int t = (int) (B.getListChromosomes().get(i).getTime() * r.NormalRandomNumber(2.0)) + (A.getListChromosomes().get(pos).getTime());
                    TimedCommand mutatedTC = new TimedCommand(new Command(left, right), t);
                    temp.setCommand(i, mutatedTC);
                }
                else
                {
                    int left = (int) (B.getListChromosomes().get(i).getC().getLeft() * r.NormalRandomNumber(3.0)) + A.getListChromosomes().get(i).getC().getLeft();
                    int right = (int) (B.getListChromosomes().get(i).getC().getRight() * r.NormalRandomNumber(3.0)) + A.getListChromosomes().get(i).getC().getRight();
                    int t = (int) (B.getListChromosomes().get(i).getTime() * r.NormalRandomNumber(2.0)) + (A.getListChromosomes().get(i).getTime());
                    TimedCommand mutatedTC = new TimedCommand(new Command(left, right), t);
                    temp.setCommand(i, mutatedTC);
                }
            }
            if (curGM >= growthMutation)
            {
                double BestFB = Collections.min(fB);
                int iposbfb = fB.indexOf(BestFB);
                int icounter = 0;
                int k = 0;
                boolean bgm = false;
                while ((icounter <= iposbfb) && (k <= B.getListChromosomes().size() - 1) && (bgm == false))
                {
                    icounter = icounter + B.getListChromosomes().get(k).getTime();
                    if (icounter >= iposbfb)
                    {
                        int left = B.getListChromosomes().get(k).getC().getLeft();
                        int right = B.getListChromosomes().get(k).getC().getRight();
                        int t = B.getListChromosomes().get(k).getTime();
                        TimedCommand coTC = new TimedCommand(new Command(left, right), t);

                        int pos = r.UniformPositiveRandomInteger((temp.getListChromosomes().size() - 1)*1.0);
                        temp.setCommand(pos, coTC);
                        bgm =true;
                    }
                    k = k +1;
                }
            }
            if (curShM >= shrinkMutation)
            {
                int pos = r.UniformPositiveRandomInteger((temp.getListChromosomes().size() - 1)*1.0);
                temp.getRemoveChromosomes(pos);
            }
        }
        MotionSimulator ms = new MotionSimulator();
        ArrayList<State> tempStates = new ArrayList<>();
        tempStates = ms.getPath(new State(xstart,ystart, angle), temp.getListChromosomes());
        ArrayList<Double> tempFitness = calcIndividualFitness(tempStates);
        Double tf = calcTotalFitness(tempFitness);
        temp.setTotalFitness(tf);
        Triple<Agent, ArrayList<State>, ArrayList<Double>> pcur = Triple.of(temp, tempStates, tempFitness);
        return pcur;
    }

    public Triple<Agent, ArrayList<State>, ArrayList<Double>> createNewAgent(Agent A, Agent B, ArrayList<Double> fB) {
        Randomness r = new Randomness();
        Agent temp = new Agent();
        Boolean a = false;
        if (A.listChromosomes.size() > B.listChromosomes.size())
        {
            temp.setListChromosomes(A.listChromosomes);
            a = true;
        }
        else
        {
            temp.setListChromosomes(B.listChromosomes);
            a = false;
        }

        for (int i = 0; i <= temp.listChromosomes.size() - 1; i++) {
            Double curCR = r.UniformPositiveRandomNumber(1.0);
            Double curMR = r.UniformPositiveRandomNumber(1.0);
            if (curCR >= crossoverRate)
            {
                if (a == false)
                {
                    if (i < B.listChromosomes.size()) {
                        TimedCommand tc = B.listChromosomes.get(i);
                        if (curMR >= MutationRate) {
                            int left = tc.getC().getLeft() + r.UniformRandomInteger(10.0);
                            int right = tc.getC().getRight() + r.UniformRandomInteger(10.0);
                            int time = tc.getTime() + r.UniformRandomInteger(10.0);
                            tc = new TimedCommand(new Command(left, right), time);
                        }
                        temp.setCommand(i, tc);
                    }
                    else
                    {
                        Random ra = new Random();
                        TimedCommand tc = A.listChromosomes.get(ra.nextInt(A.listChromosomes.size() - 1));
                        if (curMR >= MutationRate) {
                            int left = r.UniformRandomInteger(700.0);
                            int right = r.UniformRandomInteger(700.0);
                            int time = (int)(r.UniformPositiveRandomNumber(10.0) -1.0);
                            tc = new TimedCommand(new Command(left, right), time);
                        }
                        temp.setCommand(i, tc);
                    }
                }
                else
                {
                    if (i < A.listChromosomes.size())
                    {
                        TimedCommand tc = A.listChromosomes.get(i);
                        if (curMR >= MutationRate) {
                            int left = tc.getC().getLeft() +  r.UniformRandomInteger(10.0);
                            int right = tc.getC().getRight()+  r.UniformRandomInteger(10.0);
                            int time = tc.getTime()+  r.UniformRandomInteger(10.0);
                            tc = new TimedCommand(new Command(left, right), time);
                        }
                        temp.setCommand(i, tc);
                    }
                    else
                    {
                        Random ra = new Random();
                        TimedCommand tc = A.listChromosomes.get(ra.nextInt(A.listChromosomes.size() - 1));
                        if (curMR >= MutationRate) {
                            int left = r.UniformRandomInteger(700.0);
                            int right = r.UniformRandomInteger(700.0);
                            int time = (int)(r.UniformPositiveRandomNumber(10.0) -1.0);
                            tc = new TimedCommand(new Command(left, right), time);
                        }
                        temp.setCommand(i, tc);
                    }
                }
            }

        }
        MotionSimulator ms = new MotionSimulator();
        ArrayList<State> tempStates = new ArrayList<>();
        tempStates = ms.getPath(new State(xstart,ystart, angle), temp.getListChromosomes());
        ArrayList<Double> tempFitness = calcIndividualFitness(tempStates);
        Double tf = calcTotalFitness(tempFitness);
        temp.setTotalFitness(tf);
        Triple<Agent, ArrayList<State>, ArrayList<Double>> pcur = Triple.of(temp, tempStates, tempFitness);
        return pcur;

    }


    public ArrayList<Triple<Agent, ArrayList<State>, ArrayList<Double>>> Breed(ArrayList<Pair<Integer, Integer>> breedingpairs)
    {
        ArrayList<Triple<Agent, ArrayList<State>, ArrayList<Double>>> temp = new ArrayList<>();
        Agent c = Collections.min(population, new sortTripleGA()).getLeft();
        MotionSimulator ms = new MotionSimulator();
        ArrayList<State> tempStates = new ArrayList<>();
        tempStates = ms.getPath(new State(xstart,ystart, angle), c.getListChromosomes());
        ArrayList<Double> tempFitness = calcIndividualFitness(tempStates);
        Double tf = calcTotalFitness(tempFitness);
        c.setTotalFitness(tf);
        Triple<Agent, ArrayList<State>, ArrayList<Double>> pcur = Triple.of(c, tempStates, tempFitness);
        temp.add(pcur);
        for (int i = 1; i <= breedingpairs.size() - 1; i++)
        {
            Agent A = population.get(breedingpairs.get(i).getFirst()).getLeft();
            Agent B = population.get(breedingpairs.get(i).getSecond()).getLeft();
            ArrayList<Double> DDD = population.get(breedingpairs.get(i).getSecond()).getRight() ;
            Triple<Agent, ArrayList<State>, ArrayList<Double>> curChild = createNewAgent(A, B, population.get(breedingpairs.get(i).getSecond()).getRight());
            temp.add(curChild);
        }
        return temp;
    }
    public ArrayList<Triple<Agent, ArrayList<State>, ArrayList<Double>>> BreedOther(ArrayList<Pair<Integer, Integer>> breedingpairs)
    {
        ArrayList<Triple<Agent, ArrayList<State>, ArrayList<Double>>> temp = new ArrayList<>();
        Agent c = Collections.min(population, new sortTripleGA()).getLeft();
        MotionSimulator ms = new MotionSimulator();
        ArrayList<State> tempStates = new ArrayList<>();
        tempStates = ms.getPath(new State(xstart,ystart, angle), c.getListChromosomes());
        ArrayList<Double> tempFitness = calcIndividualFitness(tempStates);
        Double tf = calcTotalFitness(tempFitness);
        c.setTotalFitness(tf);
        Triple<Agent, ArrayList<State>, ArrayList<Double>> pcur = Triple.of(c, tempStates, tempFitness);
        temp.add(pcur);
        for (int i = 1; i <= breedingpairs.size() - 1; i++)
        {
            Agent A = population.get(breedingpairs.get(i).getFirst()).getLeft();
            Agent B = population.get(breedingpairs.get(i).getSecond()).getLeft();
            ArrayList<Double> DDD = population.get(breedingpairs.get(i).getSecond()).getRight() ;
            Triple<Agent, ArrayList<State>, ArrayList<Double>> curChild = createAgent(A, B, population.get(breedingpairs.get(i).getSecond()).getRight());
            temp.add(curChild);
        }
        return temp;
    }
    public TimedCommand randomTimedCommand()
    {
        Randomness r = new Randomness();
        int size = r.UniformPositiveRandomNaturalNumber(100.0);
        int left = r.UniformRandomInteger(700.0);
        int right = r.UniformRandomInteger(700.0);
        int time = r.UniformPositiveRandomNaturalNumber(8.0);
        return  new TimedCommand(new Command(left, right), time);

    }

    public ArrayList<Double> calcIndividualFitness( ArrayList<State> tempStates)
    {
        ArrayList<Double> tempFitness = new ArrayList<>();
        for (int k = 0; k <= targetStates.size() - 1; k++)
        {
            if (k <= tempStates.size() - 1) {
                Distance D = new EuclideanDistance(targetStates.get(k).getX(), tempStates.get(k).getX(), targetStates.get(k).getY(), tempStates.get(k).getY());
                tempFitness.add(D.calculateDistance());
            }
            else
            {
                tempFitness.add(10000.0);
            }
        }
        return tempFitness;
    }
    public Double calcTotalFitness(ArrayList<Double> tempFitness)
    {
        Double tf = 0.0;
        for (Double d:tempFitness)
        {
            tf =tf+d;
        }
        return tf;
    }

    public void Evolve()
    {
        Collections.sort(population, new sortTripleGA());
        int igen =0;
        VisualFrame vf = new VisualFrame(population.get(0).getMiddle());
        System.out.println(population.get(0).getLeft().TotalFitness);
        while (igen <= Generations)
        {
            Triple<Agent, ArrayList<State>, ArrayList<Double>> curMin = Collections.min(population, new sortTripleGA());
            //Life curMax = Collections.max(population, new sortY());
            System.out.println("Generation " + igen);
            System.out.println("Size " + population.size());
            System.out.println("Min : " + curMin.getLeft().getTotalFitness());
           // System.out.println("Max : " + curMax.toString());
            ArrayList<Pair<Integer, Integer>> breeders = new ArrayList<>();
            breeders = ElitistSelection();
            ArrayList<Triple<Agent, ArrayList<State>, ArrayList<Double>>>children = new ArrayList<>();
            ArrayList<Triple<Agent, ArrayList<State>, ArrayList<Double>>> children2 = new ArrayList<>();
            children = Breed(breeders);
            children2 = Breed(breeders);
           /* ArrayList<Triple<Agent, ArrayList<State>, ArrayList<Double>>>children3 = new ArrayList<>();
            ArrayList<Triple<Agent, ArrayList<State>, ArrayList<Double>>> children4 = new ArrayList<>();*/
            children = Breed(breeders);
            children2 = BreedOther(breeders);
            population = new ArrayList<>();
            population.addAll(children);
            population.addAll(children2);
          /*  population.addAll(children3);
            population.addAll(children4);*/
            //breeders = selectBest10Percent();
            //BreedToPop(breeders);
            igen = igen + 1;
        }
        Collections.sort(population, new sortTripleGA());
        vf = new VisualFrame(population.get(0).getMiddle());
        System.out.println(population.get(0).getLeft().TotalFitness);
    }


}
class sortTripleGA implements Comparator<Triple<Agent, ArrayList<State>, ArrayList<Double>>>
{
    @Override
    public int compare(Triple<Agent, ArrayList<State>, ArrayList<Double>> o1, Triple<Agent, ArrayList<State>, ArrayList<Double>> o2) {
        return Double.compare(o1.getLeft().TotalFitness, o2.getLeft().TotalFitness);
    }
}