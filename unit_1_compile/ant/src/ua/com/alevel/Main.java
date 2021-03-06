package ua.com.alevel;

import ua.com.alevel.databases.Database;
import ua.com.alevel.styles.Style;

public class Main {
    public static void main(String[] args) {
        Style style = new Style();
        style.run();
        Database database = new Database();
        database.run();
    }
}
