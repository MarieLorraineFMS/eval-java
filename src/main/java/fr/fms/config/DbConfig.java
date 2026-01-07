package fr.fms.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import fr.fms.utils.AppLogger;
import static fr.fms.utils.Helpers.spacer;

public class DbConfig {

    private static final Properties PROPS = new Properties();
    private static boolean hasLogged = false;

    static {
        String env = System.getProperty("env", "platform");
        String filename = env + ".properties";

        try (var in = DbConfig.class.getClassLoader().getResourceAsStream(filename)) {
            if (in == null) {
                throw new IllegalStateException(filename + " not found");
            }

            PROPS.load(in);
            spacer();
            AppLogger.rocket("DB config loaded (" + env + ")");
        } catch (Exception e) {
            spacer();
            AppLogger.error("Error loading DB config: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    private static final String URL = PROPS.getProperty("db.url");
    private static final String USER = PROPS.getProperty("db.user");
    private static final String PWD = PROPS.getProperty("db.pwd");

    public static Connection getConnection() throws SQLException {
        Connection cnx = DriverManager.getConnection(URL, USER, PWD);

        if (!hasLogged) {
            AppLogger.rocket("Connected to DB: " + cnx.getMetaData().getURL());
            spacer();
            hasLogged = true;
        }
        return cnx;
    }
}
