package net.ausiasmarch.projectBiblio.service;

import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.ausiasmarch.projectBiblio.entity.LibroFisicoEntity;
import net.ausiasmarch.projectBiblio.entity.LibroGenericoEntity;
import net.ausiasmarch.projectBiblio.exception.NotAcceptableException;
import net.ausiasmarch.projectBiblio.exception.ResourceNotFoundException;
import net.ausiasmarch.projectBiblio.exception.UnauthorizedAccessException;
import net.ausiasmarch.projectBiblio.repository.LibroGenericoRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@AllArgsConstructor
public class LibroGenericoService {

    HttpServletRequest oHttpServletRequest;

    LibroGenericoRepository oLibroGenericoRepository;

    AuthService oAuthService;

    RandomService oRandomService;

    TipoLibroService oTipoLibroService;

    private String[] arrayTitulos;
    private String[] arrayAutores;
    private String[] arrayEditorial;
    private String[] arrayDescripcion;

    private String IsbnRandom() {
        return "978-" + oRandomService.getRandomInt(0, 999) + "-" + oRandomService.getRandomInt(0, 99999) + "-" + oRandomService.getRandomInt(0, 999) + "-" + oRandomService.getRandomInt(0, 9);
    }

    public Long randomCreate(Long cantidad) {

        arrayTitulos= new String[]{"El Quijote", "La Celestina", "El Lazarillo de Tormes", "La Regenta", "Fortunata y Jacinta", "La Colmena", "El Jarama", "El camino", "La familia de Pascual Duarte", "Nada", "La sombra del viento", "Los pilares de la tierra", "El nombre de la rosa", "Cien años de soledad", "Rayuela", "Pedro Páramo", "Don Juan Tenorio", "La casa de Bernarda Alba", "Bodas de sangre", "Yerma"};
        arrayAutores= new String[]{"Miguel de Cervantes", "Fernando de Rojas", "Anónimo", "Leopoldo Alas Clarín", "Benito Pérez Galdós", "Camilo José Cela", "Rafael Sánchez Ferlosio", "Miguel Delibes", "Camilo José Cela", "Carmen Laforet", "Carlos Ruiz Zafón", "Ken Follet", "Umberto Eco", "Gabriel García Márquez", "Julio Cortázar", "Juan Rulfo", "Tirso de Molina", "Federico García Lorca", "Federico García Lorca", "Federico García Lorca"};
        arrayEditorial= new String[]{"Alfaguara", "Anaya", "Santillana", "SM", "Vicens Vives", "Oxford", "Cambridge", "Pearson", "Macmillan", "Longman", "Richmond", "Express Publishing", "McGraw Hill", "Hachette", "Larousse", "Difusión", "Edelsa", "Edebe", "Burlington", "Cornelsen"};
        arrayDescripcion= new String[]{"Libro de aventuras", "Libro de amor", "Libro de misterio", "Libro de terror", "Libro de ciencia ficción", "Libro de fantasía", "Libro de intriga", "Libro de humor", "Libro de viajes", "Libro de historia", "Libro de biografía", "Libro de ensayo", "Libro de poesía", "Libro de teatro", "Libro de novela", "Libro de cuento", "Libro de relato", "Libro de cómic", "Libro de manga", "Libro de novela gráfica"};

        for (int i = 0; i < cantidad; i++) {
            LibroGenericoEntity oLibroGenericoEntity = new LibroGenericoEntity();
            oLibroGenericoEntity.setTitulo(arrayTitulos[oRandomService.getRandomInt(0, arrayTitulos.length - 1)]);
            oLibroGenericoEntity.setAutor(arrayAutores[oRandomService.getRandomInt(0, arrayAutores.length - 1)]);
            oLibroGenericoEntity.setEditorial(arrayEditorial[oRandomService.getRandomInt(0, arrayEditorial.length - 1)]);
            oLibroGenericoEntity.setIsbn("ISBN" + IsbnRandom());
            oLibroGenericoEntity.setAnio(Long.valueOf(oRandomService.getRandomInt(1900, LocalDateTime.now().getYear())));
            oLibroGenericoEntity.setDescripcion(arrayDescripcion[oRandomService.getRandomInt(0, arrayDescripcion.length - 1)]);
            oLibroGenericoEntity.setTipolibro(oTipoLibroService.randomSelection());
            oLibroGenericoRepository.save(oLibroGenericoEntity);
        }
        return oLibroGenericoRepository.count();
    }

    public LibroGenericoEntity get(Long id) {
        if (oAuthService.isContableWithItsOwnData(id) || oAuthService.isAdmin()
                || oAuthService.isAuditorWithItsOwnData(id)) {
            Optional<LibroGenericoEntity> LibroGenerico = oLibroGenericoRepository.findById(id);
            if (LibroGenerico.isPresent()) {
                return LibroGenerico.get();
            } else {
                throw new EntityNotFoundException("LibroGenerico no encontrado con ID: " + id);
            }
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver el LibroGenerico");
        }
    }

    public Page<LibroGenericoEntity> getPage(Pageable oPageable) {
        if (oAuthService.isAdmin()) {
                return oLibroGenericoRepository.findAll(oPageable);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver los LibroGenericos");
        }

    }

    public Page<LibroGenericoEntity> getPageXTipoLibro(Optional<Long> idTipoLibro, Pageable oPageable) {
        if (oAuthService.isAdmin()) {
            if (idTipoLibro.isPresent()) {
            return oLibroGenericoRepository.findByTipolibroId(idTipoLibro.get(),oPageable);
            }else{
                 throw new ResourceNotFoundException("TipoLibro no encontrada");
            }
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para ver los LibroGenericos");
        }

    }

    public Long count() {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para contar los LibroGenericos");
        } else {
            return oLibroGenericoRepository.count();
        }
    }

    public Long delete(Long id) {
        if (oAuthService.isAdmin()) {
            LibroGenericoEntity oLibroGenericoEntity = oLibroGenericoRepository.findById(id).get();
            if (oLibroGenericoEntity.getLibrosFisicos() != 0) {
                throw new NotAcceptableException("No se puede borrar el LibroGenerico porque tiene copias activos");
            }
            oLibroGenericoRepository.deleteById(id);
            return 1L;
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para borrar el LibroGenerico");
        }
    }

    public LibroGenericoEntity create(LibroGenericoEntity oLibroGenericoEntity) {
        if (oAuthService.isAdmin()) {
            return oLibroGenericoRepository.save(oLibroGenericoEntity);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para crear el LibroGenerico");
        }
    }

    public LibroGenericoEntity update(LibroGenericoEntity oLibroGenericoEntity) {
        if (oAuthService.isContableWithItsOwnData(oLibroGenericoEntity.getId()) || oAuthService.isAdmin()
                || oAuthService.isAuditorWithItsOwnData(oLibroGenericoEntity.getId())) {
            LibroGenericoEntity oLibroGenericoEntityFromDatabase = oLibroGenericoRepository.findById(oLibroGenericoEntity.getId()).get();
            if (oLibroGenericoEntity.getTitulo() != null) {
                oLibroGenericoEntityFromDatabase.setTitulo(oLibroGenericoEntity.getTitulo());
            }
            if (oLibroGenericoEntity.getAutor() != null) {
                oLibroGenericoEntityFromDatabase.setAutor(oLibroGenericoEntity.getAutor());
            }
            if (oLibroGenericoEntity.getEditorial() != null) {
                oLibroGenericoEntityFromDatabase.setEditorial(oLibroGenericoEntity.getEditorial());
            }
            if (oLibroGenericoEntity.getIsbn() != null) {
                oLibroGenericoEntityFromDatabase.setIsbn(oLibroGenericoEntity.getIsbn());
            }
            if (oLibroGenericoEntity.getAnio() != null) {
                oLibroGenericoEntityFromDatabase.setAnio(oLibroGenericoEntity.getAnio());
            }
            if (oLibroGenericoEntity.getDescripcion() != null) {
                oLibroGenericoEntityFromDatabase.setDescripcion(oLibroGenericoEntity.getDescripcion());
            }
            if (oLibroGenericoEntity.getTipolibro() != null) {
                oLibroGenericoEntityFromDatabase.setTipolibro(oLibroGenericoEntity.getTipolibro());
            }
            return oLibroGenericoRepository.save(oLibroGenericoEntityFromDatabase);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para modificar el LibroGenerico");
        }
    }

    public Long deleteAll() {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para borrar todos los LibroGenericos");
        } else {
            oLibroGenericoRepository.deleteAll();
            return this.count();
        }
    }

    public LibroGenericoEntity randomSelection() {
        return oLibroGenericoRepository.findAll()
                .get(oRandomService.getRandomInt(0, (int) (oLibroGenericoRepository.count() - 1)));
    }
}
