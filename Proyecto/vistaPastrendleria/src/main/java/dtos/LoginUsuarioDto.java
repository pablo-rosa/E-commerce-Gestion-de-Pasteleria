package dtos;

/**
 * Clase DTO para representar las credenciales de un usuario al momento de iniciar sesión.
 * <p>
 * Este objeto se utiliza para enviar y recibir los datos de inicio de sesión del usuario, 
 * como el correo electrónico, la contraseña y el rol, a través de la API.
 * </p>
 */
public class LoginUsuarioDto {

    private Long id;
    private String email;
    private String password;
    private String rol;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
