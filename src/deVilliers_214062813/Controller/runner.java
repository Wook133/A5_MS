package deVilliers_214062813.Controller;

public class runner {
    public static void main(String[] args) {
           // public population(Integer populationsize, Double cr, Double mr, Integer generations, String s) {
        population p3 = new population(100, 0.5, 0.5, 15, "states.csv");
        p3.InitializePopulation();
        p3.Evolve();

    }
}
