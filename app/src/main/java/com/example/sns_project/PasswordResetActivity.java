package com.example.sns_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//최병욱
public class PasswordResetActivity extends AppCompatActivity {
    private static final String TAG = "PasswordResetActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_send).setOnClickListener(onClickListener);
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
                case R.id.btn_send:    // 로그인 버튼을 클릭했을 때 데이터베이스의 내용과 일치하면 메인으로 이동 메서드 실행
                    send();
                    break;
            }
        }
    };


    private void send() {   // 비밀번호 변경 메서드
        String userEmail = ((EditText)findViewById(R.id.et_email)).getText().toString();   // 텍스트에 적은 이메일, 비밀번호를 string으로 가져온다.

        if(userEmail.length() > 0 ) {
            mAuth.sendPasswordResetEmail(userEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PasswordResetActivity.this, "이메일을 보냈습니다.", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Email sent.");
                            }
                        }
                    });
        }else   {
            Toast.makeText(PasswordResetActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
        }
    }



}