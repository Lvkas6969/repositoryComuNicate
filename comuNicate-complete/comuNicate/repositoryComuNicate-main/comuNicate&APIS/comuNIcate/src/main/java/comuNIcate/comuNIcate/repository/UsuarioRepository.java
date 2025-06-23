package comuNIcate.comuNIcate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import comuNIcate.comuNIcate.model.Comuna;
import comuNIcate.comuNIcate.model.Usuario;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByCorreoUsuario(String correoUsuario);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    List<Usuario> findByComuna(Comuna comuna);
    void deleteByComuna(Comuna comuna);
    Optional<Usuario> findByNombreYFechNacimiento(String nombreUsuario, Date fechaNacimientoUsuario);
    @Query(
        value =
        """
            SELECT
                usu.nombre_usuario AS Usuario,
                TIMESTAMPDIFF(YEAR, usu.fecha_nacimiento_usuario, CURDATE()) AS Edad,
                com.nombre_comuna AS Comuna,
                reg.nombre_region AS Region
            FROM usuario usu
            JOIN comuna com ON usu.id_comuna = com.id_comuna
            JOIN region reg ON com.id_region = reg.id_region
        """,
            nativeQuery = true
    )
    List<Object[]> findDetallesUsuario();

    @Query(
        value = 
        """
            SELECT 
                u.nombre_usuario AS nombreUsuario,
                u.correo_usuario AS correoUsuario,
                p.descripcion_publicacion AS descripcionPublicacion,
                p.fecha_hora_publicacion AS fechaHoraPublicacion
            FROM usuario u
            JOIN comuna c ON u.id_comuna = c.id_comuna
            JOIN region r ON c.id_region = r.id_region
            JOIN publicacion p ON p.id_usuario = u.id_usuario
            WHERE p.fecha_hora_publicacion >= :fechaDesde
        """,
        nativeQuery = true
    )
    List<Map<String, Object>> findPublicacionUsuarioDesde(@Param("fechaDesde") String fechaDesde);

}