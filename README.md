# SunRayChart
Polarised chart of sun ray look

![](https://github.com/salim-hbk/SunRayChart/blob/master/Screenshot_2018-05-02-10-15-44.png)

Usage

    val sunRayChart = SunRayChart(this)
        sunRayChart.addEntries(resources.getTextArray(R.array.datset_labels))
        sunRayChart.setFonSize(resources.getDimension(R.dimen.nine_sp))
        sunRayChart.setFontColor(ContextCompat.getColor(this, R.color.gray_light))
        sunRayChart.addDataSet(Util.generateDemoChrtVals())
        sunRayChart.invalidate()
        lVcontainer.addView(sunRayChart)
        

Gradle support

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.salim-hbk:SunRayChart:0.1'
	}
  
  
