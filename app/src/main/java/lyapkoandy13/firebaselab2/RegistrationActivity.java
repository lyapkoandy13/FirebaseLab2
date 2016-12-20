package lyapkoandy13.firebaselab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by lyapkoandy13 on 10.12.16.
 */

public class RegistrationActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnRegister, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        btnRegister = (Button) findViewById(R.id.registerButton);
        inputEmail = (EditText) findViewById(R.id.loginEditText);
        inputPassword = (EditText) findViewById(R.id.passwordEditText);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean result = auth.createUserWithEmailAndPassword(email, password)
                    .isSuccessful();

                if(result){
                    Toast.makeText(getApplicationContext(), "Successfuly registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Registration failed, sorry :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
