package com.example.othello;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.othello.modelo.Jugador;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Matchmaking extends AppCompatActivity {

    private DatabaseReference mDatabase;

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
            mDatabase.child("users").child("personId").setValue(personId);
            mDatabase.child("users").child("personName").setValue(personName);
            mDatabase.child("users").child("email").setValue(personEmail);

        }

        ValueEventListener postListener = new ValueEventListener() {
            public void onDataChange(DataSnapshot mDatabase) {
                // Get Post object and use the values to update the UI
                Jugador jugador = mDatabase.child("personName").getValue(Jugador.class);
                // ...
            }
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("ERROR", "loadPost:onCancelled", databaseError.toException());
            }
        };
        //mPostReference.addValueEventListener(postListener);

    }
}
