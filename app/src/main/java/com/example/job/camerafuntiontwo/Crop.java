package com.example.job.camerafuntiontwo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Crop extends AppCompatActivity {

private Uri picUri;
File f;
Button btn_Camera;
ImageView im_crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        im_crop=(ImageView)findViewById(R.id.im_crop);

        btn_Camera=(Button)findViewById(R.id.btn_camera);
        btn_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                            //capturing the image
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(android.os.Environment.getExternalStorageDirectory(), "makegifimage.jpg");
                    picUri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                    startActivityForResult(intent, 1);

                } catch (ActivityNotFoundException anfe) {

                    Toast.makeText(getApplicationContext(),"couldn't open your camera",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                CropImage();

            } else if (requestCode == 2) {

                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);
                ImageView im_crop = (ImageView) findViewById(R.id.im_crop);
                im_crop.setImageBitmap(bitmap);

            }
        }

    }

    private void CropImage() {
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 2);
            cropIntent.putExtra("aspectY", 2);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, 2);
        }
        catch (ActivityNotFoundException e) {

            Toast.makeText(this, "Your device is not supporting the crop action", Toast.LENGTH_SHORT);

        }
    }
}
