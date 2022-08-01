package com.goMars.yeongsungbus.screen


import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.goMars.yeongsungbus.databinding.ActivityFindLocBinding
import com.google.android.gms.location.*


class findLoc : AppCompatActivity() {
    lateinit var binding: ActivityFindLocBinding
    val Tag: String = "findLocTest" // Log 찍을때 Tag
    val MY_PERMISSION_ACCESS_ALL = 100  //권한 창 생성 여부 판단 변수

    // Google's API for location Services. The majority of the app functions using this class
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: com.google.android.gms.location.LocationRequest
    lateinit var locationCallback: LocationCallback

    // lat, lon
    lateinit var lat: String
    lateinit var long: String

    override fun onPause() { // activity가 중지된 상태일 때
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback) // 위치 서비스 업데이트 제거
    }

    override fun onResume() {
        super.onResume()
        askGPSpermission() // Mainfest에서 필요한 권한들을 물어보는 메소드
        initLocation() // 최근 업데이트 한 위치를 가져옴 and 위치를 업데이트 해줌
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFindLocBinding.inflate(layoutInflater) // 뷰바인딩 인스턴스
        setContentView(binding.root) // 뷰바인딩 루트로 뷰 생성
        binding.btnAdr.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "위도 : $lat 경도: $long", Toast.LENGTH_SHORT).show()
        }) //위치 확인 버튼

    }

    private fun askGPSpermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        )  // 정확한 위치(ACCESS_FINE_LOCATION) or 대략적인 위치(ACCESS_COARSE_LOCATION) 승인이 안된 경우
        {
            var permission = arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,  // 정확한 위치
                android.Manifest.permission.ACCESS_COARSE_LOCATION //대략적인 위치
            ) //permission 변수에 권한 값들을 배열에 담음
            ActivityCompat.requestPermissions(
                this,
                permission,
                MY_PERMISSION_ACCESS_ALL
            ) // 권한 요청 메소드에 (activity, 권한배열객체, 생성여부코드) 값 전달
        } // *Compat은 API 버전별 차이점을 알아서 지원하도록 mapping 됨
    }

    override fun onRequestPermissionsResult( //사용자가 권한 선택 3개중 아무 버튼을 눌렀을때
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == MY_PERMISSION_ACCESS_ALL) { //requestCode == 100 이고
            if (grantResults.size > 0) {  // 승인결과 값이 존재하면
                for (grant in grantResults) { // 승인결과 배열 객체 안에있는 승인값들을 하나씩 반복
                    if (grant != PackageManager.PERMISSION_GRANTED) finish()
                    // 이용자가 승인 거부 시 앱 종료
                }
            }
        }
    }

    private fun initLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }   // 권한 설정 확인

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this) // 위치 서비스 클라이언트 만듬
        fusedLocationClient.lastLocation // 최근 가져온 위치
            .addOnSuccessListener { location ->
                if (location == null) {
                    Log.e(Tag, "location get fail")
                } else {
                    Log.d(Tag, "${location.latitude},${location.longitude}")
                }
            }
            .addOnFailureListener {  // 최근 위치 가져오는 것이 실패하면
                Log.e(Tag, "location error is ${it.message}")
                it.printStackTrace()
            }
        locationRequest = com.google.android.gms.location.LocationRequest() // 위치 요청을 위한 객체
        locationRequest.run {
            priority =
                com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도
            interval = 60 * 1000 // 시간 간격
        }
        locationCallback = object : LocationCallback() { //위치 데이터를 받을 콜백 함수
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult?.let {
                    // withIndex() : original array에서 반복되는 return 값들을 index로 wrap한 것
                    for ((i, location) in it.locations.withIndex()) {
                        Log.d(TAG, "#$i ${location.latitude}, ${location.longitude}")
                        lat = location.latitude.toString()
                        long = location.longitude.toString()
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates( //위치를 요청함
            locationRequest,
            locationCallback,
            Looper.myLooper() // 쓰레드를 통해 메시지 큐가 들어오면 이를 반복적으로 읽어 들임
        )
    }


}


