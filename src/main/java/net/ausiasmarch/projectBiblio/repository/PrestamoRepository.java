package net.ausiasmarch.projectBiblio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.projectBiblio.entity.PrestamoEntity;
import org.springframework.data.jpa.repository.Query;

public interface PrestamoRepository extends JpaRepository<PrestamoEntity, Long> {

    Page<PrestamoEntity> findByUsuarioId(Long idUsuario, Pageable oPageable);

    Page<PrestamoEntity> findByLibroFisicoId(Long idLibroFisico, Pageable oPageable);

    @Query(value= "SELECT * FROM Prestamo WHERE estado = 1", nativeQuery = true)
    Page<PrestamoEntity> findActivePrestamos(Pageable oPageable);
}
