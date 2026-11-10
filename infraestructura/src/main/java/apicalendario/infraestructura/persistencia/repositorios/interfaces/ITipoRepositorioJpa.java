package apicalendario.infraestructura.persistencia.repositorios.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apicalendario.infraestructura.persistencia.entidades.TipoEntidad;

@Repository
public interface ITipoRepositorioJpa extends JpaRepository<TipoEntidad, Long> {

    List<TipoEntidad> findByTipoContainingIgnoreCaseOrderByIdAsc(String tipo);

    boolean existsByTipoIgnoreCaseAndIdNot(String tipo, Long id);
}
