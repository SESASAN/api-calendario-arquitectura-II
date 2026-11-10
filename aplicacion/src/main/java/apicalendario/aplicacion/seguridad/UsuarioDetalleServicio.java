package apicalendario.aplicacion.seguridad;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import apicalendario.core.repositorios.IUsuarioRepositorio;

@Service
public class UsuarioDetalleServicio implements UserDetailsService {

    private final IUsuarioRepositorio repositorio;

    public UsuarioDetalleServicio(IUsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        return repositorio.obtener(nombreUsuario)
                .map(UsuarioDetalles::new)
                .orElseThrow(() -> new UsernameNotFoundException(nombreUsuario));
    }
}
