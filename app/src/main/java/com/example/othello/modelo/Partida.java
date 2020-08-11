package com.example.othello.modelo;

public class Partida {
    public static int TURNO1 = 1;
    public static int TURNO2 = 2;
    Tablero obT;
    private int turno;

    public Partida(Tablero obT) {
        this.obT = obT;
        this.turno = TURNO1;
    }

    public void posiblesFichas(){
        if (this.turno == TURNO1){

        }
    }
}
