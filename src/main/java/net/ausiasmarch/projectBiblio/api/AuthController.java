package net.ausiasmarch.projectBiblio.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.PermitAll;
import net.ausiasmarch.projectBiblio.bean.LogindataBean;
import net.ausiasmarch.projectBiblio.entity.UsuarioEntity;
import net.ausiasmarch.projectBiblio.service.AuthService;
import net.ausiasmarch.projectBiblio.service.OAuth2Service;
import net.ausiasmarch.projectBiblio.service.UsuarioService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService oAuthService;

    @Autowired
    OAuth2Service oAuth2Service;

    @Autowired
    UsuarioService oUsuarioService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LogindataBean oLogindataBean) {
        if (oAuthService.checkLogin(oLogindataBean)) {
            return ResponseEntity.ok("\"" + oAuthService.getToken(oLogindataBean.getEmail()) + "\"");
        } else {
            return ResponseEntity.status(401).body("\"" + "Error de autenticación" + "\"");
        }
    }

    /*@PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> request) {
        String googleToken = request.get("token");
        return ResponseEntity.ok(oAuth2Service.loginWithGoogle(googleToken));
    }*/

    @PostMapping("/google") 
    public ResponseEntity<?> googleAuth(@RequestBody Map<String, String> request) {
    String googleToken = request.get("token");
    // Verifica el token con Google API
    // Genera tu JWT
    return ResponseEntity.ok().body(Map.of("token", oAuth2Service.loginWithGoogle(googleToken)));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioEntity> register(@RequestBody UsuarioEntity usuario) {
        if (usuario.getTipousuario() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        UsuarioEntity savedUsuario = oUsuarioService.register(usuario);
        return ResponseEntity.ok(savedUsuario);
        
    }

}
