package net.ausiasmarch.projectBiblio.service;

import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.ausiasmarch.projectBiblio.entity.LibroFisicoEntity;
import net.ausiasmarch.projectBiblio.exception.NotAcceptableException;
import net.ausiasmarch.projectBiblio.exception.ResourceNotFoundException;
import net.ausiasmarch.projectBiblio.exception.ResourceNotModifiedException;
import net.ausiasmarch.projectBiblio.exception.UnauthorizedAccessException;
import net.ausiasmarch.projectBiblio.repository.LibroFisicoRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@AllArgsConstructor
public class LibroFisicoService {

    HttpServletRequest oHttpServletRequest;

    LibroFisicoRepository oLibroFisicoRepository;

    AuthService oAuthService;

    RandomService oRandomService;

    LibroGenericoService oLibroGenericoService;

    //private String[] arrayEstados;

    public Long randomCreate(Long cantidad) {

        //arrayEstados = new String[]{"Disponible", "Prestado", "En reparación", "Perdido"};

        for (int i = 0; i < cantidad; i++) {
            LibroFisicoEntity oLibroFisicoEntity = new LibroFisicoEntity();
            oLibroFisicoEntity.setCodigoInventario(Long.valueOf(oRandomService.getRandomInt(999, 9999)));
            oLibroFisicoEntity.setLibroGenerico(oLibroGenericoService.randomSelection());
            oLibroFisicoEntity.setEstado(Long.valueOf(0));
            oLibroFisicoRepository.save(oLibroFisicoEntity);
        }
        return oLibroFisicoRepository.count();
    }

    public LibroFisicoEntity get(Long id) {
        if (oAuthService.isContableWithItsOwnData(id) || oAuthService.isAdmin()
                || oAuthService.isAuditorWithItsOwnData(id)) {
            Optional<LibroFisicoEntity> LibroFisico = oLibroFisicoRepository.findById(id);
            if (LibroFisico.isPresent()) {
                return LibroFisico.get();
            } else {
                throw new EntityNotFoundException("LibroFisico no encontrado con ID: " + id);
            }
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver el LibroFisico");
        }
    }

    public Page<LibroFisicoEntity> getPage(Pageable oPageable) {
        if (oAuthService.isAdmin()) {
            return oLibroFisicoRepository.findAll(oPageable);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver los LibroFisicos");
        }
    }


    public Page<LibroFisicoEntity> getPageLibroFisicoDisponible(Pageable oPageable) {
        if (oAuthService.isAdmin() || oAuthService.isContable()) {
            return oLibroFisicoRepository.findByLibroFisicoDisponible(oPageable);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver los LibroFisicos");
        }

    }

    public Page<LibroFisicoEntity> getPageXLibroGenerico(Optional<Long> idLibroGenerico, Pageable oPageable) {
        if (oAuthService.isAdmin()) {
            if (idLibroGenerico.isPresent()) {
            return oLibroFisicoRepository.findByLibroGenericoId(idLibroGenerico.get(),oPageable);
            }else{
                 throw new ResourceNotFoundException("LibroGenerico no encontrada");
            }
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver los LibroFisicos");
        }

    }

    public Long count() {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para contar los LibroFisicos");
        } else {
            return oLibroFisicoRepository.count();
        }
    }

    public Long delete(Long id) {
        if (oAuthService.isAdmin()) {
            LibroFisicoEntity oLibroFisicoEntity = oLibroFisicoRepository.findById(id).get();
            if (oLibroFisicoEntity.getPrestamos() != 0) {
                throw new NotAcceptableException("No se puede borrar el LibroFisico porque tiene préstamos realizados");
            }
            oLibroFisicoRepository.deleteById(id);
            return 1L;
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para borrar el LibroFisico");
        }
    }

    public LibroFisicoEntity create(LibroFisicoEntity oLibroFisicoEntity) {
        if (oAuthService.isAdmin()) {
            return oLibroFisicoRepository.save(oLibroFisicoEntity);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para crear el LibroFisico");

            }

        }
            
            public LibroFisicoEntity update(LibroFisicoEntity oLibroFisicoEntity) {
                if (oAuthService.isContableWithItsOwnData(oLibroFisicoEntity.getId()) || oAuthService.isAdmin()
                        || oAuthService.isAuditorWithItsOwnData(oLibroFisicoEntity.getId())) {
                    LibroFisicoEntity oLibroFisicoEntityFromDatabase = oLibroFisicoRepository
                            .findById(oLibroFisicoEntity.getId()).get();
                    if (oLibroFisicoEntity.getCodigoInventario() != null) {
                        oLibroFisicoEntityFromDatabase.setCodigoInventario(oLibroFisicoEntity.getCodigoInventario());
                    }
                    if (oLibroFisicoEntity.getLibroGenerico() != null) {
                        oLibroFisicoEntityFromDatabase.setLibroGenerico(oLibroFisicoEntity.getLibroGenerico());
                    }
                    if (oLibroFisicoEntity.getEstado() != null) {
                        oLibroFisicoEntityFromDatabase.setEstado(oLibroFisicoEntity.getEstado());
                    }
                    return oLibroFisicoRepository.save(oLibroFisicoEntityFromDatabase);
                } else {
                    throw new UnauthorizedAccessException("No tienes permisos para modificar el LibroFisico");
                }
            }

            public Long deleteAll() {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para borrar todos los LibroFisicos");
        } else {
            oLibroFisicoRepository.deleteAll();
            return this.count();
        }
    }

    public LibroFisicoEntity randomSelection() {
        return oLibroFisicoRepository.findAll()
                .get(oRandomService.getRandomInt(0, (int) (oLibroFisicoRepository.count() - 1)));
    }
}
