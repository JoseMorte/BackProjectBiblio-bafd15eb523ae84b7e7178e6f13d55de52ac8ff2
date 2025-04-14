package net.ausiasmarch.projectBiblio.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.ausiasmarch.projectBiblio.entity.UsuarioEntity;
import net.ausiasmarch.projectBiblio.exception.NotAcceptableException;
import net.ausiasmarch.projectBiblio.exception.ResourceNotFoundException;
import net.ausiasmarch.projectBiblio.exception.ResourceNotModifiedException;
import net.ausiasmarch.projectBiblio.exception.UnauthorizedAccessException;
import net.ausiasmarch.projectBiblio.repository.UsuarioRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@AllArgsConstructor
public class UsuarioService {

    HttpServletRequest oHttpServletRequest;

    UsuarioRepository oUsuarioRepository;

    AuthService oAuthService;

    RandomService oRandomService;

    TipoUsuarioService oTipoUsuarioService;

    private String[] arrNombres;

    private String[] arrApellidos;

    public Long randomCreate(Long cantidad) {

        arrNombres = new String[] { "Pepe", "Laura", "Ignacio", "Maria", "Lorenzo", "Carmen", "Rosa", "Paco", "Luis",
                "Ana", "Rafa", "Manolo", "Lucia", "Marta", "Sara", "Rocio" };

        arrApellidos = new String[] { "Sancho", "Gomez", "Pérez", "Rodriguez", "Garcia", "Fernandez", "Lopez",
                "Martinez", "Sanchez", "Gonzalez", "Gimenez", "Feliu", "Gonzalez", "Hermoso", "Vidal", "Escriche",
                "Moreno" };

        for (int i = 0; i < cantidad; i++) {
            UsuarioEntity oUsuarioEntity = new UsuarioEntity();
            oUsuarioEntity.setNombre(arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)]);
            oUsuarioEntity.setApellido1(arrApellidos[oRandomService.getRandomInt(0, arrApellidos.length - 1)]);
            oUsuarioEntity.setApellido2(arrApellidos[oRandomService.getRandomInt(0, arrApellidos.length - 1)]);
            oUsuarioEntity.setEmail(
                    "email" + oUsuarioEntity.getNombre() + oRandomService.getRandomInt(999, 9999) + "@gmail.com");
            oUsuarioEntity.setPassword("");
            oUsuarioEntity.setTipousuario(oTipoUsuarioService.randomSelection());
            oUsuarioRepository.save(oUsuarioEntity);
        }
        return oUsuarioRepository.count();
    }

    public UsuarioEntity getByEmail(String email) {
        UsuarioEntity oUsuarioEntity = oUsuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con email " + email + " no existe"));
        if (oAuthService.isContableWithItsOwnData(oUsuarioEntity.getId()) || oAuthService.isAdmin()
                || oAuthService.isAuditorWithItsOwnData(oUsuarioEntity.getId())) {
            return oUsuarioEntity;
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver el usuario");
        }
    }

    public UsuarioEntity get(Long id) {
        if (oAuthService.isContableWithItsOwnData(id) || oAuthService.isAdmin()
                || oAuthService.isAuditorWithItsOwnData(id)) {
            Optional<UsuarioEntity> usuario = oUsuarioRepository.findById(id);
            if (usuario.isPresent()) {
                return usuario.get();
            } else {
                throw new EntityNotFoundException("Usuario no encontrado con ID: " + id);
            }
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver el usuario");
        }
    }

    public Page<UsuarioEntity> getPage(Pageable oPageable, Optional<String> filter) {
        if (oAuthService.isAdmin()) {
            if (filter.isPresent()) {
                return oUsuarioRepository
                        .findByNombreContainingOrApellido1ContainingOrApellido2ContainingOrEmailContaining(
                                filter.get(), filter.get(), filter.get(), filter.get(),
                                oPageable);
            } else {
                return oUsuarioRepository.findAll(oPageable);
            }
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver los usuarios");
        }
    }

    public Page<UsuarioEntity> getPageXTipoUsuario(Optional<Long> idTipoUsuario, Pageable oPageable) {
        if (oAuthService.isAdmin()) {
            if (idTipoUsuario.isPresent()) {
                return oUsuarioRepository.findByTipousuarioId(idTipoUsuario.get(), oPageable);
            } else {
                throw new ResourceNotFoundException("TipoUsuario no encontrada");
            }
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver los Usuarios");
        }

    }

    public Long count() {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para contar los usuarios");
        } else {
            return oUsuarioRepository.count();

        }
    }

    public Long delete(Long id) {
        if (oAuthService.isAdmin()) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
            if (oUsuarioEntity.getPrestamos() != 0) {
                throw new NotAcceptableException("No se puede borrar el usuario porque tiene préstamos realizados");
            }
            oUsuarioRepository.deleteById(id);
            return 1L;
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para borrar el usuario");
        }
    }

    public UsuarioEntity create(UsuarioEntity oUsuarioEntity) {
        if (oAuthService.isAdmin()) {
            return oUsuarioRepository.save(oUsuarioEntity);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para crear el usuario");
        }
    }

    public UsuarioEntity register(UsuarioEntity oUsuarioEntity) {
        if (oUsuarioRepository.findByEmail(oUsuarioEntity.getEmail()).isPresent()) {
            throw new ResourceNotModifiedException("El email ya existe en la base de datos");
        } else if ( oUsuarioEntity.getNombre() == null || oUsuarioEntity.getApellido1() == null
                || oUsuarioEntity.getEmail() == null || oUsuarioEntity.getPassword() == null) {
            throw new ResourceNotModifiedException("Faltan datos obligatorios para el registro");
        } else if (oUsuarioEntity.getTipousuario() == null || oUsuarioEntity.getTipousuario().getId() == 1L ) {
            throw new ResourceNotModifiedException("nO PUEDES TENER PERMISOS DE ASADMINISTRADOR EN EL REGISTRO");

        }

        return oUsuarioRepository.save(oUsuarioEntity); 
    }

    public UsuarioEntity update(UsuarioEntity oUsuarioEntity) {
        if (oAuthService.isContableWithItsOwnData(oUsuarioEntity.getId()) || oAuthService.isAdmin()
                || oAuthService.isAuditorWithItsOwnData(oUsuarioEntity.getId())) {
            UsuarioEntity oUsuarioEntityFromDatabase = oUsuarioRepository.findById(oUsuarioEntity.getId()).get();
            if (oUsuarioEntity.getNombre() != null) {
                oUsuarioEntityFromDatabase.setNombre(oUsuarioEntity.getNombre());
            }
            if (oUsuarioEntity.getApellido1() != null) {
                oUsuarioEntityFromDatabase.setApellido1(oUsuarioEntity.getApellido1());
            }
            if (oUsuarioEntity.getApellido2() != null) {
                oUsuarioEntityFromDatabase.setApellido2(oUsuarioEntity.getApellido2());
            }
            if (oUsuarioEntity.getEmail() != null) {
                oUsuarioEntityFromDatabase.setEmail(oUsuarioEntity.getEmail());
            }
            return oUsuarioRepository.save(oUsuarioEntityFromDatabase);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para modificar el usuario");
        }
    }

    public Long deleteAll() {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para borrar todos los usuarios");
        } else {
            oUsuarioRepository.deleteAll();
            return this.count();
        }
    }

    public UsuarioEntity randomSelection() {
        return oUsuarioRepository.findAll()
                .get(oRandomService.getRandomInt(0, (int) (oUsuarioRepository.count() - 1)));
    }
}


