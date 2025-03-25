package msureda.fitmanagerpro.utils;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import javax.swing.UIManager;

/**
 * Clase de utilidades para importar y gestionar fuentes personalizadas.
 * 
 * @author Marc Sureda
 */
public class FontUtils {
    /**
     * Carga la fuente Roboto desde el archivo y la registra en el sistema.
     * Después, aplica la fuente Roboto a todos los componentes de la interfaz de usuario.
     */
    public static void loadRobotoFont() {
        try {
            InputStream is = FontUtils.class.getResourceAsStream("/fonts/Roboto-Variable.ttf");
            Font robotoFont = Font.createFont(Font.TRUETYPE_FONT, is);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(robotoFont);

            Font defaultFont = robotoFont.deriveFont(Font.PLAIN, 12);
            setUIFont(new javax.swing.plaf.FontUIResource(defaultFont));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Establece una fuente personalizada para los componentes UI de la aplicación.
     * 
     * @param font La fuente a establecer.
     */
    private static void setUIFont(javax.swing.plaf.FontUIResource font) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }

    /**
     * Obtiene una instancia de la fuente Roboto con el estilo y tamaño especificado.
     *
     * @param style El estilo de la fuente.
     * @param size El tamaño de la fuente.
     * @return La fuente Roboto con el estilo y tamaño indicados, o null si ocurre un error.
     */
    public static Font getRobotoFont(int style, float size) {
        try {
            InputStream is = FontUtils.class.getResourceAsStream("/fonts/Roboto-Variable.ttf");
            Font robotoFont = Font.createFont(Font.TRUETYPE_FONT, is);
            return robotoFont.deriveFont(style, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
