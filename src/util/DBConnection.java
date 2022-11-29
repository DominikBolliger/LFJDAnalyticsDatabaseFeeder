package util;

import controller.LFJDAnalyticsDatabaseFeederController;
import javafx.application.Platform;
import modell.Article;
import modell.DataBehaviour;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    String url = "jdbc:mysql://localhost:3306/semestertest";
    String user = "root";
    String pass = "";
    Connection con;

    public void connect() {
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
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
            ResultSet rs = stmt.executeQuery("Select articleorderID from semestertest.articleorder order by articleorderID DESC LIMIT 1");
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
            ResultSet rs = stmt.executeQuery("Select orderID from semestertest.order order by orderID DESC LIMIT 1");
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
            ResultSet rs = stmt.executeQuery("Select * from semestertest.article");
            while (rs.next()) {
                new Article(rs.getInt("articleID"), rs.getString("name"), rs.getInt("fk_behaviourID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOrder(LocalDate value) {
        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO semestertest.order (buyDate) VALUES ('" + value.toString() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createBehaviours() {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from semestertest.behaviour");
            while (rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("BehaviourID");
                List<Integer> multiplicators = new ArrayList<Integer>();
                multiplicators.add(rs.getInt("january"));
                multiplicators.add(rs.getInt("february"));
                multiplicators.add(rs.getInt("march"));
                multiplicators.add(rs.getInt("april"));
                multiplicators.add(rs.getInt("may"));
                multiplicators.add(rs.getInt("june"));
                multiplicators.add(rs.getInt("july"));
                multiplicators.add(rs.getInt("august"));
                multiplicators.add(rs.getInt("september"));
                multiplicators.add(rs.getInt("october"));
                multiplicators.add(rs.getInt("november"));
                multiplicators.add(rs.getInt("december"));
                new DataBehaviour(name, id, multiplicators);
            }
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
