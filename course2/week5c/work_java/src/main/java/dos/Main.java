package dos;

//import org.apache.commons.math4.linear.Array2DRowRealMatrix;
//import org.apache.commons.math4.linear.RealMatrix;
//import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.MatrixUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main{
  
  public static double distance_cosine(RealVector aa, RealVector bb) {
    return 1 - aa.cosine(bb);
  }
  
  public static void main(String ... args) throws IOException {
    List<RealVector> xtrain = new ArrayList<>();
    try (InputStream xStream = Main.class.getResourceAsStream("xtrain.csv");
      Scanner scanner = new Scanner(xStream).useDelimiter(";")) {
      int count = 0;
      double[] buffer = null;
      while (scanner.hasNext()) {
        if ( count == 0 ) {
          buffer = new double[64];
        }
        buffer[count++] = Double.parseDouble(scanner.next());
        if ( count == 64 ) {
          xtrain.add(MatrixUtils.createRealVector(buffer));
        }
      }
      //String result = scanner.hasNext() ? scanner.next() : "";
    }
      //InputStreamReader reader = new InputStreamReader(xStream, "UTF-8"); ) {
      //IOUtils.copy(inputStream, writer, encoding);
//String theString = writer.toString();
    }
    //File file = new File("E:\\MyProj\\Python\\curs\\1\\Task1a\\cats.txt");
    /*String file = "E:\\MyProj\\Python\\curs\\1\\Task1a\\cats.txt";
    List<List<String>> lines = 
    //Stream<?> lines =
      Files.lines(Paths.get(file)).map(s -> s.toLowerCase().replace('.', ' ').replace(',', ' ').replace('\t', ' '))
        .map(s -> Arrays.asList(s.split(" +")))
        .collect(Collectors.toList());
  
    List<String> distinct = lines.stream().flatMap( x -> x.stream()).distinct().collect(Collectors.toList());
    
    RealMatrix vectors = new Array2DRowRealMatrix(lines.size(), distinct.size()); 
    int col; double[] vector;
    for ( int row = 0; row < lines.size(); row++ ) {
      List<String> line = lines.get(row);
      vector = vectors.getRow(row);
      for (String word : line)
      {
        col = distinct.indexOf(word);
        vector[col]++;
        //vectors. .getEntry(row, col);
      }
      vectors.setRow(row, vector);
    }
    
    //lines.forEach(System.out::println);
    //vectors.toString();
    //System.out.println(distinct);
    //for ( int row = 0; row < lines.size(); row++ )
    //  System.out.println(Arrays.toString( vectors.getRow(row)));
    int best = -1, second = -1; double bestV = 2, secondV = 2; 
    RealVector base = vectors.getRowVector(0);
    for ( int row = 1; row < lines.size(); row++ ) {
      double dist = 1 - base.cosine( vectors.getRowVector(row));
      System.out.println(String.format("Строка %d, расстояние - %f", row, dist));
      if (dist < bestV) {
        second = best; secondV = bestV;
        best = row; bestV = dist;
      } else if (dist < secondV) {
        second = row; secondV = dist;
      }
    }
    
    System.out.println(String.format("Лучшее про котов - %d, на втором месте - %d", best, second));*/
      
}