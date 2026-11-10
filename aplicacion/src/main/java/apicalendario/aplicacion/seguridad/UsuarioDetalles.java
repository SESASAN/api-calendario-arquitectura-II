package apicalendario.aplicacion.seguridad;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import apicalendario.dominio.entidades.Usuario;

public class UsuarioDetalles implements UserDetails {

    private final String nombreUsuario;
    private final String clave;
    private final List<GrantedAuthority> permisos;

    public UsuarioDetalles(Usuario usuario) {
        nombreUsuario = usuario.getUsuario();
        clave = usuario.getClave();
        permisos = usuario.getRoles() == null || usuario.getRoles().isBlank()
                ? List.of()
                : Arrays.stream(usuario.getRoles().split(","))
                        .map(String::trim)
                        .<GrantedAuthority>map(SimpleGrantedAuthority::new)
                        .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permisos;
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }
}
