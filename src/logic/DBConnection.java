package logic;

import com.mysql.cj.jdbc.ConnectionImpl;
import controller.LFJDAnalyticsDatabaseFeederController;
import javafx.application.Platform;
import modell.Article;
import modell.DataBehaviour;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private String url;
    private String user;
    private String pass;
    private Connection con;
    private LFJDAnalyticsDatabaseFeederController controller;

    public DBConnection(String url, String user, String pass, LFJDAnalyticsDatabaseFeederController controller) {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.controller = controller;
    }

    public void connect() {
        try {
            con = DriverManager.getConnection(url, user, pass);
            Platform.runLater(() -> controller.taResult.appendText("Connected to Database\n"));

        } catch (SQLException e) {
            Platform.runLater(() -> controller.taResult.appendText("Connection to Database failed\n"));
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLastArticleOrderID() {
        int articleOrderNumber = 0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select articleorderID from position order by articleorderID DESC LIMIT 1");
            if (rs.next()) {
                articleOrderNumber = rs.getInt("articleorderID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (articleOrderNumber == 0) {
            articleOrderNumber = 1;
        }
        return articleOrderNumber;
    }

    public int getLastOrderID() {
        int orderNumber = 0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select orderID from order order by orderID DESC LIMIT 1");
            if (rs.next()) {
                orderNumber = rs.getInt("orderID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (orderNumber == 0) {
            orderNumber = 1;
        }
        return orderNumber;
    }

    public void getArticles() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from article");
            while (rs.next()) {
                new Article(rs.getInt("articleID"), rs.getString("articlename"), rs.getDouble("articleprice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOrder(LocalDate value) {
        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO order (buyDate) VALUES ('" + value.toString() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void writeDataToDB(int count, int orderID, int articleID, LFJDAnalyticsDatabaseFeederController controller) {
        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO articleorder (fk_orderID, fk_articleID) VALUES ('" + orderID + "','" + articleID + "')");
            Platform.runLater(() -> controller.taResult.appendText(count + ". orderID: " + orderID + " | articleID: " + articleID + "\r\n"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void truncateTables(LFJDAnalyticsDatabaseFeederController controller) {
        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("call TruncateTables()");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Platform.runLater(() -> controller.taResult.appendText("Tables truncated"));
        }
    }
}
