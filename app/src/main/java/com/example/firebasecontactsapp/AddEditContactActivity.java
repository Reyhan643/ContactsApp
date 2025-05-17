package com.example.firebasecontactcapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasecontactcapp.models.Contact;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddEditContactActivity extends AppCompatActivity {
    private EditText inputName, inputPhone;
    private Button btnSave;
    private FirebaseFirestore db;
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        inputName = findViewById(R.id.editTextName);
        inputPhone = findViewById(R.id.editTextPhone);
        btnSave = findViewById(R.id.buttonSave);
        db = FirebaseFirestore.getInstance();
        contactId = getIntent().getStringExtra("contactId");

        if (contactId != null) {
            db.collection("users").document(FirebaseAuth.getInstance().getUid())
                    .collection("contacts").document(contactId)
                    .get().addOnSuccessListener(doc -> {
                        Contact c = doc.toObject(Contact.class);
                        inputName.setText(c.getName());
                        inputPhone.setText(c.getPhone());
                    });
        }

        btnSave.setOnClickListener(v -> {
            String name = inputName.getText().toString();
            String phone = inputPhone.getText().toString();
            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Isi semua field", Toast.LENGTH_SHORT).show();
                return;
            }

            Contact contact = new Contact();
            contact.setName(name);
            contact.setPhone(phone);

            if (contactId != null) {
                contact.setId(contactId);
                db.collection("users").document(FirebaseAuth.getInstance().getUid())
                        .collection("contacts").document(contactId).set(contact);
            } else {
                String id = db.collection("users").document(FirebaseAuth.getInstance().getUid())
                        .collection("contacts").document().getId();
                contact.setId(id);
                db.collection("users").document(FirebaseAuth.getInstance().getUid())
                        .collection("contacts").document(id).set(contact);
            }

            finish();
        });
    }
}
