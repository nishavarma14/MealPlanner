package com.example.myapplication;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRActivity extends AppCompatActivity {

    ImageView ivqrcode;
    private static final int QRWidth = 300;
    private static final int QRHeight = 300;

    Bitmap bitmap;
    SharedPreferences preferences;
    String strusername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qractivity);

        preferences = PreferenceManager.getDefaultSharedPreferences(QRActivity.this);
        strusername = preferences.getString("username", "");

        ivqrcode = findViewById(R.id.ivqrcode);
        setTitle("QR Code");

        try {
            createQRCode();
        }
        catch (WriterException e)
        {
            Toast.makeText(QRActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void createQRCode() throws WriterException {
        bitmap = textToImageEncode(strusername);
        ivqrcode.setImageBitmap(bitmap);
    }

    private Bitmap textToImageEncode(String strusername) throws WriterException{
        BitMatrix bitMatrix;
        bitMatrix = new MultiFormatWriter().encode(strusername, BarcodeFormat.QR_CODE,QRWidth,QRHeight);

        int[] pixels = new int[bitMatrix.getWidth()*bitMatrix.getHeight()];

        for (int x= 0;x<bitMatrix.getWidth();x++)
        {
            int offset = x*bitMatrix.getHeight();

            for (int y=0;y<bitMatrix.getHeight();y++)
            {
                pixels[offset+y] = bitMatrix.get(x,y)?getResources().getColor(R.color.black):
                        getResources().getColor(R.color.white);
            }
        }

        bitmap = Bitmap.createBitmap(bitMatrix.getWidth(),bitMatrix.getHeight(),Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels,0,bitMatrix.getWidth(),0,0,bitMatrix.getWidth(),bitMatrix.getHeight());
         return bitmap;
    }
}

