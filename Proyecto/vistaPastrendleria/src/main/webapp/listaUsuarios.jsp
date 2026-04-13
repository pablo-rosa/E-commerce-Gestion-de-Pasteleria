<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Usuarios Registrados</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
<body>

<!-- Mensajes de error y éxito -->
<div class="text-center mt-3 text-danger">
    <c:if test="${not empty errorMensaje}">
        <p class="error">${errorMensaje}</p>
    </c:if>
</div>

<div class="text-center mt-3 text-success">
    <c:if test="${not empty successMensaje}">
        <p class="success">${successMensaje}</p>
    </c:if>
</div>

<div class="container mt-5">
    <div class="text-center mb-4">
        <img width="148" height="118" src="imagenes/Imagen_favicon.png" alt="Logo Pastrendleria" class="img-fluid">
        <h2 class="mt-3"><b>Usuarios Registrados</b></h2>
    </div>

    <c:choose>
        <c:when test="${not empty usuarios}">
            <div class="table-responsive">
                <table class="table table-bordered table-striped text-center align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th>Foto</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th>Teléfono</th>
                            <th>Rol</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    <div class="mb-3">
    <input type="text" id="searchInput" class="form-control" placeholder="Buscar usuarios por nombre o email...">
</div>
                    
                        <c:forEach var="usuario" items="${usuarios}">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty usuario.fotoUsuario}">
                                            <img src="data:image/jpeg;base64,${usuario.fotoUsuario}" width="60" height="60"
                                                 style="object-fit: cover; border-radius: 50%;" />
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted">Sin foto</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${usuario.nombreUsuario}</td>
                                <td>${usuario.emailUsuario}</td>
                                <td>${usuario.telefonoUsuario}</td>
                                <td>${usuario.rol}</td>
                                <td>
                                    <button class="btn btn-warning btn-sm me-1"
                                            onclick="llenarFormularioModificar('${usuario.idUsuario}', '${usuario.nombreUsuario}', '${usuario.telefonoUsuario}', '${usuario.rol}')"
                                            data-bs-toggle="modal" data-bs-target="#modalModificar">Modificar</button>

                                    <button class="btn btn-danger btn-sm"
                                            onclick="confirmarEliminacion('${usuario.idUsuario}', '${usuario.nombreUsuario}')"
                                            data-bs-toggle="modal" data-bs-target="#modalEliminar">Eliminar</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <p class="text-center text-danger">No hay usuarios registrados.</p>
        </c:otherwise>
    </c:choose>

    <div class="text-center mt-4">
        <a href="menuAdministrador.jsp" class="btn btn-primary">Volver al menú</a>
    </div>
</div>

<!-- Modal Modificar -->
<div class="modal fade" id="modalModificar" tabindex="-1">
  <div class="modal-dialog">
    <form class="modal-content" action="<%= request.getContextPath() %>/modificarUsuario" method="post" enctype="multipart/form-data">
      <div class="modal-header">
        <h5 class="modal-title">Modificar Usuario</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        <input type="hidden" name="idUsuario" id="modIdUsuario">
        <div class="mb-3">
          <label for="modNombre">Nuevo Nombre</label>
          <input type="text" class="form-control" name="nuevoNombre" id="modNombre" required>
        </div>
        <div class="mb-3">
          <label for="modTelefono">Nuevo Teléfono</label>
          <input type="text" class="form-control" name="nuevoTelefono" id="modTelefono" required>
        </div>
        <div class="mb-3">
          <label for="modRol">Rol</label>
          <select class="form-select" name="nuevoRol" id="modRol" required>
            <option value="admin">Admin</option>
            <option value="usuario">Usuario</option>
          </select>
        </div>
        <div class="mb-3">
          <label for="modFoto">Nueva Foto (opcional)</label>
          <input type="file" class="form-control" name="nuevaFoto" id="modFoto" accept="image/*">
        </div>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
      </div>
    </form>
  </div>
</div>

<!-- Modal Eliminar -->
<div class="modal fade" id="modalEliminar" tabindex="-1">
  <div class="modal-dialog">
    <form class="modal-content" action="<%= request.getContextPath() %>/eliminarUsuario" method="post">
      <div class="modal-header">
        <h5 class="modal-title">Confirmar Eliminación</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>

      <div class="modal-body">
        <p>¿Estás seguro de que deseas eliminar este usuario?</p>
        <input type="hidden" name="idUsuario" id="elimIdUsuario">
        <p class="mt-3">
            Escribe <b>Si(<span id="nombreValidacion"></span>)SeguroDeEliminar</b> para confirmar:
        </p>
        <input type="text" class="form-control" name="confirmacionEliminar" id="confirmacionEliminar" required>
      </div>

      <div class="modal-footer">
        <button type="submit" class="btn btn-danger">Eliminar</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
      </div>
    </form>
  </div>
</div>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function llenarFormularioModificar(id, nombre, telefono, rol) {
        document.getElementById("modIdUsuario").value = id;
        document.getElementById("modNombre").value = nombre;
        document.getElementById("modTelefono").value = telefono;
        document.getElementById("modRol").value = rol;
    }

    function confirmarEliminacion(id, nombre) {
        document.getElementById("elimIdUsuario").value = id;
        document.getElementById("nombreValidacion").textContent = nombre;
    }
</script>
<script src="javascript/busquedaUsuarios.js"></script>

</body>
</html>
