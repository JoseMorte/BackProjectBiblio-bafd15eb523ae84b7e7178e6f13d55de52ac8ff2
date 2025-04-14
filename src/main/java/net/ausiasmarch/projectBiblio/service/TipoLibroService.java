package net.ausiasmarch.projectBiblio.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import net.ausiasmarch.projectBiblio.entity.LibroGenericoEntity;
import net.ausiasmarch.projectBiblio.entity.TipoLibroEntity;
import net.ausiasmarch.projectBiblio.exception.NotAcceptableException;
import net.ausiasmarch.projectBiblio.exception.UnauthorizedAccessException;
import net.ausiasmarch.projectBiblio.repository.TipoLibroRepository;
@Service
public class TipoLibroService {

    @Autowired
    private TipoLibroRepository oTipoLibroRepository;

    @Autowired
    private RandomService oRandomService;

    @Autowired
    private AuthService oAuthService;

    public Long delete(Long id) {
        if (oAuthService.isAdmin()) {
            TipoLibroEntity oTipoLibroEntity = oTipoLibroRepository.findById(id).get();
            if (oTipoLibroEntity.getLibros() != 0) {
                throw new NotAcceptableException("No se puede borrar el TipoLibro porque tiene libros activos");
            }
            oTipoLibroRepository.deleteById(id);
            return 1L;
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para borrar el TipoLibro");
        }
    }

    public TipoLibroEntity create(TipoLibroEntity oTipoLibroEntity) {
        if (oAuthService.isAdmin()) {
            return oTipoLibroRepository.save(oTipoLibroEntity);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para crear el TipoLibro");
        }
    }

    public TipoLibroEntity update(TipoLibroEntity oTipoLibroEntity) {
        if (oAuthService.isContableWithItsOwnData(oTipoLibroEntity.getId()) || oAuthService.isAdmin()
                || oAuthService.isAuditorWithItsOwnData(oTipoLibroEntity.getId())) {
            TipoLibroEntity oTipoLibroEntityFromDatabase = oTipoLibroRepository
                    .findById(oTipoLibroEntity.getId()).get();
            if (oTipoLibroEntity.getGenero() != null) {
                oTipoLibroEntityFromDatabase.setGenero(oTipoLibroEntity.getGenero());
            }
            return oTipoLibroRepository.save(oTipoLibroEntityFromDatabase);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para modificar el TipoLibro");
        }
    }

    // Método para obtener un TipoLibro por ID
    public TipoLibroEntity get(Long id) {
        Optional<TipoLibroEntity> tipoLibro = oTipoLibroRepository.findById(id);
        if (tipoLibro.isPresent()) {
            return tipoLibro.get();
        } else {
            throw new EntityNotFoundException("Libro no encontrado con ID: " + id);
        }
    }

    // Método para obtener la lista paginada de TipoLibro
    public Page<TipoLibroEntity> getPage(Pageable oPageable, Optional<String> filter) {
        if (filter.isPresent()) {
            return oTipoLibroRepository.findByGeneroContaining(filter.get(), oPageable);
        } else {
            return oTipoLibroRepository.findAll(oPageable);
        }
    }

     public TipoLibroEntity randomSelection() {
        return oTipoLibroRepository.findAll()
                .get(oRandomService.getRandomInt(0, (int) (oTipoLibroRepository.count() - 1)));
    }
}