package com.example.othello;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Matchmaking extends AppCompatActivity {

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
        }

    }
}
