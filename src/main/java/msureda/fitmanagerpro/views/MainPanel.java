package msureda.fitmanagerpro.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
        StyleUtils.styleButton(loginAccessButton);
        setLayout(new GridBagLayout());
        
        // Estructuración de componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        add(logoLabel, gbc);

        gbc.gridy = 1;
        add(loginAccessButton, gbc);
        
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginAccessButton = new javax.swing.JButton();
        logoLabel = new javax.swing.JLabel();

        setForeground(new java.awt.Color(60, 63, 65));
        setPreferredSize(new java.awt.Dimension(290, 277));
        setLayout(null);

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
        logoLabel.setBounds(0, 0, 290, 260);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton loginAccessButton;
    private javax.swing.JLabel logoLabel;
    // End of variables declaration//GEN-END:variables
}
