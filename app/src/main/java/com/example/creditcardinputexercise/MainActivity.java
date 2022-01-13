package com.example.creditcardinputexercise;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private Button submit;
    private TextInputLayout CardNum, cvv, expDate, FName, LName;
    private TextInputEditText etCardNum, etCvv, eteExpDate, etFName, etLName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        For TextInputLayout
        CardNum = findViewById(R.id.tilCardNumber);
        expDate = findViewById(R.id.tilExpDate);
        cvv = findViewById(R.id.tilcvv);
        FName = findViewById(R.id.tilFName);
        LName = findViewById(R.id.tilLName);

//        For TextInputEditText
        etCardNum = findViewById(R.id.etCardNumber);
        eteExpDate = findViewById(R.id.etExpDate);
        etCvv = findViewById(R.id.etCVV);
        etFName = findViewById(R.id.etFName);
        etLName = findViewById(R.id.etLName);

        submit = findViewById(R.id.submit_btn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validCard(v);
            }
        });
    }

    public void validCard(View view) {
        String CardNumber = etCardNum.getText().toString();
        String expirationDate = eteExpDate.getText().toString();
        String CVV = etCvv.getText().toString();
        String fName = etFName.getText().toString();
        String lName = etLName.getText().toString();

        if (!CreditCard.isValidCardNum(CardNumber)) {
            CardNum.setError(getString(R.string.invalidCdNum));
            etCardNum.requestFocus();
        } else if (!CreditCard.isValidExpDate(expirationDate)) {
            expDate.setError(getString(R.string.invalidExpDate));
            eteExpDate.requestFocus();
        } else if (!CreditCard.isValidCvv(CardNumber, CVV)) {
            cvv.setError(getString(R.string.invalidCVV));
            etCvv.requestFocus();
        } else if (fName.isEmpty()) {
            FName.setError(getString(R.string.EnterFirstName));
            etFName.requestFocus();
        } else if (lName.isEmpty()) {
            LName.setError(getString(R.string.EnterLastName));
            etLName.requestFocus();
        } else {
            Keyboard(view);
            CreditCard Card = new CreditCard(CardNumber, expirationDate, CVV, fName, lName);
            alertDialog(submitCreditCard(Card), null, getString(R.string.ok));
        }
    }

    private String submitCreditCard(CreditCard creditCard) {
        return getString(R.string.success);
    }


    public void Keyboard(View view) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void alertDialog(String title, String message, String btnTxt) {
        new AlertDialog.Builder(this, R.style.AlertTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(btnTxt, null)
                .show();
    }
}