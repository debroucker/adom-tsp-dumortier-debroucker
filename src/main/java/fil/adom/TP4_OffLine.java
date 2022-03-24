package fil.adom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TP4_OffLine {

    public static final String mainDir = "./files/generated/OffLine/";

    public static void main(String[] args) {
        var allMatrices = TP1.createAllMatrices();
        var dim = 100;
        var nb = 1000;
        for (var i = 0; i < 6; i+=2) {
            var allDatas = createAllData(dim, nb, allMatrices.get(i), allMatrices.get(i+1));
            var filtered = filteredOffLine(allDatas);
            writeInfo(filtered, TP1.allInstances[i] + TP1.allInstances[i+1]);
        }
    }

    public static TPA_Data createOneData(int dim, int[][] matrix1, int[][] matrix2) {
        var way = TP1.generateRandomCities(dim);
        var allCost = TP4.evaluateTwoObjectives(way, matrix1, matrix2);
        return new TPA_Data(way, matrix1, allCost[0], matrix2, allCost[1], false);
    }

    public static List<TPA_Data> createAllData(int dim, int nb, int[][] matrix1, int[][] matrix2) {
        var all = new ArrayList<TPA_Data>();
        for (var i = 0; i < nb; i++) {
            all.add(createOneData(dim, matrix1, matrix2));
        }
        return all;
    }

    public static List<TPA_Data> filteredOffLine(List<TPA_Data> datas) {
        var len = datas.size();
        for (var i = 0; i < len; i++) {
            int j = i;
            var dataI = datas.get(i);
            while (j < len && !dataI.dominate) {
                var dataJ = datas.get(j);
                if (dataJ.isDominateBy(dataI)) {
                    dataJ.dominate = true;
                } else if (dataI.isDominateBy(dataJ)) {
                    dataI.dominate = true;
                }
                j++;
            }
        }
        return datas.stream().filter(data -> !data.dominate).toList();
    }

    public static void writeInfo(List<TPA_Data> datas, String instance) {
        try {
            var f = new File(mainDir + instance + "100.tsp");
            var writer = new BufferedWriter(new FileWriter(f));
            for (var data:  datas) {
                    writer.write(data.cost1 + "\t" + data.cost2 + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
