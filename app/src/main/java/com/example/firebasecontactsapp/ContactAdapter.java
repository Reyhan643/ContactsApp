package com.example.firebasecontactcapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasecontactcapp.AddEditContactActivity;
import com.example.firebasecontactcapp.R;
import com.example.firebasecontactcapp.models.Contact;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ContactAdapter extends FirestoreRecyclerAdapter<Contact, ContactAdapter.ContactViewHolder> {

    private Context context;

    public ContactAdapter(@NonNull FirestoreRecyclerOptions<Contact> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ContactViewHolder holder, int position, @NonNull Contact model) {
        holder.name.setText(model.getName());
        holder.phone.setText(model.getPhone());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditContactActivity.class);
            intent.putExtra("contactId", model.getId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("contacts")
                    .document(model.getId()).delete();
        });
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(v);
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone;
        ImageButton btnEdit, btnDelete;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            phone = itemView.findViewById(R.id.textViewPhone);
            btnEdit = itemView.findViewById(R.id.buttonEdit);
            btnDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
