package msureda.fitmanagerpro.views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import javax.swing.JLabel;
import msureda.fitmanagerpro.Main;
import msureda.fitmanagerpro.dto.User;
import msureda.fitmanagerpro.utils.StyleUtils;
import msureda.fitmanagerpro.views.LoginDialog;

/**
 * Panel prinicpal de la aplicación
 * @author Marc Sureda
 */
public class MainPanel extends javax.swing.JPanel {
    private Main main = null;

    public MainPanel(Main mainJFrame) {
        initComponents();
        main = mainJFrame;
        
        // Configuración de estilos básica
        StyleUtils.stylePanel(this);
        StyleUtils.styleButton(loginAccessButton, StyleUtils.ButtonStyle.PRIMARY);
        StyleUtils.styleButton(registerAccessButton,StyleUtils.ButtonStyle.SECONDARY);
        setLayout(new GridBagLayout());
        
        // Estructuración principal y logo
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(logoLabel, gbc);

        // Título de la aplicación
        JLabel titleLabel = new javax.swing.JLabel();
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("<html><span style='font-family: Helvetica, sans-serif; font-size: 34px; font-weight: bold; text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);'>"
                + "<span style='color: #00BFFF;'>FIT </span>"
                + "<span style='color: #FFFFFF;'>MANAGER </span>"
                + "<span style='color: #1E90FF;'>PRO </span>"
                + "</span></html>");
        titleLabel.setFont(new java.awt.Font("Helvetica", java.awt.Font.BOLD, 64));
        
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
                }
            }
        });
        
        //Estructuración del título, login, register y enlace
        gbc.gridy = 1;
        add(titleLabel, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(80, 10, 10, 10);
        add(loginAccessButton, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(registerAccessButton, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(40, 10, 10, 10);
        add(linkLabel, gbc);
        
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

    private void loginAccessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginAccessButtonActionPerformed
        LoginDialog loginDialog = new LoginDialog(main, main);
        loginDialog.setLocationRelativeTo(main);
        loginDialog.setVisible(true);

        User getLoggedUser = loginDialog.getAuthenticatedUser();
        if (getLoggedUser == null) return;

        main.setInstructor(getLoggedUser);
        main.showHomePanel();
    }//GEN-LAST:event_loginAccessButtonActionPerformed

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
