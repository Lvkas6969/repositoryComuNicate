package comuNIcate.comuNIcate.repository;

import comuNIcate.comuNIcate.model.Tarjeta;
import comuNIcate.comuNIcate.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, String> {
    List<Tarjeta> findByFkUsuario_idUsuario(Long IdUsuario);
    void deleteByUsuario(Usuario usuario);
    Optional<Tarjeta> findNombreYFechVencimiento(String nombreTitular, Date fechaVencimientoTarjeta);
}
