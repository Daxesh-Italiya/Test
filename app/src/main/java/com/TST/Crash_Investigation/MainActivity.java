package com.TST.Crash_Investigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.TST.Crash_Investigation.Function.Image_table;
import com.TST.Crash_Investigation.object.ShowCamara;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSION =101;
    private String[] REQUEST_PERMISSION=new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};

    FrameLayout mframeLayout;
    ImageView image;
    private Camera mCamera;
    ShowCamara showCamara;
    Image_table image_table;
    private String Path1,Path2,Path3,Path4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Define();

        if(allPermissionGranted())
        {
            mframeLayout=(FrameLayout)findViewById(R.id.View_finder);
            mCamera= Camera.open();
            showCamara = new ShowCamara(this, mCamera);
            mframeLayout.addView(showCamara);
        }else
        {
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSION,REQUEST_CODE_PERMISSION);
        }
    }

    private void Define() {
        image_table= new Image_table(MainActivity.this);
        image=(ImageView) findViewById(R.id.left);
    }

    private boolean allPermissionGranted() {
        for (String  permission: REQUEST_PERMISSION)
        {
            if (ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }

        return true;
    }

    Camera.PictureCallback mP=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile= getOutputMediaFile();
            if(pictureFile==null)
            {
                return;
            }else
            {
                try {
                    FileOutputStream foe = new FileOutputStream(pictureFile);
                    foe.write(data);
                    foe.close();

                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

        }
    };

    private File getOutputMediaFile() {
        String state= Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(MainActivity.this,"Muted",Toast.LENGTH_SHORT).show();
            return  null;
        }
        else
        {
            File folder_gui=new File(getFilesDir()+File.separator+"GUI");
            if(!folder_gui.exists())
            {
                Toast.makeText(MainActivity.this,String.valueOf(folder_gui.mkdir()),Toast.LENGTH_SHORT).show();
            }
            File outputFile=new File(folder_gui,System.currentTimeMillis()+".jpg");
            return outputFile;
        }
    }

    public void Capture(View view)
    {
        if(mCamera!=null)
        {
            mCamera.takePicture(null,null,mP);
        }
    }
}
