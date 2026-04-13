package com.gestion.gestion_negocio.repositorios;

import com.gestion.gestion_negocio.daos.PedidoDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoDao, Long> {
}
