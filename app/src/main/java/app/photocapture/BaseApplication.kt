package app.photocapture

// import com.google.firebase.analytics.FirebaseAnalytics
import android.content.Context
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import app.photocapture.com.util.SharedPrefUtils
import com.bugsnag.android.Bugsnag


/**
 * This is the Application class of the LusoSmile project. As we want to enable multi-dex inorder to
 * have greater quantity of methods, we are extending [MultiDexApplication] class here.
 * @property sInstance an instance of this Application class
 * @author Al. Mujahid Khan
 */
class BaseApplication : MultiDexApplication() {

    init {
        sInstance = this
    }

    companion object {
        private lateinit var sInstance: BaseApplication

        /**
         * This method provides the Application context
         * @return [Context] application context
         */
        fun getBaseApplicationContext(): Context {
            return sInstance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Bugsnag.init(this)
        // DataUtils.getAndroidHashKey()
    }



    override fun attachBaseContext(base: Context?) {
        SharedPrefUtils.init(base!!)
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}