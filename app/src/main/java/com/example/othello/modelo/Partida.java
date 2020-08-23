package com.example.othello.modelo;

public class Partida {
    public static final int INICIO = 0;
    public static final int AGREGAR_POSIBLE = 1;
    public static final int TURNO_NEGRAS = 2;
    public static final int TURNO_BLANCAS = 1;
    private String personId;
    private String username;
    Tablero obT;
    private int turno;

    public Partida(Tablero obT) {
        this.obT = obT;
        this.turno = TURNO_NEGRAS;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
}
