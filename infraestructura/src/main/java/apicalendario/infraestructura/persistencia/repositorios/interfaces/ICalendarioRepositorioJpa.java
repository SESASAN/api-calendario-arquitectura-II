package apicalendario.infraestructura.persistencia.repositorios.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import apicalendario.infraestructura.persistencia.entidades.CalendarioEntidad;

@Repository
public interface ICalendarioRepositorioJpa extends JpaRepository<CalendarioEntidad, Long> {

    List<CalendarioEntidad> findByFechaBetweenOrderByFechaAsc(LocalDate desde, LocalDate hasta);

    Optional<CalendarioEntidad> findByFecha(LocalDate fecha);

    boolean existsByTipoId(Long idTipo);

    @Modifying
    @Query("DELETE FROM CalendarioEntidad c WHERE c.fecha BETWEEN ?1 AND ?2")
    int eliminarEntre(LocalDate desde, LocalDate hasta);
}
