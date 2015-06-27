#ColorSelector4Android

##ColorSelector4Android是安卓平台下的颜色选择器

* 使用Android Studio开发，依赖本库也只能在Android Studio，API等级为19
* 已封装好Activity、DialogFragment、AlertDialog等形式供直接使用
* 也可以直接在自己的程序里内嵌ColorPalette来使用
* 若不需要自定义可直接依赖aar包，需自定义可导入Module：ColorPalette自行修改源码
* 无其他依赖项

###Activity模式使用方法

启动ColorSelectActivity

```java
    //生成一个Intent指向ColorSelectActivity
    Intent intent = new Intent(this, ColorSelectActivity.class);
    //在Intent中放入上一次的颜色数据
    intent.putExtra(ColorSelectActivity.LAST_COLOR,lastColor);
    //启动ColorSelectActivity
    startActivityForResult(intent, 0);
```

接收返回的数据

```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //通过ColorSelectActivity.RESULT这个键来取值
                lastColor=data.getIntExtra(ColorSelectActivity.RESULT,0x000000);
                view.setBackgroundColor(lastColor);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
```

####注意在使用ColorSelectActivity时记得在自己项目的AndroidManifest文件中加入：

```xml
    <activity android:name="zhou.colorpalette.ColorSelectActivity"/>
```

![Activity模式](http://git.oschina.net/uploads/images/2015/0523/164516_70d13e80_141009.png "Activity模式")

###DialogFragment模式使用方法

```java
    ColorSelectDialogFragment colorSelectDialogFragment=new ColorSelectDialogFragment();
    //设置颜色选择完成后的回调事件
    colorSelectDialogFragment.setOnColorSelectListener(new ColorSelectDialogFragment.OnColorSelectListener() {
        @Override
        public void onSelectFinish(int color) {
            lastColor=color;
            MainActivity.this.view.setBackgroundColor(lastColor);
        }
    });
    //设置上次选择的颜色（可选）
    colorSelectDialogFragment.setLastColor(lastColor);
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    //需要打开时调用show方法
    colorSelectDialogFragment.show(ft, "colorSelectDialogFragment");
```

![DialogFragment模式](http://git.oschina.net/uploads/images/2015/0523/164557_53236ec5_141009.png "DialogFragment模式")

###AlertDialog模式使用方法

```java
    ColorSelectDialog colorSelectDialog = new ColorSelectDialog(this);
    //绑定颜色选择完成后的回调事件
    colorSelectDialog.setOnColorSelectListener(new ColorSelectDialog.OnColorSelectListener() {
        @Override
        public void onSelectFinish(int color) {
            // ...
        }
    });
    //设置上次的颜色（可选）
    colorSelectDialog.setLastColor(lastColor);
    //要显示Dialog时别忘了调用show方法
    colorSelectDialog.show();
```

![ALertDialog模式](http://git.oschina.net/uploads/images/2015/0523/164645_147874e6_141009.png "AlertDialog模式")

##具体操作请查看Demo

_by zzhoujay_
