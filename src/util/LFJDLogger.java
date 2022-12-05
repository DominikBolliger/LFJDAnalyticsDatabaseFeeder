package util;

import java.io.FileWriter;
import java.io.IOException;

public class LFJDLogger {
    private static String path = "C:\\Program Files\\LFJDAnalyticsDatabaseFeeder";

    public static void log(String logMessage) {
        path = path.substring(0,path.length()-8) + "errors.log";
        try {
            FileWriter myWriter = new FileWriter(path, true);
            myWriter.write(logMessage + System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the log.");
            e.printStackTrace();
        }
    }
}
