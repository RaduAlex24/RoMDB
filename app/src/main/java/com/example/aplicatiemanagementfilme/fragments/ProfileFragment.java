package com.example.aplicatiemanagementfilme.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aplicatiemanagementfilme.LogInActivity;
import com.example.aplicatiemanagementfilme.MainActivity;
import com.example.aplicatiemanagementfilme.R;
import com.example.aplicatiemanagementfilme.database.model.UserAccount;

import static android.content.Context.MODE_PRIVATE;
import static com.example.aplicatiemanagementfilme.LogInActivity.PASSWORD_SP;
import static com.example.aplicatiemanagementfilme.LogInActivity.REMEMBER_CHECKED;
import static com.example.aplicatiemanagementfilme.LogInActivity.USERNAME_SP;


public class ProfileFragment extends Fragment {

    private TextView tvTitle;
    private TextView tvFullName;
    private TextView tvEmail;
    private ImageView ivProfile;
    private Button btnEditAccount;
    private Button btnDeleteAccount;
    private Button btnSignOut;
    private CheckBox checkBoxAutoLogIn;

    public static final String AUTO_LOG_IN_CHECKED = "AUTO_LOG_IN_CHECKED";
    private SharedPreferences preferences;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initializare componente
        initComponents(view);

        // Creare pagina pentru utilizatorul logat
        createProfileFromUserAccount(MainActivity.currentUserAccount);

        // Butoane
        // Sign out
        btnSignOut.setOnClickListener(onClickSignOutListener());

        // Verificare auto log in
        getAutoLogInFromSharedPreferences();

        // Auto log in
        checkBoxAutoLogIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    boolean rememberChecked = preferences.getBoolean(REMEMBER_CHECKED, false);
                    if(rememberChecked){
                        putAutoLogInInSharedPreferences();
                    }
                    else{
                        putUserAccountInSharedPreferences();
                    }
                } else {
                    removeAutoLogInInSharedPreferences();
                }
            }
        });

        return view;
    }


    // Functii
    // Initializare componente
    private void initComponents(View view) {
        // Componente
        tvTitle = view.findViewById(R.id.tv_title_profile);
        tvFullName = view.findViewById(R.id.tv_fullName_profile);
        tvEmail = view.findViewById(R.id.tv_userEmail_profile);
        ivProfile = view.findViewById(R.id.iv_avatar_profile);
        btnEditAccount = view.findViewById(R.id.btn_editAccount_profile);
        btnDeleteAccount = view.findViewById(R.id.btn_deleteAccount_profile);
        btnSignOut = view.findViewById(R.id.btn_signOut_profile);
        checkBoxAutoLogIn = view.findViewById(R.id.checkbox_autoLogIn_profile);

        // Preferinte
        preferences = getActivity().getSharedPreferences(LogInActivity.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
    }


    // Creare pagina pentru utilizatorul logat
    private void createProfileFromUserAccount(UserAccount userAccount) {
        // Profile
        tvTitle.setText(getString(R.string.profile_username_title_param, userAccount.getUsername()));
        // Full name
        tvFullName.setText(userAccount.getFullName());
        // Email
        tvEmail.setText(userAccount.getEmail());
        // Imagine
        Glide.with(getContext()).load(getString(R.string.url_image_profile)).into(ivProfile);
    }

    // Verificare auto log in
    private void getAutoLogInFromSharedPreferences(){
        boolean rememberAutoLogIn = preferences.getBoolean(AUTO_LOG_IN_CHECKED, false);
        if(rememberAutoLogIn){
            checkBoxAutoLogIn.setChecked(true);
        }
    }

    // Punere auto logIn in shared preferences
    private void putAutoLogInInSharedPreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(AUTO_LOG_IN_CHECKED, true);
        editor.apply();
    }

    // Punere user account in shared preferences
    private void putUserAccountInSharedPreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME_SP, MainActivity.currentUserAccount.getUsername());
        editor.putString(PASSWORD_SP, MainActivity.currentUserAccount.getPassword());
        editor.putBoolean(REMEMBER_CHECKED, true);
        editor.putBoolean(AUTO_LOG_IN_CHECKED, true);
        editor.apply();
    }

    // Stergere auto logIn in shared preferences
    private void removeAutoLogInInSharedPreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(AUTO_LOG_IN_CHECKED, false);
        editor.apply();
    }



    // Butoane
    // Sign out
    private View.OnClickListener onClickSignOutListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean rememberChecked = preferences.getBoolean(REMEMBER_CHECKED, false);
                if(!rememberChecked) {
                    LogInActivity.deleteUserInfoFromLogIn();
                }
                getActivity().finish();
            }
        };
    }


}