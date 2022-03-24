package fil.adom;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TPA_Data {
    public Integer[] way;
    public int[][] matrix1;
    public Integer cost1;
    public int[][] matrix2;
    public Integer cost2;
    public boolean dominate;

    public boolean isDominateBy(TPA_Data other) {
        return cost1 < other.cost1 && cost2 < other.cost2;
    }
}
