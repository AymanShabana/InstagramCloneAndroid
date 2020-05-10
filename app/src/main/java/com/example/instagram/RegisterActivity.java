package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText name;
    private EditText email;
    private EditText password;
    private Button register;
    private TextView alreadyUser;
    private FirebaseAuth auth;
    private DatabaseReference mRootRef;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=(EditText)findViewById(R.id.usernameTxt);
        name=(EditText)findViewById(R.id.nameTxt);
        email=(EditText)findViewById(R.id.emailTxt);
        password=(EditText)findViewById(R.id.passTxt);
        alreadyUser=(TextView) findViewById(R.id.alreadyUserTxt);
        register=(Button)findViewById(R.id.regBtn);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTxt= String.valueOf(email.getText());
                String passTxt = password.getText().toString();
                String userTxt = username.getText().toString();
                String nameTxt = name.getText().toString();
                if(TextUtils.isEmpty(emailTxt)||TextUtils.isEmpty(passTxt)||TextUtils.isEmpty(userTxt)||TextUtils.isEmpty(nameTxt)){
                    Toast.makeText(RegisterActivity.this.getApplicationContext(),"Empty credentials!",Toast.LENGTH_LONG).show();
                }
                else if(passTxt.length()<6){
                    Toast.makeText(RegisterActivity.this.getApplicationContext(),"Password is too short!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    registerUser(userTxt,nameTxt,emailTxt,passTxt);
                }
            }
        });
    }

    private void registerUser(final String userTxt, final String nameTxt, final String emailTxt, String passTxt) {
        pd.setMessage("Hold on a second.");
        pd.show();
        auth.createUserWithEmailAndPassword(emailTxt,passTxt)
                .addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if(task.isSuccessful()){
                            HashMap<String,Object> map = new HashMap<String, Object>();
                            map.put("name",nameTxt);
                            map.put("email",emailTxt);
                            map.put("username",userTxt);
                            map.put("id",auth.getCurrentUser().getUid());
                            map.put("bio","");
                            map.put("imageUrl","default");
                            mRootRef.child("MyUsers").child(auth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this.getApplicationContext(),"Registration successful! Update your profile for the full experience!",
                                                Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(RegisterActivity.this,Main2Activity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this.getApplicationContext(),"Registration failed! "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
}
