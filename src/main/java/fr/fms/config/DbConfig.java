package fr.fms.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import fr.fms.utils.AppLogger;
import static fr.fms.utils.Helpers.spacer;

/**
 * DB configuration & connection provider.
 *
 * Loads DB settings from properties file based on the "env" system property:
 * - env=platform -> platform.properties
 * - env=dev -> dev.properties
 * etc.
 *
 * Provides getConnection() method for DAOs.
 */
public class DbConfig {

    /** Loaded properties from the selected *.properties file */
    private static final Properties PROPS = new Properties();

    /** Used to log "Connected to DB" only once to avoid spam */
    private static boolean hasLogged = false;

    static {
        // Choose environment: default is "platform"
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

    /** JDBC connection URL */
    private static final String URL = PROPS.getProperty("db.url");

    /** DB username */
    private static final String USER = PROPS.getProperty("db.user");

    /** DB password */
    private static final String PWD = PROPS.getProperty("db.pwd");

    /**
     * Creates & returns a new JDBC connection.
     *
     * Notes:
     * - Each call opens a new connection (OK for a small CLI app).
     * - Logs the DB URL only once to confirm the connection is working.
     *
     * @return a new JDBC Connection
     * @throws SQLException if connection cannot be created
     */
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
