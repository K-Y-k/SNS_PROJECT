package com.example.sns_project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_register).setOnClickListener(onClickListener);
        findViewById(R.id.btn_check).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {   // 버튼 클릭 시 회원가입 메서드 작동
            switch (v.getId()) {
                case R.id.btn_register:
                    signUp();
                    break;
                case R.id.btn_check:
                    MystartActivity(LoginActivity.class);
                    break;
            }
        }
    };


    private void signUp() {   // 회원가입 메서드
        String userEmail = ((EditText)findViewById(R.id.et_email)).getText().toString();   // 텍스트에 적은 이메일, 비밀번호를 string으로 가져온다.
        String userPass = ((EditText)findViewById(R.id.et_pass)).getText().toString();

        mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "success");
                            Toast.makeText(getApplicationContext(),"회원가입 되었습니다.",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Log.w(TAG, "failure", task.getException());
                            Toast.makeText(getApplicationContext(),"회원가입에 실패하였습니다. 이메일 형식과 비밀번호 6자리 이상인지 확인해주세요",Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"이메일 형식과 비밀번호 6자리 이상인지 확인해주세요",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void MystartActivity(Class c){
        Intent intent = new Intent(this, c);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}