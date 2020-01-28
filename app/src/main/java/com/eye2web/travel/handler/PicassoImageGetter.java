package com.eye2web.travel.handler;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.Gravity;
import android.widget.TextView;

import com.eye2web.travel.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PicassoImageGetter extends Activity implements Html.ImageGetter {

    private TextView textView;

    private Picasso picasso;

    public PicassoImageGetter(@NonNull TextView textView) {
        this.textView = textView;
    }

    @Override
    public Drawable getDrawable(String source) {
        BitmapDrawablePlaceHolder drawable = new BitmapDrawablePlaceHolder();

        Picasso.get().load(source).error(R.mipmap.logo_final).into(drawable);

        return drawable;
    }

    private class BitmapDrawablePlaceHolder extends BitmapDrawable implements Target {

        protected Drawable drawable;

        @Override
        public void draw(final Canvas canvas) {
            if(drawable != null) {
                checkBound();
                drawable.draw(canvas);
            }
        }

        public void setDrawable(@NonNull Drawable drawable) {
            if(drawable != null) {
                this.drawable = drawable;
                checkBound();
            }
        }

        private void checkBound() {
            Bitmap bm = ((BitmapDrawable) drawable).getBitmap();
            float defaultProportion = (float) bm.getWidth() / (float) bm.getHeight();
            int width = Math.min(textView.getWidth(), (bm.getWidth() * 2));
            int height = (int) ((float) width / defaultProportion);

            if(getBounds().right != textView.getWidth() || getBounds().bottom != height) {
                setBounds(0, 0, textView.getWidth(), height);

                int halfOfPlaceHolderWidth = (int)((float) getBounds().right / 2f);
                int halfOfImageWidth = (int)((float) width / 2f);

                drawable.setBounds(halfOfPlaceHolderWidth - halfOfImageWidth
                        , 0, (halfOfPlaceHolderWidth + halfOfImageWidth), height);
                textView.setText(textView.getText());
                textView.setGravity(Gravity.LEFT);
            }
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            setDrawable(new BitmapDrawable(bitmap));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            setDrawable(errorDrawable);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            setDrawable(placeHolderDrawable);
        }

    }
}
