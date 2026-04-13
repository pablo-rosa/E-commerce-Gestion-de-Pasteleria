package dtos;

/**
 * Clase DTO para representar los datos de un usuario a modificar.
 * <p>
 * Este objeto se utiliza para enviar y recibir los datos del usuario a modificar, 
 * como el nombre, DNI, teléfono, rol, etc., a través de la API.
 * </p>
 */
public class ModificarUsuarioDto {

    private long idUsuario;
    private String nombreUsuario;
    private String telefonoUsuario;
    private String rol;

    // Getters & Setters

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
