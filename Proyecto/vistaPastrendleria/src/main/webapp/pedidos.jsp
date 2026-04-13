<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Lista de Pedidos</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
<body>

<div class="text-center mb-4">
    <img width="148" height="118" src="imagenes/Imagen_favicon.png" alt="Logo Pastrendleria" class="img-fluid">
</div>

<div class="text-center mt-3">
    <a href="menuAdministrador.jsp" class="btn btn-primary">Volver al menú</a>
</div>

<h1 class="text-center mt-3">Lista de Pedidos</h1>

<div class="text-center mt-3 text-danger">
    <c:if test="${not empty errorMensaje}">
        <p>${errorMensaje}</p>
    </c:if>
</div>

<div class="text-center mt-3 text-success">
    <c:if test="${not empty successMensaje}">
        <p>${successMensaje}</p>
    </c:if>
</div>

<c:if test="${not empty pedidos}">
<div class="container mt-4">

    <!-- Input de búsqueda agregado -->
    <div class="mb-3">
        <input type="text" id="searchInputPedidos" class="form-control" placeholder="Buscar pedidos...">
    </div>

    <table class="table table-bordered table-striped text-center align-middle">
        <thead class="table-dark">
            <tr>
                <th>Numero de Pedido</th>
                <th>Fecha</th>
                <th>Dirección</th>
                <th>Estado</th>
                <th>Total</th>
                <th>Detalles</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>

        <c:forEach var="pedido" items="${pedidos}">
            <tr>
                <td>${pedido.idPedido}</td>
                <td>${pedido.fechaPedido}</td>
                <td>${pedido.direccionPedido}</td>
                <td>${pedido.estadoPedido}</td>
                <td>${pedido.totalPedido} €</td>

                <td>
                    <ul class="list-unstyled">
                        <c:choose>
                            <c:when test="${not empty pedido.detalles}">
                                <c:forEach var="d" items="${pedido.detalles}">
                                    <li>
                                        ${d.nombreProducto} |
                                        Cant: ${d.cantidad} |
                                        ${d.precioUnitario} € |
                                        Subtotal: ${d.subtotal} €
                                    </li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <li>No hay detalles</li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </td>

                <td>
                    <!-- Cambiar estado -->
                    <form action="modificarEstadoPedido" method="post" class="mb-2">
                        <input type="hidden" name="idPedido" value="${pedido.idPedido}">
                        <select name="nuevoEstado" class="form-select" onchange="this.form.submit()">
                            <option value="Pendiente" ${pedido.estadoPedido == 'Pendiente' ? 'selected' : ''}>Pendiente</option>
                            <option value="Preparado" ${pedido.estadoPedido == 'Preparado' ? 'selected' : ''}>Preparado</option>
                            <option value="Enviado" ${pedido.estadoPedido == 'Enviado' ? 'selected' : ''}>Enviado</option>
                            <option value="Recogido" ${pedido.estadoPedido == 'Recogido' ? 'selected' : ''}>Recogido</option>
                        </select>
                    </form>

                    <!-- Descargar factura -->
                    <form action="facturaPedido" method="get">
                        <input type="hidden" name="idPedido" value="${pedido.idPedido}">
                        <button type="submit" class="btn btn-secondary btn-sm">
                            Descargar factura PDF
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
</c:if>

<c:if test="${empty pedidos}">
    <p class="text-center text-danger">No hay pedidos disponibles.</p>
</c:if>

<!-- Script de búsqueda agregado -->
<script src="javascript/busquedaPedidos.js"></script>


</body>
</html>
