package comuNIcate.comuNIcate.services;

import comuNIcate.comuNIcate.model.Region;
import comuNIcate.comuNIcate.repository.RegionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class RegionService {
    @Autowired
    RegionRepository regRepo;

    //Retorna todas las regiones
    public List<Region> getRegiones(){
        return regRepo.findAll();
    }

    //Retorna una region por ID
    public Region getRegionById(Integer id){
            return regRepo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Region no encontrada"));
    }

    //Retorna una region por nombre
    public Region getRegionByNombre(String nombreRegion){
        return regRepo.findByNombreRegion(nombreRegion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Region no encontrada"));
    }

    //Guarda una region
    public Region saveRegion(Region newRegion){
        if (newRegion.getNombreRegion() == null || newRegion.getNombreRegion().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La region debe tener un nombre");
        }
        return regRepo.save(newRegion);

    }

    //Actualizar nombre de una region
    public Region updateNombreRegion(Integer id, Region newRegion){
        Region region = regRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Region no encontrada"));
        if(newRegion.getNombreRegion()!=null && !newRegion.getNombreRegion().isBlank()){
            region.setNombreRegion(newRegion.getNombreRegion());
        }
        return regRepo.save(region);
    }

    //Borra una region
    public void deleteRegion(Integer id){
        Region region = regRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        regRepo.deleteById(id);
    }
}