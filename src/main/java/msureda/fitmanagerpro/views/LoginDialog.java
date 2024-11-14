package msureda.fitmanagerpro.views;

import javax.swing.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import msureda.fitmanagerpro.Main;

import msureda.fitmanagerpro.dataaccess.DataAccess;
import msureda.fitmanagerpro.dto.User;
import msureda.fitmanagerpro.utils.ErrorHandler;
import msureda.fitmanagerpro.utils.StyleUtils;

/**
 * Diálogo modal para el login
 * @author Marc Sureda
 */
public class LoginDialog extends JDialog {
    private User authenticatedInstructor = null;

    public LoginDialog(JFrame parent, Main mainFrame) {
        super(mainFrame, "Login", true);
        initComponents();

        // Configuración de estilos básica
        setSize(600, 250);
        setResizable(false);
        setLayout(new GridBagLayout());
        this.getContentPane().setBackground(StyleUtils.BACKGROUND_COLOR);

        StyleUtils.styleLabel(emailLabel);
        StyleUtils.styleLabel(passwordLabel);
        StyleUtils.styleTextField(emailField);
        StyleUtils.stylePasswordField(passwordField);
        StyleUtils.styleButton(loginButton, StyleUtils.ButtonStyle.PRIMARY);

        // Estructuración de componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginButton = new javax.swing.JButton();
        emailField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        loginButton.setText("Ingresar");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        getContentPane().add(loginButton);
        loginButton.setBounds(150, 150, 75, 23);

        emailField.setText("a@b.c");
        emailField.setToolTipText("");
        getContentPane().add(emailField);
        emailField.setBounds(270, 40, 64, 22);

        emailLabel.setText("Email: ");
        getContentPane().add(emailLabel);
        emailLabel.setBounds(60, 50, 90, 16);

        passwordLabel.setText("Contraseña: ");
        getContentPane().add(passwordLabel);
        passwordLabel.setBounds(60, 90, 90, 16);

        passwordField.setText("string");
        passwordField.setToolTipText("");
        getContentPane().add(passwordField);
        passwordField.setBounds(270, 80, 90, 22);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        String email = emailField.getText();
        char[] password = passwordField.getPassword();

        try {
            User instructor = DataAccess.getInstructorByEmail(email);
            
            if (instructor == null) {
                ErrorHandler.showCustomError("El usuario no existe", this);
                return;
            }

            BCrypt.Result result = BCrypt.verifyer().verify(password, instructor.getPasswordHash());
            if (!result.verified) {
                ErrorHandler.showCustomError("Contraseña incorrecta", this);
                return;
            }

            authenticatedInstructor = instructor;

            dispose();
        } catch (SQLException e) {
            ErrorHandler.showSQLError(e, this);
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    public User getAuthenticatedInstructor() {
        return authenticatedInstructor;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    // End of variables declaration//GEN-END:variables
}
