package net.ausiasmarch.projectBiblio.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import net.ausiasmarch.projectBiblio.entity.TipoLibroEntity;
import net.ausiasmarch.projectBiblio.entity.TipoUsuarioEntity;
import net.ausiasmarch.projectBiblio.exception.NotAcceptableException;
import net.ausiasmarch.projectBiblio.exception.UnauthorizedAccessException;
import net.ausiasmarch.projectBiblio.repository.TipoUsuarioRepository;
@Service
public class TipoUsuarioService {

    @Autowired
    private TipoUsuarioRepository oTipousuarioRepository;

    @Autowired
    private RandomService oRandomService;

    @Autowired
    private AuthService oAuthService;

    // Método para obtener un TipoUsuario por ID
    public TipoUsuarioEntity get(Long id) {
        Optional<TipoUsuarioEntity> tipousuario = oTipousuarioRepository.findById(id);
        if (tipousuario.isPresent()) {
            return tipousuario.get();
        } else {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + id);
        }
    }

    // Método para obtener la lista paginada de Tipousuario
    public Page<TipoUsuarioEntity> getPage(Pageable oPageable, Optional<String> filter) {
        if (filter.isPresent()) {
            return oTipousuarioRepository.findByDescripcionContaining(filter.get(), oPageable);
        } else {
            return oTipousuarioRepository.findAll(oPageable);
        }
    }

    public TipoUsuarioEntity randomSelection() {
        return oTipousuarioRepository.findAll()
                .get(oRandomService.getRandomInt(0, (int) (oTipousuarioRepository.count() - 1)));
    }

     public Long delete(Long id) {
        if (oAuthService.isAdmin()) {
            TipoUsuarioEntity oTipoUsuarioEntity = oTipousuarioRepository.findById(id).get();
            if (oTipoUsuarioEntity.getUsuarios() != 0) {
                throw new NotAcceptableException("No se puede borrar el TipoUsuario porque tiene Usuarios activos");
            }
            oTipousuarioRepository.deleteById(id);
            return 1L;
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para borrar el TipoUsuario");
        }
    }

    public TipoUsuarioEntity create(TipoUsuarioEntity oTipoUsuarioEntity) {
        if (oAuthService.isAdmin()) {
            return oTipousuarioRepository.save(oTipoUsuarioEntity);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para crear el TipoUsuario");
        }
    }

    public TipoUsuarioEntity update(TipoUsuarioEntity oTipoUsuarioEntity) {
        if (oAuthService.isContableWithItsOwnData(oTipoUsuarioEntity.getId()) || oAuthService.isAdmin()
                || oAuthService.isAuditorWithItsOwnData(oTipoUsuarioEntity.getId())) {
            TipoUsuarioEntity oTipoUsuarioEntityFromDatabase = oTipousuarioRepository
                    .findById(oTipoUsuarioEntity.getId()).get();
            if (oTipoUsuarioEntity.getDescripcion() != null) {
                oTipoUsuarioEntityFromDatabase.setDescripcion(oTipoUsuarioEntity.getDescripcion());
            }
            return oTipousuarioRepository.save(oTipoUsuarioEntityFromDatabase);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para modificar el TipoUsuario");
        }
    }
}