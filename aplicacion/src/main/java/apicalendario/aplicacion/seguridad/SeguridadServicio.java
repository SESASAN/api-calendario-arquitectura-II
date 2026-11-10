package apicalendario.aplicacion.seguridad;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class SeguridadServicio {

    private final SecretKey claveFirma;
    private final long minutosExpiracion;

    public SeguridadServicio(@Value("${seguridad.jwt.secreto}") String secreto,
            @Value("${seguridad.jwt.minutos-expiracion:30}") long minutosExpiracion) {
        this.claveFirma = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secreto));
        this.minutosExpiracion = minutosExpiracion;
    }

    public String generarToken(String nombreUsuario) {
        long ahora = System.currentTimeMillis();
        return Jwts.builder()
                .subject(nombreUsuario)
                .issuedAt(new Date(ahora))
                .expiration(new Date(ahora + minutosExpiracion * 60_000))
                .signWith(claveFirma, Jwts.SIG.HS256)
                .compact();
    }

    public String extraerNombreUsuario(String token) {
        return extraerDeclaracion(token, Claims::getSubject);
    }

    public Date extraerExpiracion(String token) {
        return extraerDeclaracion(token, Claims::getExpiration);
    }

    public boolean validarToken(String token, UserDetails detallesUsuario) {
        return extraerNombreUsuario(token).equals(detallesUsuario.getUsername())
                && !extraerExpiracion(token).before(new Date());
    }

    private <T> T extraerDeclaracion(String token, Function<Claims, T> resolver) {
        Claims declaraciones = Jwts.parser()
                .verifyWith(claveFirma)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(declaraciones);
    }
}
