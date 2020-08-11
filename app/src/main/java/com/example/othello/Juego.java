package com.example.othello;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.othello.modelo.Tablero;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Juego extends AppCompatActivity implements Observer {
    private GridLayout tableroCont;
    Tablero obT = new Tablero();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        this.obT.addObserver(this);
        tableroCont = findViewById(R.id.tableroCont);
        ConstraintLayout tiles [][] = new ConstraintLayout[8][8];

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
                        Toast.makeText(Juego.this, "i: " + finalI + " j: " + finalJ, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        }
        obT.inicioTablero();
    }

    @Override
    public void update(Observable observable, Object o) {
        final ArrayList args = (ArrayList) o;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int [][] blancas = (int[][]) args.get(0);
                tableroCont.getChildAt((blancas[0][0] * 8 ) + blancas[0][1]).setBackgroundResource(R.drawable.ficha_blanca);
                tableroCont.getChildAt((blancas[1][0] * 8 ) + blancas[1][1]).setBackgroundResource(R.drawable.ficha_blanca);
                //(finalAux2.getFila() * Tablero.ANCHO) + finalAux2.getColumna())
                int [][] negras = (int[][]) args.get(1);
                tableroCont.getChildAt((negras[0][0] * 8 ) + negras[0][1]).setBackgroundResource(R.drawable.ficha_negra);
                tableroCont.getChildAt((negras[1][0] * 8 ) + negras[1][1]).setBackgroundResource(R.drawable.ficha_posible);

            }
        });
    }
}
