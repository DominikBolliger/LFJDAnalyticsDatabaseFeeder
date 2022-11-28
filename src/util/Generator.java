package util;

import controller.LFJDAnalyticsDatabaseFeederController;
import javafx.application.Platform;
import modell.DBData;
import modell.DataBehaviour;

import java.time.LocalDate;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

public class Generator {

    private LocalDate fromDate;
    private LocalDate toDate;
    private long numberOfDays;
    private int lastOrderNumber;
    private DBConnection con;
    private LFJDAnalyticsDatabaseFeederController controller;

    public Generator(LocalDate fromDate, LocalDate toDate, LFJDAnalyticsDatabaseFeederController controller) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numberOfDays = DAYS.between(fromDate, toDate);
        this.lastOrderNumber = 0;
        this.controller = controller;
        this.con = new DBConnection();
    }

    public void createData(int lastArticleOrderID, int lastOrderID){
        Random rnd = new Random();
        int ordArtID = lastArticleOrderID;
        int orderID= lastOrderID;
        for (int i = 0; i < numberOfDays; i++) {
            int posInOrder = rnd.nextInt(20 - 10 + 1) + 10;
            con.createOrder(fromDate.plusDays(i));
            for (int j = 0; j < posInOrder; j++) {
                int articleID = rnd.nextInt(10-1+1)+1;
                int behaviourID = 0;
                for (DataBehaviour behaviour:DataBehaviour.getBehaviourList()) {
                    if (behaviour.getId() == articleID){
                        behaviourID = behaviour.getId();
                        break;
                    }
                }
                new DBData(fromDate.plusDays(i), articleID, orderID + i, ordArtID, behaviourID);
                ordArtID++;
            }
        }
    }

    public void start() {

        Thread thread = new Thread(() -> {
            int count = 0;
            con.connect();
            con.createBehaviours();
            con.getArticles();
            Platform.runLater(() -> controller.taResult.appendText("Database connected"));
            long startTime = System.nanoTime();
            createData(con.getLastArticleOrderID(), con.getLastOrderID());
            for (int i = 0; i < DBData.dataList.size(); i++) {
                count = i;
                con.writeDataToDB(i, DBData.dataList.get(i).getOrderID(), DBData.dataList.get(i).getArticleID(), controller);
                int finalI = i;
                Platform.runLater(() -> controller.pgbResult.setProgress((float)1/ DBData.dataList.size()* finalI));
                Util.delay(5);
            }
            con.close();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime)/1000000000;
            System.out.println(duration);
            int finalCount = count;
            Platform.runLater(() -> controller.taResult.appendText("Database updated with " + finalCount + " items in " + duration + " seconds.."));
        });
        thread.start();
    }
}
