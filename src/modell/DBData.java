package modell;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBData {

    private LocalDate buyDate;
    private int articleID;
    private int orderID;
    private int orderArticleID;

    private int behaviourID;
    private static List<DBData> dataList = new ArrayList<>();

    public DBData(LocalDate buyDate, int articleID, int orderID, int orderArticleID, int behaviourID){
        this.buyDate = buyDate;
        this.articleID = articleID;
        this.orderID = orderID;
        this.orderArticleID = orderArticleID;
        this.behaviourID = behaviourID;
        dataList.add(this);
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public int getArticleID() {
        return articleID;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getOrderArticleID() {
        return orderArticleID;
    }

    public static List<DBData> getDataList() {
        return dataList;
    }

    public static void resetDataList(){
        dataList.clear();
    }

}
