package com.example.sns_project;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.auth.UserProfileChangeRequest;

//최병욱1234
public class MemberInitActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init);



        findViewById(R.id.btn_check).setOnClickListener(onClickListener);
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
                    profileUpdate();
                    break;
            }
        }
    };


    private void profileUpdate() {   // 로그인 메서드
        String name = ((EditText)findViewById(R.id.nameEditText)).getText().toString();   // 텍스트에 적은 이메일, 비밀번호를 string으로 가져온다.




        if(name.length() > 0 ) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            if(user != null){
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MemberInitActivity.this, "회원정보 등록이 되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }



        }else   {
            Toast.makeText(MemberInitActivity.this, "회원정보를 입력해주세요", Toast.LENGTH_SHORT).show();
        }
    }



}