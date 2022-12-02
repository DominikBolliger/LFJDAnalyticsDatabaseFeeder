package modell;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBData {

    private int articleID;
    private int orderID;
    private static List<DBData> dataList = new ArrayList<>();

    public DBData(int articleID, int orderID) {
        this.articleID = articleID;
        this.orderID = orderID;
        dataList.add(this);
    }

    public int getArticleID() {
        return articleID;
    }

    public int getOrderID() {
        return orderID;
    }

    public static List<DBData> getDataList() {
        return dataList;
    }

    public static void resetDataList() {
        dataList.clear();
    }

}
