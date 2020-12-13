import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PojoCreator4j {

    public static void main(String[] args) {
        /**
         * 将Navicat软件打开，打开一个数据表，点开右侧的DDL，复制其中的sql语句，运行本代码即可获取其pojo类
         */
        String text = "CREATE TABLE `reservation` (\n" +
                "  `reservation_id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  `borrower_id` int(11) DEFAULT NULL,\n" +
                "  `book_id` int(11) DEFAULT NULL,\n" +
                "  `expire` timestamp NULL DEFAULT NULL,\n" +
                "  PRIMARY KEY (`reservation_id`),\n" +
                "  KEY `fk_Reservation_Borrower1_idx` (`borrower_id`),\n" +
                "  KEY `fk_Reservation_Book1_idx` (`book_id`),\n" +
                "  CONSTRAINT `fk_Reservation_Borrower1` FOREIGN KEY (`borrower_id`) REFERENCES `borrower` (`borrower_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
                "  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;";

        RegUtis();//加载初始化集合
        Matcher m = patternCompile.matcher(text); //具体匹配内容格式：「tel` varchar」

        while(m.find()){
            String[] preSplit = m.group(0).split("` "); //进一步将匹配的内容进行分割成 字段和类型的数据，例如：[id,int]
            String preText =preSplit[0];
            String preType =preSplit[1]; //类型

            if(Integerlist.contains(preType)) { //判断类型打印不同参数的属性成员字段
                System.out.println("private Integer "+preText+";");
            }else if(Stringlist.contains(preType)) {
                System.out.println("private String "+preText+";");
            }else if(Doublelist.contains(preType)) {
                System.out.println("private double "+preText+";");
            }else if(Datelist.contains(preType)) {
                System.out.println("private Date "+preText+";");
            }else{
                System.out.println("private String "+preText+";");
            }
        }
    }

    protected static Pattern patternCompile = Pattern.compile( //利用正在匹配所有可以匹配的字段
            "(?<=`).+(char|varchar|tinytext|text|blob|mediumtext|mediumblob|longtext|longblob|enum|set|"
                    + "tinyint|smallint|mediumint|int|bigint|decimal|float|double|"
                    + "datetime|date|timestamp|year|time)");

    protected static ArrayList<String> Integerlist =new ArrayList<String>(); //对字整数型封装
    protected static ArrayList<String> Stringlist =new ArrayList<String>(); //对字符串进行封装
    protected static ArrayList<String> Doublelist =new ArrayList<String>(); //对小数类型封装
    protected static ArrayList<String> Datelist =new ArrayList<String>(); //对日期进行封装

    protected static void RegUtis() { //给上面四个集合进行初始化赋值

        Collections.addAll(Integerlist,"tinyint|smallint|mediumint|int|bigint|decimal".split("\\|"));
        Collections.addAll(Stringlist,"charsize|varchar|tinytext|text|blob|mediumtext|mediumblob|longtext|longblob|enumxyzetc|enum|set".split("\\|"));
        Collections.addAll(Doublelist,"float|double".split("\\|"));
        Collections.addAll(Datelist,"datetime|date|timestamp|year|time".split("\\|"));
    }
}
