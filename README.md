# PhotoPicker
[![Build Status](https://travis-ci.org/donglua/PhotoPicker.svg?branch=master)](https://travis-ci.org/donglua/PhotoPicker)

## Example

<p style="float:left;">
  <a href="https://play.google.com/store/apps/details?id=me.iwf.PhotoPickerDemo">
    <img alt="Get it on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_60.png" />
  </a>
</p>


## Usage

```java
  PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this, PhotoPickerActivity.class);
  intent.setPhotoCount(9);
  intent.setShowCamera(true);
  startActivityForResult(intent, REQUEST_CODE);
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
