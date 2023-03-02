//////////////////////////////////////////////////////////////////////////////////
// Author: Geovani Gonzalez
// Name:   Project One
// Class:  CS 478 Spring 23
// Date:   March 1st, 2023
// Desc:   This is the main activity file for Project One. Application takes number
// from user and then checks if it is in a valid format. If so, it is given to the
// dialer app of the phone's choice, and put in, without dialing.
//////////////////////////////////////////////////////////////////////////////////
// Notes:
//
// 1. The PDF requires use of the setOnEditorActionListener, as seen in the
// SecondActivity file. This has been done, however I feel the need to point out,
// this means the user's input is ONLY captured when they press enter. This seems
// inferior to the version I first went with, where user input is captured with
// the use of onBackPressed, so even if they don't press enter it is saved.
// These two methods are of course incompatible, so the one in the PDF has been kept.
//
// 2. The editText field gives a numeric input keypad, but this prevents only the
// use of alphabetic characters. You can still type in '(', ')', '-', etc.
//////////////////////////////////////////////////////////////////////////////////

package edu.uic.cs478.s2023.projectone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button btnOne;
    Button btnTwo;

    String returnedPhoneNum = "";
    boolean isValidPhoneNum = false;
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
            if (isValidPhoneNum) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + returnedPhoneNum));
                startActivity(intent);
            } else {
                Toast.makeText(this, "'" + (Objects.equals(returnedPhoneNum, "") ? "[No number provided]" : returnedPhoneNum)+ "' is not in a valid format. Please try again!", Toast.LENGTH_LONG).show();
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ActivityReturned", "requestCode: " + requestCode + " resultCode: " + resultCode);
        if (data != null) {
            returnedPhoneNum = data.getStringExtra("phoneInfo");
        } else {
            throw new RuntimeException("Returned from Phone Number Activity but failed to retrieve phone number, data was null...");
        }
        isValidPhoneNum = resultCode == RESULT_OK;

        Log.d("ActivityReturned", "onActivityResult: " + returnedPhoneNum);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("heldNum", returnedPhoneNum);
        outState.putBoolean("isValid", isValidPhoneNum);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        returnedPhoneNum = savedInstanceState.getString("heldNum", null);
        isValidPhoneNum = savedInstanceState.getBoolean("isValid", false);
    }
}