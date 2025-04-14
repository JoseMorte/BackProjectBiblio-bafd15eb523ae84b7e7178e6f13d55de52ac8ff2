package net.ausiasmarch.projectBiblio.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LibroGenerico")
public class LibroGenericoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //PickPortada

    @NotNull
    @Size(min = 3, max = 255)
    private String titulo;

    @NotNull
    @Size(min = 3, max = 255)
    private String autor;

    @NotNull
    @Size(min = 3, max = 255)
    private String editorial;

    @NotNull
    @Size(min = 3, max = 255)
    private String isbn;

    @NotNull
    private Long anio;

    @NotNull
    @Size(min = 3, max = 255)
    private String descripcion;

    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "idTipoLibro")
    private TipoLibroEntity tipolibro;

    @OneToMany(mappedBy = "libroGenerico", fetch = FetchType.LAZY)
    private java.util.List<LibroFisicoEntity> librosFisicos = new java.util.ArrayList<>();

    public int getLibrosFisicos() {
        return librosFisicos.size();
    }

    
   

}