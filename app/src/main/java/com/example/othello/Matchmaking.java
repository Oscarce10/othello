package com.example.othello;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.othello.modelo.Jugador;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Matchmaking extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference users;
    long conectados = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encontrando_jugadores);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            TextView txtView;
            txtView = findViewById(R.id.bienvenido);
            String txt = getString(R.string.bienvenido, personName);
            txtView.setText(txt);
            System.out.println(personName);
            System.out.println(personGivenName);
            System.out.println(personFamilyName);
            System.out.println(personEmail);
            System.out.println(personId);



            mDatabase = FirebaseDatabase.getInstance().getReference();

            mDatabase.child("usuarios_disponibles/" + personId).child("personId").setValue(personId);
            mDatabase.child("usuarios_disponibles/" + personId).child("personName").setValue(personName);
            mDatabase.child("usuarios_disponibles/" + personId).child("email").setValue(personEmail);

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
                            if (conectados >= 2){

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
}
