# arch
How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	implementation 'com.github.jalasss:arch:1.0.2'
	}
  
That's it!


使用了navigation,项目可自行删除
'androidx.navigation:navigation-fragment-ktx:x.x.x'
'androidx.navigation:navigation-ui-ktx:x.x.x'
