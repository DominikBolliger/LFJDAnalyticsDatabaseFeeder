package logic;

import controller.LFJDAnalyticsDatabaseFeederController;
import javafx.application.Platform;
import modell.Article;
import modell.DBData;
import modell.DataBehaviour;
import util.Util;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

public class Generator extends Thread {
    private LocalDate fromDate;
    private LocalDate toDate;
    private long numberOfDays;
    private int lastOrderNumber;
    private DBConnection con;
    private LFJDAnalyticsDatabaseFeederController controller;

    public Generator(LocalDate fromDate, LocalDate toDate, LFJDAnalyticsDatabaseFeederController controller, DBConnection con) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numberOfDays = DAYS.between(fromDate, toDate);
        this.lastOrderNumber = 0;
        this.controller = controller;
        this.con = con;
    }

    public void createData(int lastArticleOrderID, int lastOrderID) {
        DBData.resetDataList();
        System.out.println(DBData.getDataList().size());
        Random rnd = new Random();
        int ordArtID = lastArticleOrderID;
        int orderID = lastOrderID;

        //TODO: make sure there are more than just 1 order per Day

        for (int i = 0; i < numberOfDays; i++) {
            int posInOrder = rnd.nextInt(20 - 5 + 1) + 5;
            con.createOrder(fromDate.plusDays(i));
            for (int j = 0; j < posInOrder; j++) {

                //TODO: maybe dont just take random Articles

                int articleID = rnd.nextInt(11 - 1 + 1) + 1;
                int behaviourID = 0;
                int multiplicator = 0;
                int month = fromDate.plusDays(i).getMonthValue();
                List<Article> list = Article.getArticleList();
                for (Article article : list) {
                    if (article.getArticleID() == articleID) {
                        for (DataBehaviour behaviour : DataBehaviour.getBehaviourList()) {
                            if (article.getBehaviourID() == behaviour.getId()) {
                                behaviourID = behaviour.getId();
                                multiplicator = behaviour.getMultiplicatorsList().get(month - 1);
                                for (int k = 0; k < multiplicator; k++) {
                                    new DBData(fromDate.plusDays(i), articleID, orderID + i, ordArtID, behaviourID);
                                    ordArtID++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void start() {
        con.connect();
        con.getArticles();
        DataBehaviour.getXmlFile();
        DataBehaviour.createBehaviourFromXml();
        for (Article article:Article.getArticleList()) {
            article.setBehaviourID();
        }
        List<Article> artList = Article.getArticleList();
        List<DataBehaviour> behavList = DataBehaviour.getBehaviourList();
        behavList.size();
        artList.size();
//        int count = 0;
//        con.connect();
//        con.getArticles();
//
//        long startTime = System.nanoTime();
//        createData(con.getLastArticleOrderID(), con.getLastOrderID());
//        for (int i = 0; i < DBData.getDataList().size(); i++) {
//            count = i;
//            con.writeDataToDB(i, DBData.getDataList().get(i).getOrderID(), DBData.getDataList().get(i).getArticleID(), controller);
//            int finalI = i;
//            Platform.runLater(() -> controller.pgbResult.setProgress((float) 1 / DBData.getDataList().size() * finalI));
//            Util.delay(5);
//        }
//        con.close();
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime) / 1000000000;
//        int finalCount = count;
//        Platform.runLater(() -> controller.taResult.appendText("Database updated with " + finalCount + " items in " + duration + " seconds..\n"));
    }
}
