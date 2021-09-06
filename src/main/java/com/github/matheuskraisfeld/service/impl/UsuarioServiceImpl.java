package com.github.matheuskraisfeld.service.impl;

import com.github.matheuskraisfeld.domain.entity.Usuario;
import com.github.matheuskraisfeld.domain.repository.UsuarioRepository;
import com.github.matheuskraisfeld.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario){
        if(usuario.getLogin() != null && usuario.getSenha() != null){
            return usuarioRepository.save(usuario);
        }
        throw new IllegalArgumentException();
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean verificaSenha = passwordEncoder.matches(usuario.getSenha(), user.getPassword());
        if(verificaSenha){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByLogin(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}
