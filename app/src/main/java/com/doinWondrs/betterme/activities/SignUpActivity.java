package com.doinWondrs.betterme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.doinWondrs.betterme.R;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG_SIGNUP_EMAIL = "Signup_Email";
    private final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupBtn();

    }

    // RR: Saves account into AWS userpool with an associated username. Also creates the new User in DynamoDB with only username field
    public void signupBtn() {
        Button signupBtn = findViewById(R.id.signupButton);
        signupBtn.setOnClickListener(view -> {

            String email = ((EditText) findViewById(R.id.signupTextEmailAddress)).getText().toString();
            String password = ((EditText) findViewById(R.id.signupTextPassword)).getText().toString();
            String username = ((EditText) findViewById(R.id.signupTextUsername)).getText().toString();

            Amplify.Auth.signUp(
                    email,
                    password,
                    AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(), email)
                            .userAttribute(AuthUserAttributeKey.nickname(), username)
                            .build(),
                    success -> {
                        Log.i(TAG, "Signed up successfully :" + success);
                        createUser(username);
                        Intent goVerify = new Intent(SignUpActivity.this, VerifyUserActivity.class);
                        goVerify.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        goVerify.putExtra(TAG_SIGNUP_EMAIL, email);
                        startActivity(goVerify);
                    },
                    fail -> {
                        Log.e(TAG, "Signup failed : " + fail);
                        runOnUiThread(() ->
                        {
                            Toast.makeText(SignUpActivity.this, "SIGNUP FAILED, TRY AGAIN", Toast.LENGTH_SHORT).show();
                        });
                    }
            );

        });
    }

    // RR: Creates new user with only username field
    public void createUser(String username){
        User newUser = User.builder()
                .username(username)
                .build();

        Amplify.API.mutate(
                ModelMutation.create(newUser),
                successResponse -> Log.i(TAG, "Successfully created new User"),
                failureResponse -> Log.i(TAG, "Failed to create new User" + failureResponse)
        );
    }

    // TODO: Styling on xml
}