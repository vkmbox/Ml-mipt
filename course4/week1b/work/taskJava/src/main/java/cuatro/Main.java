package cuatro;

import java.io.IOException;

public class Main {
  
  public static void main(String ... args) throws IOException, Exception {
    String file = "e:/MyProj/Courcera/Ml-mipt/course4/week1b/work/pines.txt";
    new MainHelper(file).run();
    //List<Row> rows = Files.lines(Paths.get(file)).skip(1).map(Row::fromString).collect(Collectors.toList());
  }
}