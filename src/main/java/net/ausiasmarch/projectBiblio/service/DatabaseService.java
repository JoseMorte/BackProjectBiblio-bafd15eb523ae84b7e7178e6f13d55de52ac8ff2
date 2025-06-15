package net.ausiasmarch.projectBiblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DatabaseService {

    @Autowired
    private UsuarioService oUsuarioService;

    @Autowired
    private LibroFisicoService oLibroFisicoService;

    @Autowired
    private LibroGenericoService oLibroGenericoService;

    @Autowired
    private PrestamoService oPrestamoService;

    public Long fillDb(){

        oLibroGenericoService.randomCreate(10L);
        oLibroFisicoService.randomCreate(10L);

        return 0L; 
    }

    
}
