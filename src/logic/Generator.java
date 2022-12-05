package logic;

import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import controller.LFJDAnalyticsDatabaseFeederController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import modell.Article;
import modell.DBData;
import modell.DataBehaviour;
import modell.Position;
import util.Const;
import util.LFJDLogger;
import util.Util;

import java.time.LocalDate;
import java.util.ArrayList;
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

    private Random rnd = new Random();

    public Generator(LocalDate fromDate, LocalDate toDate, LFJDAnalyticsDatabaseFeederController controller, DBConnection con) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numberOfDays = DAYS.between(fromDate, toDate);
        this.lastOrderNumber = 0;
        this.controller = controller;
        this.con = con;
    }

    public void createData() {
        int rndOrdersPerActualDay;
        int rndArticlesPerActualOrder;
        int multiplicator;
        int month;
        int lastOrderID;
        List<Article> allArticles = Article.getArticleList();

        for (int i = 0; i < numberOfDays; i++) {
            int maxNumberOfOrderPerDay = Const.MAX_ORDER_PER_DAY;
            int minNumberOfOrderPerDay = Const.MIN_ORDER_PER_DAY;
            int maxNumberOfArticlesPerOrder = Const.MAX_ARTICLES_PER_ORDER;
            int minNumberOfArticlesPerOrder = Const.MIN_ARTICLES_PER_ORDER;
            lastOrderID = con.getLastOrderID();
            rndOrdersPerActualDay = rnd.nextInt(maxNumberOfOrderPerDay - minNumberOfOrderPerDay + 1) + minNumberOfOrderPerDay;
            month = fromDate.plusDays(1).getMonthValue();
            for (int j = 0; j < rndOrdersPerActualDay; j++) {
                con.createOrder(fromDate.plusDays(i));
                Position.getPositionList().clear();
                rndArticlesPerActualOrder = rnd.nextInt(maxNumberOfArticlesPerOrder - minNumberOfArticlesPerOrder + 1) + minNumberOfArticlesPerOrder;
                while (Position.getPositionList().size() < rndArticlesPerActualOrder) {
                    Article article = allArticles.get(rnd.nextInt(allArticles.size()));
                    multiplicator = 0;
                    switch (article.getBehaviourID()) {
                        case 4:
                            multiplicator = rnd.nextInt(5 - 2 + 1) + 2;
                            break;
                        case 3, 2, 1:
                            for (DataBehaviour behaviour : DataBehaviour.getBehaviourList()) {
                                if (article.getBehaviourID() == behaviour.getId()) {
                                    multiplicator = behaviour.getMultiplicatorsList().get(month - 1);
                                    if (multiplicator > 1) {

                                        multiplicator = rnd.nextInt(multiplicator - (multiplicator / 2) + 1) + (multiplicator / 2);
                                    } else {
                                        multiplicator = rnd.nextInt(6 - 1 + 1) + 1;
                                        if (multiplicator != 1) {
                                            multiplicator = 0;
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                    }
                    for (int k = 0; k < multiplicator; k++) {
                        if (Position.getPositionList().size() < rndArticlesPerActualOrder) {
                            new Position(article, lastOrderID + j);
                            LFJDLogger.log("Position: " + (lastOrderID + j) + " | Article: " + article.getName() + " | Time: " + fromDate.plusDays(i));
                        }
                    }
                }
                for (Position position : Position.getPositionList()) {
                    new DBData(position.getArticle().getArticleID(), position.getOrderID());
                }
            }
        }
    }

    @Override
    public void run() {
        con.connect();
        con.getArticles();
        DataBehaviour.getXmlFile();
        DataBehaviour.createBehaviourFromXml();
        for (Article article : Article.getArticleList()) {
            article.setBehaviourID();
        }
        int count = 0;
        con.connect();
        long startTime = System.nanoTime();
        Platform.runLater(() -> controller.taResult.appendText("Preparing Data...\n"));
        createData();
        Platform.runLater(() -> controller.taResult.appendText("Writing to Database...\n"));
        for (int i = 0; i < DBData.getDataList().size(); i++) {
            count = i;
            con.writeDataToDB(i, DBData.getDataList().get(i).getOrderID(), DBData.getDataList().get(i).getArticleID());
            int finalI = i;
            Platform.runLater(() -> controller.pgbResult.setProgress((float) 1 / DBData.getDataList().size() * finalI));
        }
        con.close();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000000;
        int finalCount = count + 1;
        Platform.runLater(() -> {
            controller.btnTruncate.setDisable(false);
            controller.btnClose.setText("Close");
            controller.taResult.appendText("Database updated with " + finalCount + " items in " + duration + " seconds..\n");
        });
    }
}
