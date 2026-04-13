package dtos;

public class RecuperacionContrasenaDto {
    private String emailUsuario;
    private String nuevaPassword;

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getNuevaPassword() {
        return nuevaPassword;
    }

    public void setNuevaPassword(String nuevaPassword) {
        this.nuevaPassword = nuevaPassword;
    }
    private long expiracion; // Timestamp en milisegundos
    public long getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(long expiracion) {
        this.expiracion = expiracion;
    }

}
