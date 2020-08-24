package com.example.othello;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.othello.modelo.Jugador;
import com.example.othello.modelo.Partida;
import com.example.othello.modelo.Tablero;

public class Resultado extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_final);
        int [] fichas = getIntent().getIntArrayExtra("fichas");
        Jugador yo = (Jugador) getIntent().getSerializableExtra("yo");
        TextView resultado = findViewById(R.id.resultado);
        TextView fichas_finales = findViewById(R.id.fichas_totales);
        Button btn_inicio = findViewById(R.id.btn_inicio);
        Button btn_salir = findViewById(R.id.btn_salir);
        int contFichas = (yo.getFicha() == Tablero.NEGRA) ? 1 : 0;


        if (fichas[0] == fichas[1]){
            resultado.setText("EMPATE");
            resultado.setTextColor(Color.parseColor("#FF7E39"));
        } else {

            if (fichas[contFichas] > 32 ){
                resultado.setText("GANASTE");
                resultado.setTextColor(Color.parseColor("#69C33B"));
            } else {
                resultado.setText("PERDISTE");
                resultado.setTextColor(Color.parseColor("#FF5739"));
            }
        }

        fichas_finales.setText("Fichas finales: " + fichas[contFichas]);

    }
}
