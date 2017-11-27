#### 平时使用的libs

#### Core

- 主要是包括平时用到的一些工具类
  - AppUtils 
  - DateUtils
  - DensityUtils
  - DialogUtils
  - KeyBoardUtils
  - Log  这个里面有一个是否打印日志的标识
  - RegularUtils 这个是正则表达式的工具类
  - ScreenUtils 

---



#### acp

- 这个是权限请求
- [项目源地址](https://github.com/mylhyl/AndroidAcp)

**eg:**

```java
String[] PERMISSIONS = new String[]{
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
};
AcpSignle.requestPermisstions(
  context, 
  permisstions, 
  new AcpSignle.RequestCallBack() {
            @Override
            public void suceess() {
                //获得权限
            }

            @Override
            public void error() {
                //获取权限失败
            }
});
```

---

#### circleimageview

- 圆角图片

- [项目源地址](https://github.com/hdodenhof/CircleImageView)

- 修改了BitmapConfig

  ```java
  Bitmap.Config BITMAP_CONFIG = Bitmap.Config.RGB_888;
  改成了
  Bitmap.Config BITMAP_CONFIG = Bitmap.Config.RGB_565;
  ```

- 列表不建议使用这个，容易oom

---

#### citypicker

- 选择城市

**eg:**

```java
Intent intent = new Intent();
intent.setClass(context,ChoiseCityActivity.class);
context.startActivityForResult(intent,0x0001);

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 0x0001){
            final String address = data.getStringExtra(ChoiseCityActivity.KEY_PICKED_CITY);
   }
}
```



---

#### image_corpper

- 图片裁剪

**eg:**

```java
CropImage.activity(sorceUri,tagetUri)
     .setGuidelines(CropImageView.Guidelines.ON)
     .start(activity);
```



---

#### ptr-lib

- 下拉刷新
- 加载成功失败都只需要调用adapter的notifyDataSetChanged就行

**eg:**

```java
recyclerView.setLoadDataInterface(new PtrRecyclerView.PtrRecyclerViewInterface(){
  	public void loadData(boolean isFrist) {
      
  	}
  
  	public boolean haveMoreData() {
        
    }
  
});
recyclerView.fristLoadData();

```



---



#### wheelpickerview

- 滚轮选择

---

#### 添加依赖

```xml
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
       
    }
}

...

compile 'com.github.yudapiandroid.zhongliaolibs:core:1.7'
compile 'com.github.yudapiandroid.zhongliaolibs:wheelpickerview:1.7'
compile 'com.github.yudapiandroid.zhongliaolibs:ptr-lib:1.7'
compile 'com.github.yudapiandroid.zhongliaolibs:spinkit_library:1.7'
compile 'com.github.yudapiandroid.zhongliaolibs:image_corpper:1.7'
compile 'com.github.yudapiandroid.zhongliaolibs:gsyvideoplayer:1.7'
compile 'com.github.yudapiandroid.zhongliaolibs:mpchartlib:1.7'
compile 'com.github.yudapiandroid.zhongliaolibs:acp:1.7'
compile 'com.github.yudapiandroid.zhongliaolibs:citypicker:1.7'
compile 'com.github.yudapiandroid.zhongliaolibs:circleimageview:1.7'
```

