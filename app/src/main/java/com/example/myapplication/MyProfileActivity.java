package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyProfileActivity extends AppCompatActivity {

    ImageView ivprofilepic;
    Button btnprofilepic;
    TextView tvname, tvid, tvnumber, tvgender, tvage, tvaddress, tvuserename, tvpassword;
    TextView tvtoken;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Uri imagePath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("My Profile");

        Toast.makeText(MyProfileActivity.this, "My Profile", Toast.LENGTH_SHORT).show();

        preferences = PreferenceManager.getDefaultSharedPreferences(MyProfileActivity.this);
        editor = preferences.edit();

        ivprofilepic = findViewById(R.id.ivprofilepic);
        btnprofilepic = findViewById(R.id.btnprofilepic);
        tvname = findViewById(R.id.tvprofilename);
        tvid = findViewById(R.id.tvprofileid);
        tvnumber = findViewById(R.id.tvprofilenumber);
        tvgender = findViewById(R.id.tvprofilegender);
        tvage = findViewById(R.id.tvprofileage);
        tvaddress = findViewById(R.id.tvprofileaddress);
        tvuserename = findViewById(R.id.tvprofileusername);
        tvpassword = findViewById(R.id.tvprofilepassword);
        tvtoken = findViewById(R.id.tvprofiletoken);

        String strName = preferences.getString("Name", "");
        String strEmailId = preferences.getString("EmailId", "");
        String strMobileNo = preferences.getString("MobileNo", "");
        String strGender = preferences.getString("Gender", "");
        String strAge = preferences.getString("Age", "");
        String strAddress = preferences.getString("Address", "");
        String strUserName = preferences.getString("UserName", "");
        String strPassword = preferences.getString("Password", "");

        tvname.setText(strName);
        tvid.setText(strEmailId);
        tvnumber.setText(strMobileNo);
        tvgender.setText(strGender);
        tvage.setText(strAge);
        tvaddress.setText(strAddress);
        tvuserename.setText(strUserName);
        tvpassword.setText(strPassword);

        File imageFile = new File(getFilesDir(), "profile.jpg");
        if (imageFile.exists()) {
            Bitmap savedBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            Bitmap circularBitmap = getRoundedBitmap(savedBitmap);
            ivprofilepic.setImageBitmap(circularBitmap);
        }

        btnprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MyProfileActivity.this, "FCM token not received", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String token = task.getResult();
                        tvtoken.setText(token);
                        Toast.makeText(MyProfileActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Bitmap getRoundedBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int radius = Math.min(width, height) / 2;

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        RectF rect = new RectF(0, 0, width, height);
        Path path = new Path();
        path.addCircle(width / 2, height / 2, radius, Path.Direction.CCW);

        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return output;
    }

    private void saveData() {
        editor.putString("Name", tvname.getText().toString().trim());
        editor.putString("EmailId", tvid.getText().toString().trim());
        editor.putString("MobileNo", tvnumber.getText().toString().trim());
        editor.putString("Gender", tvgender.getText().toString().trim());
        editor.putString("Age", tvage.getText().toString().trim());
        editor.putString("Address", tvaddress.getText().toString().trim());
        editor.putString("UserName", tvuserename.getText().toString().trim());
        editor.putString("Password", tvpassword.getText().toString().trim());
        editor.apply();
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Photo"), 999);
    }

    @Override
    public void onBackPressed() {
        saveData();
        Intent i = new Intent(MyProfileActivity.this, loginactivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK && data != null) {
            imagePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                Bitmap circularBitmap = getRoundedBitmap(bitmap);
                File file = new File(getFilesDir(), "profile.jpg");
                FileOutputStream outputStream = new FileOutputStream(file);
                circularBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();

                ivprofilepic.setImageBitmap(circularBitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
