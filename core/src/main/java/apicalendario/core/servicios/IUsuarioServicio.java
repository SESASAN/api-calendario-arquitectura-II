package apicalendario.core.servicios;

import apicalendario.dominio.dtos.UsuarioLoginDto;

public interface IUsuarioServicio {

    UsuarioLoginDto validar(String nombreUsuario, String clave);
}
