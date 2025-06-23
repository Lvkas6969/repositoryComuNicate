package comuNIcate.comuNIcate.repository;

import comuNIcate.comuNIcate.model.Comuna;
import comuNIcate.comuNIcate.model.Region;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {
    Optional<Comuna> findByNombreComuna(String nombreComuna);
    List<Comuna> findByRegion(Region region);
    Optional<Comuna> findByIdYNombreComuna(Integer idComuna, String nombreComuna);
    @Query(
            value =
        """
        SELECT  com.id_comuna AS idComuna,
                com.nombre_comuna AS nombreComuna,
                COUNT(p.id_publicacion) AS total_publicaciones
        FROM COMUNA com
        JOIN usuario usu ON usu.id_usuario = com.id_usuario
        JOIN publicacion p ON p.id_usuario = usu.id_usuario
        GROUP BY p.id_publicaciones, com.nombre_coomuna;
        """,
            nativeQuery = true
        )

    List<Object[]> findConteoComunaPubli();
    
}
