package fil.adom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TP4_OnLine {

    public static final String mainDir = "./files/generated/OnLine/";

    public static void main(String[] args) {
        var allMatrices = TP1.createAllMatrices();
        var dim = 100;
        var nb = 1000;
        for (var i = 0; i < 6; i+=2) {
            var filtered = filteredOnnLineList(nb, dim, allMatrices.get(i), allMatrices.get(i+1));
            writeInfo(filtered, TP1.allInstances[i] + TP1.allInstances[i+1]);
        }
    }

    public static List<TPA_Data> filteredOnnLineList(int nb, int dim, int[][] matrix1, int[][] matrix2) {
        var res = new ArrayList<TPA_Data>();
        for (var i = 0; i < nb; i++) {
            var data = TP4_OffLine.createOneData(dim, matrix1, matrix2);
            if (filteredOnLine(data, res)) {
                res.add(data);
            }
        }
        return res.stream().filter(data -> !data.dominate).toList();
    }

    public static Boolean filteredOnLine(TPA_Data data, List<TPA_Data> datas) {
        int i = 0;
        var len = datas.size();
        while (i < len && !data.dominate) {
            var dataI = datas.get(i);
            if (data.isDominateBy(dataI)) {
                data.dominate = true;
            } else if (dataI.isDominateBy(data)) {
                dataI.dominate = true;
            }
            i++;
        }
        return !data.dominate;
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
