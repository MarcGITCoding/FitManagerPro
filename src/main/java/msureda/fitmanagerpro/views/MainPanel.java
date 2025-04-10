package msureda.fitmanagerpro.views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import msureda.fitmanagerpro.Main;
import msureda.fitmanagerpro.dto.User;
import msureda.fitmanagerpro.utils.StyleUtils;
import msureda.fitmanagerpro.views.LoginDialog;
import net.miginfocom.swing.MigLayout;

/**
 * Panel principal de la aplicación, que sirve como pantalla de inicio.
 * Muestra el logo, el título de la aplicación y los botones de acceso y registro.
 * También incluye un enlace a la página web oficial.
 * 
 * @author Marc Sureda
 */
public class MainPanel extends javax.swing.JPanel {
    private Main main = null;

    /**
     * Constructor de MainPanel.
     * Configura el panel, aplica estilos y añade los componentes principales.
     * 
     * @param mainJFrame Referencia al JFrame principal de la aplicación.
     */
    public MainPanel(Main mainJFrame) {
        initComponents();
        main = mainJFrame;

        // Configuración de estilos básica
        StyleUtils.stylePanel(this);
        StyleUtils.styleButton(loginAccessButton, StyleUtils.ButtonStyle.PRIMARY);
        StyleUtils.styleButton(registerAccessButton, StyleUtils.ButtonStyle.SECONDARY);

        // Configuración del layout con MigLayout
        setLayout(new MigLayout("wrap, alignx center, aligny center", // wrap para que los componentes se coloquen en filas, alignx y aligny para centrar
                "[grow,fill]", // Column constraints
                "[]10[]10[]10[]10[]" // Row constraints con espacios entre filas
        ));

        // Estructuración principal y logo
        add(logoLabel, "cell 0 0, alignx center, aligny center");

        // Título de la aplicación
        JLabel titleLabel = new JLabel();
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("<html><span style='font-family: Helvetica, sans-serif; font-size: 34px; font-weight: bold; text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);'>"
                + "<span style='color: #00BFFF;'>FIT </span>"
                + "<span style='color: #FFFFFF;'>MANAGER </span>"
                + "<span style='color: #1E90FF;'>PRO </span>"
                + "</span></html>");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 64));
        add(titleLabel, "cell 0 1, alignx center, aligny center");

        // Botones con ancho limitado
        add(loginAccessButton, "cell 0 2, alignx center, aligny center, w 200!, gapy 80"); // Ancho fijo de 200 píxeles
        add(registerAccessButton, "cell 0 3, alignx center, aligny center, w 200!, gapy 10"); // Ancho fijo de 200 píxeles

        // Enlace a la web
        JLabel linkLabel = new JLabel("<html><u>Visita nuestra página web</u></html>");
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkLabel.setForeground(Color.CYAN);
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.fitmanagerpro.es"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        add(linkLabel, "cell 0 4, alignx center, aligny center, w 145!, gapy 40");

        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        registerAccessButton = new javax.swing.JButton();
        loginAccessButton = new javax.swing.JButton();
        logoLabel = new javax.swing.JLabel();

        setForeground(new java.awt.Color(60, 63, 65));
        setPreferredSize(new java.awt.Dimension(290, 277));
        setLayout(null);

        registerAccessButton.setText("Registrarse");
        registerAccessButton.setToolTipText("");
        registerAccessButton.setActionCommand("LoginAccess");
        registerAccessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerAccessButtonActionPerformed(evt);
            }
        });
        add(registerAccessButton);
        registerAccessButton.setBounds(90, 260, 110, 23);

        loginAccessButton.setText("Acceder");
        loginAccessButton.setActionCommand("LoginAccess");
        loginAccessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginAccessButtonActionPerformed(evt);
            }
        });
        add(loginAccessButton);
        loginAccessButton.setBounds(90, 230, 110, 23);

        logoLabel.setForeground(new java.awt.Color(60, 63, 65));
        logoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N
        logoLabel.setToolTipText("");
        logoLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        logoLabel.setMaximumSize(new java.awt.Dimension(200, 200));
        logoLabel.setMinimumSize(new java.awt.Dimension(200, 200));
        logoLabel.setName(""); // NOI18N
        add(logoLabel);
        logoLabel.setBounds(0, -10, 290, 260);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Maneja la acción del botón "Acceder".
     * Abre el cuadro de diálogo de inicio de sesión y, si el usuario se autentica,
     * actualiza el instructor en la aplicación y muestra el panel de inicio.
     * 
     * @param evt Evento de acción del botón.
     */
    private void loginAccessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginAccessButtonActionPerformed
        LoginDialog loginDialog = new LoginDialog(main, main);
        loginDialog.setLocationRelativeTo(main);
        loginDialog.setVisible(true);

        User getLoggedUser = loginDialog.getAuthenticatedInstructor();
        if (getLoggedUser == null) return;

        main.setInstructor(getLoggedUser);
        main.showHomePanel();
    }//GEN-LAST:event_loginAccessButtonActionPerformed

    /**
     * Maneja la acción del botón "Registrarse".
     * Abre el cuadro de diálogo de registro y, si el usuario se registra correctamente,
     * actualiza el instructor en la aplicación y muestra el panel de inicio.
     * 
     * @param evt Evento de acción del botón.
     */
    private void registerAccessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerAccessButtonActionPerformed
        RegisterDialog registerDialog = new RegisterDialog(main, main);
        registerDialog.setLocationRelativeTo(main);
        registerDialog.setVisible(true);
        
        User registeredUser = registerDialog.getRegisteredUser();
        if (registeredUser != null) {
            main.setInstructor(registeredUser);
            main.showHomePanel();
        }
    }//GEN-LAST:event_registerAccessButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton loginAccessButton;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JButton registerAccessButton;
    // End of variables declaration//GEN-END:variables
}
