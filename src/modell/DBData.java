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
    public static List<DBData> dataList = new ArrayList<>();

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

    public String toString(){
        System.out.println(this.buyDate  );
        return null;
    }

    public static void printList(){
        for (DBData data:dataList) {
            System.out.println("Date: " + data.buyDate + " | ArticleID: " + data.articleID + " | OrderID: " + data.orderID + " | OrdAartID: " + data.orderArticleID );
        }
    }

}
