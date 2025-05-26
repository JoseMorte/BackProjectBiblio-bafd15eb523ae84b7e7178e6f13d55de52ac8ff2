package net.ausiasmarch.projectBiblio.service;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import net.ausiasmarch.projectBiblio.entity.UsuarioEntity;
import net.ausiasmarch.projectBiblio.exception.ResourceNotFoundException;
import net.ausiasmarch.projectBiblio.exception.UnauthorizedAccessException;
import net.ausiasmarch.projectBiblio.filter.GoogleTokenVerifier;

import net.ausiasmarch.projectBiblio.repository.TipoUsuarioRepository;
import net.ausiasmarch.projectBiblio.repository.UsuarioRepository;

@Service
public class OAuth2Service {

   @Autowired
    private GoogleTokenVerifier googleTokenVerifier;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TipoUsuarioRepository tipousuarioRepository;
    
    @Autowired
    private JWTService jwtService;

    public String loginWithGoogle(String googleToken) {
        // 1. Verificar token
        Payload payload = googleTokenVerifier.verify(googleToken);
        if (payload == null || !payload.getEmailVerified()) {
            throw new UnauthorizedAccessException("Token inválido");
        }
        // 2. Obtener o crear usuario
        UsuarioEntity usuario = usuarioRepository.findByEmail(payload.getEmail())
            .orElseGet(() -> crearUsuarioDesdeGoogle(payload));

        // 3. Generar JWT
        Map<String, String> claims = new HashMap<>();
        claims.put("email", usuario.getEmail());
        claims.put("tipoUsuario", String.valueOf(usuario.getTipousuario().getId()));
        
        String jwt = jwtService.generateToken(claims);

        // 4. Construir respuesta
        /*
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("nombre", usuario.getNombre());
        response.put("email", usuario.getEmail());
        */


        return jwt;
    }

    private UsuarioEntity crearUsuarioDesdeGoogle(Payload payload) {
        UsuarioEntity nuevoUsuario = new UsuarioEntity();
        nuevoUsuario.setNombre((String) payload.get("name"));
        nuevoUsuario.setApellido1((String) payload.get("family_name"));
        nuevoUsuario.setApellido2((String) payload.get("given_name"));
        nuevoUsuario.setPassword(null); // No se establece la contraseña al ser autenticación externa
        nuevoUsuario.setEmail(payload.getEmail());
        nuevoUsuario.setTipousuario(
            tipousuarioRepository.findById(2L) // ID del rol por defecto
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"))
        );
        return usuarioRepository.save(nuevoUsuario);
    }
}

