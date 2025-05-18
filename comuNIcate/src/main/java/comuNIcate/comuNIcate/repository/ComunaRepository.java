package comuNIcate.comuNIcate.repository;

import comuNIcate.comuNIcate.model.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {
    Optional<Comuna> findByNombreComuna(String nombreComuna);
}
