package cuatro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.FastMath;

public class Main {
  
  @Getter @Setter @Accessors(chain=true)
  static class Row {
    private String location;	
    private String town;
    private Integer mortality;	
    private Integer hardness;
    
    public static Row fromString( String line ) {
      String val[] = line.split("\t");
      if ( val.length != 4 )
        throw new IllegalArgumentException("line of wrong format");
      return new Row().setLocation(val[0]).setTown(val[1])
          .setMortality(Integer.parseInt(val[2])).setHardness(Integer.parseInt(val[3]));
    }
  }
  
  public static double[] confidenceInterval( double[] values, double alpha ) {
    if (values.length == 0 ) return null;
    NormalDistribution normal = new NormalDistribution();
    double quantile = normal.inverseCumulativeProbability(1 - alpha/2);
    //double std = FastMath.sqrt(StatUtils.variance(values));
    double high = StatUtils.mean(values) 
        + quantile * FastMath.sqrt(StatUtils.variance(values)/values.length);
    double low = StatUtils.mean(values) 
        - quantile * FastMath.sqrt(StatUtils.variance(values)/values.length);
    return new double[] {low, high};
  }
  
  public static void main(String ... args) throws IOException {
    String file = "c:\\MyProj\\Coursera\\Ml-mipt\\course4\\week1a\\work\\task1\\water.txt";
    List<Row> rows = Files.lines(Paths.get(file)).skip(1).map(Row::fromString).collect(Collectors.toList());
    /*
    Постройте 95% доверительный интервал для средней годовой смертности в больших городах.
    Чему равна его нижняя граница? Округлите ответ до 4 знаков после десятичной точки.
     */
    double[] mort1 = rows.stream().mapToDouble(Row::getMortality).toArray();
    double[] interval1 = confidenceInterval(mort1, 0.05);
    System.out.println(interval1[0]);
    /*
    На данных из предыдущего вопроса постройте 95% доверительный интервал для средней
    годовой смертности по всем южным городам. Чему равна его верхняя граница?
    Округлите ответ до 4 знаков после десятичной точки.
     */
    /*
    На тех же данных постройте 95% доверительный интервал для средней годовой
    смертности по всем северным городам. Пересекается ли этот интервал с предыдущим? 
    Как вы думаете, какой из этого можно сделать вывод?
     */
    /*
    Пересекаются ли 95% доверительные интервалы для средней жёсткости воды в северных и южных городах?
     */
  }
}