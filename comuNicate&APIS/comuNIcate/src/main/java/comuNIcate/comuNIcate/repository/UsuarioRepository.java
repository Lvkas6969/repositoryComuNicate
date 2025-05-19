package comuNIcate.comuNIcate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import comuNIcate.comuNIcate.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByCorreoUsuario(String correoUsuario);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

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
}