package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class ActivityMarketInfo  extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener  {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    private FusedLocationSource mLocationSource;
    private NaverMap naverMap;
    private Geocoder geocoder;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_info);

        mapView = findViewById(R.id.map_view);

        mapView.getMapAsync(this);

        mapView.onCreate(savedInstanceState);
    }

    public Address button1(String str){
            List<Address> list = null;
            try {
                list = geocoder.getFromLocationName
                        (
                                str,// 지역 이름
                                1); // 읽을 개수
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.e("test","입출력 오류");
            }
        assert list != null;
            return list.get(0);
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Context context = this;
        geocoder = new Geocoder(context);
        InfoWindow infoWindow = new InfoWindow();

        Address address = button1("계명대학교 동문");
        Marker marker1 = new Marker();
        marker1.setPosition(new LatLng(address.getLatitude(),address.getLongitude()));
        marker1.setMap(naverMap);

        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(address.getLatitude(), address.getLongitude()),  // 위치 지정
                12                           // 줌 레벨
        );
        naverMap.setCameraPosition(cameraPosition);

// NaverMap 객체 받아서 NaverMap 객체에 위치 소스 지정
        this.naverMap = naverMap;
        this.naverMap.setLocationSource(mLocationSource);


        // 지도를 클릭하면 정보 창을 닫음
        naverMap.setOnMapClickListener((coord, point) -> {
            infoWindow.close();
        });

// 마커를 클릭하면:
        Overlay.OnClickListener listener = overlay -> {
            Marker marker = (Marker)overlay;

            if (marker.getInfoWindow() == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(marker);
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close();
            }

            return true;
        };


        marker1.setTag("대구광역시 달서구 신당동 달구벌대로 1095");

        marker1.setOnClickListener(overlay -> {
            // 마커를 클릭할 때 정보창을 엶
            infoWindow.open(marker1);
            return true;
        });
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(context) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                // 정보 창이 열린 마커의 tag를 텍스트로 노출하도록 반환
                return (CharSequence) Objects.requireNonNull(Objects.requireNonNull(infoWindow.getMarker()).getTag());
            }
        });


        double lan = address.getLatitude();
        double lon = address.getLongitude();


        //맵 텍스트 설명 바꾸기
        final TextView textView = (TextView)findViewById(R.id.textView_lanlon);
        /*textView.setText("위도 : " + String.format("%.6f",lan) + " 경도 : "
                +String.format("%.6f",lon));*/


}

//데이터 테이블에 정보 삽입
    public void insertDataTable(int i)
    {
        //Typeface typeface = Typeface.createFromAsset(getAssets(), "font/welcome_regular.ttf"); // font 폴더내에 있는 jua.ttf 파일을 typeface로 설정


        TableLayout tableLayout = (TableLayout)findViewById(R.id.tablelayout);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));


            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.forward_icon);
            /*
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)imageView.getLayoutParams();
            params.width = 200;
            params.height = 40;
            imageView.setLayoutParams(params);
             */
            tableRow.addView(imageView);

            TextView textView = new TextView(this);
            textView.setText(String.valueOf(i));
            //textView.setTypeface(typeface,Typeface.NORMAL); // messsage는 TextView 변수
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(32);

            textView.setBackgroundResource(R.drawable.roundedtextview);
            textView.setTextColor(Color.parseColor("#000000"));
            tableRow.addView(textView);

        tableLayout.addView(tableRow);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // request code와 권한획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }

        @Override
        public boolean onClick(@NonNull Overlay overlay) {
            if (overlay instanceof Marker) {
                Toast.makeText(this.getApplicationContext(), "마커가 선택되었습니다", Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
        }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        ActivityCache.clearApplicationData(ActivityMarketInfo.this);
        finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
        android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
        //super.onBackPressed();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
