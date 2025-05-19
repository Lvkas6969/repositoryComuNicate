package comuNIcate.comuNIcate.controller;

import comuNIcate.comuNIcate.model.Tarjeta;
import comuNIcate.comuNIcate.services.TarjetaService;
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
@RequestMapping("/api/v1/tarjetas")
public class TarjetaController {
    @Autowired
    TarjetaService tarServ;

    @GetMapping
    public ResponseEntity<?> getTarjetas(){
        List<Tarjeta> tarjeta = tarServ.getTarjetas();
        if (tarjeta.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tarjeta);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getTarjetaById(@PathVariable Long id){
        try{
            Tarjeta tarjeta = tarServ.getTarjetaById(id);
            return ResponseEntity.ok(tarjeta);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();

        }
    }

    @GetMapping("/idUser/{id}")
    public ResponseEntity<?> getTarjetasByUsuario(@PathVariable Long id){
        try{
            List<Tarjeta> tarjeta = tarServ.getTarjetaByUsuario(id);
            return ResponseEntity.ok(tarjeta);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> postTarjeta(@RequestBody Tarjeta newTarjeta){
            tarServ.saveTarjeta(newTarjeta);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> putTarjeta(@RequestBody Tarjeta newTarjeta,@PathVariable Long id){

            Tarjeta tarjeta = tarServ.updateTarjeta(id,newTarjeta);
            return ResponseEntity.ok(tarjeta);

    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<?> patchTarjeta(@RequestBody Tarjeta newTarjeta,@PathVariable Long id){

            Tarjeta tarjeta = tarServ.PartialupdateTarjeta(id, newTarjeta);
            return ResponseEntity.ok(tarjeta);


    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteTarjeta(@PathVariable Long id){
        try{
            tarServ.deleteTarjetaById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }


    }
}
