package net.ausiasmarch.projectBiblio.api;

import org.springframework.beans.factory.annotation.Autowired;
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

import net.ausiasmarch.projectBiblio.entity.LibroGenericoEntity;
import net.ausiasmarch.projectBiblio.service.LibroGenericoService;
import java.util.Optional;


import org.springframework.data.domain.Page;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/libroGenerico")
public class LibroGenericoController {

    @Autowired
    LibroGenericoService oLibroGenericoService;

    @GetMapping("/{id}")
    public ResponseEntity<LibroGenericoEntity> getLibroGenericoById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(oLibroGenericoService.get(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<LibroGenericoEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<LibroGenericoEntity>>(oLibroGenericoService.getPage(oPageable), HttpStatus.OK);
    }

    @GetMapping("/xtipoLibro/{id}")
    public ResponseEntity<Page<LibroGenericoEntity>> getPageXTipoLibro(
            Pageable oPageable,
            @PathVariable Optional<Long> id,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<LibroGenericoEntity>>(oLibroGenericoService.getPageXTipoLibro(id,oPageable), HttpStatus.OK);
    }


    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oLibroGenericoService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oLibroGenericoService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<LibroGenericoEntity> createLibroGenerico(@RequestBody LibroGenericoEntity LibroGenerico) {
        LibroGenericoEntity savedLibroGenerico = oLibroGenericoService.create(LibroGenerico);
        return ResponseEntity.ok(savedLibroGenerico);
    }
    
    

    @PutMapping("")
    public ResponseEntity<LibroGenericoEntity> update(@RequestBody LibroGenericoEntity oLibroGenericoEntity) {
        return new ResponseEntity<LibroGenericoEntity>(oLibroGenericoService.update(oLibroGenericoEntity), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return new ResponseEntity<Long>(oLibroGenericoService.deleteAll(), HttpStatus.OK);
    }


}
