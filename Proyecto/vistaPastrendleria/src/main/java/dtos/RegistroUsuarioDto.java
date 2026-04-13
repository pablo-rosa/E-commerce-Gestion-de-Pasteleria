package dtos;

/**
 * Clase DTO para representar los datos de un usuario al momento de registrarse.
 * <p>
 * Este objeto se utiliza para enviar y recibir los datos del usuario durante el proceso
 * de registro, como el nickname, nombre, DNI, teléfono, correo electrónico, y rol, 
 * a través de la API.
 * </p>
 */
public class RegistroUsuarioDto {

    private long idUsuario;
    private String nicknameUsuario;
    private String nombreUsuario;
    private String telefonoUsuario;
    private String emailUsuario;
    private String passwordUsuario;
    private String rol;

    // Getters & Setters

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNicknameUsuario() {
        return nicknameUsuario;
    }

    public void setNicknameUsuario(String nicknameUsuario) {
        this.nicknameUsuario = nicknameUsuario;
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

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
