package com.lelab.chatfbuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etLogin = findViewById(R.id.et_login);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etLogin.getText().toString().equals("nab")) {
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    intent.putExtra("user", etLogin.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
