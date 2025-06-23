package comuNIcate.comuNIcate.repository;

import comuNIcate.comuNIcate.model.Region;
import comuNIcate.comuNIcate.model.Tarjeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    Optional<Region> findByNombreRegion(String nombreRegion);
    Optional<Region> findByIdYNombreRegion(Integer idRegion, String nombreRegion);    Optional<Tarjeta> findByUsuarioYPan(String idUsuario, String numeroPan);
    @Query(
        value = 
        """
        SELECT  r.id_region AS idRegion, 
                r.nombre_region AS nombreRegion,
                COUNT(com.id_comuna) as totalComunas,
                COUNT(usu.id_usuarios) as totalUsuarios
        FROM Region r
        JOIN Comuna com ON com.id_region = r.id_region 
        JOIN Usuario usu ON usu.id_comuna_com.id_comuna
        GROUP BY r.id_region, r.nombre_region
        ORDER BY r.id_region ASC;
    """,
        nativeQuery = true
    )
    List<Object[]> findConteoRegionComUsu();
}
