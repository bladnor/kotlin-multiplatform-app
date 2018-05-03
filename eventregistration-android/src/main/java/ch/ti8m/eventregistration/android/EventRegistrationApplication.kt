package ch.ti8m.eventregistration.android

import android.Manifest
import android.app.Application
import android.util.Log
import com.intentfilter.androidpermissions.PermissionManager
import java.util.*

class EventRegistrationApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        val permissionManager = PermissionManager.getInstance(this)
        val permissions = Arrays.asList(Manifest.permission.INTERNET)
        permissionManager.checkPermissions(permissions, object : PermissionManager.PermissionRequestListener {

            override fun onPermissionGranted() {
                Log.i(logTag, "permission granted for $permissions")
            }

            override fun onPermissionDenied() {
                Log.e(logTag, "permission not granted for $permissions")
            }
        })
    }

    companion object {
        val logTag = "eventregistration"


    }


}