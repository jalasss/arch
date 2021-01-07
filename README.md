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
	implementation 'com.github.jalasss:arch:1.0.4'
	}
  
That's it!


使用了navigation,项目可自行删除

com.ywy.arch.navigation.fragment.NavHostFragment 替换默认NavHostFragment，主要解决了用add/hide替换replace的页面切换机制

'androidx.navigation:navigation-fragment-ktx:x.x.x'
'androidx.navigation:navigation-ui-ktx:x.x.x'

