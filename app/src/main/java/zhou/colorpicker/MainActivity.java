package zhou.colorpicker;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import zhou.colorpalette.ColorSelectActivity;
import zhou.colorpalette.ColorSelectDialog;
import zhou.colorpalette.ColorSelectDialogFragment;

/**
 * ColorSelectDemo Activity
 */
public class MainActivity extends Activity {

    private View view;
    private ColorSelectDialog colorSelectDialog;
    private int lastColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        view = findViewById(R.id.view);
    }

    public void Color(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (colorSelectDialog == null) {
                    colorSelectDialog = new ColorSelectDialog(this);
                    colorSelectDialog.setOnColorSelectListener(new ColorSelectDialog.OnColorSelectListener() {
                        @Override
                        public void onSelectFinish(int color) {
                            lastColor=color;
                            MainActivity.this.view.setBackgroundColor(lastColor);
                        }
                    });
                }
                colorSelectDialog.setLastColor(lastColor);
                colorSelectDialog.show();

                break;
            case R.id.button2:
                Intent intent = new Intent(this, ColorSelectActivity.class);
                intent.putExtra(ColorSelectActivity.LAST_COLOR,lastColor);
                startActivityForResult(intent, 0);
                break;
            case R.id.button3:
                ColorSelectDialogFragment colorSelectDialogFragment=new ColorSelectDialogFragment();
                colorSelectDialogFragment.setOnColorSelectListener(new ColorSelectDialogFragment.OnColorSelectListener() {
                    @Override
                    public void onSelectFinish(int color) {
                        lastColor=color;
                        MainActivity.this.view.setBackgroundColor(lastColor);
                    }
                });
                colorSelectDialogFragment.setLastColor(lastColor);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                colorSelectDialogFragment.show(ft, "colorSelectDialogFragment");
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                lastColor=data.getIntExtra(ColorSelectActivity.RESULT,0x000000);
                view.setBackgroundColor(lastColor);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
