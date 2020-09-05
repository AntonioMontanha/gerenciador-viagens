package com.montanha.factory;

import com.montanha.model.Usuario;

public class UserDataFactory {
    public Usuario aValidAdmin() {
        Usuario user = new Usuario();
        user.setEmail("admin@email.com");
        user.setSenha("654321");
        return user;
    }
}
