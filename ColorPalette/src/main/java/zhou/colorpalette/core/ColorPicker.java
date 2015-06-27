package zhou.colorpalette.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import zhou.colorpalette.R;

/**
 * Created by zzhoujay on 2015/2/20 0020.
 * 色轮调色板
 */
public class ColorPicker extends ImageView implements View.OnTouchListener {

    private Bitmap bp;//色轮图片
    private int bw, bh;//色轮图片的尺寸
    private float x, y, radio;
    private OnColorSelectListener onColorSelectListener;
    private Paint paint;

    public ColorPicker(Context context) {
        this(context, null);
    }

    public ColorPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        bp = BitmapFactory.decodeResource(context.getResources(), R.drawable.circle);

        bw = bp.getWidth();
        bh = bp.getHeight();

        setImageBitmap(bp);

        setOnTouchListener(this);

        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);

        setClickable(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float xx = event.getX();
        float yy = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!inCircle(xx, yy)) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!inCircle(xx, yy)) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!inCircle(xx, yy)) {
                    return false;
                }
                break;
        }
        x = xx;
        y = yy;
        invalidate();
        if (onColorSelectListener != null) {
            onColorSelectListener.onColorSelect(getColor(xx, yy));
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        x = getWidth() / 2;
        y = getHeight() / 2;
        radio = x > y ? y : x;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPoint(x, y, paint);
    }

    private int getColor(float x, float y) {

        this.x = x;
        this.y = y;

        int w = getWidth();
        int h = getHeight();

        int dx = (int) ((x / w) * bw);
        int dy = (int) ((y / h) * bh);

        int color = Color.BLACK;

        try {
            color = bp.getPixel(dx, dy);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return color;
    }

    private boolean inCircle(float x, float y) {
        float cx = getWidth() / 2;
        float cy = getHeight() / 2;
        float d = (float) Math.abs(Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy)));
        return d <= radio;
    }

    public static interface OnColorSelectListener {
        public void onColorSelect(int color);
    }

    public OnColorSelectListener getOnColorSelectListener() {
        return onColorSelectListener;
    }

    public void setOnColorSelectListener(OnColorSelectListener onColorSelectListener) {
        this.onColorSelectListener = onColorSelectListener;
    }

    /**
     * 回收Bitmap内存
     */
    public void recycle() {
        if (bp != null) {
            if (!bp.isRecycled()) {
                bp.recycle();
            }
            bp = null;
        }
    }

    public boolean isRecycled(){
        return bp == null || bp.isRecycled();
    }

}
