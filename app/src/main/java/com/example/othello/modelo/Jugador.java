package com.example.othello.modelo;

public class Jugador {
    private String id;
    private String username;
    private int ficha;

    public Jugador(String id, String username, int ficha) {
        this.id = id;
        this.username = username;
        this.ficha = ficha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFicha() {
        return ficha;
    }

    public void setFicha(int ficha) {
        this.ficha = ficha;
    }
}
