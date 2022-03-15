package fil.adom;

public class TP2_Swap {

    public static void main(String[] args) {
        var allMatrices = TP1.createAllMatrices();
        var dim = 100;
        for (var i = 0; i < allMatrices.size(); i++) {
            var matrix = allMatrices.get(i);
            var instance = TP1.allInstances[i];
            //random
            var randomCities = TP1.generateRandomCities(dim);
            //best swap
            var randomCitiesBestSwap = bestNeighboursSwap(randomCities.clone(), matrix);
            var costRandomBestSwap = TP1.totalCostFromWay(randomCitiesBestSwap, matrix);
            //first swap
            var randomCitiesFirstSwap = firstNeighboursSwap(randomCities.clone(), matrix);
            var costRandomFirstSwap = TP1.totalCostFromWay(randomCitiesFirstSwap, matrix);
            System.out.println("Instance: " + instance + "; Cost for Random Cities and Swap: Best: " + costRandomBestSwap
                    + "; First: " + costRandomFirstSwap);
            //heuristic
            var heuristicCities = TP1.heuristicWay(randomCities.clone(), matrix);
            //best swap
            var heuristicCitiesBestSwap = bestNeighboursSwap(heuristicCities.clone(), matrix);
            var costHeuristicBestSwap = TP1.totalCostFromWay(heuristicCitiesBestSwap, matrix);
            //first swap
            var heuristicCitiesFirstSwap = firstNeighboursSwap(heuristicCities.clone(), matrix);
            var costHeuristicFirstSwap = TP1.totalCostFromWay(heuristicCitiesFirstSwap, matrix);
            System.out.println("Instance: " + instance + "; Cost for Heuristic Cities and Swap: Best: " + costHeuristicBestSwap
                    + "; First: " + costHeuristicFirstSwap);
        }
    }

    public static void swap(int i, int j, Integer[] cities) {
        int temp = cities[i];
        cities[i] = cities[j];
        cities[j] = temp;
    }

    public static int gainSwap(int i, int j, Integer[] way, int[][] matrix) {
        var previousI = i - 1;
        var nextJ = j + 1;
        if (i == 0) {
            previousI = way.length - 1;
        }
        if (j == way.length - 1) {
            nextJ = 0;
        }
        var cost1 = 0;
        var cost2 = 0;
        //on calcule seulement les nouveaux chemins. ex : A,B,C,D,E, on swap A et E, donc E,B,C,D,A, on calcule D-A et E-B
        if (j == i + 1) {
            cost1 = matrix[way[previousI] - 1][way[i] - 1] + matrix[way[j] - 1][way[nextJ] - 1];
            cost2 = matrix[way[previousI] - 1][way[j] - 1] + matrix[way[i] - 1][way[nextJ] - 1];
        }
        else if (i == 0 && j == way.length - 1) {
            cost1 = matrix[way[i] - 1][way[i + 1] - 1] + matrix[way[j - 1] - 1][way[j] - 1];
            cost2 = matrix[way[j] - 1][way[i + 1] - 1] + matrix[way[j - 1] - 1][way[i] - 1];
        } else {
            cost1 = matrix[way[previousI] - 1][way[i] - 1] + matrix[way[i] - 1][way[i+1] - 1]
                    + matrix[way[j-1] - 1][way[j] - 1] + matrix[way[j] - 1][way[nextJ] - 1];
            cost2 = matrix[way[previousI] - 1][way[j] - 1] + matrix[way[j] - 1][way[i+1] - 1]
                    + matrix[way[j-1] - 1][way[i] - 1] + matrix[way[i] - 1][way[nextJ] - 1];
        }
        return cost1 - cost2;
    }

    public static Integer[] bestNeighboursSwap(Integer[] cities, int[][] matrix) {
        var optWay = new Integer[cities.length]; //nouvelle liste de ville optimisée
        optWay = cities.clone();
        var changes = 0;
        var bestGain = 0;
        for (var i = 0; i < cities.length - 1; i++) {
            for (var j = i + 1; j < cities.length; j++) { //on cherche si on peut swap pour chaque ville
                var gain = gainSwap(i, j, cities, matrix);
                if (gain > bestGain) {
                    swap(i, j, cities);
                    changes++;
                    bestGain = gain;
                    optWay = cities.clone();
                    swap(i, j, cities); //on reswap pour revenir dans l'état précédent (1er swap pour le .clone)
                }
            }
        }
        if (changes == 0) {
            return optWay;
        } else {
            return bestNeighboursSwap(optWay, matrix);
        }
    }

    public static Integer[] firstNeighboursSwap(Integer[] cities, int[][] matrix) {
        for (var i = 0; i < cities.length - 1; i++) {
            for (var j = i + 1; j < cities.length; j++) {
                var gain = gainSwap(i, j, cities, matrix);
                if (gain > 0){ //on stop dès qu'on trouve un améliorant
                    swap(i, j, cities);
                    return firstNeighboursSwap(cities, matrix);
                }
            }
        }
        return cities;
    }

}
