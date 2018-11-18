package cuatro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;

public class MainHelper
{
  @Getter @Setter // @AllArgsConstructor
  public static class Tree {
    private double sn;
    private double we;
    
    public Tree( String arg ) {
      String[] vals = arg.split("\\t");
      if ( vals.length != 2 ) throw new IllegalArgumentException(arg);
      sn = Double.valueOf(vals[0]);
      we = Double.valueOf(vals[1]);
    }
    
    public int index() {
      return (int)((5 * Math.min(Math.round(sn/40), 4)) + Math.min(Math.round(we/40),4));
    }
  }
  
  private final String input;  //= "e:/MyProj/Courcera/Ml-mipt/course4/week1b/work/pines.txt";
  //private final String output;

  public MainHelper( String input ) throws IOException
  {
    this.input = input;
    //this.output = output;
    //this.lines = Files.lines(Paths.get(input));
  }

  void printf(String format, Object... args)
  {
    System.out.printf(format, args);
  }

  //String input;  //= "e:/MyProj/Courcera/Ml-mipt/course4/week1b/work/pines.txt";

  Stream<String> lines;

  public void run() throws Exception
  {
    
    final int[] trees = new int[25];
    
   // Consumer( Tree ) = (Tree tree) -> { int[tree.index()]++; };
    
    Stream<String> lines = Files.lines(Paths.get(input));
    List<Tree> collect = lines.skip(1).map(Tree::new).collect(Collectors.toList());
    for ( Tree tree : collect ) {
      trees[tree.index()]++;
    }
    List<Integer> list = Arrays.stream(trees).boxed().collect(Collectors.toList());
    System.out.println(list); 
    
    //lines.forEach(xx -> System.out.println(xx));
    //lines.forEach(xx -> System.out.println(xx));

  }
}
