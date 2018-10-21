package deVilliers_214062813.Controller;

public class Experiment {

    Double ybeststart;
    Double ybestend;
    String Function;
    String OptimizationMethod;
    Integer PopSize;
    Integer Generation;
    String Range;
    Double crossoverrate;
    Double mutationrate;
    Double shufflerate;

    public Experiment(Double ybeststart, Double ybestend, String function, String optimizationMethod, Integer popSize, Integer generation, String range, Double crossoverrate, Double mutationrate, Double shufflerate) {
        this.ybeststart = ybeststart;
        this.ybestend = ybestend;
        Function = function;
        OptimizationMethod = optimizationMethod;
        PopSize = popSize;
        Generation = generation;
        Range = range;
        this.crossoverrate = crossoverrate;
        this.mutationrate = mutationrate;
        this.shufflerate = shufflerate;
    }

    @Override
    public String toString() {
        return "Experiment{" +
                "ybeststart=" + ybeststart +
                ", ybestend=" + ybestend +
                ", Function='" + Function + '\'' +
                ", OptimizationMethod='" + OptimizationMethod + '\'' +
                ", PopSize=" + PopSize +
                ", Generation=" + Generation +
                ", Range=" + Range +
                ", crossoverrate=" + crossoverrate +
                ", mutationrate=" + mutationrate +
                ", shufflerate=" + shufflerate +
                '}';
    }

    public String print()
    {
        return ybeststart + ", " +
                ybestend +", " +
                Function +", " +
                OptimizationMethod +", " +
                PopSize +", " +
                Generation +", " +
                Range +", " +
                crossoverrate +", " +
                mutationrate +", " +
                shufflerate;

    }
}
