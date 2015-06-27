package zhou.colorpalette.core;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by zzhoujay on 2015/2/17 0017.
 */
public class ColorPalette extends LinearLayout{

    private ColorBoard colorBoard;//色块拾取器
    private ColorPicker colorPicker;//色轮拾取器
    private View lastColor,currColor;//上次选择的颜色，当前选择的颜色
    private SeekBar alphaSeekBar,lightSeekBar;//透明度、亮度seekbar
    private TextView alphaStart,alphaEnd,lightStart,lightEnd;
    private int alpha=255,light=255,red,green,blue,selectColor,lastSelectColor=Color.WHITE;
    private OnColorSelectListener onColorSelectListener;

    public ColorPalette(Context context) {
        this(context, null);
    }

    public ColorPalette(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPalette(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setPadding(5,10,5,2);

        colorBoard=new ColorBoard(context);
        colorPicker=new ColorPicker(context);
        lastColor=new View(context);
        currColor=new View(context);
        alphaSeekBar=new SeekBar(context);
        lightSeekBar=new SeekBar(context);

        alphaStart=new TextView(context);
        alphaEnd=new TextView(context);
        lightStart=new TextView(context);
        lightEnd=new TextView(context);

        alphaEnd.setGravity(Gravity.CENTER_VERTICAL);
        alphaStart.setGravity(Gravity.CENTER_VERTICAL);
        lightEnd.setGravity(Gravity.CENTER_VERTICAL);
        lightStart.setGravity(Gravity.CENTER_VERTICAL);

        alphaStart.setText("A:");
        alphaEnd.setText("255");
        lightStart.setText("L:");
        lightEnd.setText("255");


        alphaSeekBar.setMax(255);
        lightSeekBar.setMax(255);
        alphaSeekBar.setProgress(255);
        lightSeekBar.setProgress(255);

        alphaSeekBar.setFocusable(false);
        lightSeekBar.setFocusable(false);

        lastColor.setBackgroundColor(Color.WHITE);
        currColor.setBackgroundColor(Color.WHITE);

        //ColorPicker
        LayoutParams p1=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,0,1);
        //SeekbarLayout
        LayoutParams p2=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //ColorLayout
        LayoutParams p3=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        //ColorBoardLayout
        LayoutParams p4=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LayoutParams p33=new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);

        p33.setMargins(40,20,40,20);
        p2.setMargins(10,10,10,10);

        LinearLayout colorColor=new LinearLayout(context);
        colorColor.setMinimumHeight(120);
        colorColor.setOrientation(HORIZONTAL);

        colorColor.addView(lastColor,p33);
        colorColor.addView(currColor,p33);

        LinearLayout alphaLayout=new LinearLayout(context);
        LinearLayout lightLayout=new LinearLayout(context);

        //SeekbarText
        LayoutParams px=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //SeekBar
        LayoutParams pppp=new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);


        px.setMargins(10,0,10,0);


        alphaLayout.addView(alphaStart,px);
        alphaLayout.addView(alphaSeekBar,pppp);
        alphaLayout.addView(alphaEnd,px);

        lightLayout.addView(lightStart,px);
        lightLayout.addView(lightSeekBar,pppp);
        lightLayout.addView(lightEnd,px);

        addView(colorPicker, p1);
        addView(alphaLayout,p2);
        addView(lightLayout,p2);
        addView(colorColor,p3);
        addView(colorBoard,p4);

        setClickable(true);

        initEvent();

    }

    private void initEvent() {
        colorPicker.setOnColorSelectListener(new ColorPicker.OnColorSelectListener() {
            @Override
            public void onColorSelect(int color) {
                alpha=Color.alpha(color);
                red=Color.red(color);
                green=Color.green(color);
                blue=Color.blue(color);

                reflashColor();
            }
        });

        alphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alpha=progress;
                alphaEnd.setText(""+progress);
                reflashColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                light=progress;
                lightEnd.setText(""+progress);
                reflashColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lastColor.setClickable(true);
        lastColor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectColor=lastSelectColor;
                currColor.setBackgroundColor(selectColor);
            }
        });

        colorBoard.setOnColorSelectListener(new ColorBoard.OnColorSelectListener() {
            @Override
            public void onColorSelect(int color) {
                selectColor=color;
                currColor.setBackgroundColor(selectColor);
            }
        });
    }

    private int getColor(){
        float d=light/255.0f;
        return Color.argb(alpha, (int) (red * d), (int) (green * d), (int) (blue * d));
    }

    private void reflashColor(){
        selectColor=getColor();
        currColor.setBackgroundColor(selectColor);
    }

    public void setLastColor(int color){
        this.lastSelectColor=color;
        lastColor.setBackgroundColor(color);
    }

    public void setCurrColor(int color){
        this.selectColor=color;
        currColor.setBackgroundColor(color);
    }

    public int getSelectColor(){
        return selectColor;
    }

    public static interface OnColorSelectListener{
        public void onColorSelect(int color);
    }

    public OnColorSelectListener getOnColorSelectListener() {
        return onColorSelectListener;
    }

    public void setOnColorSelectListener(OnColorSelectListener onColorSelectListener) {
        this.onColorSelectListener = onColorSelectListener;
    }

    public void recycle(){
        if(colorPicker!=null){
            if(!colorPicker.isRecycled()){
                colorPicker.recycle();
            }
        }
    }

    public boolean isRecycle(){
        return colorPicker==null||colorPicker.isRecycled();
    }
}
