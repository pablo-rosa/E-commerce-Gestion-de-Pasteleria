package utilidades;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dtos.RegistroUsuarioDto;
import dtos.RecuperacionContrasenaDto;

/**
 * Utilidad para generación y gestión de tokens de verificación y recuperación.
 * Esta clase es reutilizable para múltiples flujos que requieran confirmación por token.
 */
public class TokenUtilidad {

    // Mapa para tokens de verificación de cuenta
    private static final Map<String, RegistroUsuarioDto> tokenUsuarioMap = new ConcurrentHashMap<>();

    // Mapa para tokens de recuperación de contraseña
    private static final Map<String, RecuperacionContrasenaDto> tokenRecuperacionMap = new ConcurrentHashMap<>();

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    // ------------------ FLUJO DE VERIFICACIÓN DE CUENTA ------------------

    /**
     * Genera un token aleatorio y lo asocia a un usuario (verificación de cuenta).
     * 
     * @param usuario el DTO del usuario.
     * @return el token generado.
     */
    public static String generarYAsignarToken(RegistroUsuarioDto usuario) {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String token = base64Encoder.encodeToString(randomBytes);

        tokenUsuarioMap.put(token, usuario);
        return token;
    }

    /**
     * Verifica un token de verificación de cuenta.
     * 
     * @param token el token a validar.
     * @return el DTO RegistroUsuarioDto si el token es válido; null si no.
     */
    public static RegistroUsuarioDto validarToken(String token) {
        return tokenUsuarioMap.get(token);
    }

    /**
     * Genera un enlace de verificación de cuenta.
     * 
     * @param token el token generado previamente.
     * @return una URL de verificación.
     */
    public static String generarEnlaceVerificacion(String token) {
        return "http://pablodominio.sbs/vistaPastrendleria/verificarCuenta?token=" + token;
    }

    /**
     * Elimina un token de verificación.
     * 
     * @param token el token a eliminar.
     */
    public static void eliminarToken(String token) {
        tokenUsuarioMap.remove(token);
    }

    // ------------------ FLUJO DE RECUPERACIÓN DE CONTRASEÑA ------------------

    /**
     * Genera un token para recuperación de contraseña y lo asocia a un email.
     * 
     * @param dto el DTO con el email del usuario.
     * @return el token generado.
     */
    public static String generarYAsignarTokenRecuperacion(RecuperacionContrasenaDto dto) {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String token = base64Encoder.encodeToString(randomBytes);

        // Establece una expiración de 15 minutos (por ejemplo)
        long expiracionEnMilis = System.currentTimeMillis() + (15 * 60 * 1000);
        dto.setExpiracion(expiracionEnMilis);

        tokenRecuperacionMap.put(token, dto);
        return token;
    }


    /**
     * Valida un token de recuperación de contraseña.
     * 
     * @param token el token recibido.
     * @return el DTO RecuperacionContrasenaDto si es válido; null si no.
     */
    public static RecuperacionContrasenaDto validarTokenRecuperacion(String token) {
        RecuperacionContrasenaDto dto = tokenRecuperacionMap.get(token);
        if (dto == null) {
            return null;
        }

        // Verificar expiración
        if (System.currentTimeMillis() > dto.getExpiracion()) {
            System.out.println("[INFO] Token expirado: " + token);
            tokenRecuperacionMap.remove(token); // Limpiar token expirado
            return null;
        }

        return dto;
    }


    /**
     * Elimina un token de recuperación de contraseña una vez usado.
     * 
     * @param token el token a eliminar.
     */
    public static void eliminarTokenRecuperacion(String token) {
        tokenRecuperacionMap.remove(token);
    }

    /**
     * Genera un enlace para recuperación de contraseña.
     * 
     * @param token el token generado previamente.
     * @return una URL de recuperación.
     */
    public static String generarEnlaceRecuperacion(String token) {
        return "http://pablodominio.sbs/vistaPastrendleria/restablecerContrasena.jsp?token=" + token;
    }
}
