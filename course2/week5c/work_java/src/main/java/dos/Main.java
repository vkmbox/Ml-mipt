package dos;

import static java.util.stream.Collectors.toList;

import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.MatrixUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Main{
  
  private double distance_cosine(RealVector aa, RealVector bb) {
    return 1 - aa.cosine(bb);
  }
  
  private List<RealVector> xList(String resource) throws IOException {
    List<RealVector> result = new ArrayList<>();
    try (InputStream xStream = Main.class.getResourceAsStream(resource);
      Scanner scanner = new Scanner(xStream).useDelimiter(";")) {
      int count = 0;
      double[] buffer = null;
      while (scanner.hasNext()) {
        if ( count == 0 ) {
          buffer = new double[64];
        }
        buffer[count++] = Double.parseDouble(scanner.next());
        if ( count == 64 ) {
          result.add(MatrixUtils.createRealVector(buffer));
          count = 0;
        }
      }
      assert count == 0 : "brain drain";
    }
    return result;
  }

  private List<Integer> yList(String resource) throws IOException {
    List<Integer> result = new ArrayList<>();
    try (InputStream xStream = Main.class.getResourceAsStream(resource);
      Scanner scanner = new Scanner(xStream).useDelimiter(";")) {
      while (scanner.hasNext()) {
        result.add(Integer.parseInt(scanner.next()));
      }
    }
    return result;
  }
  
  public void oneNearestNeighbors() throws IOException {

    List<RealVector> data = xList("data.csv");
    List<Integer> target = yList("target.csv");

    int trainSize = (int) Math.round(target.size() * 0.25) ;
    
    final List<RealVector> xtest = data.subList(0, trainSize - 1);
    final List<RealVector> xtrain = data.subList(trainSize, target.size());
    final List<Integer> ytest = target.subList(0, trainSize - 1);
    final List<Integer> ytrain = target.subList(trainSize, target.size());
    
    /*int trainSize = (int) Math.round(target.size() * 0.75) ;
    
    final List<RealVector> xtrain = data.subList(0, trainSize - 1);0,040089
    final List<RealVector> xtest = data.subList(trainSize, target.size());
    final List<Integer> ytrain = target.subList(0, trainSize - 1);
    final List<Integer> ytest = target.subList(trainSize, target.size());*/
    
    assert xtrain.size() == ytrain.size() : "Invalid train data";
    assert xtest.size() == ytest.size() : "Invalid test data";
    
    Function< RealVector, Integer > estimator = ( RealVector arg ) -> {
      int pos = -1;
      double distance = 10.0;
      for ( int ii = 0; ii < xtrain.size(); ii++ ) {
        double dist = distance_cosine(arg, xtrain.get(ii));
        if ( distance > dist ) {
          distance = dist;
          pos = ii;
        }
      }
      assert pos > -1 : "Posution not found";
      return ytrain.get(pos);
    };
    
    List<Integer> yEstimation = xtest.stream().map(estimator).collect(toList());
    int count = 0;
    for ( int ii = 0; ii < ytest.size(); ii++ ) {
      if ( ytest.get(ii).equals( yEstimation.get(ii) ) )
        count++;
    }
    double accuracy = (double)count/(double)ytest.size();
    System.out.println(String.format("AntiAccuracy is : %f", 1-accuracy));
  }
  
  public static void main(String ... args) throws IOException {
    new Main().oneNearestNeighbors();
  }
      //RealVector debug = xtrain.get(xtrain.size()-1); //[xtrain.length-1];
      //System.out.println(xtrain.size());
      //String result = scanner.hasNext() ? scanner.next() : "";
      //InputStreamReader reader = new InputStreamReader(xStream, "UTF-8"); ) {
      //IOUtils.copy(inputStream, writer, encoding);
//String theString = writer.toString();
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