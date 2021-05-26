package ua.com.alevel;

import ua.com.alevel.entity.AppProperties;
import ua.com.alevel.util.PropertyUtil;

public class Application {
    private static final String propertiesPath = "app.properties";

    public static void main(String[] args) {
        AppProperties appProperties = PropertyUtil.initialize(propertiesPath, AppProperties.class);
        System.out.println("Your result is: " + appProperties);
    }
}
