package me.taishi_y.englishsensei.Activity;

import me.taishi_y.englishsensei.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.NCMBAcl;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBFile;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBUser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    private ImageView selectPhoto;
    private EditText editName,editBio;
    private Bitmap img;
    private String userName;
    private static final int REQUEST_GALLERY = 0;

    private TextView saveEditProfile,backFromEditProfile;

    private RadioButton radioButton;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setSupportActionBar(toolbar);
        findViewById();


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.select_studyinglanguage);
        // ラジオグループのチェック状態が変更された時に呼び出されるコールバックリスナーを登録します
        assert radioGroup != null;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            // ラジオグループのチェック状態が変更された時に呼び出されます
            // チェック状態が変更されたラジオボタンのIDが渡されます
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);

                Toast.makeText(getApplicationContext(),
                        radioButton.getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_GALLERY);
            }
        });

        saveEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NCMBObject obj = new NCMBObject("UserProfile");

                obj.put("user", NCMBUser.getCurrentUser());
                obj.put("sex",radioButton.getText().toString());
//                obj.put("displayName", editName.getText().toString());
//                obj.put("bio",editBio.getText().toString());

                obj.saveInBackground(new DoneCallback() {
                    @Override
                    public void done(NCMBException e) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(),"保存できませんでした。",Toast.LENGTH_LONG).show();
                            //エラー発生時の処理
                        } else {
                            Toast.makeText(getApplicationContext(),"保存できました。",Toast.LENGTH_LONG).show();
                            //成功時の処理
                        }
                    }
                });

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, baos);

                byte[] bytes = baos.toByteArray();
                NCMBFile profilePhoto = new NCMBFile(userName+"_profile.jpeg", bytes, new NCMBAcl());
                profilePhoto.saveInBackground(new DoneCallback() {
                    @Override
                    public void done(NCMBException e) {
                        if (e != null) {
                            //失敗
                            Toast.makeText(getApplicationContext(),"画像保存できませんでした。",Toast.LENGTH_LONG).show();
                        } else {
                            //成功
                            Toast.makeText(getApplicationContext(),"画像保存できました。",Toast.LENGTH_LONG).show();
                        }
                    }
                });

//                Intent intent = new Intent(getApplicationContext(),BaseActivity.class);
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });

        backFromEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                img = BitmapFactory.decodeStream(in);
                in.close();
                // 選択した画像を表示
                selectPhoto.setImageBitmap(img);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes = baos.toByteArray();
                NCMBFile profilrPhoto = new NCMBFile("Hello.txt", bytes, new NCMBAcl());
            } catch (Exception e) {

            }
        }

    }
    private void findViewById(){
//        toolbar = (Toolbar) findViewById(R.id.toolbarEditProfile);
        selectPhoto = (ImageView) findViewById(R.id.select_photo);
//        editName = (EditText) findViewById(R.id.edit_name);
//        editBio = (EditText) findViewById(R.id.edit_bio);

        saveEditProfile = (TextView) findViewById(R.id.saveEditProfile);
        backFromEditProfile = (TextView) findViewById(R.id.backFromEditProfile);
//        editBirthday = (EditText) findViewById(R.id.edit_birthday);
//        saveEditProfile = (TextView) findViewById(R.id.saveEditProfile);
//        backFromEditProfile = (TextView) findViewById(R.id.backFromEditProfile);
    }
}
