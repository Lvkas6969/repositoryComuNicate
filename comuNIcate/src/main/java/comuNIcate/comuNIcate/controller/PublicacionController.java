package comuNIcate.comuNIcate.controller;

import comuNIcate.comuNIcate.model.Publicacion;
import comuNIcate.comuNIcate.model.Tarjeta;
import comuNIcate.comuNIcate.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
@RequestMapping("/api/v1/publicaciones")
public class PublicacionController {
    @Autowired
    PublicacionService pubServ;

    @GetMapping
    public ResponseEntity<?> getPublicaciones(){
        List<Publicacion> publicaciones = pubServ.getPublicaciones();
        if (publicaciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pubServ.getPublicaciones());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPublicacionById(@PathVariable Long id){
        try{
            Publicacion publicacion = pubServ.getPublicacionById(id);
            return ResponseEntity.ok(publicacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/idUser/{id}")
    public ResponseEntity<?> getPublicacionByUsuario(@PathVariable Long id){
        List<Publicacion> publicacion = pubServ.getPublicacionByIdUsuario(id);
        if (publicacion.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
            return ResponseEntity.ok(publicacion);
    }

    @PostMapping
    public ResponseEntity<?> postPublicacion(@RequestBody Publicacion newPublicacion){
            pubServ.savePublicacion(newPublicacion);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> putPublicacion(@RequestBody Publicacion newPublicacion, @PathVariable Long id){

            Publicacion publicacion = pubServ.updatePublicacion(id, newPublicacion);
            return ResponseEntity.ok(publicacion);

    }
    @PatchMapping("/id/{id}")
    public ResponseEntity<?> patchPublicacion(@RequestBody Publicacion newPublicacion, @PathVariable Long id){

            Publicacion publicacion = pubServ.partialUpdatePublicacion(id, newPublicacion);
            return ResponseEntity.ok(publicacion);

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deletePublicacion(@PathVariable Long id){

        try{
           pubServ.deletePublicacionById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
}