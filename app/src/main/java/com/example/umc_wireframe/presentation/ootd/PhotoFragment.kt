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
import androidx.navigation.fragment.NavHostFragment
import com.example.umc_wireframe.R
import com.example.umc_wireframe.databinding.FragmentPhotoBinding
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
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), REQUEST_CODE_PERMISSIONS)
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

    // navigateToHashtagFragment 메서드
    private fun navigateToHashtagFragment() {
        try {
            val navController = findNavController() // 현재 Fragment에 연결된 NavController 가져오기
            navController.navigate(R.id.navi_hashtag) // HashtagFragment로 이동
        } catch (e: Exception) {
            Toast.makeText(context, "해시태그 등록 페이지로 이동할 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 카메라 권한 확인
    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        requireContext(), android.Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera() // 카메라 시작
            } else {
                Toast.makeText(context, "카메라 권한이 허용되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 카메라 시작
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
                cameraProvider.unbindAll() // 기존 바인딩 해제
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                ) // 프리뷰와 캡처 바인딩
            } catch (e: Exception) {
                Log.e(TAG, "카메라 바인딩 실패: ${e.message}")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    // 사진 촬영
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

                    // 촬영된 이미지를 ImageView에 표시
                    binding.capturedImageView.setImageURI(photoFile.toUri())
                    binding.capturedImageView.visibility = View.VISIBLE // ImageView 표시
                    binding.viewFinder.visibility = View.GONE // 프리뷰 숨김
                }

            }
        )
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
