package fil.adom;

import java.util.ArrayList;

public class TP2_Evaluation {

    public static void main(String[] args) {
        var allMatrices = TP1.createAllMatrices();
        var dim = 100;
        var nb = 1000;
        for (var i = 0; i < allMatrices.size(); i++) {
            var matrix = allMatrices.get(i);
            var instance = TP1.allInstances[i];
            var allCosts = new ArrayList<Integer>();
            var allTimes = new ArrayList<Long>();
            //random best swap
            for (var j = 0; j < nb; j++) {
                var currentMs = System.currentTimeMillis();
                var randomCities = TP1.generateRandomCities(dim);
                var cities = TP2_Swap.bestNeighboursSwap(randomCities.clone(), matrix);
                allCosts.add(TP1.totalCostFromWay(cities, matrix));
                allTimes.add(System.currentTimeMillis() - currentMs);
            }
            var avgCost = allCosts.stream().mapToDouble(a -> a).average().getAsDouble();
            var avgTime = allTimes.stream().mapToDouble(a -> a).average().getAsDouble();
            System.out.println("Instance: " + instance + "; AVG for Random Cities and Best Swap: cost: " + avgCost + "; ms: " + avgTime);
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
            System.out.println("Instance: " + instance + "; AVG for Random Cities and First Swap: cost: " + avgCost + "; ms: " + avgTime);
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
            System.out.println("Instance: " + instance + "; AVG for Random Cities and Best Two-Opt: cost: " + avgCost + "; ms: " + avgTime);
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
            System.out.println("Instance: " + instance + "; AVG for Random Cities and First Two-Opt: cost: " + avgCost + "; ms: " + avgTime);

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
            System.out.println("Instance: " + instance + "; AVG for Heuristic Cities and Best Swap: cost: " + avgCost + "; ms: " + avgTime);
            //heuristic first swap
            for (var j = 0; j < nb; j++) {
                var currentMs = System.currentTimeMillis();
                var randomCities = TP1.generateRandomCities(dim);
                var heuristicCities = TP1.heuristicWay(randomCities.clone(), matrix);
                var cities = TP2_Swap.firstNeighboursSwap(heuristicCities.clone(), matrix);
                allCosts.add(TP1.totalCostFromWay(cities, matrix));
                allTimes.add(System.currentTimeMillis() - currentMs);
            }
            avgCost = allCosts.stream().mapToDouble(a -> a).average().getAsDouble();
            avgTime = allTimes.stream().mapToDouble(a -> a).average().getAsDouble();
            System.out.println("Instance: " + instance + "; AVG for Heuristic Cities and First Swap: cost: " + avgCost + "; ms: " + avgTime);
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
            System.out.println("Instance: " + instance + "; AVG for Heuristic Cities and Best Two-Opt: cost: " + avgCost + "; ms: " + avgTime);
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
            System.out.println("Instance: " + instance + "; AVG for Heuristic Cities and First Two-Opt: cost: " + avgCost + "; ms: " + avgTime);
        }
    }

}
