package fil.adom;

public class TP2_TwoOpt {

    public static void main(String[] args) {
        var allMatrices = TP1.createAllMatrices();
        var dim = 100;
        for (var i = 0; i < allMatrices.size(); i++) {
            var matrix = allMatrices.get(i);
            var instance = TP1.allInstances[i];
            //random
            var randomCities = TP1.generateRandomCities(dim);
            //best two-opt
            var randomCitiesBestTwoOpt = bestNeighboursTwoOpt(randomCities, matrix);
            var costRandomBestTwoOpt = TP1.totalCostFromWay(randomCitiesBestTwoOpt, matrix);
            //first two-opt
            var randomCitiesFirstTwoOpt = firstNeighboursTwoOpt(randomCities, matrix);
            var costRandomFirstTwoOpt = TP1.totalCostFromWay(randomCitiesFirstTwoOpt, matrix);
            System.out.println("Instance: " + instance + "; Cost for Random Cities and TwoOpt: Best: " + costRandomBestTwoOpt
                    + "; First: " + costRandomFirstTwoOpt);
            //heuristic
            var heuristicCities = TP1.heuristicWay(randomCities, matrix);
            //best two-opt
            var heuristicCitiesBestTwoOpt = bestNeighboursTwoOpt(heuristicCities, matrix);
            var costHeuristicBestTwoOpt = TP1.totalCostFromWay(heuristicCitiesBestTwoOpt, matrix);
            //first two-opt
            var heuristicCitiesFirstTwoOpt = firstNeighboursTwoOpt(heuristicCities, matrix);
            var costHeuristicFirstTwoOpt = TP1.totalCostFromWay(heuristicCitiesFirstTwoOpt, matrix);
            System.out.println("Instance: " + instance + "; Cost for Heuristic Cities and TwoOpt: Best: " + costHeuristicBestTwoOpt
                    + "; First: " + costHeuristicFirstTwoOpt);
        }
    }

    public static int gainTwoOpt(int i, int j, Integer[] cities, int[][] matrix) {
        var cost1 = matrix[cities[i] - 1][cities[i + 1] - 1] + matrix[cities[j] - 1][cities[j - 1] - 1];
        var cost2 = matrix[cities[i + 1] - 1][cities[j] - 1] + matrix[cities[j - 1] - 1][cities[i] - 1];
        return cost1 - cost2;
    }

    public static Integer[] twoOpt(Integer[] cities, int i, int j) {
        var optWay = new Integer[cities.length];
        optWay = cities.clone();
        int d = Math.abs(j - i);
        d = (d + 1) / 2;
        for (int k = 1; k < d; k++) {
            optWay[i + k] = cities[j - k];
            optWay[j - k] = cities[i + k];
        }
        return optWay;
    }

    public static Integer[] bestNeighboursTwoOpt(Integer[] cities, int[][] matrix) {
        var optWay = new Integer[cities.length]; 
        var best = 0;
        var changes = 0;
        for (var i = 0; i < cities.length - 2; i++) { //2 par 2 à cause du two-opt
            for (var j = i + 3; j < cities.length; j++) { //on cherche si on peut two-opt pour chaque ville
                var gain = gainTwoOpt(i, j, cities, matrix);
                if (gain > best) {
                    optWay = twoOpt(cities, i, j);
                    changes++;
                    best = gain;
                }
            }
            if (changes != 0)
                break;
        }
        if (changes == 0) {
            return cities;
        } else
            return bestNeighboursTwoOpt(optWay, matrix);
    }

    public static Integer[] firstNeighboursTwoOpt(Integer[] cities, int[][] matrix) {
        var optWay = new Integer[cities.length];
        for (var i = 0; i < cities.length - 2; i++) {
            for (var j = i + 3; j < cities.length; j++) {
                var gain = gainTwoOpt(i, j, cities, matrix);
                if (gain > 0) { //on stop dès qu'on trouve un améliorant
                    optWay = twoOpt(cities, i, j);
                    return firstNeighboursTwoOpt(optWay, matrix);
                }
            }
        }
        return cities;
    }
    
}
