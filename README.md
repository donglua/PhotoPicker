[![Android Gems](http://www.android-gems.com/badge/donglua/PhotoPicker.svg?branch=master)](http://www.android-gems.com/lib/donglua/PhotoPicker)

# PhotoPicker
[![Build Status](https://travis-ci.org/donglua/PhotoPicker.svg?branch=master)](https://travis-ci.org/donglua/PhotoPicker)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PhotoPicker-green.svg?style=flat)](https://android-arsenal.com/details/1/2091)

## Example
![](http://ww2.sinaimg.cn/large/5e9a81dbgw1etra5iu80lj206z0cet8r.jpg)
![](http://ww2.sinaimg.cn/large/5e9a81dbgw1etra61rnr9j206z0ce3yu.jpg)
![](http://ww4.sinaimg.cn/large/5e9a81dbgw1etra6efl1hj206z0cewet.jpg)
![](http://ww3.sinaimg.cn/large/5e9a81dbgw1etra6q2edzj206z0cedgg.jpg)

<p style="float:left;">
  <a href="https://play.google.com/store/apps/details?id=me.iwf.PhotoPickerDemo">
    <img alt="Get it on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_60.png" />
  </a>
</p>


## Usage

#### Gradle
```groovy    
dependencies {
    compile 'me.iwf.photopicker:PhotoPicker:0.1.8'
}
``` 
Or

```groovy
dependencies {
    compile 'me.iwf.photopicker:PhotoPicker:0.2.0@aar'
    
    compile 'com.android.support:appcompat-v7:22.2.0'
    compile 'com.android.support:recyclerview-v7:22.2.0'
    compile 'com.nineoldandroids:library:2.4.0'
    compile 'com.android.support:design:22.2.0'
    compile 'com.github.bumptech.glide:glide:3.6.0'
}
```

#### Pick Photo
```java
PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
intent.setPhotoCount(9);
intent.setShowCamera(true);
startActivityForResult(intent, REQUEST_CODE);
```

#### Preview Photo

```java
Intent intent = new Intent(mContext, PhotoPagerActivity.class);
intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, position);
intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, photoPaths);
startActivityForResult(intent, REQUEST_CODE);
```

#### onActivityResult
```java
@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);

  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    if (data != null) {
      ArrayList<String> photos = 
          data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
    }
  }
}
```

#### manifest
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    >
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
  <uses-feature android:name="android.hardware.camera" android:required="true" />

  <application
    ...
    >
    ...
    
    <activity android:name="me.iwf.photopicker.PhotoPickerActivity"
      android:theme="@style/Theme.AppCompat.NoActionBar" 
       />

    <activity android:name="me.iwf.photopicker.PhotoPagerActivity"
      android:theme="@style/Theme.AppCompat.NoActionBar"/>
    
  </application>
</manifest>
```

## License

    Copyright 2015 Huang Donglu

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
