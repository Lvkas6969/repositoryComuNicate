package comuNIcate.comuNIcate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import comuNIcate.comuNIcate.model.Publicacion;
import comuNIcate.comuNIcate.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    Optional<List<Publicacion>> findByfkUsuario_idUsuario(Long idUsuario);
    void deleteByUsuario(Usuario usuario);
    @Query(
            value =
        """
        SELECT
            pub.descripcion_publicacion AS descripcion,
            usu.nombre_usuario AS nombreUsuario,
            CONCAT(pub.ubicacion_latitud_publicacion, ', ', pub.ubicacion_longitud_publicacion) AS ubicacion,
            com.nombre_comuna AS nombreComuna,
            reg.nombre_region AS nombreRegion
        FROM publicacion pub
        JOIN usuario usu ON pub.id_usuario = usu.id_usuario
        JOIN comuna com ON usu.id_comuna = com.id_comuna
        JOIN region reg ON reg.id_region = com.id_region
        """,
            nativeQuery = true
    )
    List<Object[]> findDetallesPublicacion();
}
