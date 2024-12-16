package com.example.umc_wireframe.presentation.ootd

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.umc_wireframe.R
import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.databinding.FragmentPhotoBinding
import com.example.umc_wireframe.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    private var imageCapture: ImageCapture? = null // 사진 캡처 객체

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 클릭 이벤트
        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack() // 현재 프래그먼트 종료
        }

        // HashtagFragment에서 선택한 해시태그 데이터를 수신
        parentFragmentManager.setFragmentResultListener("hashtagRequestKey", this) { _, bundle ->
            val hashtags = bundle.getStringArrayList("selectedHashtags")
            displayHashtags(hashtags)
        }

        // 해시태그 텍스트뷰 클릭 이벤트 추가
        binding.hashtagTextView.setOnClickListener {
            navigateToHashtagFragment() // 해시태그 등록 페이지로 이동
        }

        // 카메라 권한 요청
        if (allPermissionsGranted()) {
            startCamera() // 카메라 시작
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_PERMISSIONS)
        }

        // 촬영 버튼 클릭 리스너
        binding.cameraCaptureButton.setOnClickListener {
            takePhoto()
        }
    }

    private fun displayHashtags(hashtags: ArrayList<String>?) {
        if (hashtags.isNullOrEmpty()) {
            binding.hashtagTextView.text = "해시태그를 선택해주세요."
        } else {
            binding.hashtagTextView.text = hashtags.joinToString(", ") // 해시태그 표시
        }
    }

    private fun navigateToHashtagFragment() {
        try {
            val navController = findNavController() // 현재 Fragment에 연결된 NavController 가져오기
            navController.navigate(R.id.navi_hashtag) // HashtagFragment로 이동
        } catch (e: Exception) {
            Toast.makeText(context, "해시태그 등록 페이지로 이동할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        requireContext(), Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(context, "카메라 권한이 허용되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                Log.e(TAG, "카메라 바인딩 실패: ${e.message}")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            requireContext().externalMediaDirs.firstOrNull(),
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "사진 촬영 실패: ${exc.message}")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Toast.makeText(context, "촬영이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                    // 이미지 표시
                    binding.capturedImageView.setImageURI(photoFile.toUri())
                    binding.capturedImageView.visibility = View.VISIBLE
                    binding.viewFinder.visibility = View.GONE

                    // 서버에 이미지 업로드
                    uploadPhoto(photoFile)
                }
            }
        )
    }

    private fun uploadPhoto(photoFile: File) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), photoFile)
                val imagePart = MultipartBody.Part.createFormData("image", photoFile.name, requestFile)

                // RetrofitClient의 serverDatasource를 통해 API 호출
                val response = retrofitClient.serverDatasource.postOotd(
                    image = imagePart,
                    maxTemperature = maxTemp,
                    minTemperature = minTemp,
                    hashtags = hashtags
                )

                withContext(Dispatchers.Main) {
                    if (response.success) {
                        Toast.makeText(context, "이미지 업로드 성공: ${response.message}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "업로드 실패: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "서버 오류: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val TAG = "PhotoFragment"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}
