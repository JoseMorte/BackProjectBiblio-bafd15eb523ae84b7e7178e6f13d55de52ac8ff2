package net.ausiasmarch.projectBiblio.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.ausiasmarch.projectBiblio.entity.TipoLibroEntity;
import net.ausiasmarch.projectBiblio.service.TipoLibroService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/tipoLibro")
public class TipoLibroController {
    
 @Autowired
    TipoLibroService oTipoLibroService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oTipoLibroService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<TipoLibroEntity> createTipoLibro(@RequestBody TipoLibroEntity TipoLibro) {
        TipoLibroEntity savedTipoLibro = oTipoLibroService.create(TipoLibro);
        return ResponseEntity.ok(savedTipoLibro);
    }
    

    @PutMapping("")
    public ResponseEntity<TipoLibroEntity> update(@RequestBody TipoLibroEntity oTipoLibroEntity) {
        return new ResponseEntity<TipoLibroEntity>(oTipoLibroService.update(oTipoLibroEntity), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<TipoLibroEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<TipoLibroEntity>>(oTipoLibroService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoLibroEntity> getTipoLibroById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(oTipoLibroService.get(id));
    }


}
