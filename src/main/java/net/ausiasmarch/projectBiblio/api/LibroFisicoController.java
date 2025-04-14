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

import net.ausiasmarch.projectBiblio.entity.LibroFisicoEntity;
import net.ausiasmarch.projectBiblio.service.LibroFisicoService;
import java.util.Optional;


import org.springframework.data.domain.Page;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/libroFisico")
public class LibroFisicoController {

    @Autowired
    LibroFisicoService oLibroFisicoService;

    @GetMapping("/{id}")
    public ResponseEntity<LibroFisicoEntity> getLibroFisicoById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(oLibroFisicoService.get(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<LibroFisicoEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> Sfilter) {
        return new ResponseEntity<Page<LibroFisicoEntity>>(oLibroFisicoService.getPage(oPageable), HttpStatus.OK);
    }

    @GetMapping("/libroFisicoDisponible")
    public ResponseEntity<Page<LibroFisicoEntity>> getPageLibroFisicoDisponible(
            Pageable oPageable,
            @RequestParam  Optional<String> Sfilter) {
        return new ResponseEntity<Page<LibroFisicoEntity>>(oLibroFisicoService.getPageLibroFisicoDisponible(oPageable), HttpStatus.OK);
    }

    @GetMapping("xlibroGenerico/{id}")
    public ResponseEntity<Page<LibroFisicoEntity>> getPageXLibroGenerico(
            Pageable oPageable,
            @PathVariable Optional<Long> id
            ) {
        return new ResponseEntity<Page<LibroFisicoEntity>>(oLibroFisicoService.getPageXLibroGenerico(id, oPageable), HttpStatus.OK);
    }


    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oLibroFisicoService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oLibroFisicoService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<LibroFisicoEntity> createLibroFisico(@RequestBody LibroFisicoEntity LibroFisico) {
        LibroFisicoEntity savedLibroFisico = oLibroFisicoService.create(LibroFisico);
        return ResponseEntity.ok(savedLibroFisico);
    }
    
    

    @PutMapping("")
    public ResponseEntity<LibroFisicoEntity> update(@RequestBody LibroFisicoEntity oLibroFisicoEntity) {
        return new ResponseEntity<LibroFisicoEntity>(oLibroFisicoService.update(oLibroFisicoEntity), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return new ResponseEntity<Long>(oLibroFisicoService.deleteAll(), HttpStatus.OK);
    }


}
