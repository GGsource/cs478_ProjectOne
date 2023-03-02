package edu.uic.cs478.s2023.projectone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    Button btnReturn;
    String currentNum = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(view -> onBackPressed());
        EditText numField = findViewById(R.id.editTextPhoneNum);
        numField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                currentNum = textView.getText().toString();
                Log.d("EditTriggered", "onEditorAction: textview now says " + currentNum);
                return true;

            }
        });
    }

    @Override
    public void onBackPressed() {
//        EditText numField = findViewById(R.id.editTextPhoneNum);
//        String phoneNum = numField.getText().toString();
        Intent intentPhone = getIntent();
        intentPhone.putExtra("phoneInfo", currentNum);
        if (isValidPhoneNum(currentNum)) {
            setResult(RESULT_OK, intentPhone);
        } else {
            setResult(RESULT_CANCELED, intentPhone);
        }
        finish();
    }

    public boolean isValidPhoneNum(String givenNum) {
//        DONE: Implement validation process
//        1112223333 - 10 characters long
//        (111)222-3333 - 13 characters long
//        (111) 222-3333 - 14 characters long
//        Only these 3 possibilities
        if (givenNum.length() == 10 && isStringNumeric(givenNum)) {
            return true;
        }
        else if (givenNum.length() > 12 && isFormattedPhoneNum(givenNum)) {
            return true;
        }
        return false;
    }

    private boolean isNumericChar(char c) {
        return (int) c >= 48 && (int) c <= 57;
    }
    private boolean isStringNumeric (String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!isNumericChar(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    private boolean isFormattedPhoneNum(String s) {
        String copyS = s;
//        (111)222- 3  3  3  3 - Phone Number of 13 length
//        012345678 9 10 11 12 - Indexes
//        (111) 222- 3  3  3  3 - Phone Number of 14 length
//        0123456789 10 11 12 13 - Indexes
//        Check Area code first (***)
        if ((int)s.charAt(0) == 40 && (int)s.charAt(4) == 41) {
            copyS = copyS.substring(1,4) + copyS.substring(5);
        }
//        Should now be 111 222-3333 or 111222-3333
        String[] strings = copyS.split(" ");
//        We should have ["111222-3333"] or ["111", "222-3333"]
        if (strings.length == 1) {
//            if 111222-3333
            String[] lastStrings = strings[0].split("-");
//            Should be ["111222", "3333"]
            if ((lastStrings[0]).length() == 6 && lastStrings[1].length() == 4 && isStringNumeric(lastStrings[0]) && isStringNumeric(lastStrings[1])) {
                return true; //This was a 13 character long formatted number!
            }
        } else if (strings.length == 2) {
//            if 111 222-3333
            String[] lastStrings = strings[1].split("-");
//            should be ["222", "3333"]
            if (strings[0].length() == 3
                && lastStrings[0].length() == 3
                && lastStrings[1].length() == 4
                && isStringNumeric(strings[0])
                && isStringNumeric(lastStrings[0])
                && isStringNumeric(lastStrings[1])
            ) {
                return true; //This was a 14 character long formatted number!
            }
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("curNum", currentNum);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentNum = savedInstanceState.getString("curNum", null);
    }
}
