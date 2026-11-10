package apicalendario.aplicacion.seguridad;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroSeguridad extends OncePerRequestFilter {

    private static final String HEADER = "Authorization";
    private static final String PREFIJO = "Bearer ";

    private final SeguridadServicio servicioSeguridad;
    private final UsuarioDetalleServicio servicioUsuario;

    public FiltroSeguridad(SeguridadServicio servicioSeguridad, UsuarioDetalleServicio servicioUsuario) {
        this.servicioSeguridad = servicioSeguridad;
        this.servicioUsuario = servicioUsuario;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String encabezado = request.getHeader(HEADER);
        if (encabezado == null || !encabezado.startsWith(PREFIJO)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = encabezado.substring(PREFIJO.length());
            String nombreUsuario = servicioSeguridad.extraerNombreUsuario(token);
            if (nombreUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails detalles = servicioUsuario.loadUserByUsername(nombreUsuario);
                if (servicioSeguridad.validarToken(token, detalles)) {
                    UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(
                            detalles, null, detalles.getAuthorities());
                    autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(autenticacion);
                }
            }
        } catch (JwtException | IllegalArgumentException | UsernameNotFoundException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token no válido");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
