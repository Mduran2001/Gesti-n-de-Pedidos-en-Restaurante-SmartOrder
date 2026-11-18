package Configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

    private static final String HOST =
        "mysql-19da0286-duranbmario-2f59.d.aivencloud.com";

    private static final String URL =
        "jdbc:mysql://" + HOST + ":20919/SmartOrder";

    private static final String USUARIO = "smartorder_app";

    public static Connection conectar() throws SQLException {

        String password =
            System.getenv("SMARTORDER_DB_PASSWORD");

        String ca =
            System.getenv("SMARTORDER_DB_CA");

        if (password == null || password.isEmpty()) {
            throw new SQLException(
                "Falta configurar SMARTORDER_DB_PASSWORD"
            );
        }

        if (ca == null || ca.isEmpty()) {
            throw new SQLException(
                "Falta configurar SMARTORDER_DB_CA"
            );
        }

        Properties propiedades = new Properties();

        propiedades.setProperty("user", USUARIO);
        propiedades.setProperty("password", password);
        propiedades.setProperty("sslMode", "VERIFY_IDENTITY");
        propiedades.setProperty("serverSslCert", ca);

        return DriverManager.getConnection(URL, propiedades);
    }
}