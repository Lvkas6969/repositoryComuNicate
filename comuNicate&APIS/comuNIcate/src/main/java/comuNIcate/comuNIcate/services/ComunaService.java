package comuNIcate.comuNIcate.services;

import comuNIcate.comuNIcate.model.Comuna;
import comuNIcate.comuNIcate.repository.ComunaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ComunaService {
    @Autowired
    ComunaRepository comRepo;

    //Retorna todas las comunas
    public List<Comuna> getComunas(){
        return comRepo.findAll();
    }
    //Retorna una comuna por ID
    public Comuna getComunaById(Integer id){
        return comRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Comuna no encontrada"));
    }
    //Retorna una comuna por nombre
    public Comuna getComunaByNombre(String nombreComuna){
        return comRepo.findByNombreComuna(nombreComuna)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Comuna no encontrada"));
    }
    //Guarda una comuna
    public Comuna saveComuna(Comuna newComuna){
        if(newComuna.getNombreComuna()==null|| newComuna.getNombreComuna().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La comuna debe tener un nombre");
        }
        return comRepo.save(newComuna);
    }
    //Actualizar completamente una comuna
    public Comuna updateComuna(Integer id, Comuna newComuna){
        Comuna comuna = comRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Comuna no encontrada"));
        if(newComuna.getNombreComuna()==null|| newComuna.getNombreComuna().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La comuna debe tener un nombre");
        }
        if(newComuna.getFkRegion()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La comuna debe tener una region");
        }
        comuna.setNombreComuna(newComuna.getNombreComuna());
        comuna.setFkRegion(newComuna.getFkRegion());
        return comRepo.save(comuna);
    }
    //Actualizar parcialmente una comuna
    public Comuna partialUpdateComuna(Integer id, Comuna newComuna){
        Comuna comuna = comRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Comuna no encontrada"));
        if (newComuna.getNombreComuna()!=null && !newComuna.getNombreComuna().isBlank()){
            comuna.setNombreComuna(newComuna.getNombreComuna());
        }
        if(newComuna.getFkRegion()!=null){
            comuna.setFkRegion(newComuna.getFkRegion());
        }
        return comRepo.save(comuna);
    }
    //Borra una comuna
    public void deleteComunaById(Integer id){
        Comuna comuna = comRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Comuna no encontradada"));
        comRepo.deleteById(id);
    }
}