package com.example.aplicatiemanagementfilme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.aplicatiemanagementfilme.util.UserAccount;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    public static final int SIGN_UP_REQUEST_CODE = 200;
    private TextInputEditText tiet_username;
    private TextInputEditText tiet_password;
    private CheckBox checkBoxRemember;
    private Button btn_login;
    private Button btn_signup;

    private UserAccount userAccount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializare componente APELARE
        initComponents();

        // Atasare functie buton pt start activity Signup APELARE
        btn_signup.setOnClickListener(onClickOpenSignUpListener());
    }


    // Activity result


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Primire userAccount nou
        if(requestCode == SIGN_UP_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            userAccount = (UserAccount) data.getSerializableExtra(SignUpActivity.USER_ACCOUNT_KEY);
            // Populare log in
            populateLogIn(userAccount);
        }
    }


    // Functii
    // Initializare componente
    private void initComponents() {
        tiet_username = findViewById(R.id.tiet_username_login);
        tiet_password = findViewById(R.id.tiet_password_login);
        checkBoxRemember = findViewById(R.id.checkbox_remember_login);
        btn_login = findViewById(R.id.btn_login_login);
        btn_signup = findViewById(R.id.btn_signup_login);
    }

    // Functie buton pt start activity sign up
    private View.OnClickListener onClickOpenSignUpListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, SIGN_UP_REQUEST_CODE);
            }
        };
    }

    // Populare campuri log in
    private void populateLogIn(UserAccount userAccount){
        tiet_username.setText(userAccount.getUsername());
        tiet_password.setText(userAccount.getPassword());
    }
}