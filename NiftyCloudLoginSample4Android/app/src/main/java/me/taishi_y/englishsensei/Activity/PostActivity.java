package me.taishi_y.englishsensei.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBUser;

import me.taishi_y.englishsensei.R;

public class PostActivity extends AppCompatActivity {

    EditText etPostEnglish,etPostJapanese;
    Button btnPost;
    private InputMethodManager inputMethodManager;
    private LinearLayout post_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        etPostEnglish = (EditText) findViewById(R.id.et_english);
        etPostJapanese = (EditText) findViewById(R.id.et_japanese);
        btnPost = (Button) findViewById(R.id.btn_post);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        //画面全体のレイアウト
        post_screen = (LinearLayout) findViewById(R.id.post_screen);
        //キーボード表示を制御するためのオブジェクト
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NCMBUser user = NCMBUser.getCurrentUser();
                String userName = user.getUserName();

                String postEnglish = etPostEnglish.getText().toString();
                String postJapanese = etPostJapanese.getText().toString();
                NCMBObject obj = new NCMBObject("Phrase");
                obj.put("postEnglish", postEnglish);
                obj.put("postJapanese", postJapanese);
                obj.put("userName", userName);
                obj.saveInBackground(new DoneCallback() {
                    @Override
                    public void done(NCMBException e) {
                        if (e != null) {
                            //エラー発生時の処理
                            Toast.makeText(getApplicationContext(),"failier",Toast.LENGTH_SHORT).show();
                        } else {
                            //成功時の処理
                            Toast.makeText(getApplicationContext(),"post successed",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(getApplicationContext(),"fafa",Toast.LENGTH_SHORT).show();
        return true;
    }
}
