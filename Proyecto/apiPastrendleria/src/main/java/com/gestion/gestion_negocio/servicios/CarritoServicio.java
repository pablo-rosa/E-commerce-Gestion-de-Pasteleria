package com.gestion.gestion_negocio.servicios;

import com.gestion.gestion_negocio.daos.CarritoDao;
import com.gestion.gestion_negocio.daos.DetalleCarritoDao;
import com.gestion.gestion_negocio.dtos.AñadirDetalleCarritoDto;
import com.gestion.gestion_negocio.dtos.CarritoDto;
import com.gestion.gestion_negocio.dtos.DetalleCarritoDto;
import com.gestion.gestion_negocio.repositorios.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona las operaciones de persistencia del carrito.
 * Este servicio se enfoca únicamente en la persistencia de datos,
 * siguiendo la arquitectura donde la API maneja solo el acceso a datos.
 */
@Service
public class CarritoServicio {

    @Autowired
    private CarritoRepository carritoRepository;

    // -------------------- OBTENER CARRITO --------------------
    public CarritoDto obtenerCarritoPorUsuario(Long idUsuario) {
        Optional<CarritoDao> carritoOpt = carritoRepository.findByIdUsuarioAndEstadoCarrito(idUsuario, "ACTIVO");

        if (carritoOpt.isPresent()) {
            return convertirADto(carritoOpt.get());
        }

        System.out.println("[INFO] No se encontró carrito activo para usuario: " + idUsuario);
        return null;
    }

    // -------------------- CREAR CARRITO --------------------
    @Transactional
    public CarritoDto crearCarrito(Long idUsuario) {
        Optional<CarritoDao> carritoExistente = carritoRepository.findByIdUsuarioAndEstadoCarrito(idUsuario, "ACTIVO");

        if (carritoExistente.isPresent()) {
            System.out.println("[INFO] Carrito ya existente para usuario: " + idUsuario);
            return convertirADto(carritoExistente.get());
        }

        CarritoDao carrito = new CarritoDao();
        carrito.setIdUsuario(idUsuario);
        carrito.setFechaCarrito(OffsetDateTime.now());
        carrito.setEstadoCarrito("ACTIVO");
        carrito.setTotalCarrito(BigDecimal.ZERO);
        carrito.setDetalles(new ArrayList<>());

        carrito = carritoRepository.save(carrito);
        System.out.println("[INFO] Carrito creado para usuario: " + idUsuario);
        return convertirADto(carrito);
    }

    // -------------------- AÑADIR PRODUCTO --------------------
    @Transactional
    public CarritoDto añadirProductoAlCarrito(Long idUsuario, AñadirDetalleCarritoDto detalleDto) {
        CarritoDao carrito = obtenerOCrearCarrito(idUsuario);

        DetalleCarritoDao detalleExistente = null;
        if (carrito.getDetalles() != null) {
            for (DetalleCarritoDao detalle : carrito.getDetalles()) {
                if (detalle.getIdProducto() != null && detalle.getIdProducto().equals(detalleDto.getIdProducto())) {
                    detalleExistente = detalle;
                    break;
                }
            }
        }

        if (detalleExistente != null) {
            int nuevaCantidad = detalleExistente.getCantidad() + detalleDto.getCantidad();
            detalleExistente.setCantidad(nuevaCantidad);
            detalleExistente.setSubtotal(detalleDto.getPrecioUnitario().multiply(BigDecimal.valueOf(nuevaCantidad)));
            System.out.println("[INFO] Cantidad actualizada del producto " + detalleDto.getNombreProducto() +
                               " en carrito usuario " + idUsuario);
        } else {
            DetalleCarritoDao nuevoDetalle = new DetalleCarritoDao();
            nuevoDetalle.setCarrito(carrito);
            nuevoDetalle.setIdProducto(detalleDto.getIdProducto());
            nuevoDetalle.setNombreProducto(detalleDto.getNombreProducto());
            nuevoDetalle.setCantidad(detalleDto.getCantidad());
            nuevoDetalle.setPrecioUnitario(detalleDto.getPrecioUnitario());
            nuevoDetalle.setSubtotal(detalleDto.getPrecioUnitario().multiply(BigDecimal.valueOf(detalleDto.getCantidad())));

            if (carrito.getDetalles() == null) {
                carrito.setDetalles(new ArrayList<>());
            }
            carrito.getDetalles().add(nuevoDetalle);
            System.out.println("[INFO] Producto añadido: " + detalleDto.getNombreProducto() +
                               " al carrito usuario " + idUsuario);
        }

        actualizarTotalCarrito(carrito);
        carrito.setFechaCarrito(OffsetDateTime.now());
        carrito = carritoRepository.save(carrito);

        return convertirADto(carrito);
    }

    // -------------------- ELIMINAR PRODUCTO --------------------
    @Transactional
    public boolean eliminarProductoDelCarrito(Long idUsuario, Long idProducto) {
        Optional<CarritoDao> carritoOpt = carritoRepository.findByIdUsuarioAndEstadoCarrito(idUsuario, "ACTIVO");

        if (!carritoOpt.isPresent()) {
            System.out.println("[WARN] Intento de eliminar producto de carrito inexistente para usuario: " + idUsuario);
            return false;
        }

        CarritoDao carrito = carritoOpt.get();
        if (carrito.getDetalles() != null) {
            boolean eliminado = carrito.getDetalles().removeIf(
                    detalle -> detalle.getIdProducto() != null && detalle.getIdProducto().equals(idProducto)
            );

            if (eliminado) {
                actualizarTotalCarrito(carrito);
                carrito.setFechaCarrito(OffsetDateTime.now());
                carritoRepository.save(carrito);
                System.out.println("[INFO] Producto " + idProducto + " eliminado del carrito usuario " + idUsuario);
                return true;
            } else {
                System.out.println("[WARN] Producto " + idProducto + " no encontrado en carrito usuario " + idUsuario);
            }
        }

        return false;
    }

    // -------------------- ACTUALIZAR CANTIDAD --------------------
    @Transactional
    public CarritoDto actualizarCantidadProducto(Long idUsuario, Long idProducto, Integer nuevaCantidad) {
        Optional<CarritoDao> carritoOpt = carritoRepository.findByIdUsuarioAndEstadoCarrito(idUsuario, "ACTIVO");

        if (!carritoOpt.isPresent()) {
            System.out.println("[WARN] No se encontró carrito activo para usuario: " + idUsuario);
            return null;
        }

        CarritoDao carrito = carritoOpt.get();

        if (nuevaCantidad <= 0) {
            eliminarProductoDelCarrito(idUsuario, idProducto);
            return obtenerCarritoPorUsuario(idUsuario);
        }

        boolean encontrado = false;
        if (carrito.getDetalles() != null) {
            for (DetalleCarritoDao detalle : carrito.getDetalles()) {
                if (detalle.getIdProducto() != null && detalle.getIdProducto().equals(idProducto)) {
                    detalle.setCantidad(nuevaCantidad);
                    detalle.setSubtotal(detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(nuevaCantidad)));
                    encontrado = true;
                    System.out.println("[INFO] Cantidad del producto " + idProducto + " actualizada a " + nuevaCantidad +
                                       " en carrito usuario " + idUsuario);
                    break;
                }
            }
        }

        if (!encontrado) {
            System.out.println("[WARN] Producto " + idProducto + " no encontrado en carrito usuario " + idUsuario);
        }

        actualizarTotalCarrito(carrito);
        carrito.setFechaCarrito(OffsetDateTime.now());
        carrito = carritoRepository.save(carrito);
        return convertirADto(carrito);
    }

    // -------------------- SINCRONIZAR CARRITO --------------------
    @Transactional
    public CarritoDto sincronizarCarrito(Long idUsuario, List<AñadirDetalleCarritoDto> detallesSesion) {
        CarritoDao carrito = obtenerOCrearCarrito(idUsuario);

        if (detallesSesion != null && !detallesSesion.isEmpty()) {
            for (AñadirDetalleCarritoDto detalleSesion : detallesSesion) {
                boolean existe = false;
                if (carrito.getDetalles() != null) {
                    for (DetalleCarritoDao detalle : carrito.getDetalles()) {
                        if (detalle.getIdProducto() != null && detalle.getIdProducto().equals(detalleSesion.getIdProducto())) {
                            int nuevaCantidad = detalle.getCantidad() + detalleSesion.getCantidad();
                            detalle.setCantidad(nuevaCantidad);
                            detalle.setSubtotal(detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(nuevaCantidad)));
                            existe = true;
                            System.out.println("[INFO] Producto existente actualizado en sincronización: " + detalleSesion.getNombreProducto());
                            break;
                        }
                    }
                }

                if (!existe) {
                    DetalleCarritoDao nuevoDetalle = new DetalleCarritoDao();
                    nuevoDetalle.setCarrito(carrito);
                    nuevoDetalle.setIdProducto(detalleSesion.getIdProducto());
                    nuevoDetalle.setNombreProducto(detalleSesion.getNombreProducto());
                    nuevoDetalle.setCantidad(detalleSesion.getCantidad());
                    nuevoDetalle.setPrecioUnitario(detalleSesion.getPrecioUnitario());
                    nuevoDetalle.setSubtotal(detalleSesion.getPrecioUnitario().multiply(BigDecimal.valueOf(detalleSesion.getCantidad())));

                    if (carrito.getDetalles() == null) {
                        carrito.setDetalles(new ArrayList<>());
                    }
                    carrito.getDetalles().add(nuevoDetalle);
                    System.out.println("[INFO] Producto añadido en sincronización: " + detalleSesion.getNombreProducto());
                }
            }
        } else {
            System.out.println("[INFO] Lista de sincronización vacía para usuario " + idUsuario);
        }

        actualizarTotalCarrito(carrito);
        carrito.setFechaCarrito(OffsetDateTime.now());
        carrito = carritoRepository.save(carrito);
        return convertirADto(carrito);
    }

    // -------------------- CERRAR CARRITO --------------------
    @Transactional
    public void cerrarCarrito(Long idUsuario) {
        Optional<CarritoDao> carritoOpt = carritoRepository.findByIdUsuarioAndEstadoCarrito(idUsuario, "ACTIVO");

        if (carritoOpt.isPresent()) {
            CarritoDao carrito = carritoOpt.get();
            carrito.setEstadoCarrito("FINALIZADO");
            if (carrito.getDetalles() != null) carrito.getDetalles().clear();
            carrito.setTotalCarrito(BigDecimal.ZERO);
            carritoRepository.save(carrito);
            System.out.println("[INFO] Carrito cerrado y finalizado para usuario " + idUsuario);
        } else {
            System.out.println("[WARN] No se encontró carrito activo para cerrar usuario " + idUsuario);
        }
    }

    // -------------------- MÉTODOS AUXILIARES --------------------
    private CarritoDao obtenerOCrearCarrito(Long idUsuario) {
        Optional<CarritoDao> carritoOpt = carritoRepository.findByIdUsuarioAndEstadoCarrito(idUsuario, "ACTIVO");

        if (carritoOpt.isPresent()) return carritoOpt.get();

        CarritoDao carrito = new CarritoDao();
        carrito.setIdUsuario(idUsuario);
        carrito.setFechaCarrito(OffsetDateTime.now());
        carrito.setEstadoCarrito("ACTIVO");
        carrito.setTotalCarrito(BigDecimal.ZERO);
        carrito.setDetalles(new ArrayList<>());

        carrito = carritoRepository.save(carrito);
        System.out.println("[INFO] Nuevo carrito creado para usuario " + idUsuario);
        return carrito;
    }

    private void actualizarTotalCarrito(CarritoDao carrito) {
        BigDecimal total = BigDecimal.ZERO;
        if (carrito.getDetalles() != null) {
            for (DetalleCarritoDao detalle : carrito.getDetalles()) {
                total = total.add(detalle.getSubtotal());
            }
        }
        carrito.setTotalCarrito(total);
    }

    private CarritoDto convertirADto(CarritoDao carrito) {
        List<DetalleCarritoDto> detallesDto = null;
        if (carrito.getDetalles() != null) {
            detallesDto = carrito.getDetalles().stream()
                    .map(detalle -> new DetalleCarritoDto(
                            detalle.getIdDetalleCarrito(),
                            detalle.getIdProducto(),
                            detalle.getNombreProducto(),
                            detalle.getCantidad(),
                            detalle.getPrecioUnitario(),
                            detalle.getSubtotal()
                    )).collect(Collectors.toList());
        }

        return new CarritoDto(
                carrito.getIdCarrito(),
                carrito.getIdUsuario(),
                carrito.getFechaCarrito(),
                carrito.getDireccionCarrito(),
                carrito.getEstadoCarrito(),
                carrito.getTotalCarrito(),
                detallesDto
        );
    }
}
