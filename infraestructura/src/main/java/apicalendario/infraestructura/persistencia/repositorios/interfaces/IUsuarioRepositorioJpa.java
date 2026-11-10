package apicalendario.infraestructura.persistencia.repositorios.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apicalendario.infraestructura.persistencia.entidades.UsuarioEntidad;

@Repository
public interface IUsuarioRepositorioJpa extends JpaRepository<UsuarioEntidad, Long> {

    Optional<UsuarioEntidad> findByUsuario(String usuario);

    boolean existsByUsuario(String usuario);
}
