package zhou.colorpalette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import zhou.colorpalette.core.ColorPalette;

/**
 * 颜色选择Activity
 */
public class ColorSelectActivity extends Activity {

    public static final String RESULT = "result";
    public static final String LAST_COLOR = "last_color";

    private ColorPalette colorPalette;
    private Class aClass;
    private int lastColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhou_activity_color);
        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        aClass = intent.getClass();

        lastColor = intent.getIntExtra(LAST_COLOR, 0x000000);

        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case 10086:
                int color = colorPalette.getSelectColor();
                finishAndBack(color);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 10086, 0, "确定");
        item.setIcon(R.drawable.ic_action_accept);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    private void initView() {
        colorPalette = (ColorPalette) findViewById(R.id.zhou_colorpalette);
        colorPalette.setLastColor(lastColor);
        colorPalette.setCurrColor(lastColor);
    }



    private void finishAndBack(int color) {
        if (!colorPalette.isRecycle()) {
            colorPalette.recycle();
            System.gc();
        }
        Intent intent = new Intent(getApplicationContext(), aClass);
        intent.putExtra(RESULT, color);
        setResult(RESULT_OK, intent);
        finish();
    }
}
