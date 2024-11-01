package com.anvipus.explore.ui.compose.scanqr

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.common.util.concurrent.ListenableFuture
import com.anvipus.core.R
import com.anvipus.core.utils.BarCodeAnalyser
import com.anvipus.core.utils.Constants
import com.anvipus.core.utils.getActivity
import com.anvipus.explore.ui.xml.MainActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SuppressLint("StaticFieldLeak")
var context: Context? = null
var qrCode: String? = null

@Composable
fun CameraPreview(navCon: NavController) {
    context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }
    val barCodeVal = remember { mutableStateOf("") }
    var showGif by remember { mutableStateOf(true) }
    var failure by remember { mutableStateOf(true) }
    var isBack by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (!showGif){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                successAnimation()
                //test
            }

        }
        if (!failure){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                failureAnimation()
            }

        }
        if (!isBack){
            if(qrCode != null){
                backNavigation(qrCode!!,navCon)
            }

        }
        AndroidView(

            factory = { AndroidViewContext ->
                PreviewView(AndroidViewContext).apply {
                    this.scaleType = PreviewView.ScaleType.FILL_CENTER
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }
            },
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
                .align(Alignment.Center),
            update = { previewView ->

                val cameraSelector: CameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
                val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                    ProcessCameraProvider.getInstance(context!!)

                cameraProviderFuture.addListener({
                    preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                    val barcodeAnalyser = BarCodeAnalyser { barcodes ->
                        barcodes.forEach { barcode ->
                            barcode.rawValue?.let { barcodeValue ->
                                barCodeVal.value = barcodeValue


                                if (barcodeValue.length >= 10) {
                                    qrCode = barcodeValue
                                    isBack = !isBack
//                                    showGif = !showGif

                                }
                                if(barcodeValue.length < 10){
                                    failure = !failure
                                }

                            }
                        }
                    }
                    val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                        }

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                    }
                }, ContextCompat.getMainExecutor(context!!))
            }
        )
    }


}

@Composable
fun backNavigation(code: String, navController: NavController) {
    navController.previousBackStackEntry?.savedStateHandle?.set(Constants.EXTRA_QR, code)
    navController.popBackStack(com.anvipus.explore.R.id.fragment_scan_qr, false)
}

@Composable
fun successAnimation() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.success)
    )
    LottieAnimation(
        composition = composition,
        //progress = { /*TODO*/ },
        iterations = 2
    )
}

@Composable
fun failureAnimation() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.failure)
    )
    LottieAnimation(
        composition = composition,
        //progress = { /*TODO*/ },
        iterations = 2
    )
}

/*@Composable
fun LoadingGif(
    context: Context,
    modifier: Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = R.drawable.success).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = context.gifLoader()
        ),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
    )
}*/