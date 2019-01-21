# LoadViewHelper
布局切换库，加载、空白、错误，可自定义布局类型。 

![展示](https://raw.githubusercontent.com/cunmin/LoadViewHelper/master/5wbex-chpku.gif)
## Setup

要使用这个库 `minSdkVersion`  >= 14.

```gradle
allprojects {
    repositories {
        ...
        jcenter()//一般android studio新建项目都会自动加这行引进这个仓库的
    }
}

dependencies {
    implementation 'com.littleyellow:loadviewhelper:1.0.0'
}
```

## Usage
- 1.添加全局布局资源，不强制一定添加全局资源，可创建LoadViewHelper时再设置
```
public static class SettingCallBack implements DefaultSettingCallBack {

        @Override
        public int loadLayoutRes() {
            return R.layout.loading_view;
        }

        @Override
        public int emptyLayoutRes() {
            return R.layout.empty_view;
        }

        @Override
        public int errorLayoutRes() {
            return R.layout.error_view;
        }

        @Override
        public StateChangeListener stateChangeListener() {
            return null;//可返回null，这里可自定义布局显示时的动画
        }
    }
```
在Application的onCreate()方法里调用下面方法设置全局资源，也可在其他地方设置不过要注意内部类内存泄露问题
```
LoadViewHelper.DefaultLayoutListener(new SettingCallBack());
```

- 2.viewGroup`有且只有一个子控件，即内容控件`
```
LoadViewHelper loadViewHelper = LoadViewHelper.newBuilder()
//                .emptyLayoutRes(R.layout.empty_view)
//                .errorLayoutRes(R.layout.error_view)
//                .errorLayoutRes(R.layout.loading_view)
//               .addCustomLayout(LOGIN_LAYOUT,R.layout.login_view)    //这里添加自定义布局，显示时要对应设置对的LOGIN_LAYOUT状态
//               .stateChangeListener(new ShadeStateChangeListener())
                 .build()
                 .attach(viewGroup);
```


显示内容
```
loadViewHelper.showContent();
```
显示加载
```
loadViewHelper.showLoading();
```
显示空白
```
loadViewHelper.showEmpty();
```
显示错误
```
loadViewHelper.showError();
```
显示自定义布局
```
loadViewHelper.showCustom(int state);
```

# License

```
Copyright (C) 2019, 小黄
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```



