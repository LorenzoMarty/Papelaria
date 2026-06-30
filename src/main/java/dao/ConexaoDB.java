package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/papelaria";
    private static final String DEFAULT_USER = "postgres";
    private static final String DEFAULT_PASSWORD = "123";

    private static String config(String propertyName, String environmentName, String defaultValue) {
        String value = System.getProperty(propertyName);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(environmentName);
        }
        return value == null || value.trim().isEmpty() ? defaultValue : value;
    }

    public static Connection getConexao() throws SQLException {

        String url = config("papelaria.db.url", "PAPELARIA_DB_URL", DEFAULT_URL);
        String user = config("papelaria.db.user", "PAPELARIA_DB_USER", DEFAULT_USER);
        String password = config("papelaria.db.password", "PAPELARIA_DB_PASSWORD", DEFAULT_PASSWORD);

        return DriverManager.getConnection(url, user, password);
    }
}
