package msureda.fitmanagerpro;

import java.awt.Color;
import javax.swing.JPanel;
import msureda.fitmanagerpro.dto.User;
import msureda.fitmanagerpro.views.MainPanel;
import msureda.fitmanagerpro.views.UserPanel;
import msureda.fitmanagerpro.views.HomePanel;

/**
 * Archivo de ejecución main para programa de gestión de entrenamientos
 * @author Marc Sureda
 */
public class Main extends javax.swing.JFrame {
    private User instructor;
    private JPanel currentPanel;

    public Main() {
        initComponents();
        setSize(800, 800);
        setBackground(new Color(60,63,65));
        
        //Crear y establecer como panel inicial
        MainPanel pnlMain = new MainPanel(this);
        setCurrentPanel(pnlMain);

        // Handler para hacer resize de los paneles en cambio de tamaño de ventana
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resizeCurrentPanel();
            }
        });

        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void showHomePanel() {
        HomePanel homePanel = new HomePanel(this, instructor);
        setCurrentPanel(homePanel);
    }
    
    public void showUserPanel(User user) {
        UserPanel userPanel = new UserPanel(this, user);
        setCurrentPanel(userPanel);
    }

    private void setCurrentPanel(JPanel panel) {
        getContentPane().removeAll();
        currentPanel = panel;
        currentPanel.setSize(getWidth() - 10, getHeight() - 40);
        getContentPane().add(currentPanel);
        
        currentPanel.revalidate();
        repaint();
    }

    private void resizeCurrentPanel() {
        if (currentPanel == null) return;

        currentPanel.setSize(getWidth() - 10, getHeight() - 40);
        currentPanel.revalidate();
        currentPanel.repaint();
    }

    public void setInstructor(User user) {
        this.instructor = user;
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
