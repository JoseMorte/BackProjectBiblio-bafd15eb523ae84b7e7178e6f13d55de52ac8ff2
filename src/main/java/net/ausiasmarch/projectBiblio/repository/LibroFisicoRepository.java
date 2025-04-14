package net.ausiasmarch.projectBiblio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.projectBiblio.entity.LibroFisicoEntity;


public interface LibroFisicoRepository extends JpaRepository<LibroFisicoEntity, Long> {

         Page<LibroFisicoEntity> findByLibroGenericoId(Long idLibroGenerico, Pageable oPageable);

         @Query(value= "SELECT * FROM LibroFisico WHERE estado = 0", nativeQuery = true)
         Page<LibroFisicoEntity> findByLibroFisicoDisponible(Pageable oPageable);
    
         
}
