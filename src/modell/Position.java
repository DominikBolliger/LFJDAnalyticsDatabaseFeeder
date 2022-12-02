package modell;

import java.util.ArrayList;
import java.util.List;

public class Position {
    private Article article;

    private int orderID;

    private static List<Position> positionList = new ArrayList<>();

    public Position(Article article, int orderID) {
        this.article = article;
        this.orderID = orderID;
        positionList.add(this);
    }

    public static List<Position> getPositionList() {
        return positionList;
    }

    public Article getArticle() {
        return article;
    }

    public int getOrderID() {
        return orderID;
    }
}
