package fil.adom;

import java.util.List;

public class TP4 {

    public static void main(String[] args) {
        var allMatrices = TP1.createAllMatrices();
        var dim = 100;
        var randomCities = TP1.generateRandomCities(dim);
        for(var i = 0; i < 6; i+=2) {
            var allCost = evaluateTwoObjectives(randomCities.clone(), allMatrices.get(i), allMatrices.get(i+1));
            System.out.println("Instance: " + TP1.allInstances[i] + TP1.allInstances[i+1] + "; Cost for Random Cities: " + allCost[0] + " " + allCost[1]);
        }
    }

    public static Integer[] evaluateTwoObjectives(Integer[] way, int[][] matrix1, int[][] matrix2) {
        return List.of(TP1.totalCostFromWay(way, matrix1), TP1.totalCostFromWay(way, matrix2)).toArray(new Integer[0]);
    }

}
