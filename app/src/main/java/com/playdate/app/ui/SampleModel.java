package com.playdate.app.ui;

import android.graphics.Bitmap;

public class SampleModel {
    Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public SampleModel(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
