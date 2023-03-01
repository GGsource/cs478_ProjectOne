package edu.uic.cs478.s2023.projectone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnOne;
    Button btnTwo;

    String validPhoneNum = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);

        btnOne.setOnClickListener(v -> {
            int SECOND_ACTIVITY_CODE = 1;
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivityForResult(intent, SECOND_ACTIVITY_CODE);
        });

        btnTwo.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ActivityReturned", "requestCode: " + requestCode + " resultCode: " + resultCode);
        if (resultCode == RESULT_OK) {
            if (data != null && data.getExtras().containsKey("phoneInfo")) {
                validPhoneNum = data.getStringExtra("phoneInfo");
            } else {
                throw new RuntimeException("Returned from Phone Number Activity but failed to retrieve phone number...");
            }
        }

        Log.d("ActivityReturned", "onActivityResult: " + validPhoneNum);
    }
}