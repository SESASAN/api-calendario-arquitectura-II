package apicalendario.presentacion.configuracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import apicalendario.core.repositorios.IUsuarioRepositorio;
import apicalendario.dominio.entidades.Usuario;

@Component
public class InicializadorUsuario implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(InicializadorUsuario.class);

    private final IUsuarioRepositorio repositorio;
    private final PasswordEncoder codificador;
    private final String usuario;
    private final String clave;

    public InicializadorUsuario(IUsuarioRepositorio repositorio, PasswordEncoder codificador,
            @Value("${seguridad.usuario-inicial.usuario}") String usuario,
            @Value("${seguridad.usuario-inicial.clave}") String clave) {
        this.repositorio = repositorio;
        this.codificador = codificador;
        this.usuario = usuario;
        this.clave = clave;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (usuario.isBlank() || repositorio.existe(usuario)) {
            return;
        }
        repositorio.guardar(new Usuario(0, usuario, "Administrador", codificador.encode(clave), "ADMIN"));
        LOG.info("Usuario inicial '{}' creado", usuario);
    }
}
