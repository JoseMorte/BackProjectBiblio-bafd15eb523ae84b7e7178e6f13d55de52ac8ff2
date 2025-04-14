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

import net.ausiasmarch.projectBiblio.entity.PrestamoEntity;
import net.ausiasmarch.projectBiblio.service.PrestamoService;
import java.util.Optional;


import org.springframework.data.domain.Page;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    PrestamoService oPrestamoService;

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoEntity> getPrestamoById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(oPrestamoService.get(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<PrestamoEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<PrestamoEntity>>(oPrestamoService.getPage(oPageable), HttpStatus.OK);
    }

    @GetMapping("activePrestamos")
    public ResponseEntity<Page<PrestamoEntity>> getPageActivePrestamos(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<PrestamoEntity>>(oPrestamoService.getPageActivePrestamos(oPageable), HttpStatus.OK);
    }

    @GetMapping("xusuario/{id}")
    public ResponseEntity<Page<PrestamoEntity>> getPageXUsuario(
            Pageable oPageable,
            @PathVariable Optional<Long> id
            ) {
        return new ResponseEntity<Page<PrestamoEntity>>(oPrestamoService.getPageXUsuario(id,oPageable), HttpStatus.OK);
    }


    @GetMapping("xlibroFisico/{id}")
    public ResponseEntity<Page<PrestamoEntity>> getPageXLibroFisico(
            Pageable oPageable,
            @PathVariable Optional<Long> id
            ) {
        return new ResponseEntity<Page<PrestamoEntity>>(oPrestamoService.getPageXLibroFisico(id,oPageable), HttpStatus.OK);
    }
    


    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oPrestamoService.count(), HttpStatus.OK);
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oPrestamoService.delete(id), HttpStatus.OK);
    }*/

    @PostMapping("/new")
    public ResponseEntity<PrestamoEntity> createPrestamo(@RequestBody PrestamoEntity Prestamo) {
        PrestamoEntity savedPrestamo = oPrestamoService.create(Prestamo);
        return ResponseEntity.ok(savedPrestamo);
    }
    
    

    /*@PutMapping("")
    public ResponseEntity<PrestamoEntity> update(@RequestBody PrestamoEntity oPrestamoEntity) {
        return new ResponseEntity<PrestamoEntity>(oPrestamoService.update(oPrestamoEntity), HttpStatus.OK);
    }*/

    /*@DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return new ResponseEntity<Long>(oPrestamoService.deleteAll(), HttpStatus.OK);
    }*/


}
