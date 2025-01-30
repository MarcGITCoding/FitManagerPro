package msureda.fitmanagerpro.utils;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import javax.swing.UIManager;

/**
 * Clase de utilidades para importar fuentes
 * @author Marc Sureda
 */
public class FontUtils {
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

    private static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

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
