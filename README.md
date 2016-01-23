[![API](https://img.shields.io/badge/API-8%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=8)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ContinuableCircleCountDownView-green.svg?style=true)](https://android-arsenal.com/details/1/3057)


# ContinuableCircleCountDownView

Android custom view and progress for Continuable CountDownView.

You can feel free to contribute and add new features or bug fixes. Thank you.

# Screen 
<img src="https://media.giphy.com/media/l49FphqDgMaQDy1LW/giphy.gif" width="400" height="700"/>

# Usage 

You can define values on you XML file or you can make it programmatically. There are 7 values to customize
CountDownView. 

You can define maximum 60,000 milli seconds to cound down.


```java
mCountDownView = (ContinuableCircleCountDownView) findViewById(R.id.countDownView);
```  

```shapeRate```  must be between 6 or 15. The bigger rate means bigger inner circle radius.

```innerColor``` Color of inner circle

```outerColor``` Color of outer circle

```progressColor``` Color of progress

```textColor``` Color of text 

```progress``` Angle of progress. Define it from 0 to 360

```textSize``` Size of text  


Try these values yourself from example apk.

## XML Usage

```xml
  <com.serhatsurguvec.continuablecirclecountdownview.ContinuableCircleCountDownView
            android:id="@+id/circleCountDownView"
            android:layout_width="200dp"
            android:layout_height="200dp"
            android:layout_centerInParent="true"
            android:layout_gravity="center_horizontal"
            android:layout_marginBottom="20dp"
            android:layout_marginTop="20dp"
            app:shapeRate="9"
            app:innerColor="#02ADC6"
            app:outerColor="#02ADC6"
            app:progress="180"
            app:textColor="#FF000000"
            app:progressColor="#FFFF0000"
            app:textSize="23sp" />
```
##Listener
```java
mCountDownView.setListener(new ContinuableCircleCountDownView.OnCountDownCompletedListener() {
            @Override
            public void onTick(long passedMillis) {
                Log.w(TAG, "Tick." + passedMillis);
            }

            @Override
            public void onCompleted() {
                Log.w(TAG, "Completed.");
            }
        });
```

        
##  Methods

Set timer with ms.
```java
mCountDownView.setTimer(10000);
```

Start CountDownView
```java
mCountDownView.start();
```

Stop CountDownView
```java
mCountDownView.stop();
```

Continue CountDownView
```java
mCountDownView.continueE();
```

Cancel/Reset CountDownView
```java
mCountDownView.cancel();
```

Start CountDownView from a certain angle. You can animate view to reach that point.
```java
countDownView.startFrom(angle, isAnimate);
```

# Import
Project build.gradle

```
repositories {
    maven {
        url "https://jitpack.io"
    }
}
```

Module build.gradle
```
dependencies {
  compile 'com.github.SerhatSurguvec:continuablecirclecountdownview:v1.0'
}
```

Example Apk
------------

https://www.dropbox.com/s/cl000kx9ij5b9bj/app-debug.apk?dl=0

License
--------


    Copyright 2015 Serhat Sürgüveç.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


