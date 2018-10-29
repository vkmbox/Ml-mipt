package cuatro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
  
  public static void main(String args) throws IOException {
    String file = "c:\\MyProj\\Coursera\\Ml-mipt\\course4\\week1a\\work\\task1\\water.txt";
    List<Row> rows = Files.lines(Paths.get(file)).map(Row::fromString).collect(Collectors.toList());
    /*
    Постройте 95% доверительный интервал для средней годовой смертности в больших городах. 
    Чему равна его нижняя граница? Округлите ответ до 4 знаков после десятичной точки.    
    */
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