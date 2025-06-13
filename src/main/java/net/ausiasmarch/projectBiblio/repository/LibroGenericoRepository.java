package net.ausiasmarch.projectBiblio.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.projectBiblio.entity.LibroGenericoEntity;

public interface LibroGenericoRepository extends JpaRepository<LibroGenericoEntity, Long> {
        Page<LibroGenericoEntity> findByTipolibroId(Long idTipolibro, Pageable oPageable);

        Page<LibroGenericoEntity> findByTituloContaining(
        Pageable oPageable, String filter);
}
