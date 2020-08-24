package com.example.othello.modelo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Tablero extends Observable {
    public final static int VACIO = 0;
    public final static int BLANCA = 1;
    public final static int NEGRA = 2;
    public final static int POSIBLE=3;
    public final static int AGREGAR = 4;
    public final static int LIMPIAR = 5;
    public final static int ENCERRAR = 6;
    private int [][] tableroLogico;
    private  ArrayList <Ficha> cambio;

    public Tablero() {
        this.tableroLogico = new int[8][8];
    }



    public void inicioTablero(){
        this.tableroLogico[3][3] = BLANCA;
        this.tableroLogico[3][4] = NEGRA;
        this.tableroLogico[4][3] = NEGRA;
        this.tableroLogico[4][4] = BLANCA;
        ArrayList<Object> args = new ArrayList<>();
        int[][] blancas = new int[2][2];
        blancas[0][0] = 3;
        blancas[0][1] = 3;
        blancas[1][0] = 4;
        blancas[1][1] = 4;
        int[][] negras = new int[2][2];
        negras[0][0] = 3;
        negras[0][1] = 4;
        negras[1][0] = 4;
        negras[1][1] = 3;
        args.add(Partida.INICIO);
        args.add(blancas);
        args.add(negras);
        this.setChanged();
        this.notifyObservers(args);
    }

    public void fichasPosibles(int turno){
        int oponente = (turno==1)?2:1;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(this.tableroLogico [i][j] == oponente){
                    if(i-1 >=0){
                        validar(0,-1,i,j, turno,0,1);
                        if(j-1 >=0){
                            validar(-1,-1,i,j,turno,1,1);
                        }
                        if(j+1<=7){
                            validar(1,-1,i,j,turno,-1,1);
                        }
                    }
                    if(i+1 <=7){
                        validar(0,1,i,j,turno,0,-1);
                        if(j-1 >=0){
                            validar(-1,1,i,j,turno,1,-1);
                        }
                        if(j+1<=7){
                            validar(1,1,i,j,turno,-1,-1);
                        }
                    }if(j-1 >= 0){
                        validar(-1,0,i,j, turno,1,0);
                    }
                    if(j+1 <= 7){
                        validar(1,0,i,j, turno,-1,0);
                    }

                }
            }
        }
    }

    public void validar(int x, int y, int i, int j,int turno, int x2, int y2){
        int oponente = (turno==1)?2:1;
            if(this.tableroLogico[i+y][j+x] == turno){
                boolean exist=true;
                while(exist){
                    if(i+y2 >=0 && i+y2 <=7 && j+x2 >=0 && j+x2 <=7){
                        if(this.tableroLogico[i+y2][j+x2] == VACIO){
                            this.tableroLogico[i+y2][j+x2]= POSIBLE;
                            ArrayList <Object> args = new ArrayList<>();
                            args.add(Partida.AGREGAR_POSIBLE);
                            args.add(i+y2);
                            args.add(j+x2);
                            this.setChanged();
                            this.notifyObservers(args);
                            exist=false;
                        }else if(this.tableroLogico[i+y2][j+x2] == turno){
                            exist=false;
                        }else if(this.tableroLogico[i+y2][j+x2] ==oponente){
                            i=i+y2;
                            j=j+x2;
                        }else{
                            exist=false;
                        }

                    } else{
                        exist=false;
                    }
                }


            }
    }

    public void agregarFicha(int turno, int fila, int columna){
        ArrayList <Object> args = new ArrayList<>();
        this.tableroLogico[fila][columna] = turno;
        args.add(AGREGAR);
        args.add(turno);
        args.add(fila);
        args.add(columna);
        this.setChanged();
        this.notifyObservers(args);
    }

    public void limpiaPosibles(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tableroLogico[i][j] == POSIBLE){
                    tableroLogico[i][j] = VACIO;
                    ArrayList <Object> args = new ArrayList<>();
                    args.add(LIMPIAR);
                    args.add(i);
                    args.add(j);
                    this.setChanged();
                    this.notifyObservers(args);
                }
            }
        }
    }


    public int[][] getTableroLogico() {
        return tableroLogico;
    }

    public void setTableroLogico(int[][] tableroLogico) {
        this.tableroLogico = tableroLogico;
    }

    public void encerrar(int i, int j, int turno){
        cambio = new ArrayList<>();
        int oponente = (turno==1)?2:1;
        if(i-1 >=0){
            agregarlista(i, j, 0,-1,turno,oponente);
            if(j-1 >=0){
                agregarlista(i, j, -1,-1,turno,oponente);
            }
            if(j+1<=7){
                agregarlista(i, j, 1,-1,turno,oponente);
            }
        }
        if(i+1 <=7){
                agregarlista(i, j, 0,1,turno,oponente);
            if(j-1 >=0){
                agregarlista(i, j, -1,1,turno,oponente);
            }
            if(j+1<=7){
                agregarlista(i, j, 1,1,turno,oponente);
            }
        }if(j-1 >= 0){
            agregarlista(i, j, -1,0,turno,oponente);
        }
        if(j+1 <= 7){
            agregarlista(i, j, 1,0,turno,oponente);
        }


    }

    public void agregarlista(int i,int j,int x,int y,int turno,int oponente){
         boolean exist= true;
         boolean valido= false;
        while(exist){
            if((i+y >=0 && i+y <=7) && (j+x >=0 && j+x <=7)){
            if(this.tableroLogico[i+y][j+x]== oponente){
                cambio.add(new Ficha(j+x, i+y));
                i=i+y;
                j=j+x;
                valido=true;
            }else if(this.tableroLogico[i+y][j+x]== VACIO){
                cambio.clear();
                exist= false;
                valido=false;
            }else if(this.tableroLogico[i+y][j+x]== turno){
                exist=false;
            }

            }else if((i+y <0 || i+y >7) || (j+x <0 || j+x >7)){
                valido=false;
                cambio.clear();
                exist=false;
            }
        }
        if(valido == true){
            for (Ficha ficha: cambio){
                this.tableroLogico[ficha.getY()][ficha.getX()] = turno;
            }
            ArrayList <Object> args = new ArrayList<>();
            args.add(ENCERRAR);
            args.add(cambio);
            args.add(turno);
            this.setChanged();
            this.notifyObservers(args);
        }


    }

    public int[] totalFichas(){
        int [] res = new int[3];
        int blancas = 0, negras = 0, posibles = 0;
        for (int[] c: this.tableroLogico){
            for (int d: c){
                if (d == Tablero.NEGRA)
                    negras++;
                else if (d == Tablero.BLANCA)
                    blancas++;
                else if (d == Tablero.POSIBLE)
                    posibles++;
            }
        }
        res[0] = blancas;
        res[1] = negras;
        res[2] = posibles;
        return res;
    }


}
