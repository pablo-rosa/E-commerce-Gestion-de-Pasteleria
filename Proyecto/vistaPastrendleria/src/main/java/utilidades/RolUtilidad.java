package utilidades;

import jakarta.servlet.http.HttpSession;

public class RolUtilidad {

    /**
     * Comprueba si el usuario en sesión tiene el rol requerido.
     *
     * @param session HttpSession actual
     * @param rolRequerido Rol que se necesita
     * @return true si el usuario tiene el rol, false si no tiene o no hay sesión
     */
    public static boolean tieneRol(HttpSession session, String rolRequerido) {
        if (session == null) return false;

        String rolUsuario = (String) session.getAttribute("rol");
        return rolRequerido != null && rolRequerido.equals(rolUsuario);
    }

    /**
     * Comprueba si hay algún usuario logueado.
     *
     * @param session HttpSession actual
     * @return true si hay usuario logueado, false si no
     */
    public static boolean estaLogueado(HttpSession session) {
        if (session == null) return false;
        return session.getAttribute("rol") != null;
    }
}
