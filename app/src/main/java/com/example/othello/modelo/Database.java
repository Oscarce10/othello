package com.example.othello.modelo;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Snapshot;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;

public class Database {
    private static DatabaseReference mDatabase;
    private static int res;

    public static int getTurno_juego(String id) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        System.out.println("id: " + id);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("usuarios_disponibles").child(id).child("turno_juego").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("ZZZZZZZZZZZZZZZZZZZAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                System.out.println(Integer.parseInt(String.valueOf((long) (snapshot.getValue()))));
                res = Integer.parseInt(String.valueOf((long) (snapshot.getValue())));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        latch.await();
        return res;
    }
}
