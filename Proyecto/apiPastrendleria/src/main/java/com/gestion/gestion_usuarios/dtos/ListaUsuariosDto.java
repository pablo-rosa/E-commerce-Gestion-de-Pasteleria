package com.gestion.gestion_usuarios.dtos;

/**
 * DTO utilizado para representar la información pública de un usuario.
 * Esta clase omite información sensible como la contraseña.
 */
public class ListaUsuariosDto {

    // Identificador único del usuario
    private long idUsuario;

    // Nombre completo del usuario
    private String nombreUsuario;

    // Número de teléfono del usuario
    private String telefonoUsuario;

    // Foto de perfil del usuario en formato binario
    private byte[] fotoUsuario;

    // Correo electrónico del usuario
    private String emailUsuario;

    // Rol asignado al usuario (por ejemplo: "admin", "usuario")
    private String rol;

    /**
     * Constructor que inicializa todos los campos de la clase.
     * 
     * @param idUsuario ID del usuario
     * @param nombreUsuario Nombre del usuario
     * @param telefonoUsuario Teléfono del usuario
     * @param fotoUsuario Foto del usuario en formato byte[]
     * @param emailUsuario Email del usuario
     * @param rol Rol del usuario
     */
    public ListaUsuariosDto(long idUsuario, String nombreUsuario, String telefonoUsuario, byte[] fotoUsuario, String emailUsuario, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.fotoUsuario = fotoUsuario;
        this.emailUsuario = emailUsuario;
        this.rol = rol;
    }

    // Getters y setters

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

    public byte[] getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(byte[] fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
