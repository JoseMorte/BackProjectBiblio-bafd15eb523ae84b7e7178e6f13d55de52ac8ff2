package net.ausiasmarch.projectBiblio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.projectBiblio.entity.TipoUsuarioEntity;


public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Long> {
 Page<TipoUsuarioEntity> findByDescripcionContaining(
        String filter2,Pageable oPageable);
}
