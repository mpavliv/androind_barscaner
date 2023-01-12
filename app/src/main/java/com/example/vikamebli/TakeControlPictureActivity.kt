package com.example.vikamebli

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.DisplayMetrics
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.android.synthetic.main.activity_take_control_picture.*
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class TakeControlPictureActivity : AppCompatActivity() {

    private lateinit var outputDir: File
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private var counter = 0
    private var code = ""

    private fun getOutputDir(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.absoluteFile.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdir() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir
        else filesDir
    }

    companion object{
        private const val TAG = "CameraX"
        private const val FILE_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PERMISSION_CODE = 10
        private val PERMISSION = arrayOf(Manifest.permission.CAMERA)
    }

    private fun allPermissinGranted() = PERMISSION.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_control_picture)
        code = intent.getStringExtra("code").toString()
        val resStr = intent.getStringExtra("resStr")
        var tvInfo:TextView = findViewById(R.id.tvInfo)
        tvInfo.text = resStr

        if (allPermissinGranted()){
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, PERMISSION, PERMISSION_CODE)
        }
        btn_save.setOnClickListener {
            takePhoto()
        }
        outputDir = getOutputDir()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE){
            if (allPermissinGranted()){
                startCamera()
            } else{
                Toast.makeText(this, "Permission error", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
                .also {
                    it.setSurfaceProvider(pv_camera.surfaceProvider)
                }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            imageCapture = ImageCapture.Builder().build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception){
                Log.e(TAG, "bind Error" ,e)

            }
        },
            ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        counter++
        val imageCapture = imageCapture?:return
        val photoFile = File(outputDir, code + "_$counter.jpg")
        val outputOption = ImageCapture.OutputFileOptions
            .Builder(photoFile).build()
        imageCapture.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(baseContext),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val uri = Uri.fromFile(photoFile)
                    val msg = "Photo: $uri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(baseContext, "Save error: ${exception.message}", Toast.LENGTH_SHORT).show()
                }

            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
