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
//최병욱
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_check).setOnClickListener(onClickListener);
        findViewById(R.id.btn_register).setOnClickListener(onClickListener);
        findViewById(R.id.btn_passwordreset).setOnClickListener(onClickListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();;
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }



    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {   // 각 버튼 클릭 시 이벤트 발생
            switch (v.getId()) {
                case R.id.btn_check:    // 로그인 버튼을 클릭했을 때 데이터베이스의 내용과 일치하면 메인으로 이동 메서드 실행
                    login();
                    break;
                case R.id.btn_passwordreset:    // 비밀번호 찾기 버튼을 클릭했을 때 데이터베이스의 내용과 일치하면 메인으로 이동 메서드 실행
                    MystartActivity(PasswordResetActivity.class);
                    break;
                case R.id.btn_register:  // 회원가입 액티비티로 이동
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };


    private void login() {   // 로그인 메서드
        String userEmail = ((EditText)findViewById(R.id.et_email)).getText().toString();   // 텍스트에 적은 이메일, 비밀번호를 string으로 가져온다.
        String userPass = ((EditText)findViewById(R.id.et_pass)).getText().toString();

        if(userEmail.length() > 0 && userPass.length() > 0) {
            mAuth.signInWithEmailAndPassword(userEmail, userPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "success");
                                Toast.makeText(LoginActivity.this, "로그인 되었습니다", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);           // 다른 식으로 뒤로가지지 않기 위해 초기화해준다.
                                MystartActivity(MainActivity.class);
                            } else {
                                Log.w(TAG, "falilure", task.getException());
                                Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, "아이디, 비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }else   {
            Toast.makeText(LoginActivity.this, "이메일, 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    private void MystartActivity(Class c){
        Intent intent = new Intent(this, c);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}