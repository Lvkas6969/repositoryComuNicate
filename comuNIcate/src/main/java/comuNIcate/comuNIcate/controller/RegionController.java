package comuNIcate.comuNIcate.controller;

import comuNIcate.comuNIcate.model.Region;
import comuNIcate.comuNIcate.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regiones")
public class RegionController {
    @Autowired
    RegionService regServ;
    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<?> getRegiones(){
        List<Region> region = regServ.getRegiones();
        if (region.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regServ.getRegiones());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getRegionById(@PathVariable Integer id){
        try{
            Region region = regServ.getRegionById(id);
            return ResponseEntity.ok(region);
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getRegionByNombre(@PathVariable String nombre){
        try{
            Region region = regServ.getRegionByNombre(nombre);
            return ResponseEntity.ok(region);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<?> postRegion(@RequestBody Region newRegion){
            regServ.saveRegion(newRegion);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PatchMapping("/id/{id}")
    public ResponseEntity<?> putRegion(@PathVariable Integer id, @RequestBody Region newRegion){

            Region region = regServ.updateNombreRegion(id, newRegion);
            return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteRegion(@PathVariable Integer id){
        try{
            regServ.deleteRegion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}