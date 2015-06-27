package zhou.colorpalette.core;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by zzhoujay on 2015/2/20 0020.
 * 颜色选择板
 */
public class ColorBoard extends LinearLayout {

    public static final int widthSize=5;//色块横向数目
    public static final int heightSize=2;//色块纵向数目
    public static final int colorHeight=100;//色块的最小高度
    public static final int colorWidth=120;//色块的最小宽度
    private LayoutParams params,ppp;
    private LinearLayout[] layouts;
    private View[][] colors;//色块
    private int[][] colorValue;//色块的颜色
    private OnColorSelectListener onColorSelectListener;

    public ColorBoard(Context context) {
       this(context, null);
    }

    public ColorBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);


        layouts=new LinearLayout[heightSize];
        ppp=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);


        for(int i=0;i<heightSize;i++){
            layouts[i]=new LinearLayout(context);
            layouts[i].setOrientation(HORIZONTAL);
            addView(layouts[i],ppp);
        }

        params=new LayoutParams(colorWidth,colorHeight,1);
        params.setMargins(5,5,5,5);

        colors=new View[heightSize][widthSize];
        colorValue=new int[heightSize][widthSize];


        for(int i=0;i<heightSize;i++){
            for(int j=0;j<widthSize;j++){
                colors[i][j]=new View(context);
                colors[i][j].setClickable(true);
                layouts[i].addView(colors[i][j], params);
                final int finalI = i;
                final int finalJ = j;
                colors[i][j].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onColorSelectListener!=null){
                            onColorSelectListener.onColorSelect(colorValue[finalI][finalJ]);
                            Log.d("colorBoard",""+colorValue[finalI][finalJ]);
                        }
                    }
                });
            }
        }

        colorValue[0][0]=Color.RED;
        colorValue[0][1]=Color.YELLOW;
        colorValue[0][2]=Color.GREEN;
        colorValue[0][3]=Color.GRAY;
        colorValue[0][4]=Color.BLUE;
        colorValue[1][0]=Color.LTGRAY;
        colorValue[1][1]=Color.DKGRAY;
        colorValue[1][2]=Color.MAGENTA;
        colorValue[1][3]=Color.WHITE;
        colorValue[1][4]=Color.BLACK;

        resetColor();

    }


    private void resetColor(){
        for(int i=0;i<heightSize;i++){
            for(int j=0;j<widthSize;j++){
                colors[i][j].setBackgroundColor(colorValue[i][j]);
            }
        }
    }

    //设置色块的颜色
    public void setColors(int[][] cs){
        for(int i=0;i<heightSize;i++){
            for(int j=0;j<widthSize;j++){
                colorValue[i][j]=cs[i][j];
                colors[i][j].setBackgroundColor(cs[i][j]);
            }
        }
    }

    //设置某个色块的颜色
    public void setColor(int x,int y,int color){
        colorValue[x][y]=color;
        colors[x][y].setBackgroundColor(color);
    }

    //获取某个色块的颜色
    public int getColor(int x,int y){
        return colorValue[x][y];
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
}
