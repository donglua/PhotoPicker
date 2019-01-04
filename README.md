
# PhotoPicker
[![CircleCI](https://circleci.com/gh/donglua/PhotoPicker/tree/master.svg?style=svg)](https://circleci.com/gh/donglua/PhotoPicker/tree/master)
[![Build Status](https://travis-ci.org/donglua/PhotoPicker.svg?branch=master)](https://travis-ci.org/donglua/PhotoPicker)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PhotoPicker-green.svg?style=flat)](https://android-arsenal.com/details/1/2091)
[ ![Download](https://api.bintray.com/packages/donglua/maven/PhotoPicker/images/download.svg) ](https://bintray.com/donglua/maven/PhotoPicker/_latestVersion)
[![API](https://img.shields.io/badge/API-10%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)

---

# DEPRECATED

PhotoPicker is deprecated.

> * 本项目已废弃，仍可使用，但不推荐；推荐使用 [zhihu/Matisse](https://github.com/zhihu/Matisse)。
> * 本项目建于2015年初，是最早的图片选择库之一。由于目前同类开源项目很多，也做得更完善，加上迁移成本低，本项目没有继续维护的必要，感谢大家支持。

# Example
![](http://ww2.sinaimg.cn/large/5e9a81dbgw1etra61rnr9j206z0ce3yu.jpg)
![](http://ww3.sinaimg.cn/large/5e9a81dbgw1etra6q2edzj206z0cedgg.jpg)
![](http://ogkb67oc8.bkt.clouddn.com/F028B942CF5978D900B15033941478B7.jpg?imageView2/2/w/250/)

<p style="float:left;">
 <a href="https://play.google.com/store/apps/details?id=me.iwf.PhotoPickerDemo&utm_source=global_co&utm_medium=prtnr&utm_content=Mar2515&utm_campaign=PartBadge&pcampaignid=MKT-AC-global-none-all-co-pr-py-PartBadges-Oct1515-1">
 <img HEIGHT="80" WIDTH="270" alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/apps/en-play-badge.png" />
 </a>
</p>

---

# Usage

### Gradle

```groovy
dependencies {
    compile 'me.iwf.photopicker:PhotoPicker:0.9.12@aar'
    
    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:recyclerview-v7:23.4.0'
    compile 'com.android.support:design:23.4.0'
    compile 'com.github.bumptech.glide:glide:4.1.1'
}
```
* ```appcompat-v7```version >= 23.0.0

### eclipse
[![GO HOME](http://ww4.sinaimg.cn/large/5e9a81dbgw1eu90m08v86j20dw09a3yu.jpg)

### Pick Photo
```java
PhotoPicker.builder()
    .setPhotoCount(9)
    .setShowCamera(true)
    .setShowGif(true)
    .setPreviewEnabled(false)
    .start(this, PhotoPicker.REQUEST_CODE);
```

### Preview Photo

```java
ArrayList<String> photoPaths = ...;

PhotoPreview.builder()
    .setPhotos(selectedPhotos)
    .setCurrentItem(position)
    .setShowDeleteButton(false)
    .start(MainActivity.this);
```

### onActivityResult
```java
@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);

  if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
    if (data != null) {
      ArrayList<String> photos = 
          data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
    }
  }
}
```

### manifest
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    >
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.CAMERA" />
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
### Custom style
```xml
<style name="actionBarTheme" parent="ThemeOverlay.AppCompat.Dark.ActionBar">
  <item name="android:textColorPrimary">@android:color/primary_text_light</item>
  <item name="actionBarSize">@dimen/actionBarSize</item>
</style>

<style name="customTheme" parent="Theme.AppCompat.Light.NoActionBar">
  <item name="actionBarTheme">@style/actionBarTheme</item>
  <item name="colorPrimary">#FFA500</item>
  <item name="actionBarSize">@dimen/actionBarSize</item>
  <item name="colorPrimaryDark">#CCa500</item>
</style>
```

### Proguard

```
# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
# support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
# support-design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
```

---


# License

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



