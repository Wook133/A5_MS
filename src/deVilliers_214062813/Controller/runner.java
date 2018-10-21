package deVilliers_214062813.Controller;

import java.util.ArrayList;

public class runner {
    public static void main(String[] args) {
           // public population(Integer populationsize, Double cr, Double mr, Integer generations, String s) {
        //population p3 = new population(400, 0.5, 0.8, 200, "book1.csv");
        /*ArrayList<Double> listMutationRate = new ArrayList<>();
        listMutationRate.add(0.1);
        listMutationRate.add(0.3);
        listMutationRate.add(0.5);
        listMutationRate.add(0.7);
        listMutationRate.add(0.9);
        listMutationRate.add(0.99);
        ArrayList<Double> listShuffleRate = new ArrayList<>();
        listShuffleRate.add(0.0);
        listShuffleRate.add(0.01);
        listShuffleRate.add(0.05);
        listShuffleRate.add(0.10);
        listShuffleRate.add(0.30);
        listShuffleRate.add(0.50);
        listShuffleRate.add(0.70);
        listShuffleRate.add(0.90);
        ArrayList<Integer> generations = new ArrayList<>();
        generations.add(10);
        generations.add(50);
        generations.add(100);
        generations.add(200);
        generations.add(400);
        generations.add(800);
        generations.add(1000);
        ArrayList<Integer> popsize = new ArrayList<>();
        popsize.add(10);
        popsize.add(20);
        popsize.add(40);
        popsize.add(80);
        popsize.add(100);
        popsize.add(200);
        popsize.add(400);
        popsize.add(1000);*/

    /*    for (int j = 0; j <= 9; j++) {
            for (int i = 0; i <= generations.size() - 1; i++) {
                System.out.println(i);
                population p3 = new population(100, 0.5, 0.99, 0.05, 0.0, 0.0, generations.get(i), "book2.csv", "Euclidean");
                p3.InitializePopulation();
                p3.EvolvePrint("Generations.csv");
            }
        }*/
        /*for (int j = 0; j <= 9; j++) {

                population p3 = new population(200, 0.5, 0.99, 0.0, 0.0, 0.0, 200, "book2.csv", "Euclidean");
                p3.InitializePopulation();
                p3.EvolvePrint("ReplaceMutation.csv");

        }*/
        population p3 = new population(200, 0.5, 0.99, 0.0, 0.0, 0.0, 400, "book2.csv", "Euclidean");
        p3.InitializePopulation();
        p3.Evolve();
    }
}
