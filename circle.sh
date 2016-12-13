#!/bin/bash

# Fix the CircleCI path
function getAndroidSDK(){
  export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools:$PATH"

  DEPS="$ANDROID_HOME/installed-dependencies"

  if [ ! -e $DEPS ]; then
    cp -r /usr/local/android-sdk-linux $ANDROID_HOME &&
    echo y | android update sdk -u -a -t android-25 &&
    echo y | android update sdk -u -a -t platform-tools &&
    echo y | android update sdk -u -a -t build-tools-25.0.1 &&
    touch $DEPS
  fi
}
