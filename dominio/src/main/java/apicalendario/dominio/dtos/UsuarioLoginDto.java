package apicalendario.dominio.dtos;

import apicalendario.dominio.entidades.Usuario;

public record UsuarioLoginDto(Usuario usuario, String token) {
}
