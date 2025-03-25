package msureda.fitmanagerpro.utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;

/**
 * Clase de utilidades para aplicar estilos predefinidos a los componentes gráficos de la interfaz de usuario.
 * 
 * @author Marc Sureda
 */
public class StyleUtils {
    public static final Color PRIMARY_COLOR = new Color(45, 85, 255);
    public static final Color SECONDARY_COLOR = new Color(60, 179, 113);
    public static final Color DANGER_COLOR = new Color(239, 68, 68);
    public static final Color BACKGROUND_COLOR = new Color(60,63,65);
    public static final Color TEXT_COLOR = Color.WHITE;

    // Tamaños comunes
    public static final Dimension BUTTON_SIZE = new Dimension(200, 40);
    public static final Dimension FIELD_SIZE = new Dimension(250, 30);
    public static final Dimension LABEL_SIZE = new Dimension(200, 30);
    public static final Dimension LIST_SIZE = new Dimension(300, 150);

    /**
     * Estilos de los botones
     */
    public enum ButtonStyle {
        PRIMARY, SECONDARY, DANGER
    }
    
    /**
     * Aplica un estilo a un botón, ajustando colores, tamaño, borde y eventos de mouse.
     * 
     * @param button El botón al que se le aplicará el estilo.
     * @param style El estilo del botón.
     */
    public static void styleButton(JButton button, ButtonStyle style) {
        Color backgroundColor = PRIMARY_COLOR;
        if (style == ButtonStyle.SECONDARY) backgroundColor = SECONDARY_COLOR;
        else if (style == ButtonStyle.DANGER) backgroundColor = DANGER_COLOR;
        final Color backgroundColorCopy = backgroundColor; // Creamos copia para que no se referencie al original
        
        Border buttonBorder = BorderFactory.createLineBorder(backgroundColorCopy, 1, true);
        
        button.setPreferredSize(BUTTON_SIZE);
        button.setBackground(backgroundColorCopy);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(buttonBorder);
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColorCopy.brighter());
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColorCopy);
                button.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    /**
     * Aplica un estilo a un panel, configurando su color de fondo y borde.
     * 
     * @param panel El panel al que se le aplicará el estilo.
     */
    public static void stylePanel(JPanel panel) {
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(10, 10, 10, 15));
    }

    /**
     * Aplica un estilo a una tabla, configurando su color de fondo, color de texto y borde.
     * 
     * @param table La tabla a la que se le aplicará el estilo.
     */
    public static void styleTable(JTable table) {
        table.setBackground(BACKGROUND_COLOR);
        table.setForeground(TEXT_COLOR);
        table.setBorder(new EmptyBorder(10, 10, 10, 15));
    }

    /**
     * Aplica un estilo a un diálogo, configurando su color de fondo.
     * 
     * @param dialog El diálogo al que se le aplicará el estilo.
     */
    public static void styleDialog(JDialog dialog) {
        dialog.setBackground(BACKGROUND_COLOR);
    }

    /**
     * Aplica un estilo a una etiqueta, configurando su tamaño, color de texto y fuente.
     * 
     * @param label La etiqueta a la que se le aplicará el estilo.
     */
    public static void styleLabel(JLabel label) {
        label.setPreferredSize(LABEL_SIZE);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Roboto", Font.PLAIN, 16));
    }

    /**
     * Aplica un estilo a una lista, configurando su tamaño, fuente, color de fondo y color de texto.
     * 
     * @param list La lista a la que se le aplicará el estilo.
     */
    public static void styleList(JList<?> list) {
        list.setPreferredSize(LIST_SIZE);
        list.setFont(new Font("Roboto", Font.PLAIN, 14));
        list.setForeground(TEXT_COLOR);
        list.setBackground(BACKGROUND_COLOR);
    }

    /**
     * Aplica un estilo a un campo de texto, configurando su tamaño y fuente.
     * 
     * @param textField El campo de texto al que se le aplicará el estilo.
     */
    public static void styleTextField(JTextField textField) {
        textField.setPreferredSize(FIELD_SIZE);
        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
    }

    /**
     * Aplica un estilo a un campo de contraseña, configurando su tamaño y fuente.
     * 
     * @param passwordField El campo de contraseña al que se le aplicará el estilo.
     */
    public static void stylePasswordField(JPasswordField passwordField) {
        passwordField.setPreferredSize(FIELD_SIZE);
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 14));
    }
}
