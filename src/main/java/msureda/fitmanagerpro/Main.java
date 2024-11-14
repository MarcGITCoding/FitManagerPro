package msureda.fitmanagerpro;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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

        setResizable(false);
        
        // Handler para hacer resize de los paneles en cambio de tamaño de ventana
        // Para que esto funcione, el setResizable de arriba debe estar en true
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resizeCurrentPanel();
            }
        });
        
        createMenuBar();

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
        currentPanel.setSize(getWidth() - 10, getHeight() - 60);
        getContentPane().add(currentPanel);
        
        currentPanel.revalidate();
        repaint();
    }

    private void resizeCurrentPanel() {
        if (currentPanel == null) return;

        currentPanel.setSize(getWidth() - 10, getHeight() - 60);
        currentPanel.revalidate();
        currentPanel.repaint();
    }

    public void setInstructor(User user) {
        this.instructor = user;
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menú "File"
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Botón de "Exit"
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        // Menú "Help"
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cuadro de diálogo "About"
                JOptionPane.showMessageDialog(
                    Main.this,
                    "Nombre: Marc Sureda\nCurso: Desarrollo de interfaces 2023/24\nRecursos:\n - Logo hecho con ChatGPT\n - Programado en Java Swing",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
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
