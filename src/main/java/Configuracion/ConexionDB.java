
package Configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.File;

public class ConexionDB {

    private static final String HOST =
        "mysql-19da0286-duranbmario-2f59.d.aivencloud.com";

    private static final String URL =
        "jdbc:mysql://" + HOST + ":20919/SmartOrder";

    private static final String USUARIO = "smartorder_app";

    public static Connection conectar() throws SQLException {

        String password =
            System.getenv("SMARTORDER_DB_PASSWORD");

        String truststore =
            System.getenv("SMARTORDER_DB_CA");

        String truststorePassword =
            System.getenv("SMARTORDER_DB_TRUSTSTORE_PASSWORD");

        if (password == null || password.isEmpty()) {
            throw new SQLException(
                "Falta configurar SMARTORDER_DB_PASSWORD"
            );
        }

        if (truststore == null || truststore.isEmpty()) {
            throw new SQLException(
                "Falta configurar SMARTORDER_DB_CA"
            );
        }

        if (truststorePassword == null ||
            truststorePassword.isEmpty()) {
            throw new SQLException(
                "Falta configurar SMARTORDER_DB_TRUSTSTORE_PASSWORD"
            );
        }

        File archivo = new File(truststore);

        if (!archivo.isFile()) {
            throw new SQLException(
                "No se encuentra el almacén de certificados"
            );
        }

        Properties propiedades = new Properties();

        propiedades.setProperty("user", USUARIO);
        propiedades.setProperty("password", password);

        propiedades.setProperty("sslMode", "VERIFY_IDENTITY");

        propiedades.setProperty(
            "trustCertificateKeyStoreUrl",
            archivo.toURI().toString()
        );

        propiedades.setProperty(
            "trustCertificateKeyStoreType",
            "PKCS12"
        );

        propiedades.setProperty(
            "trustCertificateKeyStorePassword",
            truststorePassword
        );

        return DriverManager.getConnection(URL, propiedades);
    }
}
