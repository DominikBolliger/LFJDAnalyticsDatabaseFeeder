package modell;

import java.util.ArrayList;
import java.util.List;

public class Article {
    private int articleID;
    private String name;
    private int behaviourID;
    private static List<Article> articleList = new ArrayList<>();

    public Article(int articleID, String name, int behaviourID) {
        this.articleID = articleID;
        this.name = name;
        this.behaviourID = behaviourID;
        articleList.add(this);
    }

    public int getArticleID() {
        return articleID;
    }

    public String name() {
        return name;
    }

    public int getBehaviourID() {
        return behaviourID;
    }

    public static List<Article> getArticleList() {
        return articleList;
    }
}
