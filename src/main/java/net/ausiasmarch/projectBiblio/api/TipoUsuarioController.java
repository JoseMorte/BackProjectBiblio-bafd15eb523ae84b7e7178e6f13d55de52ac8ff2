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
import net.ausiasmarch.projectBiblio.entity.TipoUsuarioEntity;
import net.ausiasmarch.projectBiblio.service.TipoUsuarioService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/tipoUsuario")
public class TipoUsuarioController {
    
 @Autowired
    TipoUsuarioService oTipoUsuarioService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oTipoUsuarioService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<TipoUsuarioEntity> createTipoUsuario(@RequestBody TipoUsuarioEntity TipoUsuario) {
        TipoUsuarioEntity savedTipoUsuario = oTipoUsuarioService.create(TipoUsuario);
        return ResponseEntity.ok(savedTipoUsuario);
    }

    @PutMapping("")
    public ResponseEntity<TipoUsuarioEntity> update(@RequestBody TipoUsuarioEntity oTipoUsuarioEntity) {
        return new ResponseEntity<TipoUsuarioEntity>(oTipoUsuarioService.update(oTipoUsuarioEntity), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<TipoUsuarioEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<TipoUsuarioEntity>>(oTipoUsuarioService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuarioEntity> getTipoUsuarioById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(oTipoUsuarioService.get(id));
    }


}
