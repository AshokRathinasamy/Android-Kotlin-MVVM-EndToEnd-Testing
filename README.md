# Android-Kotlin-MVVM-EndToEnd-Testing(Instrument Testing)
Android-Kotlin-MVVM-EndToEnd-Testing

<p><strong>Android</strong> <strong>Testing Topics</strong><span style="font-weight: 400;">:</span></p>
<ul>
<li style="font-weight: 400;"><span style="font-weight: 400;">Coroutines, including view model, scoped coroutines.</span></li>
<li style="font-weight: 400;"><span style="font-weight: 400;">Room.</span></li>
<li style="font-weight: 400;"><span style="font-weight: 400;">Databinding.</span></li>
<li style="font-weight: 400;"><span style="font-weight: 400;">Dependency Injection and Test Doubles.</span></li>
<li style="font-weight: 400;"><span style="font-weight: 400;">Testing code with coroutines.</span></li>
<li style="font-weight: 400;"><span style="font-weight: 400;">Testing Room.</span></li>
<li style="font-weight: 400;"><span style="font-weight: 400;">Espresso Idling Resource.</span></li>
<li style="font-weight: 400;"><span style="font-weight: 400;">End to End testing with Data Binding.</span></li>
</ul>
<p><span style="font-weight: 400;">You'll learn about the following libraries and code concepts:</span></p>
<ul>
<li style="font-weight: 400;"><span style="color: #0000ff;"><a style="color: #0000ff;" href="https://junit.org/junit4/"><span style="font-weight: 400;">JUnit4</span></a></span></li>
<li style="font-weight: 400;"><span style="color: #0000ff;"><a style="color: #0000ff;" href="http://hamcrest.org/"><span style="font-weight: 400;">Hamcrest</span></a></span></li>
<li style="font-weight: 400;"><span style="color: #0000ff;"><a style="color: #0000ff;" href="https://developer.android.com/training/testing/set-up-project"><span style="font-weight: 400;">AndroidX Test Library</span></a></span></li>
<li style="font-weight: 400;"><span style="color: #0000ff;"><a style="color: #0000ff;" href="https://developer.android.com/reference/android/arch/core/executor/testing/package-summary"><span style="font-weight: 400;">AndroidX Architecture Components Core Test Library</span></a></span></li>
<li style="font-weight: 400;"><span style="color: #0000ff;"><a style="color: #0000ff;" href="https://developer.android.com/training/testing/espresso"><span style="font-weight: 400;">Espresso</span></a></span></li>
<li style="font-weight: 400;"><span style="color: #0000ff;"><a style="color: #0000ff;" href="https://site.mockito.org/"><span style="font-weight: 400;">Mockito</span></a></span></li>
<li style="font-weight: 400;"><span style="color: #0000ff;"><a style="color: #0000ff;" href="https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/run-blocking.html"><span style="font-weight: 400;">runBlocking</span></a><span style="font-weight: 400;"> and </span><a style="color: #0000ff;" href="https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-blocking-test.html"><span style="font-weight: 400;">runBlockingTest</span></a></span></li>
<li style="font-weight: 400;"><span style="color: #0000ff;"><a style="color: #0000ff;" href="https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/"><span style="font-weight: 400;">TestCoroutineDispatcher</span></a></span></li>
<li style="font-weight: 400;"><span style="color: #0000ff;"><a style="color: #0000ff;" href="https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-delay-controller/pause-dispatcher.html"><span style="font-weight: 400;">pauseDispatcher</span></a><span style="font-weight: 400;"> and </span><a style="color: #0000ff;" href="https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-delay-controller/resume-dispatcher.html"><span style="font-weight: 400;">resumeDispatcher</span></a></span></li>
<li style="font-weight: 400;"><span style="color: #0000ff;"><a style="color: #0000ff;" href="https://developer.android.com/reference/androidx/room/Room.html#inMemoryDatabaseBuilder(android.content.Context,%20java.lang.Class%3CT%3E)"><span style="font-weight: 400;">inMemoryDatabaseBuilder</span></a></span></li>
<li style="font-weight: 400;"><span style="color: #0000ff;"><a style="color: #0000ff;" href="https://developer.android.com/reference/androidx/test/espresso/IdlingResource.html"><span style="font-weight: 400;">IdlingResource</span></a></span></li>
</ul>
<p><span style="font-weight: 400;">You should be familiar with:</span></p>
<ul>
<li style="font-weight: 400;"><span style="font-weight: 400;">The Kotlin programming language, including </span><a href="https://developer.android.com/kotlin/coroutines"><span style="font-weight: 400;"><span style="color: #0000ff;">Kotlin coroutines</span></span></a> <span style="font-weight: 400;">and their interaction with </span><span style="color: #0000ff;"><a style="color: #0000ff;" href="https://developer.android.com/topic/libraries/architecture/coroutines"><span style="font-weight: 400;">Android Jetpack components</span></a><span style="font-weight: 400;">.</span></span></li>
<li style="font-weight: 400;">The following core Android Jetpack libraries: <span style="color: #0000ff;"><a style="color: #0000ff;" href="https://developer.android.com/topic/libraries/architecture/viewmodel">ViewModel</a>, <a style="color: #0000ff;" href="https://developer.android.com/topic/libraries/architecture/livedata">LiveData</a>, <a style="color: #0000ff;" href="https://developer.android.com/guide/navigation">Navigation Component</a>, </span>and <span style="color: #0000ff;"><a style="color: #0000ff;" href="https://developer.android.com/topic/libraries/data-binding">Data Binding</a>.</span></li>
</ul>


<p><strong>App overview and Instrument testing in Device:</strong></p>
<table style="margin-left: auto; margin-right: auto;">
<tbody>
<tr>
<td><img src="images/screenshot_final_project_structure.png" alt="" width="400" height="500" /></td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td><img style="display: block; margin-left: auto; margin-right: auto;" src="images/final_endtoend_testing_android.gif" alt="" width="300" height="500" /></td>
</tr>
</tbody>
</table>


<p><strong>Execute Instrument test in Android studio:</strong></p>
<p><strong><img src="images/project_run_execute.gif" alt="" width="649" height="364" /></strong></p>


<p><strong>Final Instrument testing output:</strong></p>
<p><strong><img src="images/screenshot_final_logcat_result.png" alt="" width="700" height="600" /></strong></p>
