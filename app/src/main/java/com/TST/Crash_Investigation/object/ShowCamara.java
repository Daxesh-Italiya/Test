package com.TST.Crash_Investigation.object;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class ShowCamara extends SurfaceView implements SurfaceHolder.Callback {

    private Camera mycamera;
    private SurfaceHolder holder;
    private Context context;

    public ShowCamara(Context context, Camera camera) {
        super(context);
        mycamera=camera;
        holder=getHolder();
        holder.addCallback(this);
        this.context=context;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Camera.Parameters params = mycamera.getParameters();
        mycamera.stopPreview();
        List<Camera.Size> sizes= params.getSupportedPictureSizes();
        Camera.Size msize = null;


        msize=sizes.get(0);
//        Toast.makeText(context,String.valueOf(msize.width),Toast.LENGTH_SHORT).show();


//        Toast.makeText(context,String.valueOf(msize.width),Toast.LENGTH_SHORT).show();
        if(this.getResources().getConfiguration().orientation!= Configuration.ORIENTATION_LANDSCAPE)
        {
            params.set("orientation","portrait");
            mycamera.setDisplayOrientation(90);
            params.setRotation(0);
        }else
        {
            params.set("orientation","landscape");
            mycamera.setDisplayOrientation(0);
            params.setRotation(0);
        }

        params.setPictureSize(msize.width,msize.height);
        params.setAutoWhiteBalanceLock(true);
        if (params.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }

//        mycamera.setAutoFocusMoveCallback((Camera.AutoFocusMoveCallback) myAutoFocusCallback);
        mycamera.setParameters(params);

        try {
            mycamera.setPreviewDisplay(holder);
            mycamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mycamera.stopPreview();
        mycamera.release();
    }
}
