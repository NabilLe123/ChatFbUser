package com.lelab.chatfbuser;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lelab.chatfbuser.model.Chat;
import com.lelab.chatfbuser.model.ChatAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final String user = getIntent().getStringExtra("user");

        TextView tvWelcome = findViewById(R.id.tv_welcome);
        tvWelcome.setText("Welcome User: " + user);

        final EditText etMsg = findViewById(R.id.et_msg);
        ImageView ivSend = findViewById(R.id.iv_send);

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etMsg.getText().toString().trim())) {
                    sendMessage(user, "driver123", etMsg.getText().toString().trim());

                    etMsg.setText("");
                }
            }
        });

        final List<Chat> chats = new ArrayList<>();
        final RecyclerView rvChats = findViewById(R.id.rv_chats);
        final ChatAdapter chatAdapter = new ChatAdapter(this, chats, user);
        rvChats.setAdapter(chatAdapter);

        reference = FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("rxjava", "onDataChange " + dataSnapshot.getChildrenCount());
                chats.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Chat chat = dataSnapshot1.getValue(Chat.class);
                    if (chat != null)
                        chats.add(chat);
                }

                chatAdapter.addChats(chats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("rxjava", "onCancelled " + databaseError.getMessage());
            }
        });
    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);


        Log.d("rxjava", "sendMessage");
        Task<Void> chats = reference.child("Chats").push().setValue(hashMap);
        chats.addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("rxjava", "task: " + task.getResult());
            }
        });
    }
}
