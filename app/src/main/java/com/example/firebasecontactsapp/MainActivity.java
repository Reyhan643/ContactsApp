package com.example.firebasecontactcapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.firebasecontactcapp.adapters.ContactAdapter;
import com.example.firebasecontactcapp.models.Contact;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private ContactAdapter adapter;
    private RecyclerView recyclerView;
    private View btnAdd, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnAdd = findViewById(R.id.buttonAdd);
        btnLogout = findViewById(R.id.buttonLogout);
        recyclerView = findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = db.collection("users")
                .document(auth.getUid())
                .collection("contacts");

        FirestoreRecyclerOptions<Contact> options =
                new FirestoreRecyclerOptions.Builder<Contact>()
                        .setQuery(query, Contact.class)
                        .build();

        adapter = new ContactAdapter(options, this);
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditContactActivity.class)));

        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
