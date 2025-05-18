package comuNIcate.comuNIcate.services;

import comuNIcate.comuNIcate.model.Usuario;
import comuNIcate.comuNIcate.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;
import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository useRepo;

    //Retorna todos los usuario
    public List<Usuario> getUsuarios(){
        return useRepo.findAll();
    }
    //Retorna un usuario por ID
    public Usuario getUsuarioById(Long id){
        return useRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado"));
    }
    //Retorna un usuario por Correo
    public Usuario getUsuarioByCorreo(String correoUsuario){
        return useRepo.findByCorreoUsuario(correoUsuario)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado"));
    }
    //Guarda un usuario
    public Usuario saveUsuario(Usuario newUsuario){
        if (newUsuario.getNombreUsuario()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Debe tener un nombre de usuario");
        }if (newUsuario.getFechaUsuario()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Debe tener una fecha de nacimiento");
        }if (newUsuario.getCorreoUsuario()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Debe tener un correo");
        }if (newUsuario.getFkComuna()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Debe tener una comuna");
        }
        return useRepo.save(newUsuario);
    }
    //Actualiza un usuario
    public Usuario updateUsuario(long id,Usuario newUsuario){
        Usuario usuario = useRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado"));
        if (newUsuario.getNombreUsuario()==null||newUsuario.getNombreUsuario().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Debe tener un nombre de usuario");
        }if (newUsuario.getFechaUsuario()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Debe tener una fecha de nacimiento");
        }if (newUsuario.getCorreoUsuario()==null||newUsuario.getCorreoUsuario().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Debe tener un correo");
        }if (newUsuario.getFkComuna()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Debe tener una comuna");
        }
        return useRepo.save(newUsuario);
    }
    //Actualiza parcialmente un usuario
    public Usuario partialUpdateUsuario(long id, Usuario newUsuario){
        Usuario usuario = useRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado"));
        if (newUsuario.getNombreUsuario()!=null&&!newUsuario.getNombreUsuario().isBlank()){
            usuario.setNombreUsuario(newUsuario.getNombreUsuario());
        }if (newUsuario.getFechaUsuario()!=null){
            usuario.setFechaUsuario(newUsuario.getFechaUsuario());
        }if (newUsuario.getCorreoUsuario()!=null&&!newUsuario.getCorreoUsuario().isBlank()){
            usuario.setCorreoUsuario(newUsuario.getCorreoUsuario());
        }if (newUsuario.getFkComuna()!=null){
            usuario.setFkComuna(newUsuario.getFkComuna());
        }
        return useRepo.save(usuario);
    }
    //Borra un usuario
    public void deleteUsuarioById(long id){
        Usuario usuario = useRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado"));
        useRepo.deleteById(id);
    }
}
