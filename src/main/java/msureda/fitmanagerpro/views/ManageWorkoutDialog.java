package msureda.fitmanagerpro.views;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import msureda.fitmanagerpro.Main;

import msureda.fitmanagerpro.dataaccess.DataAccess;
import msureda.fitmanagerpro.dto.Exercise;
import msureda.fitmanagerpro.dto.User;
import msureda.fitmanagerpro.dto.Workout;
import msureda.fitmanagerpro.utils.ErrorHandler;
import msureda.fitmanagerpro.utils.StyleUtils;

/**
 * Diálogo modal para la gestión de workouts (añadir, editar o eliminar)
 * @author Marc Sureda
 */
public class ManageWorkoutDialog extends JDialog {
    private Main main;
    private Workout workout;
    private User user;
    private JTextField commentField;
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;
    private JList<Exercise> exerciseList;
    private JButton saveButton;
    private JButton deleteButton;

    public ManageWorkoutDialog(JFrame parent, Main main, Workout workout, User user) {
        super(parent, workout == null ? "Crear Workout" : "Editar Workout", true);
        this.main = main;
        this.workout = workout;
        this.user = user;

        initComponents();

        // Configuración de estilos básica
        setSize(600, 500);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(StyleUtils.BACKGROUND_COLOR);
        
        // Estructuración de componentes
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setPreferredSize(new Dimension(200, 30));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);

        // Spinner para la hora
        timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setPreferredSize(new Dimension(200, 30));
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);

        // Campo de comentarios
        commentField = new JTextField();
        StyleUtils.styleTextField(commentField);

        // Lista de ejercicios
        ArrayList<Exercise> allExercises = null;
        try {
            allExercises = DataAccess.getAllExercises();
        } catch (SQLException e) {
            ErrorHandler.showSQLError(e, this);
        }
        
        exerciseList = new JList<>(new DefaultListModel<>());
        for (Exercise exercise : allExercises) {
            ((DefaultListModel<Exercise>) exerciseList.getModel()).addElement(exercise);
        }
        exerciseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        exerciseList.setPreferredSize(StyleUtils.LIST_SIZE);
        /*
            Handler para seleccionar varios items sin usar ctrl
            https://stackoverflow.com/questions/2404546/select-multiple-items-in-jlist-without-using-the-ctrl-command-key
        */
        exerciseList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(super.isSelectedIndex(index0)) super.removeSelectionInterval(index0, index1);
                else super.addSelectionInterval(index0, index1);
            }
        });

        // Botón de guardar o actualizar
        saveButton = new JButton(workout == null ? "Guardar" : "Actualizar");
        StyleUtils.styleButton(saveButton);
        saveButton.addActionListener(this::saveWorkout);

        // Escribir datos del workout y añadir botón de eliminar (solo en modo editar)
        if (workout != null) {
            setCurrentData();
            
            deleteButton = new JButton("Eliminar");
            StyleUtils.styleButton(deleteButton);
            deleteButton.addActionListener(this::deleteWorkout);
        }

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fecha
        StyleUtils.styleLabel(dateLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(dateLabel, gbc);
        gbc.gridx = 1;
        add(dateSpinner, gbc);

        // Hora
        StyleUtils.styleLabel(hourLabel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(hourLabel, gbc);
        gbc.gridx = 1;
        add(timeSpinner, gbc);

        // Comentarios
        StyleUtils.styleLabel(commentsLabel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(commentsLabel, gbc);
        gbc.gridx = 1;
        add(commentField, gbc);

        // Lista de ejercicios
        StyleUtils.styleLabel(exercisesLabel);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(exercisesLabel, gbc);
        gbc.gridy = 4;
        add(new JScrollPane(exerciseList), gbc);

        // Botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(StyleUtils.BACKGROUND_COLOR);
        buttonPanel.add(saveButton);
        if (workout != null) buttonPanel.add(deleteButton);

        gbc.gridy = 5;
        add(buttonPanel, gbc);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exercisesLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        hourLabel = new javax.swing.JLabel();
        commentsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        exercisesLabel.setText("Ejercicios:");
        getContentPane().add(exercisesLabel);
        exercisesLabel.setBounds(90, 130, 70, 16);
        exercisesLabel.getAccessibleContext().setAccessibleDescription("");

        dateLabel.setText("Fecha: ");
        getContentPane().add(dateLabel);
        dateLabel.setBounds(90, 40, 50, 16);

        hourLabel.setText("Hora: ");
        hourLabel.setToolTipText("");
        getContentPane().add(hourLabel);
        hourLabel.setBounds(90, 70, 32, 16);

        commentsLabel.setText("Comentarios: ");
        getContentPane().add(commentsLabel);
        commentsLabel.setBounds(90, 100, 80, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setCurrentData() {
        // Configurar la fecha y hora
        dateSpinner.setValue(workout.getForDate());
        timeSpinner.setValue(workout.getForDate());

        // Comentario
        commentField.setText(workout.getComments());

        // Seleccionar ejercicios existentes
        ArrayList<Exercise> workoutExercises = workout.getExercises();

        // Aplicar lista de seleccionados
        DefaultListModel<Exercise> model = (DefaultListModel<Exercise>) exerciseList.getModel();

        Set<Integer> workoutExerciseIds = workoutExercises.stream()
            .map(Exercise::getId)
            .collect(Collectors.toSet());

        int[] indices = IntStream.range(0, model.getSize())
            .filter(i -> workoutExerciseIds.contains(model.getElementAt(i).getId()))
            .toArray();

        exerciseList.setSelectedIndices(indices);
    }

    // Guardar o actualizar workout
    private void saveWorkout(ActionEvent ev) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea guardar los cambios?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        java.util.Date date = (java.util.Date) dateSpinner.getValue();
        java.util.Date time = (java.util.Date) timeSpinner.getValue();
        String comments = commentField.getText();
        ArrayList<Exercise> selectedExercises = new ArrayList<>(exerciseList.getSelectedValuesList());

        LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
        LocalTime localTime = time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalDateTime localDateTime = localDate.atTime(localTime);

        Date sqlDate = java.sql.Date.valueOf(localDateTime.toLocalDate());

        try {
            if (workout == null) DataAccess.saveWorkout(sqlDate, user.getId(), comments, selectedExercises);
            else DataAccess.updateWorkout(workout.getId(), sqlDate, comments, selectedExercises);
        } catch (SQLException e) {
            if (e.getMessage().contains("FK_Intents_ExercicisWorkouts")) {
                JOptionPane.showMessageDialog(this, "No se puede actualizar el entrenamiento porque algunos ejercicios ya tienen intentos asociados.", "Error", JOptionPane.ERROR_MESSAGE);
            } else ErrorHandler.showSQLError(e, this);
        }
        dispose();
    }

    // Eliminar workout
    private void deleteWorkout(ActionEvent ev) {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar este workout?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        try {
            DataAccess.deleteWorkout(workout.getId());
        } catch (SQLException e) {
            if (e.getMessage().contains("FK_Intents_ExercicisWorkouts")) {
                JOptionPane.showMessageDialog(this, "No se puede actualizar el entrenamiento porque algunos ejercicios ya tienen intentos asociados.", "Error", JOptionPane.ERROR_MESSAGE);
            } else ErrorHandler.showSQLError(e, this);
        }
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel commentsLabel;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel exercisesLabel;
    private javax.swing.JLabel hourLabel;
    // End of variables declaration//GEN-END:variables
}
