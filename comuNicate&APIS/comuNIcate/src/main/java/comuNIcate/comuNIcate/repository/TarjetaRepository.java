package comuNIcate.comuNIcate.repository;

import comuNIcate.comuNIcate.model.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
    List<Tarjeta> findByFkUsuario_idUsuario(Long IdUsuario);
}
