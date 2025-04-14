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

import jakarta.annotation.security.PermitAll;
import net.ausiasmarch.projectBiblio.entity.UsuarioEntity;
import net.ausiasmarch.projectBiblio.service.UsuarioService;

import java.util.Optional;


import org.springframework.data.domain.Page;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService oUsuarioService;

    @GetMapping("/byemail/{email}")
    public ResponseEntity<UsuarioEntity> getUsuarioByEmail(@PathVariable(value = "email") String email) {
        return ResponseEntity.ok(oUsuarioService.getByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> getUsuarioById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(oUsuarioService.get(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<UsuarioEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<UsuarioEntity>>(oUsuarioService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/xtipoUsuario/{id}")
    public ResponseEntity<Page<UsuarioEntity>> getPageXTipoUsuario(
            Pageable oPageable,
            @PathVariable Optional<Long> id,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<UsuarioEntity>>(oUsuarioService.getPageXTipoUsuario(id,oPageable), HttpStatus.OK);
    }


    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oUsuarioService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oUsuarioService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<UsuarioEntity> createUsuario(@RequestBody UsuarioEntity usuario) {
        if (usuario.getTipousuario() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        UsuarioEntity savedUsuario = oUsuarioService.create(usuario);
        return ResponseEntity.ok(savedUsuario);
    }

    
    @PutMapping("")
    public ResponseEntity<UsuarioEntity> update(@RequestBody UsuarioEntity oUsuarioEntity) {
        return new ResponseEntity<UsuarioEntity>(oUsuarioService.update(oUsuarioEntity), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return new ResponseEntity<Long>(oUsuarioService.deleteAll(), HttpStatus.OK);
    }


}
