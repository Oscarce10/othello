package com.example.othello;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.othello.modelo.Ficha;
import com.example.othello.modelo.Jugador;
import com.example.othello.modelo.Partida;
import com.example.othello.modelo.Tablero;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Juego extends AppCompatActivity implements Observer {
    private GridLayout tableroCont;
    private Partida obP;
    private Tablero obT;
    private DatabaseReference mDatabase;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Jugador yo = getIntent().getParcelableExtra("yo");

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        obT = new Tablero();
        obP = new Partida(obT);
        this.obT.addObserver(this);
        tableroCont = findViewById(R.id.tableroCont);
        ConstraintLayout tiles [][] = new ConstraintLayout[8][8];
        final TextView txtTurno = findViewById(R.id.txtTurno);
        final TextView txtFichasBlancas = findViewById(R.id.txtFichasBlancas);
        final TextView txtFichasNegras = findViewById(R.id.txtFichasNegras);
        txtTurno.setText((yo.getFicha() == Partida.TURNO_NEGRAS)?"Tu turno":"Turno del oponente");
        txtFichasBlancas.setText(R.string.fichas_blancas);
        txtFichasBlancas.append("2");
        txtFichasNegras.setText(R.string.fichas_negras);
        txtFichasNegras.append("2");

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                tiles[i][j] = new ConstraintLayout(this);
                tiles[i][j].setBackgroundResource(R.drawable.tile);
                tiles[i][j].setMinHeight((int) (height * 0.50 / 8));
                tiles[i][j].setMinWidth((int) (width * 0.9 / 8));
                tiles[i][j].setMaxHeight((int) (height * 0.50 / 8));
                tiles[i][j].setMaxWidth((int) (width * 0.9 / 8));
                tableroCont.addView(tiles[i][j]);
                final int finalI = i;
                final int finalJ = j;
                tiles[i][j].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (obT.getTableroLogico()[finalI][finalJ] == Tablero.POSIBLE){
                            obT.agregarFicha(obP.getTurno(), finalI, finalJ);
                            obT.limpiaPosibles();
                            obT.encerrar(finalI, finalJ, obP.getTurno());
                            int nuevoTurno = (obP.getTurno()==1)?2:1;
                            obP.setTurno(nuevoTurno);
                            obT.fichasPosibles(obP.getTurno());
                            txtTurno.setText((obP.getTurno() == Partida.TURNO_NEGRAS)?R.string.turno_negras:R.string.turno_blancas);
                            int [] fichas = obT.totalFichas();
                            txtFichasBlancas.setText(R.string.fichas_blancas);
                            txtFichasBlancas.append(fichas[0] + "");
                            txtFichasNegras.setText(R.string.fichas_negras);
                            txtFichasNegras.append("" + fichas[1]);
                        }
                        return true;
                    }
                });
            }
        }
        obT.inicioTablero();
        obT.fichasPosibles(obP.getTurno());
    }

    @Override
    public void update(Observable observable, Object o) {
        final ArrayList args = (ArrayList) o;
        int accion = Integer.parseInt(args.get(0).toString());
        switch (accion){
            case Partida.INICIO:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int [][] blancas = (int[][]) args.get(1);
                        tableroCont.getChildAt((blancas[0][0] * 8 ) + blancas[0][1]).setBackgroundResource(R.drawable.ficha_blanca);
                        tableroCont.getChildAt((blancas[1][0] * 8 ) + blancas[1][1]).setBackgroundResource(R.drawable.ficha_blanca);
                        //(finalAux2.getFila() * Tablero.ANCHO) + finalAux2.getColumna())
                        int [][] negras = (int[][]) args.get(2);
                        tableroCont.getChildAt((negras[0][0] * 8 ) + negras[0][1]).setBackgroundResource(R.drawable.ficha_negra);
                        tableroCont.getChildAt((negras[1][0] * 8 ) + negras[1][1]).setBackgroundResource(R.drawable.ficha_negra);

                    }
                });
                break;

            case Partida.AGREGAR_POSIBLE:
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("f: " + Integer.parseInt(args.get(1).toString()) + "c: " + Integer.parseInt(args.get(2).toString()));
                        tableroCont.getChildAt((Integer.parseInt(args.get(1).toString()) * 8 ) + (Integer.parseInt(args.get(2).toString()))).setBackgroundResource(R.drawable.ficha_posible);
                    }
                });
                break;

            case Tablero.AGREGAR:
                 this.runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         if (Integer.parseInt(args.get(1).toString()) == Partida.TURNO_NEGRAS){
                             tableroCont.getChildAt((Integer.parseInt(args.get(2).toString()) * 8 ) + (Integer.parseInt(args.get(3).toString()))).setBackgroundResource(R.drawable.ficha_negra);
                         } else {
                             tableroCont.getChildAt((Integer.parseInt(args.get(2).toString()) * 8 ) + (Integer.parseInt(args.get(3).toString()))).setBackgroundResource(R.drawable.ficha_blanca);
                         }
                     }
                 });
                 break;

            case Tablero.LIMPIAR:
                tableroCont.getChildAt((Integer.parseInt(args.get(1).toString()) * 8 ) + (Integer.parseInt(args.get(2).toString()))).setBackgroundResource(R.drawable.tile);
                break;

            case Tablero.ENCERRAR:
                for (Ficha ficha: (ArrayList<Ficha>) args.get(1)){
                    if (Integer.parseInt(args.get(2).toString()) == Partida.TURNO_NEGRAS){
                        tableroCont.getChildAt((ficha.getY() * 8) + ficha.getX()).setBackgroundResource(R.drawable.ficha_negra);
                    } else if(Integer.parseInt(args.get(2).toString()) == Partida.TURNO_BLANCAS) {
                        tableroCont.getChildAt((ficha.getY() * 8) + ficha.getX()).setBackgroundResource(R.drawable.ficha_blanca);
                    }
                }
                break;
        }

    }
}
