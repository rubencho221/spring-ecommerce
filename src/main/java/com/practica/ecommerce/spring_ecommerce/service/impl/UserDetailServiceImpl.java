package com.practica.ecommerce.spring_ecommerce.service.impl;

import com.practica.ecommerce.spring_ecommerce.model.Usuario;
import com.practica.ecommerce.spring_ecommerce.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final IUsuarioService usuarioService;
    private final BCryptPasswordEncoder bCrypt;
    private final HttpSession session;

    private final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    public UserDetailServiceImpl(IUsuarioService usuarioService, BCryptPasswordEncoder bCrypt, HttpSession session) {
        this.usuarioService = usuarioService;
        this.bCrypt = bCrypt;
        this.session = session;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Autenticando usuario: {}", username);

        Optional<Usuario> optionalUser = usuarioService.findByEmail(username);
        if (optionalUser.isPresent()) {
            Usuario usuario = optionalUser.get();
            session.setAttribute("idusuario", usuario.getId());

            String role = usuario.getTipo().startsWith("ROLE_") ? usuario.getTipo() : "ROLE_" + usuario.getTipo();

            return User.builder()
                    .username(usuario.getNombre())
                    .password(usuario.getPassword())
                    .roles(role.replace("ROLE_", ""))
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
