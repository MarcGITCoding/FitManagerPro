package msureda.fitmanagerpro.dataaccess;

/**
 * Clase que proporciona la configuración de conexión a la base de datos.
 * 
 * Contiene los parámetros necesarios para establecer la conexión JDBC con el servidor
 * de base de datos, incluyendo URL, credenciales y parámetros de seguridad.
 * 
 * @author Marc Sureda
 */
public class DatabaseConfig {
    //OLD LOCAL DB STRING
    //public static final String CONNECTION_STRING = "jdbc:sqlserver://localhost;database=simulapdb;user=sa;"
    //        + "password=Pwd1234;encrypt=false;";
    /**
     * Cadena de conexión actual para la base de datos.
     * 
     * Incluye parámetros de encriptación y verificación de certificado para conexión segura.
     * 
     */
    public static final String CONNECTION_STRING = 
        "jdbc:sqlserver://simulapsqlserver.database.windows.net:1433;database=simulapdb25;" +
        "user=simulapdbadmin@simulapsqlserver;password=Pwd1234.;" +
        "encrypt=true;trustServerCertificate=false;" +
        "hostNameInCertificate=*.database.windows.net;loginTimeout=30";
    
    /**
     * Obtiene la cadena de conexión configurada para la base de datos.
     * 
     * @return La cadena de conexión completa con todos los parámetros necesarios.
     */
    public static String getConnectionString() {
        return CONNECTION_STRING;
    }
}