package msureda.fitmanagerpro.utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;

/**
 * Clase de utilidades para aplicar estilos a los componentes
 * @author Marc Sureda
 */
public class StyleUtils {
    public static final Color PRIMARY_COLOR = new Color(45, 85, 255);
    public static final Color SECONDARY_COLOR = new Color(60, 179, 113);
    public static final Color BACKGROUND_COLOR = new Color(60,63,65);
    public static final Color TEXT_COLOR = Color.WHITE;

    // Tamaños comunes
    public static final Dimension BUTTON_SIZE = new Dimension(200, 40);
    public static final Dimension FIELD_SIZE = new Dimension(250, 30);
    public static final Dimension LABEL_SIZE = new Dimension(200, 30);
    public static final Dimension LIST_SIZE = new Dimension(300, 150);

    public enum ButtonStyle {
        PRIMARY, SECONDARY
    }
    
    public static void styleButton(JButton button, ButtonStyle style) {
        Color backgroundColor = (style == ButtonStyle.PRIMARY) ? PRIMARY_COLOR : SECONDARY_COLOR;
        Border buttonBorder = BorderFactory.createLineBorder(backgroundColor, 1, true);
        
        button.setPreferredSize(BUTTON_SIZE);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(buttonBorder);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
                button.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    public static void stylePanel(JPanel panel) {
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(10, 10, 10, 15));
    }

    public static void styleDialog(JDialog dialog) {
        dialog.setBackground(BACKGROUND_COLOR);
    }

    public static void styleLabel(JLabel label) {
        label.setPreferredSize(LABEL_SIZE);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    public static void styleList(JList<?> list) {
        list.setPreferredSize(LIST_SIZE);
        list.setFont(new Font("Arial", Font.PLAIN, 14));
        list.setForeground(TEXT_COLOR);
        list.setBackground(BACKGROUND_COLOR);
    }

    public static void styleTextField(JTextField textField) {
        textField.setPreferredSize(FIELD_SIZE);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    public static void stylePasswordField(JPasswordField passwordField) {
        passwordField.setPreferredSize(FIELD_SIZE);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
    }
}
