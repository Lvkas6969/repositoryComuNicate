package comuNIcate.comuNIcate.service;

import comuNIcate.comuNIcate.model.Publicacion;
import comuNIcate.comuNIcate.model.Usuario;
import comuNIcate.comuNIcate.repository.PublicacionRepository;
import comuNIcate.comuNIcate.services.PublicacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PublicacionServiceTest {
    @Autowired
    private PublicacionService pubServ;
    @MockBean
    private PublicacionRepository pubRepo;

    private Publicacion createPublicacion(){
        return new Publicacion(1L,
                "Haitianos caminando muy tranquilamente por la calle",
                new Date(),
                45.567,
                -32.356,
                new Usuario());
    }
    //Testeo del metodo findAllPublicaciones
    @Test
    public void testFindAllPublicaciones(){
        when(pubRepo.findAll()).thenReturn(List.of(createPublicacion()));
        List<Publicacion> publicaciones = pubServ.findAllPublicaciones();
        assertNotNull(publicaciones);
        assertEquals(1,publicaciones.size());
    }
    //Testeo del metodo findPublicacionById
    @Test
    public void testFindPublicacionById(){
        long id = 1L;
        when(pubRepo.findById(id)).thenReturn(Optional.of(createPublicacion()));
        Publicacion publicacion = pubServ.findPublicacionById(id);
        assertNotNull(publicacion);
        assertEquals("Haitianos caminando muy tranquilamente por la calle",
                publicacion.getDescripcionPublicacion());
    }
    //Testeo del metodo findPublicacionByIdUsuario
    @Test
    public void testFindPublicacionByIdUsuario(){
        long idUsuario = 1L;
        when(pubRepo.findByfkUsuario_idUsuario(idUsuario)).thenReturn(Optional.of(List.of(createPublicacion())));
        List<Publicacion> publicaciones = pubServ.findPublicacionesByIdUsuario(idUsuario);
        assertNotNull(publicaciones);
        assertEquals(1,publicaciones.size());
    }
    //Testeo del metodo findDetallesPublicacion
    @Test
    public void testFindDetallesPublicaciones(){
        Object[] row1 = {"Holaaaaa, me asaltaron al lado de mi casa:(",
                "JuanitoAlQueRoban",
                "3.45, 6.54",
                "Estacion Central",
                "Santiago"};
        Object[] row2 = {"Holaaaaa, asalte a alguien aca al lado de mi casa:)",
                "BrayanElQueRoba",
                "3.45, 6.54",
                "Estacion Central",
                "Santiago"};
        List<Object[]> results = List.of(row1,row2);
        when(pubRepo.findDetallesPublicacion()).thenReturn(results);

        List<Map<String, Object>> result = pubServ.findDetallesPublicaciones();
        assertNotNull(result);
        assertEquals(2,result.size());
    }
    //Testeo del metodo savePublicacion
    @Test
    public void testSavePublicacion(){
        Publicacion publicacion = createPublicacion();
        when(pubRepo.save(publicacion)).thenReturn(publicacion);
        Publicacion savedPublicacion = pubServ.savePublicacion(publicacion);
        assertNotNull(savedPublicacion);
        assertEquals("Haitianos caminando muy tranquilamente por la calle",
                savedPublicacion.getDescripcionPublicacion());
    }
    //Testeo del metodo updatePublicacion
    @Test
    public void testUpdatePublicacion(){
        long id = 1L;
        Publicacion publicacion = createPublicacion();
        Publicacion patchPublicacion = new Publicacion();
        patchPublicacion.setDescripcionPublicacion("Robaron otra vez a juanito");
        patchPublicacion.setFechaHoraPublicacion(new Date());
        patchPublicacion.setUbicacionLongitudPublicacion(6.43);
        patchPublicacion.setUbicacionLatitudPublicacion(3.45);
        patchPublicacion.setFkUsuario(new Usuario());

        when(pubRepo.findById(id)).thenReturn(Optional.of(publicacion));
        when(pubRepo.save(any(Publicacion.class))).thenReturn(publicacion);

        Publicacion updatedPublicacion = pubServ.updatePublicacion(id, patchPublicacion);
        assertNotNull(updatedPublicacion);
        assertEquals("Robaron otra vez a juanito",updatedPublicacion.getDescripcionPublicacion());
    }
    //Testeo del metodo partialUpdatePublicacion
    @Test
    public void testPartialUpdatePublicacion(){
        long id = 1L;
        Publicacion publicacion = createPublicacion();
        Publicacion patchPublicacion = createPublicacion();
        patchPublicacion.setDescripcionPublicacion("Robaron otra vez a juanito");


        when(pubRepo.findById(id)).thenReturn(Optional.of(publicacion));
        when(pubRepo.save(any(Publicacion.class))).thenReturn(publicacion);

        Publicacion updatedPublicacion = pubServ.updatePublicacion(id, patchPublicacion);
        assertNotNull(updatedPublicacion);
        assertEquals("Robaron otra vez a juanito",updatedPublicacion.getDescripcionPublicacion());
    }
    //Testeo del metodo deletePublicacion
    @Test
    public void testDeletePublicacion(){
        long id = 1L;
        doNothing().when(pubRepo).deleteById(id);
        when(pubRepo.findById(id)).thenReturn(Optional.of(createPublicacion()));
        pubServ.deletePublicacionById(id);
        verify(pubRepo,times(1)).deleteById(id);
    }

}
