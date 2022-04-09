package com.example.sns_project;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            MystartActivity(RegisterActivity.class);
        }else
        {
            //회원가입 or 로그인
            for (UserInfo profile : user.getProviderData()) {

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                Log.e("이름:","이름:"+name);
                if(name != null){
                    if(name.length() == 0){
                        MystartActivity(MemberInitActivity.class);
                    }
                }

            }
        }

        findViewById(R.id.btn_logout).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {   // 각 버튼 클릭 시 이벤트 발생
            switch (v.getId()) {
                case R.id.btn_logout:    // 로그아웃 버튼 클릭시 로그아웃되면서 로그인 액티비티로 이동
                    FirebaseAuth.getInstance().signOut();
                    MystartActivity(RegisterActivity.class);
                    break;

            }
        }
    };

    private void MystartActivity(Class c){
        Intent intent = new Intent(this, c);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}