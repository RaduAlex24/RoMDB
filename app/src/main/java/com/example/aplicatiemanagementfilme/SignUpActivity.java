package com.example.aplicatiemanagementfilme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplicatiemanagementfilme.database.dao.UserAccountDao;
import com.example.aplicatiemanagementfilme.database.model.UserAccount;
import com.example.aplicatiemanagementfilme.database.service.UserAccountService;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    public static final String USER_ACCOUNT_KEY = "USER_ACCOUNT_KEY";
    private TextInputEditText tiet_username;
    private TextInputEditText tiet_password;
    private TextInputEditText tiet_email;
    private TextInputEditText tiet_fullname;
    private Button btn_signup;

    private Intent intent;
    private UserAccountService userAccountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initializare componente APELARE
        initComponents();

        // Atasare functie buton pt Signup APELARE
        btn_signup.setOnClickListener(onClickSignUpListener());
    }


    // Functii
    // Initializare componente
    private void initComponents() {
        tiet_username = findViewById(R.id.tiet_username_signup);
        tiet_password = findViewById(R.id.tiet_password_signup);
        tiet_email = findViewById(R.id.tiet_email_signup);
        tiet_fullname = findViewById(R.id.tiet_fullname_signup);
        btn_signup = findViewById(R.id.btn_signup_signup);

        // Preluare intent
        intent = getIntent();

        // Initializare dao
        userAccountService = new UserAccountService(getApplicationContext());

    }


    // Atasare functie buton pt Signup
    private View.OnClickListener onClickSignUpListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    // validare db
                    UserAccount userAccount = createAccountFromWidgets();

                    // Inserare in baza de date


                    intent.putExtra(USER_ACCOUNT_KEY, userAccount);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }


    // Functie validare componente fara db
    private boolean validate() {
        if (tiet_username.getText().toString().trim().length() <= 5
                || tiet_username.getText().toString().trim().length() >= 16) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.toast_username_signup),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tiet_password.getText().toString().trim().length() <= 5
                || tiet_password.getText().toString().trim().length() >= 16) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.toast_password_signup),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        // Bonus validare adresa email cu @ si .
        if (tiet_email.getText().toString().trim().length() <= 9
                || tiet_email.getText().toString().trim().length() >= 26) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.toast_email_signup),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tiet_fullname.getText().toString().trim().length() <= 4
                || tiet_fullname.getText().toString().trim().length() >= 26) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.toast_fullname_signup),
                    Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    // Creare account din widgets
    private UserAccount createAccountFromWidgets() {
        String username = tiet_username.getText().toString();
        String password = tiet_password.getText().toString();
        String email = tiet_email.getText().toString();
        String fullName = tiet_fullname.getText().toString();

        return new UserAccount(username, password, email, fullName);
    }
}