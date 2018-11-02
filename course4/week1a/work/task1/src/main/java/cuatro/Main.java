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
import org.apache.commons.math3.distribution.TDistribution;
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
    TDistribution normal = new TDistribution(values.length - 1);
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
    System.out.println("1) lower: " + interval1[0] + ", upper :" + interval1[1] );
    /*
    На данных из предыдущего вопроса постройте 95% доверительный интервал для средней
    годовой смертности по всем южным городам. Чему равна его верхняя граница?
    Округлите ответ до 4 знаков после десятичной точки.
     */
    double[] mort2 = rows.stream().filter(row -> "South".equals(row.location)) .mapToDouble(Row::getMortality).toArray();
    double[] interval2 = confidenceInterval(mort2, 0.05);
    System.out.println("2) lower: " + interval2[0] + ", upper :" + interval2[1] );
    /*
    На тех же данных постройте 95% доверительный интервал для средней годовой
    смертности по всем северным городам. Пересекается ли этот интервал с предыдущим? 
    Как вы думаете, какой из этого можно сделать вывод?
     */
    double[] mort3 = rows.stream().filter(row -> "North".equals(row.location)) .mapToDouble(Row::getMortality).toArray();
    double[] interval3 = confidenceInterval(mort3, 0.05);
    System.out.println("3) lower: " + interval3[0] + ", upper :" + interval3[1] );
    /*
    Пересекаются ли 95% доверительные интервалы для средней жёсткости воды в северных и южных городах?
     */
    /*
_tconfint_generic(data[data.location == 'South'].hardness.mean(), data[data.location == 'South'].hardness.std(ddof=1) / np.sqrt(len(data[data.location == 'South'])), len(data[data.location == 'South']) - 1, 0.05, 'two-sided')
_tconfint_generic(data[data.location == 'North'].hardness.mean(), data[data.location == 'North'].hardness.std(ddof=1) / np.sqrt(len(data[data.location == 'North'])), len(data[data.location == 'North']) - 1, 0.05, 'two-sided')    
    */
    double[] Hardness1 = rows.stream()
        .filter(row -> "South".equals(row.location) && (row.getHardness() != null) ) 
        .mapToDouble(Row::getHardness).toArray();
    double[] interval41 = confidenceInterval(Hardness1, 0.05);
    System.out.println("4a) lower: " + interval41[0] + ", upper :" + interval41[1] );

    double[] Hardness2 = rows.stream()
        .filter(row -> "North".equals(row.location) && (row.getHardness() != null) ) 
        .mapToDouble(Row::getHardness).toArray();
    double[] interval42 = confidenceInterval(Hardness2, 0.05);
    System.out.println("4b) lower: " + interval42[0] + ", upper :" + interval42[1] );

    NormalDistribution norm = new NormalDistribution();
    System.out.println("5)Size is: " 
        + 100 * Math.pow(norm.inverseCumulativeProbability(0.975), 2 ));
    System.out.println("---------------");
    /*
from statsmodels.stats.proportion import proportion_confint
proportion_confint(1, 50, method = 'normal')    
    */
    double pp = 1.0/50.0;
    double delta = norm.inverseCumulativeProbability(0.975)*FastMath.sqrt((pp*(1-pp))/50.0);
    System.out.println("1) lower: " + (pp-delta) + ", upper :" + (pp+delta) ); 
  }
}