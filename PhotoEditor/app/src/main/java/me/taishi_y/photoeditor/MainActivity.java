package me.taishi_y.photoeditor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.adobe.creativesdk.aviary.AdobeImageIntent;

import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    /* 1) Add a member variable for our Image View */
    private ImageView mEditedImageView;

    private static final int RESULT_PICK_IMAGEFILE = 1001;
    private Button button;
    private TextView dcimPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        /* 1) Make a new Uri object (Replace this with a real image on your device) */
//        Uri imageUri = Uri.parse("content://media/external/images/media/####");

//         /* 2) Create a new Intent */
//        Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
//                .setData(imageUri)
//                .build();
//
//        /* 3) Start the Image Editor with request code 1 */
//        startActivityForResult(imageEditorIntent, 1);




        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                // Filter to only show results that can be "opened", such as a
                // file (as opposed to a list of contacts or timezones)
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                // Filter to show only images, using the image MIME data type.
                // it would be "*/*".
                intent.setType("image/*");

                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
            }
        });


    }


    private String getGalleryPath() {
        return Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("", "Uri: " + uri.toString());

                /* 2) Create a new Intent */
            Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
                .setData(uri)
                .build();

            /* 3) Start the Image Editor with request code 1 */
            startActivityForResult(imageEditorIntent, 1);

            }
        }

    }


//    /* 3) Handle the results */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//
//                /* 4) Make a case for the request code we passed to startActivityForResult() */
//                case 1:
//
//                    /* 5) Show the image! */
//                    Uri editedImageUri = data.getData();
//
//                    Toast.makeText(getApplicationContext(),editedImageUri.toString(),Toast.LENGTH_SHORT)
//
//                    /* 2) Create a new Intent */
//                    Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
//                    .setData(editedImageUri)
//                    .build();
//
//                    /* 3) Start the Image Editor with request code 1 */
//                    startActivityForResult(imageEditorIntent, 1);
//
//
////                    mEditedImageView.setImageURI(editedImageUri);
//
//                    break;
//            }
//        }
//
//        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
//        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
//        // response to some other intent, and the code below shouldn't run at all.
////        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
////            // The document selected by the user won't be returned in the intent.
////            // Instead, a URI to that document will be contained in the return intent
////            // provided to this method as a parameter.
////            // Pull that URI using resultData.getData().
////            Uri uri = null;
////            if (data != null) {
////                uri = data.getData();
////                Log.i("", "Uri: " + uri.toString());
////
////                try {
////                    Bitmap bmp = getBitmapFromUri(uri);
////                    mEditedImageView.setImageBitmap(bmp);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
////        }
//    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
