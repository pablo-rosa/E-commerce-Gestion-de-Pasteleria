package com.gestion.gestion_usuarios.daos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase que representa la entidad Usuario en la base de datos.
 * <p>
 * Esta clase contiene los atributos que definen a un usuario, como su 
 * Nombre, teléfono, foto, email, contraseña y rol. 
 * Está mapeada como una entidad JPA para facilitar su persistencia en la base de datos.
 * </p>
 */
@Entity // Marca la clase como una entidad JPA
@Table(name = "usuario", schema = "gestion_usuario") // Mapea esta clase a la tabla "usuario" del esquema "gestion"
public class UsuarioDao {

    /** Identificador único del usuario, generado automáticamente. */
    @Id // Marca este campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el ID automáticamente
    @Column(name = "id_usuario", updatable = false) // Configura la columna de la base de datos
    private long idUsuario;

    /** El nombre del usuario. */
    @Column(name = "nombre_usuario", unique = true, length = 50) // Campo único y requerido, longitud máxima 50
    private String nombreUsuario;

    /** El teléfono del usuario. */
    @Column(name = "telefono_usuario", length = 15) // Campo opcional, longitud máxima 15
    private String telefonoUsuario;

    /** Foto del usuario en formato de bytes. */
    @Column(name = "foto_usuario", columnDefinition = "bytea") // Personaliza la definición del tipo de columna
    private byte[] fotoUsuario;

    /** El email del usuario. */
    @Column(name = "email_usuario", unique = true, length = 150) // Campo único y requerido, longitud máxima 150
    private String emailUsuario;

    /** La contraseña del usuario. */
    @Column(name = "passwd_usuario", length = 255) // Campo requerido, longitud máxima 255
    private String passwordUsuario;

    /** El rol del usuario (ej. Admin, User, etc.). */
    @Column(name = "rol_usuario", length = 50) // Campo opcional, longitud máxima 50
    private String rol;

    // ============================
    // Constructores
    // ============================

    /**
     * Constructor vacío. Necesario para JPA.
     * Este constructor es utilizado por el framework JPA al momento de crear instancias de la entidad.
     */
    public UsuarioDao() {
    }

    /**
     * Constructor con todos los campos (excepto el ID).
     * Útil para crear nuevos objetos antes de persistirlos en la base de datos.
     *
     * @param nombreUsuario El nombre del usuario.
     * @param telefonoUsuario El teléfono del usuario.
     * @param fotoUsuario La foto del usuario en formato de bytes.
     * @param emailUsuario El email del usuario.
     * @param passwordUsuario La contraseña del usuario.
     * @param rol El rol del usuario.
     */
    public UsuarioDao(String nombreUsuario, String telefonoUsuario, 
                   byte[] fotoUsuario, String emailUsuario, String passwordUsuario, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.fotoUsuario = fotoUsuario;
        this.emailUsuario = emailUsuario;
        this.passwordUsuario = passwordUsuario;
        this.rol = rol;
    }

    /**
     * Constructor completo (incluyendo ID).
     * Útil para pruebas o cuando el ID ya está definido.
     *
     * @param idUsuario El ID único del usuario.
     * @param nombreUsuario El nombre del usuario.
     * @param telefonoUsuario El teléfono del usuario.
     * @param fotoUsuario La foto del usuario en formato de bytes.
     * @param emailUsuario El email del usuario.
     * @param passwordUsuario La contraseña del usuario.
     * @param rol El rol del usuario.
     */
    public UsuarioDao(long idUsuario, String nombreUsuario, 
                   String telefonoUsuario, byte[] fotoUsuario, String emailUsuario, 
                   String passwordUsuario, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.fotoUsuario = fotoUsuario;
        this.emailUsuario = emailUsuario;
        this.passwordUsuario = passwordUsuario;
        this.rol = rol;
    }

    // ============================
    // Getters y Setters
    // ============================

    /**
     * Obtiene el ID único del usuario.
     *
     * @return El ID del usuario.
     */
    public long getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param idUsuario El ID del usuario.
     */
    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombreUsuario El nombre del usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene el teléfono del usuario.
     *
     * @return El teléfono del usuario.
     */
    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    /**
     * Establece el teléfono del usuario.
     *
     * @param telefonoUsuario El teléfono del usuario.
     */
    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }

    /**
     * Obtiene la foto del usuario en formato de bytes.
     *
     * @return La foto del usuario en formato de bytes.
     */
    public byte[] getFotoUsuario() {
        return fotoUsuario;
    }

    /**
     * Establece la foto del usuario en formato de bytes.
     *
     * @param fotoUsuario La foto del usuario en formato de bytes.
     */
    public void setFotoUsuario(byte[] fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    /**
     * Obtiene el email del usuario.
     *
     * @return El email del usuario.
     */
    public String getEmailUsuario() {
        return emailUsuario;
    }

    /**
     * Establece el email del usuario.
     *
     * @param emailUsuario El email del usuario.
     */
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param passwordUsuario La contraseña del usuario.
     */
    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return El rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol El rol del usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    // ============================
    // Método toString
    // ============================

    /**
     * Representación en formato string del objeto UsuarioDao.
     * 
     * @return Una cadena con los detalles del usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
               "idUsuario=" + idUsuario +
               ", nombreUsuario='" + nombreUsuario + '\'' +
               ", telefonoUsuario='" + telefonoUsuario + '\'' +
               ", emailUsuario='" + emailUsuario + '\'' +
               ", rol='" + rol + '\'' +
               '}';
    }
}
