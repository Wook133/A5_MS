package deVilliers_214062813.Controller;

public class runner {
    public static void main(String[] args) {
           // public population(Integer populationsize, Double cr, Double mr, Integer generations, String s) {
        //population p3 = new population(400, 0.5, 0.8, 200, "book1.csv");
        population p3 = new population(400, 0.5, 0.99, 0.01, 0.0, 0.0, 100, "book2.csv");
        p3.InitializePopulation();
        p3.Evolve();

    }
}
