package config;

public class Config {
    public static String baseUrl() {

        return System.getProperty("baseUrl", "https://www.saucedemo.com");
    }

    public static String browser() {

        return System.getProperty("browser", "chrome"); // chrome|firefox|edge
    }

    public static boolean headless() {

        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }

    public static long timeoutSeconds() {

        return 10;
    }
}
