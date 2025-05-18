package comuNIcate.comuNIcate.controller;

import comuNIcate.comuNIcate.model.Usuario;
import comuNIcate.comuNIcate.services.UsuarioService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuServ;

    @GetMapping
    public ResponseEntity<?> getUsuario(){
        List<Usuario> usuario = usuServ.getUsuarios();
        if (usuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUsuarioByID(@PathVariable Long id){
        try{
            Usuario usuario = usuServ.getUsuarioById(id);
            return ResponseEntity.ok(usuServ.getUsuarioById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<?> getUsuarioByCorreo(@PathVariable String correo){
        try{
            Usuario usuario = usuServ.getUsuarioByCorreo(correo);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> postUsuario(@RequestBody Usuario newUsuario){
        Usuario usuario = usuServ.saveUsuario(newUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> putUsuario(@RequestBody Usuario newUsuario, @PathVariable Long id){

            Usuario usuario = usuServ.updateUsuario(id, newUsuario);
            return ResponseEntity.ok(usuario);

    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<?> patchUsuario(@RequestBody Usuario newUsuario, @PathVariable Long id){

            Usuario usuario = usuServ.partialUpdateUsuario(id, newUsuario);
            return ResponseEntity.ok(usuario);

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id){
        try{
            usuServ.deleteUsuarioById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
