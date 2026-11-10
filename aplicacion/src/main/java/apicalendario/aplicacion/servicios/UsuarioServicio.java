package apicalendario.aplicacion.servicios;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import apicalendario.aplicacion.seguridad.SeguridadServicio;
import apicalendario.core.excepciones.CredencialesInvalidasException;
import apicalendario.core.repositorios.IUsuarioRepositorio;
import apicalendario.core.servicios.IUsuarioServicio;
import apicalendario.dominio.dtos.UsuarioLoginDto;
import apicalendario.dominio.entidades.Usuario;

@Service
public class UsuarioServicio implements IUsuarioServicio {

    private final IUsuarioRepositorio repositorio;
    private final SeguridadServicio servicioSeguridad;
    private final PasswordEncoder codificador;

    public UsuarioServicio(IUsuarioRepositorio repositorio, SeguridadServicio servicioSeguridad,
            PasswordEncoder codificador) {
        this.repositorio = repositorio;
        this.servicioSeguridad = servicioSeguridad;
        this.codificador = codificador;
    }

    @Override
    public UsuarioLoginDto validar(String nombreUsuario, String clave) {
        Usuario usuario = repositorio.obtener(nombreUsuario)
                .filter(u -> codificador.matches(clave, u.getClave()))
                .orElseThrow(CredencialesInvalidasException::new);
        return new UsuarioLoginDto(usuario, servicioSeguridad.generarToken(usuario.getUsuario()));
    }
}
