package com.goMars.yeongsungbus.screen

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.goMars.yeongsungbus.databinding.ActivityFindLocBinding

class findLoc : AppCompatActivity() {
    lateinit var binding: ActivityFindLocBinding
    val Tag: String = "findLocTest" // Log 찍을때 Tag
    val MY_PERMISSION_ACCESS_ALL = 100  //권한 창 생성 여부 판단 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFindLocBinding.inflate(layoutInflater) // 뷰바인딩 인스턴스
        setContentView(binding.root) // 뷰바인딩 루트로 뷰 생성

        binding.btnAdr.setOnClickListener(findAdr()) //위치 확인 버튼

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
                    if (grant != PackageManager.PERMISSION_GRANTED) System.exit(0)
                    // 이용자가 승인 거부 시 앱 종료
                }
            }
        }
    }

    fun findAdr(): View.OnClickListener = View.OnClickListener {

    }
}


