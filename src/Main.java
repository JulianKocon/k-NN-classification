import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File trainingFile = new File("iris_training.txt");
        File testFile = new File("iris_test.txt");

        List<Cell> trainingCells = convertFileToList(trainingFile);
        List<Cell> testCells = convertFileToList(testFile);

        Scanner input = new Scanner(System.in);
        int k = input.nextInt();
        calculate(testCells, trainingCells, k);


        while(true){
            Scanner in = new Scanner(System.in);
            System.out.println("Input correct number of atributes separated by a Tab");
            System.out.println("For example: 2,0    3,4    4,0    2,8    Check");
            Cell testCell = convertStringToCell(in.nextLine());
            String calculatedColor = testNewCell(trainingCells, testCell,k);
            System.out.println("Color: " + calculatedColor + '\n');
        }

    }

    private static void calculate(List<Cell> testCells, List<Cell> trainingCells, int k) {
        int correctlyCalculatedCells = 0;
        int numberOfCells = testCells.size();
        for(Cell cell : testCells){
            String calculatedColor = testNewCell(trainingCells, cell, k);
            if(Objects.equals(cell.name.trim(), calculatedColor)){
                correctlyCalculatedCells++;
            }
        }
        double percentage = ((double)correctlyCalculatedCells/numberOfCells)*100;
        System.out.println( percentage + "%");
        System.out.println("Number of correctly guessed cells: " + correctlyCalculatedCells + '\n');
    }

    public static String testNewCell(List<Cell> trainingCells, Cell newCell, int k){
        List<Cell> closestCells = trainingCells.stream()
                .sorted(Comparator.comparingDouble(c -> c.euclideanDistance(newCell)))
                .limit(k)
                .toList();

        Map<String, Long> temp = closestCells.stream()
                .collect(Collectors.groupingBy(a -> a.name, Collectors.counting()));

        return Collections.max(temp.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static ArrayList<Cell> convertFileToList(File inputFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(inputFile);
        ArrayList<Cell> cells = new ArrayList<>();

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
           Cell cell = convertStringToCell(line);
            cells.add(cell);
        }
        scanner.close();
        return cells;
    }

    public static Cell convertStringToCell(String line){
        String[] split = line.split("\t");
        Cell cell = new Cell();

        cell.name = split[split.length -1];

        for (int i = 0; i < split.length - 1; i++) {
            Double attribute = Double.parseDouble(split[i].replaceAll(",","."));
            cell.attributes.add(attribute);
        }
        return cell;
    }
}
