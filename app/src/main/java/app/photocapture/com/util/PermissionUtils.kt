package app.photocapture.com.util

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.Fragment
import app.photocapture.BaseApplication
import java.util.*

/**
 * This is a class that contains utils for the runtime permissions
 * @author Al. Mujahid Khan
 * */
object PermissionUtils {

    const val REQUEST_CODE_PERMISSION_DEFAULT = 1
    const val REQUEST_CODE_PERMISSION_LOCATION_AND_STORAGE = 2
    const val REQUEST_CODE_PERMISSION_CALL_PHONE = 3

    /**
     * This method is to get specific permission/s from user in the provided activity
     * with default request code and
     * returns true/false depending on the allowance of the requested permissions
     *
     * @param activity current activity
     * @param permissions required permission/s
     * @return true if the requested permissions are already allowed otherwise false
     * */
    @Synchronized
    fun requestPermission(activity: Activity, vararg permissions: String): Boolean {
        return requestPermission(null, activity,
                REQUEST_CODE_PERMISSION_DEFAULT, permissions.toList())
    }

    /**
     * This method is to get specific permission/s from user in the provided fragment
     * with default request code and
     * returns true/false depending on the allowance of the requested permissions
     *
     * @param fragment current fragment
     * @param permissions required permission/s
     * @return true if the requested permissions are already allowed otherwise false
     * */
    @Synchronized
    fun requestPermission(fragment: Fragment, vararg permissions: String): Boolean {
        return requestPermission(fragment, null,
                REQUEST_CODE_PERMISSION_DEFAULT, permissions.toList())
    }

    /**
     * This method is to get specific permission/s from user in the provided activity
     * with custom request code and
     * returns true/false depending on the allowance of the requested permissions
     *
     * @param activity current activity
     * @param requestCode custom request code
     * @param permissions required permission/s
     * @return true if the requested permissions are already allowed otherwise false
     * */

    @Synchronized
    fun requestPermission(activity: Activity, requestCode: Int,
                          vararg permissions: String): Boolean {
        return requestPermission(null, activity, requestCode, permissions.toList())
    }

    /**
     * This method is to get specific permission/s from user in the provided fragment
     * with custom request code and
     * returns true/false depending on the allowance of the requested permissions
     *
     * @param fragment current fragment
     * @param requestCode custom request code
     * @param permissions required permission/s
     * @return true if the requested permissions are already allowed otherwise false
     * */
    @Synchronized
    fun requestPermission(fragment: Fragment, requestCode: Int,
                          vararg permissions: String): Boolean {
        return requestPermission(fragment, null, requestCode, permissions.toList())
    }

    private fun requestPermission(fragment: Fragment?, activity: Activity?,
                                  requestCode: Int, permissions: List<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        val permissionsNotTaken = ArrayList<String>()

        for (i in permissions.indices) {
            if (!isAllowed(permissions[i])) {
                permissionsNotTaken.add(permissions[i])
            }
        }

        if (permissionsNotTaken.isEmpty()) {
            return true
        }

        if (fragment == null) {
            activity?.requestPermissions(permissionsNotTaken.toTypedArray(), requestCode)
        } else {
            fragment.requestPermissions(permissionsNotTaken.toTypedArray(), requestCode)
        }

        return false
    }

    /**
     * This method returns the allowance state of a permission
     *
     * @param permission permission to be checked
     * @return true if the permission is already granted, else false
     * */
    fun isAllowed(permission: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        return BaseApplication.getBaseApplicationContext()
                .checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}