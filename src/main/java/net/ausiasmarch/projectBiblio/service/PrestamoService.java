package net.ausiasmarch.projectBiblio.service;

import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.ausiasmarch.projectBiblio.entity.PrestamoEntity;
import net.ausiasmarch.projectBiblio.exception.ResourceNotFoundException;
import net.ausiasmarch.projectBiblio.exception.UnauthorizedAccessException;
import net.ausiasmarch.projectBiblio.repository.PrestamoRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@AllArgsConstructor
public class PrestamoService {

    HttpServletRequest oHttpServletRequest;

    PrestamoRepository oPrestamoRepository;

    AuthService oAuthService;

    RandomService oRandomService;

    UsuarioService oUsuarioService;

    LibroFisicoService oLibroFisicoService;

    public Long randomCreate(Long cantidad){

        for (int i = 0; i < cantidad; i++) {
            PrestamoEntity oPrestamoEntity = new PrestamoEntity();
            oPrestamoEntity.setUsuario(oUsuarioService.randomSelection());
            oPrestamoEntity.setLibroFisico(oLibroFisicoService.randomSelection());
            oPrestamoEntity.setInicioPrestamo(LocalDate.now());
            oPrestamoEntity.setFinPrestamo(LocalDate.now().plusDays(oRandomService.getRandomInt(1, 10)));
            oPrestamoEntity.setEstado(1L);
            oPrestamoRepository.save(oPrestamoEntity);
        }
        return oPrestamoRepository.count();
    }

    public PrestamoEntity get(Long id) {
        if (oAuthService.isContableWithItsOwnData(id) || oAuthService.isAdmin()
                || oAuthService.isAuditorWithItsOwnData(id)) {
            Optional<PrestamoEntity> Prestamo = oPrestamoRepository.findById(id);
            if (Prestamo.isPresent()) {
                return Prestamo.get();
            } else {
                throw new EntityNotFoundException("Prestamo no encontrado con ID: " + id);
            }
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver el Prestamo");
        }
    }

    public Page<PrestamoEntity> getPage(Pageable oPageable) {
        if (oAuthService.isAdmin()) {

            return oPrestamoRepository.findAll(oPageable);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver los Prestamos");
        }

    }

    public Page<PrestamoEntity> getPageActivePrestamos(Pageable oPageable) {
        if (oAuthService.isAdmin()) {

            return oPrestamoRepository.findActivePrestamos(oPageable);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver los Prestamos");
        }

    }

    public Page<PrestamoEntity> getPageXUsuario(Optional<Long> idUsuario, Pageable oPageable) {
        if (oAuthService.isAdmin()) {
            if (idUsuario.isPresent()) {
            return oPrestamoRepository.findByUsuarioId(idUsuario.get(),oPageable);
            }else{
                 throw new ResourceNotFoundException("Usuario no encontrada");
            }
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver los Prestamos");
        }
    }

    public Page<PrestamoEntity> getPageXLibroFisico(Optional<Long> idLibroFisico, Pageable oPageable) {
        if (oAuthService.isAdmin()) {
            if (idLibroFisico.isPresent()) {
            return oPrestamoRepository.findByLibroFisicoId(idLibroFisico.get(),oPageable);
            }else{
                 throw new ResourceNotFoundException("LibroFisico no encontrada");
            }
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver los Prestamos");
        }
    }


    public Long count() {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para contar los Prestamos");
        } else {
            return oPrestamoRepository.count();
        }
    }

    /*public Long delete(Long id) {
        if (oAuthService.isAdmin()) {
            oPrestamoRepository.deleteById(id);
            System.out.println("Eliminando préstamo con ID: " + id);
            return 1L;
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para borrar el Prestamo");
        }
    }*/

    public PrestamoEntity create(PrestamoEntity oPrestamoEntity) {
        if (oAuthService.isAdmin()) {
            oPrestamoEntity.getLibroFisico().setEstado(1L);
            return oPrestamoRepository.save(oPrestamoEntity);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para crear el Prestamo");
        }
    }

    /*public PrestamoEntity update(PrestamoEntity oPrestamoEntity) {
        if (oAuthService.isContableWithItsOwnData(oPrestamoEntity.getId()) || oAuthService.isAdmin()
                || oAuthService.isAuditorWithItsOwnData(oPrestamoEntity.getId())) {
            PrestamoEntity oPrestamoEntityFromDatabase = oPrestamoRepository.findById(oPrestamoEntity.getId()).get();
            if (oPrestamoEntity.getUsuario() != null) {
                oPrestamoEntityFromDatabase.setUsuario(oPrestamoEntity.getUsuario());
            }
            if (oPrestamoEntity.getLibroFisico() != null) {
                oPrestamoEntityFromDatabase.setLibroFisico(oPrestamoEntity.getLibroFisico());
            }
            if (oPrestamoEntity.getInicioPrestamo() != null) {
                oPrestamoEntityFromDatabase.setInicioPrestamo(oPrestamoEntity.getInicioPrestamo());
            }
            if (oPrestamoEntity.getFinPrestamo() != null) {
                oPrestamoEntityFromDatabase.setFinPrestamo(oPrestamoEntity.getFinPrestamo());
            }
            if (oPrestamoEntity.getEstado() != null) {
                oPrestamoEntityFromDatabase.setEstado(oPrestamoEntity.getEstado());
            }
            if (oPrestamoEntity.getOpinion() != null) {
                oPrestamoEntityFromDatabase.setOpinion(oPrestamoEntity.getOpinion());
            }
            return oPrestamoRepository.save(oPrestamoEntityFromDatabase);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para modificar el Prestamo");
        }
    }*/

    /*public Long deleteAll() {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para borrar todos los Prestamos");
        } else {
            oPrestamoRepository.deleteAll();
            return this.count();
        }
    }*/
}
