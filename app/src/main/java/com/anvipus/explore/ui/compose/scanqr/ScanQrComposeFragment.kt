package com.anvipus.explore.ui.compose.scanqr

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.anvipus.core.utils.theme.MyExploreTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.anvipus.core.R
import com.anvipus.explore.base.BaseFragmentCompose
import com.codedisruptors.dabestofme.di.Injectable

@ExperimentalPermissionsApi
class ScanQrComposeFragment : BaseFragmentCompose(), Injectable {
    override val statusBarColor: Int
        get() = R.color.colorAccent

    override val showToolbar: Boolean
        get() = true

    override val headTitle: Int
        get() = com.anvipus.explore.R.string.title_toolbar_mlkit_compose_screen

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MyExploreTheme {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))

                            val navController = rememberNavController()
                            NavHost(
                                navController = navController,
                                startDestination = Screen.CameraScreen.route
                            ) {
                                composable(
                                    route = Screen.CameraScreen.route
                                ) {
                                    CameraPreview(navController())
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
