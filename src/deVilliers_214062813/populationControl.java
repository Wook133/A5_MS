package deVilliers_214062813;

import java.util.ArrayList;

public class populationControl {
    ArrayList<Agent> population = new ArrayList<>();
    Double Range, crossoverRate, MutationRate;
    Double growthMutation, shrinkMutation, swapMutation, replaceMutation, parameterMutation;
    Integer PopulationSize, Generations, Dimensions;

    public populationControl(Double range, Double crossoverRate, Double mutationRate, Double growthMutation, Double shrinkMutation, Double swapMutation, Double replaceMutation, Double parameterMutation, Integer populationSize, Integer generations, Integer dimensions) {
        Range = range;
        this.crossoverRate = crossoverRate;
        MutationRate = mutationRate;
        this.growthMutation = growthMutation;
        this.shrinkMutation = shrinkMutation;
        this.swapMutation = swapMutation;
        this.replaceMutation = replaceMutation;
        this.parameterMutation = parameterMutation;
        PopulationSize = populationSize;
        Generations = generations;
        Dimensions = dimensions;
    }

    public void InitializePopulation()
    {
        population = new ArrayList<>();
        for (int i = 0; i <= PopulationSize - 1; i++)
        {
            Agent cur = new Agent();
            cur.initialize();
            population.add(cur);
        }
    }

}
