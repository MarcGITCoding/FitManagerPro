package msureda.fitmanagerpro.utils;

import java.awt.Component;
import javax.swing.JOptionPane;
import java.sql.SQLException;

/**
 * Clase para el manejo de errores
 * @author Marc Sureda
 */

public class ErrorHandler {
    public static void showSQLError (SQLException e, Component parent) {
        String errorMessage;

        switch (e.getErrorCode()) {
            case 1045:
                errorMessage = "Acceso denegado en la base de datos: Usuario o contraseña incorrectos.";
                break;
            case 1049:
                errorMessage = "La base de datos especificada no existe.";
                break;
            case 1054:
                errorMessage = "Columna desconocida en la consulta.";
                break;
            case 1062:
                errorMessage = "Error: Entrada duplicada. El valor ya existe en una columna única o clave primaria.";
                break;
            case 1064:
                errorMessage = "Error de sintaxis en la consulta SQL. Verifique la consulta.";
                break;
            case 1146:
                errorMessage = "La tabla especificada no existe en la base de datos.";
                break;
            case 1216:
                errorMessage = "Error de clave externa: la operación viola la integridad referencial.";
                break;
            case 1217:
                errorMessage = "No se puede eliminar o actualizar una fila padre que tiene registros asociados en otra tabla.";
                break;
            case 1364:
                errorMessage = "Error: El campo no tiene un valor predeterminado y no permite valores nulos.";
                break;
            case 1451:
                errorMessage = "No se puede eliminar o actualizar una fila padre referenciada en otra tabla.";
                break;
            case 1452:
                errorMessage = "Error de clave externa: la fila de la tabla hija no tiene un valor coincidente en la tabla padre.";
                break;
            case 2003:
                errorMessage = "No se puede conectar al servidor MySQL. Verifique la configuración de red.";
                break;
            case 2013:
                errorMessage = "Conexión perdida con el servidor MySQL durante la consulta. Intente nuevamente.";
                break;
            default:
                errorMessage = "Error desconocido: " + e.getMessage();
                break;
        }

        JOptionPane.showMessageDialog(parent, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        //e.printStackTrace();
    }
    
    public static void showCustomError (String message, Component parent) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}