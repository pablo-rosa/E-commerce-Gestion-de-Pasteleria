package utilidades;

import java.util.regex.Pattern;

public class ContrasenaSeguraUtilidad {

    private static final String PATRON_SEGURIDAD =
    		"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!¡?*()_\\-.])[A-Za-z\\d@#$%^&+=!¡?*()_\\-.]{8,}$";

    private static final Pattern pattern = Pattern.compile(PATRON_SEGURIDAD);

    /**
     * Verifica si la contraseña cumple con los criterios de seguridad:
     * - Al menos 8 caracteres
     * - Al menos una letra minúscula
     * - Al menos una letra mayúscula
     * - Al menos un número
     * - Al menos un símbolo especial
     *
     * @param password Contraseña a validar
     * @return true si es segura, false si no lo es
     */
    public static boolean esSegura(String password) {
        return pattern.matcher(password).matches();
    }
}
