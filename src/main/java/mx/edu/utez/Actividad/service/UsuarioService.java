package mx.edu.utez.Actividad.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.Actividad.exception.NotFoundException;
import mx.edu.utez.Actividad.model.Usuario;
import mx.edu.utez.Actividad.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;


    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }


    @Transactional
    public void desactivarUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("El usuario con ID " + usuarioId + " no existe."));


        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}