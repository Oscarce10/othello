package com.example.othello;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.othello.modelo.Jugador;
import com.example.othello.modelo.Partida;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Matchmaking extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference users;
    long conectados = 0;
    private String personName;
    private String personEmail;
    private String personId;
    private int turno;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encontrando_jugadores);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();
            personId = acct.getId();
            TextView txtView;
            txtView = findViewById(R.id.bienvenido);
            String txt = getString(R.string.bienvenido, personName);
            txtView.setText(txt);

            mDatabase = FirebaseDatabase.getInstance().getReference();

            mDatabase.child("usuarios_disponibles/" + personId).child("personId").setValue(personId);
            mDatabase.child("usuarios_disponibles/" + personId).child("personName").setValue(personName);
            mDatabase.child("usuarios_disponibles/" + personId).child("email").setValue(personEmail);
            mDatabase.child("usuarios_disponibles/"+ personId).child("turno_juego").setValue(Partida.TURNO_NEGRAS);

            users = mDatabase.child("usuarios_disponibles/");


            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    conectados = snapshot.getChildrenCount();
                    TextView usuarios_online = findViewById(R.id.usuarios_conectados);
                    String online = String.format(getResources().getString(R.string.usuarios_conectados), conectados);
                    usuarios_online.setText(online);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            users.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            conectados = snapshot.getChildrenCount();
                            TextView usuarios_online = findViewById(R.id.usuarios_conectados);
                            String online = String.format(getResources().getString(R.string.usuarios_conectados), conectados);
                            usuarios_online.setText(online);
                            if (conectados == 2){
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                generarPartida();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    conectados--;
                    TextView usuarios_online = findViewById(R.id.usuarios_conectados);
                    String online = String.format(getResources().getString(R.string.usuarios_conectados), conectados);
                    usuarios_online.setText(online);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    public void generarPartida(){
        mDatabase.child("usuarios_disponibles/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                turno = Integer.parseInt(String.valueOf((long) (snapshot.child(personId).child("turno").getValue())));
                Jugador yo = new Jugador(personId, personName, turno);
                Intent intent = new Intent(Matchmaking.this, Juego.class);
                intent.putExtra("yo", yo);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
