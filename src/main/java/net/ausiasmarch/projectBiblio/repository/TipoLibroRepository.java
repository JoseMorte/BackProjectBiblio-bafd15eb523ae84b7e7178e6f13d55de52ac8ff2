package net.ausiasmarch.projectBiblio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.projectBiblio.entity.TipoLibroEntity;


public interface TipoLibroRepository extends JpaRepository<TipoLibroEntity, Long> {
    Page<TipoLibroEntity> findByGeneroContaining(
            String filter2,Pageable oPageable);
        
}
