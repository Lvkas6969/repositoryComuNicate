package comuNIcate.comuNIcate.services;

import comuNIcate.comuNIcate.model.Comuna;
import comuNIcate.comuNIcate.model.Region;
import comuNIcate.comuNIcate.repository.ComunaRepository;
import comuNIcate.comuNIcate.repository.RegionRepository;
import comuNIcate.comuNIcate.repository.UsuarioRepository;
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
    private RegionRepository regRepo;
    @Autowired
    private ComunaRepository comRepo;
    @Autowired
    private UsuarioRepository usuRepo;

    //Retorna todas las regiones
    public List<Region> findAllRegiones(){
        return regRepo.findAll();
    }

    //Retorna una region por ID
    public Region findRegionById(Integer id){
            return regRepo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Region no encontrada"));
    }

    //Retorna una region por nombre
    public Region findRegionByNombre(String nombreRegion){
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
        List<Comuna> comunas = comRepo.findByRegion(region);
        for(Comuna comuna : comunas){
            usuRepo.deleteByComuna(comuna);
            comRepo.delete(comuna);
        }
        regRepo.deleteById(id);

    }
}