package controladores;

import dtos.RegistroUsuarioDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.RegistroServicio;
import utilidades.TokenUtilidad;
import utilidades.EmailUtilidad;
import utilidades.EscritorDeLogsUtilidad;

import java.io.IOException;
import jakarta.mail.MessagingException;

/**
 * Servlet encargado de procesar la verificación del correo electrónico mediante token.
 * Si el token es válido, se registra definitivamente al usuario en la base de datos.
 */
@WebServlet("/verificarCuenta")
public class VerificarCuentaControlador extends HttpServlet {

    private RegistroServicio registroServicio;

    @Override
    public void init() throws ServletException {
        registroServicio = new RegistroServicio();
        EscritorDeLogsUtilidad.registrar("VerificarCuentaControlador - init() - Servicio de registro inicializado");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EscritorDeLogsUtilidad.registrar("VerificarCuentaControlador - doGet() - Inicio de petición");

        String token = request.getParameter("token");
        EscritorDeLogsUtilidad.registrar("VerificarCuentaControlador - Token recibido: " + token);

        if (token == null || token.isEmpty()) {
            EscritorDeLogsUtilidad.registrar("VerificarCuentaControlador - ERROR: Token no proporcionado");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token no proporcionado.");
            return;
        }

        RegistroUsuarioDto usuarioTemporal = TokenUtilidad.validarToken(token);

        if (usuarioTemporal != null) {
            EscritorDeLogsUtilidad.registrar("VerificarCuentaControlador - Token válido, procesando registro del usuario");

            boolean creado = registroServicio.insertarUsuarioEnApi(usuarioTemporal);
            TokenUtilidad.eliminarToken(token);
            EscritorDeLogsUtilidad.registrar("VerificarCuentaControlador - Token eliminado tras el registro");

            if (creado) {
                // Enviar correo de confirmación
                try {
                    EmailUtilidad emailServicio = new EmailUtilidad();
                    String asunto = "¡Cuenta verificada con éxito!";
                    String cuerpo = "Hola " + usuarioTemporal.getNombreUsuario() + ",\n\n" +
                            "Tu cuenta en *El Diario Fuun* ha sido verificada exitosamente.\n\n" +
                            "¡Gracias por unirte a nuestra comunidad!\n\n" +
                            "El equipo de El Diario Fuun.";
                    emailServicio.enviarCorreo(usuarioTemporal.getEmailUsuario(), asunto, cuerpo);
                    EscritorDeLogsUtilidad.registrar("Correo de confirmación enviado a " + usuarioTemporal.getEmailUsuario());
                } catch (MessagingException e) {
                    EscritorDeLogsUtilidad.registrar("Error al enviar correo de confirmación: " + e.getMessage());
                    e.printStackTrace();
                }

                response.sendRedirect("confirmado.jsp");
                EscritorDeLogsUtilidad.registrar("Usuario verificado y registrado correctamente, redirigiendo a confirmado.jsp");
            } else {
                response.sendRedirect("noconfirmado.jsp");
                EscritorDeLogsUtilidad.registrar("Error al registrar usuario verificado, redirigiendo a noconfirmado.jsp");
            }
        } else {
            response.sendRedirect("noconfirmado.jsp");
            EscritorDeLogsUtilidad.registrar("Token inválido o no encontrado, redirigiendo a noconfirmado.jsp");
        }
    }
}
