package com.example.othello.modelo;

import java.util.ArrayList;
import java.util.Observable;

public class Tablero extends Observable {
    public static int VACIO = 0;
    public static int BLANCA = 1;
    public static int NEGRA = 2;
    private int [][] tableroLogico;

    public Tablero() {
        this.tableroLogico = new int[8][8];
    }



    public void inicioTablero(){
        this.tableroLogico[3][3] = BLANCA;
        this.tableroLogico[3][4] = NEGRA;
        this.tableroLogico[4][3] = NEGRA;
        this.tableroLogico[4][4] = BLANCA;
        ArrayList<Object> args = new ArrayList<>();
        int blancas [][] = new int[2][2];
        blancas[0][0] = 3;
        blancas[0][1] = 3;
        blancas[1][0] = 4;
        blancas[1][1] = 4;
        int negras [][] = new int[2][2];
        negras[0][0] = 3;
        negras[0][1] = 4;
        negras[1][0] = 4;
        negras[1][1] = 3;
        args.add(blancas);
        args.add(negras);
        this.setChanged();
        this.notifyObservers(args);
    }

    public void fichasPosibles(int turno){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(this.tableroLogico [i][j] != turno && this.tableroLogico [i][j] != VACIO){
                    if(this.tableroLogico[i - 1][j - 1])
                }
            }
        }
    }

}
