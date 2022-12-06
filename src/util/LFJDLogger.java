package util;

import java.io.FileWriter;
import java.io.IOException;

public class LFJDLogger {

    public static void log(String logMessage) {
        String path = "C:\\Program Files\\LFJDAnalyticsDatabaseFeeder";
        path = path + "\\errors.log";
        try {
            FileWriter myWriter = new FileWriter(path, true);
            myWriter.write( logMessage + System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
