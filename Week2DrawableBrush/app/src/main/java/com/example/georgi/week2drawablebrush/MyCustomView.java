package com.example.georgi.week2drawablebrush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Georgi on 25.10.2014 г..
 */
public class MyCustomView extends View {

    private Bitmap.Config conf = Bitmap.Config.ARGB_8888;
    private Bitmap backingBitmap;
    private Canvas myCanvas;
    private BitmapDrawable bitmapDrawable;

    private MyTouchListener myTouchListener;
    private float x;
    private float y;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        backingBitmap = Bitmap.createBitmap(getWidth(), getHeight(), conf);
        myCanvas = new Canvas(backingBitmap);
        myTouchListener = new MyTouchListener();
        this.setOnTouchListener(myTouchListener);
    }

    public MyCustomView(Context context) {
        super(context);
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(backingBitmap, 0, 0, new Paint());
    }

    class MyTouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int action = event.getAction();

            x = event.getX();
            y = event.getY();

            Bitmap bm = bitmapDrawable.getBitmap();
            Paint paint = new Paint();
            paint.setAlpha(127);

            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    myCanvas.drawBitmap(bm, x, y, paint);
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    myCanvas.drawBitmap(bm, x, y, paint);
                    break;
                }
            }

            MyCustomView.this.invalidate();

            return true;
        }
    }

    public void setBitmapDrawable(BitmapDrawable bitmapDrawable) {
        this.bitmapDrawable = bitmapDrawable;
    }
}
