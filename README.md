# Profile

AndroidCommon是一个项目的基础框架，里面包含了常用的工具类、自定义的控件以及项目的基本功能等


# How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.beasonshu:AndroidCommon:v1.0.9'
	}


# Update Record
### 1.0.9
- fix verified code  countdown issues



# License

    Copyright 2019 Beason Shu
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


