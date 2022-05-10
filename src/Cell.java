import java.util.ArrayList;
import java.util.List;

public class Cell {
    String name;
    List<Double> attributes = new ArrayList<>();

    public double euclideanDistance(Cell cell){
        double distance = 0;
        for (int i = 0; i < attributes.size(); i++) {
            distance += Math.pow(Math.abs(this.attributes.get(i) - cell.attributes.get(i)),2);
        }
        distance = Math.sqrt(distance);
        return Math.sqrt(distance);
    }

    @Override
    public String toString() {
        return "Cell : " + attributes + name + '\n';
    }
}
