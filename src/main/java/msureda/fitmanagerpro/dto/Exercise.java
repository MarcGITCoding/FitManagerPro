package msureda.fitmanagerpro.dto;

/**
 * Clase DTO que representa un ejercicio de la tabla Exercisis.
 * Contiene información sobre el identificador, nombre, descripción y una foto de demostración.
 * 
 * @author Marc Sureda
 */
public class Exercise {
    private int Id;
    private String Name;
    private String Description;
    private String DemoPhoto;

    @Override
    public String toString() {
        return Name + ": " + Description;
    }

    /**
     * Obtiene el identificador del ejercicio.
     * @return el Id del ejercicio.
     */
    public int getId() {
        return Id;
    }

    /**
     * Establece el identificador del ejercicio.
     * @param Id el Id a asignar.
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * Obtiene el nombre del ejercicio.
     * @return el nombre del ejercicio.
     */
    public String getName() {
        return Name;
    }

    /**
     * Establece el nombre del ejercicio.
     * @param Name el nombre a asignar.
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Obtiene la descripción del ejercicio.
     * @return la descripción del ejercicio.
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Establece la descripción del ejercicio.
     * @param Description la descripción a asignar.
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * Obtiene la foto de demostración del ejercicio.
     * @return la foto de demostración del ejercicio.
     */
    public String getDemoPhoto() {
        return DemoPhoto;
    }

    /**
     * Establece la foto de demostración del ejercicio.
     * @param DemoPhoto la foto a asignar.
     */
    public void setDemoPhoto(String DemoPhoto) {
        this.DemoPhoto = DemoPhoto;
    }
}