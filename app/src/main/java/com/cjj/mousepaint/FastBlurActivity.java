package com.cjj.mousepaint;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cjj.mousepaint.customview.FastBlur;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FastBlurActivity extends AppCompatActivity {

    @Bind(R.id.btn_start_blur)
    Button mBtnStartBlur;
    @Bind(R.id.iv_blur)
    ImageView mIvBlur;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_blur);
        ButterKnife.bind(this);

        mBtnStartBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvBlur.buildDrawingCache();
                Bitmap bitmap = FastBlur.doBlur(mIvBlur.getDrawingCache(), 20, true);
                mIvBlur.setImageBitmap(bitmap);
            }
        });
    }


//    private void RenderScriptblur(Bitmap sentBitmap, int radius) {
//
//        long start = System.currentTimeMillis();
//
//        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
//
//        final RenderScript rs = RenderScript.create(FastBlurActivity.this);
//        final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
//                Allocation.USAGE_SCRIPT);
//        final Allocation output = Allocation.createTyped(rs, input.getType());
//        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//        script.setRadius(radius /* e.g. 3.f */);
//        script.setInput(input);
//        script.forEach(output);
//        output.copyTo(bitmap);
//
//        mIvBlur.setImageBitmap(bitmap);
//        long end = System.currentTimeMillis();
//
//        Log.v("cjj_log", "fastblur time:" + (end - start));
//    }


}
