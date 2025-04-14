package net.ausiasmarch.projectBiblio.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TipoLibro")
public class TipoLibroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String genero;

    @OneToMany(mappedBy = "tipolibro", fetch = FetchType.LAZY)
    private List<LibroGenericoEntity> libros = new java.util.ArrayList<>();;
    
    public int getLibros() {
        return libros.size();
    }
    
}