package fil.adom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TP4_OffLine {

    public static final String mainDir = "C:\\Users\\monpc\\Desktop\\adom-tsp-dumortier-debroucker\\files\\generated\\OffLine\\";

    public static void main(String[] args) {
        var allMatrices = TP1.createAllMatrices();
        var dim = 100;
        var nb = 500;
        for (var i = 0; i < 6; i+=2) {
            var allDatas = createAllData(dim, nb, allMatrices.get(i), allMatrices.get(i+1));
            var filtered = filteredOffLine(allDatas);
            writeInfo(filtered, TP1.allInstances[i] + TP1.allInstances[i+1]);
        }
    }

    public static List<TPA_Data> createAllData(int dim, int nb, int[][] matrix1, int[][] matrix2) {
        var all = new ArrayList<TPA_Data>();
        for (var i = 0; i < nb; i++) {
            var way = TP1.generateRandomCities(dim);
            var allCost = TP4.evaluateTwoObjectives(way, matrix1, matrix2);
            all.add(new TPA_Data(way, matrix1, allCost[0], matrix2, allCost[1], false));
        }
        return all;
    }

    public static List<TPA_Data> filteredOffLine(List<TPA_Data> datas) {
        var i = 0;
        var len = datas.size();
        while (i < len) {
            int j = i;
            var dataI = datas.get(i);
            while (j < len && !dataI.dominate) {
                var dataJ = datas.get(j);
                if (isDominate(dataJ, dataI)) {
                    dataI.dominate = true;
                } else if (isDominate(dataI, dataJ)) {
                    dataJ.dominate = true;
                }
                j++;
            }
            i++;
        }
        return datas.stream().filter(data -> data.dominate).toList();
    }

    public static boolean isDominate(TPA_Data data1, TPA_Data data2) {
        return data1.cost1 < data2.cost1 && data1.cost2 < data2.cost2;
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
