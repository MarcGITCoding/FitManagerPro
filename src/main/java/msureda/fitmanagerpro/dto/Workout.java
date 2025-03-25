package msureda.fitmanagerpro.dto;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Clase DTO que representa un entrenamiento en la tabla Workouts.
 * Contiene información sobre el identificador, la fecha del entrenamiento, el usuario que lo realizó, comentarios y la lista de ejercicios.
 * 
 * @author Marc Sureda
 */
public class Workout {
    private int Id;
    private Date ForDate;
    private int UserId;
    private String Comments;
    private ArrayList<Exercise> Exercises;

    /**
     * Obtiene el identificador del entrenamiento.
     * @return el Id del entrenamiento.
     */
    public int getId() {
        return Id;
    }

    /**
     * Establece el identificador del entrenamiento.
     * @param Id el Id a asignar.
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * Obtiene la fecha del entrenamiento.
     * @return la fecha del entrenamiento.
     */
    public Date getForDate() {
        return ForDate;
    }

    /**
     * Establece la fecha del entrenamiento.
     * @param ForDate la fecha a asignar.
     */
    public void setForDate(Date ForDate) {
        this.ForDate = ForDate;
    }

    /**
     * Obtiene el identificador del usuario que realizó el entrenamiento.
     * @return el Id del usuario.
     */
    public int getUserId() {
        return UserId;
    }

    /**
     * Establece el identificador del usuario que realizó el entrenamiento.
     * @param UserId el Id del usuario a asignar.
     */
    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    /**
     * Obtiene los comentarios del entrenamiento.
     * @return los comentarios del entrenamiento.
     */
    public String getComments() {
        return Comments;
    }

    /**
     * Establece los comentarios del entrenamiento.
     * @param Comments los comentarios a asignar.
     */
    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    /**
     * Obtiene la lista de ejercicios del entrenamiento.
     * @return una lista de ejercicios.
     */
    public ArrayList<Exercise> getExercises() {
        return Exercises;
    }

    /**
     * Establece la lista de ejercicios del entrenamiento.
     * @param Exercises la lista de ejercicios a asignar.
     */
    public void setExercises(ArrayList<Exercise> Exercises) {
        this.Exercises = Exercises;
    }
}