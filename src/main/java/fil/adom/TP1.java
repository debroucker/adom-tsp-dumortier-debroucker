package fil.adom;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TP1 {

    public static final String[] allInstances = {"A", "B", "C", "D", "E", "F"};

    public static final String mainDir = "./files/random/";

    public static void main(String[] args) {
        var allMatrices = createAllMatrices();
        var dim = 100;
        for (var i = 0; i < allMatrices.size(); i++) {
            var matrix = allMatrices.get(i);
            var instance = allInstances[i];
            //random
            var randomCities = generateRandomCities(dim);
            var costRandom = totalCostFromWay(randomCities, matrix);
            //heuristic
            var bestWayCities = heuristicWay(randomCities, matrix);
            var costHeuristicWay = totalCostFromWay(bestWayCities, matrix);
            System.out.println("Instance: " + instance + "; Cost for Random: " + costRandom + "; Cost for Heuristic Way: " + costHeuristicWay);
        }
    }

    public static List<int[][]> createAllMatrices() {
        var allInstances = new String[]{"A", "B", "C", "D", "E", "F"};
        return Arrays.stream(allInstances).map(TP1::createMatrixFromFile).toList();
    }

    public static int[][] createMatrixFromFile(String instance) {
        var matrix = new int[100][100];
        try {
            var f = new File(mainDir + "random" + instance + "100.tsp");
            var br = new BufferedReader(new FileReader(f));
            for (var i = 1; i < 101; i++) {
                for (var j = i; j < 101; j++) {
                    var cost = Integer.parseInt(br.readLine());
                    matrix[i - 1][j - 1] = cost;
                    matrix[j - 1][i - 1] = cost;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    public static int distance(int cityA, int cityB, int[][] matrix) {
        return matrix[cityA - 1][cityB - 1];
    }

    public static int totalCostFromWay(Integer[] way, int[][] matrix) {
        int len = way.length - 1;
        var val = distance(way[0], way[len], matrix);
        for (var i = 0; i < len; i++) {
            val += distance(way[i], way[i + 1], matrix);
        }
        return val;
    }

    public static Integer[] generateRandomCities(int dimension) {
        var range = IntStream.rangeClosed(1, dimension).boxed().collect(Collectors.toList());
        Collections.shuffle(range);
        return range.toArray(new Integer[0]);
    }

    public static Integer[] heuristicWay(Integer[] neighbours, int[][] matrix) {
        var neighboursList = Arrays.stream(neighbours).toList();
        var heuristicWay = new ArrayList<Integer>();
        var beginCity = neighboursList.get(0);
        heuristicWay.add(beginCity);
        var tail = neighboursList.size();
        while (tail > 1) {
            //On passe en liste des voisins, tous les voisins restants en enlevant ceux par lesquels on est déjà passé
            var bestNeighbour = bestNeighbour(beginCity, neighboursList.toArray(new Integer[0]), matrix);
            beginCity = bestNeighbour;
            heuristicWay.add(bestNeighbour);
            neighboursList = neighboursList.stream().filter(n -> n != bestNeighbour).toList();
            tail--;
        }
        return heuristicWay.toArray(new Integer[0]);
    }

    public static int bestNeighbour(int beginCity, Integer[] neighbours, int[][] matrix) {
        var min = neighbours[0];
        for (var i : neighbours) {
            if ((i != beginCity) && matrix[beginCity - 1][i - 1] < matrix[beginCity - 1][min - 1]) {
                min = i;
            }
        }
        return min;
    }

}
