package edu.uic.cs478.s2023.projectone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    Button btnReturn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnReturn = findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        EditText numField = findViewById(R.id.editTextPhoneNum);
        String phoneNum = numField.getText().toString();
        Intent intentPhone = getIntent();
        intentPhone.putExtra("phoneInfo", phoneNum);
        setResult(RESULT_OK, intentPhone);
        finish();
    }
}
