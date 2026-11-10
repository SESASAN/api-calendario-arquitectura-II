package apicalendario.infraestructura.persistencia.mapeadores;

import apicalendario.dominio.entidades.Usuario;
import apicalendario.infraestructura.persistencia.entidades.UsuarioEntidad;

public final class UsuarioMapeador {

    private UsuarioMapeador() {
    }

    public static Usuario aDominio(UsuarioEntidad entidad) {
        return new Usuario(entidad.getId() == null ? 0 : entidad.getId(), entidad.getUsuario(), entidad.getNombre(),
                entidad.getClave(), entidad.getRoles());
    }

    public static UsuarioEntidad aEntidad(Usuario usuario) {
        Long id = usuario.getId() > 0 ? usuario.getId() : null;
        return new UsuarioEntidad(id, usuario.getUsuario(), usuario.getNombre(),
                usuario.getClave(), usuario.getRoles());
    }
}
