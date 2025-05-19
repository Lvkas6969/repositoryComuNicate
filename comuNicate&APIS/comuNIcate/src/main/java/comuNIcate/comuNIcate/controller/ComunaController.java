package comuNIcate.comuNIcate.controller;

import comuNIcate.comuNIcate.model.Comuna;
import comuNIcate.comuNIcate.services.ComunaService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/comunas")
public class ComunaController {
    @Autowired
    ComunaService comServ;

    @GetMapping
    public ResponseEntity<?> getComunas(){
        List<Comuna> comuna = comServ.getComunas();
        if (comuna.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comServ.getComunas());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getComunasById(@PathVariable Integer id){
        try{
            Comuna comuna = comServ.getComunaById(id);
            return ResponseEntity.ok(comuna);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getComunaByNombre(@PathVariable String nombre){
        try{
            Comuna comuna = comServ.getComunaByNombre(nombre);
            return ResponseEntity.ok(comuna);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<?> saveComuna(@RequestBody Comuna newComuna){
        Comuna comuna = comServ.saveComuna(newComuna);
        return ResponseEntity.ok(comuna);
    }
    @PutMapping("/id/{id}")
    public ResponseEntity<?> putComuna(@RequestBody Comuna newComuna, @PathVariable Integer id){

            Comuna comuna = comServ.updateComuna(id,newComuna);
            return ResponseEntity.ok(comuna);

    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<?> patchComuna(@RequestBody Comuna newComuna, @PathVariable Integer id){

            Comuna comuna = comServ.partialUpdateComuna(id, newComuna);
            return ResponseEntity.ok(comuna);

    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteComuna(@PathVariable Integer id){
        try{
            comServ.deleteComunaById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
