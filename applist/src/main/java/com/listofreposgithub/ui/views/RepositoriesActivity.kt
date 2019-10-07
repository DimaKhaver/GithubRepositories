package com.listofreposgithub.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.listofreposgithub.R
import com.listofreposgithub.utils.DETAILS_DYNAMIC_FEATURE
import timber.log.Timber

class RepositoriesActivity : AppCompatActivity() {

    lateinit var splitInstallManager: SplitInstallManager
    lateinit var request: SplitInstallRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDynamicModules()
    }

    private fun initDynamicModules() {
        splitInstallManager = SplitInstallManagerFactory.create(this)
        request = SplitInstallRequest
            .newBuilder()
            .addModule(DETAILS_DYNAMIC_FEATURE)
            .build()

        if (!isDynamicFeatureDownloaded(DETAILS_DYNAMIC_FEATURE))
            downloadFeature()
    }

    private fun downloadFeature() {
        try {
            splitInstallManager.startInstall(request)
                .addOnFailureListener { Timber.d("splitInstallManager failed: ${it.localizedMessage}") }
                .addOnSuccessListener { Timber.d("splitInstallManager success") }
                .addOnCompleteListener { Timber.d("splitInstallManager completed: ${it.result}") }
        } catch (exception: Exception) {
            Timber.e("Exception downloading Feature ${exception.message}")
        }
    }

    private fun isDynamicFeatureDownloaded(feature: String) = splitInstallManager.installedModules.contains(feature)
    private fun uninstallDynamicFeature(list: List<String>) = splitInstallManager.deferredUninstall(list)
}
