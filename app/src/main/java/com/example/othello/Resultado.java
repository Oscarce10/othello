package com.example.othello;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.othello.modelo.Jugador;
import com.example.othello.modelo.Partida;
import com.example.othello.modelo.Tablero;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Resultado extends AppCompatActivity {
    private DatabaseReference mDatabase;
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
        int contFichasOp = (contFichas == 1) ? 0 : 1;

        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                finishActivity(0);
                mDatabase.child("partida").setValue(null);
                mDatabase.child("usuarios_disponibles").setValue(null);
                startActivity(new Intent(Resultado.this, MainActivity.class));
            }
        });


        if (fichas[0] == fichas[1]){
            resultado.setText("EMPATE");
            resultado.setTextColor(Color.parseColor("#FF7E39"));
        } else {
            if (fichas[contFichas] > fichas[contFichasOp] ){
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
