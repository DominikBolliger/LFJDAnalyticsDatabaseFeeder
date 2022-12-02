package modell;

import java.util.ArrayList;
import java.util.List;

public class Article {
    private int articleID;
    private String name;
    private double price;
    private int behaviourID;
    private static List<Article> articleList = new ArrayList<>();

    //TODO: Make sure articles are coming from th db into an XML file and then associate behaviour here

    public Article(int articleID, String name , double price) {
        this.articleID = articleID;
        this.name = name;
        this.price = price;
        this.behaviourID = 0;
        articleList.add(this);
    }

    public void setBehaviourID(){
        for (DataBehaviour behaviour:DataBehaviour.getBehaviourList()) {
            for (Integer id:behaviour.getArticleList()){
                if (this.articleID == id){
                    this.behaviourID = behaviour.getId();
                }
            }
        }
    }

    public int getArticleID() {
        return articleID;
    }

    public String getName() {
        return name;
    }

    public int getBehaviourID() {
        return behaviourID;
    }

    public static List<Article> getArticleList() {
        return articleList;
    }
}
