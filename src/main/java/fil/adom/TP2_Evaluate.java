package fil.adom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TP2_Evaluate {

    public static final String mainDir = "./files/generated/evaluate/";

    public static void main(String[] args) {
        var numberFormat = new DecimalFormat("#.000");
        var allMatrices = TP1.createAllMatrices();
        var dim = 100;
        var nb = 1000;
        for (var i = 0; i < allMatrices.size(); i++) {
            var res = "";
            var matrix = allMatrices.get(i);
            var instance = TP1.allInstances[i];
            var allCosts = new ArrayList<Integer>();
            var allTimes = new ArrayList<Long>();
            //heuristic first swap
            for (var j = 0; j < nb; j++) {
                var currentMs = System.currentTimeMillis();
                var randomCities = TP1.generateRandomCities(dim);
                var heuristicCities = TP1.heuristicWay(randomCities.clone(), matrix);
                var cities = TP2_Swap.firstNeighboursSwap(heuristicCities.clone(), matrix);
                allCosts.add(TP1.totalCostFromWay(cities, matrix));
                allTimes.add(System.currentTimeMillis() - currentMs);
            }
            var avgCost = allCosts.stream().mapToDouble(a -> a).average().getAsDouble();
            var avgTime = allTimes.stream().mapToDouble(a -> a).average().getAsDouble();
            res += "Heuristic - First - Swap    -> cost: " + numberFormat.format(avgCost) + " | time: " + numberFormat.format(avgTime) + "\n";
            //heuristic first two-opt
            for (var j = 0; j < nb; j++) {
                var currentMs = System.currentTimeMillis();
                var randomCities = TP1.generateRandomCities(dim);
                var heuristicCities = TP1.heuristicWay(randomCities.clone(), matrix);
                var cities = TP2_TwoOpt.firstNeighboursTwoOpt(heuristicCities.clone(), matrix);
                allCosts.add(TP1.totalCostFromWay(cities, matrix));
                allTimes.add(System.currentTimeMillis() - currentMs);
            }
            avgCost = allCosts.stream().mapToDouble(a -> a).average().getAsDouble();
            avgTime = allTimes.stream().mapToDouble(a -> a).average().getAsDouble();
            res += "Heuristic - First - Two-Opt -> cost: " + numberFormat.format(avgCost) + " | time: " + numberFormat.format(avgTime) + "\n";
            //heuristic best swap
            for (var j = 0; j < nb; j++) {
                var currentMs = System.currentTimeMillis();
                var randomCities = TP1.generateRandomCities(dim);
                var heuristicCities = TP1.heuristicWay(randomCities.clone(), matrix);
                var cities = TP2_Swap.bestNeighboursSwap(heuristicCities.clone(), matrix);
                allCosts.add(TP1.totalCostFromWay(cities, matrix));
                allTimes.add(System.currentTimeMillis() - currentMs);
            }
            avgCost = allCosts.stream().mapToDouble(a -> a).average().getAsDouble();
            avgTime = allTimes.stream().mapToDouble(a -> a).average().getAsDouble();
            res += "Heuristic - Best  - Swap    -> cost: " + numberFormat.format(avgCost) + " | time: " + numberFormat.format(avgTime) + "\n";
            //heuristic best two-opt
            for (var j = 0; j < nb; j++) {
                var currentMs = System.currentTimeMillis();
                var randomCities = TP1.generateRandomCities(dim);
                var heuristicCities = TP1.heuristicWay(randomCities.clone(), matrix);
                var cities = TP2_TwoOpt.bestNeighboursTwoOpt(heuristicCities.clone(), matrix);
                allCosts.add(TP1.totalCostFromWay(cities, matrix));
                allTimes.add(System.currentTimeMillis() - currentMs);
            }
            avgCost = allCosts.stream().mapToDouble(a -> a).average().getAsDouble();
            avgTime = allTimes.stream().mapToDouble(a -> a).average().getAsDouble();
            res += "Heuristic - Best  - Two-Opt -> cost: " + numberFormat.format(avgCost) + " | time: " + numberFormat.format(avgTime) + "\n";

            //random first swap
            for (var j = 0; j < nb; j++) {
                var currentMs = System.currentTimeMillis();
                var randomCities = TP1.generateRandomCities(dim);
                var cities = TP2_Swap.firstNeighboursSwap(randomCities.clone(), matrix);
                allCosts.add(TP1.totalCostFromWay(cities, matrix));
                allTimes.add(System.currentTimeMillis() - currentMs);
            }
            avgCost = allCosts.stream().mapToDouble(a -> a).average().getAsDouble();
            avgTime = allTimes.stream().mapToDouble(a -> a).average().getAsDouble();
            res += "Random    - First - Swap    -> cost: " + numberFormat.format(avgCost) + " | time: " + numberFormat.format(avgTime) + "\n";
            //random first two-opt
            for (var j = 0; j < nb; j++) {
                var currentMs = System.currentTimeMillis();
                var randomCities = TP1.generateRandomCities(dim);
                var cities = TP2_TwoOpt.firstNeighboursTwoOpt(randomCities.clone(), matrix);
                allCosts.add(TP1.totalCostFromWay(cities, matrix));
                allTimes.add(System.currentTimeMillis() - currentMs);
            }
            avgCost = allCosts.stream().mapToDouble(a -> a).average().getAsDouble();
            avgTime = allTimes.stream().mapToDouble(a -> a).average().getAsDouble();
            res += "Random    - First - Two-Opt -> cost: " + numberFormat.format(avgCost) + " | time: " + numberFormat.format(avgTime) + "\n";
            //random best swap
            for (var j = 0; j < nb; j++) {
                var currentMs = System.currentTimeMillis();
                var randomCities = TP1.generateRandomCities(dim);
                var cities = TP2_Swap.bestNeighboursSwap(randomCities.clone(), matrix);
                allCosts.add(TP1.totalCostFromWay(cities, matrix));
                allTimes.add(System.currentTimeMillis() - currentMs);
            }
            avgCost = allCosts.stream().mapToDouble(a -> a).average().getAsDouble();
            avgTime = allTimes.stream().mapToDouble(a -> a).average().getAsDouble();
            res += "Random    - Best  - Swap    -> cost: " + numberFormat.format(avgCost) + " | time: " + numberFormat.format(avgTime) + "\n";
            //random best two-opt
            for (var j = 0; j < nb; j++) {
                var currentMs = System.currentTimeMillis();
                var randomCities = TP1.generateRandomCities(dim);
                var cities = TP2_TwoOpt.bestNeighboursTwoOpt(randomCities.clone(), matrix);
                allCosts.add(TP1.totalCostFromWay(cities, matrix));
                allTimes.add(System.currentTimeMillis() - currentMs);
            }
            avgCost = allCosts.stream().mapToDouble(a -> a).average().getAsDouble();
            avgTime = allTimes.stream().mapToDouble(a -> a).average().getAsDouble();
            res += "Random    - Best  - Two-Opt -> cost: " + numberFormat.format(avgCost) + " | time: " + numberFormat.format(avgTime) + "\n";
            writeInfo(res, instance);
        }
    }

    public static void writeInfo(String res, String instance) {
        try {
            var f = new File(mainDir + instance + "100.tsp");
            var writer = new BufferedWriter(new FileWriter(f));
            writer.write(res + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
