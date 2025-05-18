package comuNIcate.comuNIcate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comuNIcate.comuNIcate.model.Publicacion;


import java.util.List;
import java.util.Optional;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    Optional<List<Publicacion>> findByfkUsuario_idUsuario(Long idUsuario);
}
