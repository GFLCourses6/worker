package executor.service.factory.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class LogsStoreConnectionFactory {

    private static final String SOURCE_CONFIG_PATH = "src/main/resources/datasource.properties";
    private static final Logger logger = LogManager.getLogger(LogsStoreConnectionFactory.class);
    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {
        logger.info("Getting the connection for logs");
        synchronized (LogsStoreConnectionFactory.class) {
            if (dataSource == null) {
                logger.info("Initializing HikariCP for logs");
                HikariConfig hikariConfig = new HikariConfig(SOURCE_CONFIG_PATH);
                dataSource = new HikariDataSource(hikariConfig);
            }
        }
        return dataSource.getConnection();
    }
}
