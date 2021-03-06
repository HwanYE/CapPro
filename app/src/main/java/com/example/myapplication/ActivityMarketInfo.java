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
                                str,// ?????? ??????
                                1); // ?????? ??????
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.e("test","????????? ??????");
            }
        assert list != null;
            return list.get(0);
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Context context = this;
        geocoder = new Geocoder(context);
        InfoWindow infoWindow = new InfoWindow();

        Address address = button1("??????????????? ??????");
        Marker marker1 = new Marker();
        marker1.setPosition(new LatLng(address.getLatitude(),address.getLongitude()));
        marker1.setMap(naverMap);

        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(address.getLatitude(), address.getLongitude()),  // ?????? ??????
                12                           // ??? ??????
        );
        naverMap.setCameraPosition(cameraPosition);

// NaverMap ?????? ????????? NaverMap ????????? ?????? ?????? ??????
        this.naverMap = naverMap;
        this.naverMap.setLocationSource(mLocationSource);


        // ????????? ???????????? ?????? ?????? ??????
        naverMap.setOnMapClickListener((coord, point) -> {
            infoWindow.close();
        });

// ????????? ????????????:
        Overlay.OnClickListener listener = overlay -> {
            Marker marker = (Marker)overlay;

            if (marker.getInfoWindow() == null) {
                // ?????? ????????? ?????? ?????? ???????????? ?????? ?????? ???
                infoWindow.open(marker);
            } else {
                // ?????? ?????? ????????? ?????? ?????? ???????????? ?????? ??????
                infoWindow.close();
            }

            return true;
        };


        marker1.setTag("??????????????? ????????? ????????? ??????????????? 1095");

        marker1.setOnClickListener(overlay -> {
            // ????????? ????????? ??? ???????????? ???
            infoWindow.open(marker1);
            return true;
        });
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(context) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                // ?????? ?????? ?????? ????????? tag??? ???????????? ??????????????? ??????
                return (CharSequence) Objects.requireNonNull(Objects.requireNonNull(infoWindow.getMarker()).getTag());
            }
        });


        double lan = address.getLatitude();
        double lon = address.getLongitude();


        //??? ????????? ?????? ?????????
        final TextView textView = (TextView)findViewById(R.id.textView_lanlon);
        /*textView.setText("?????? : " + String.format("%.6f",lan) + " ?????? : "
                +String.format("%.6f",lon));*/


}

//????????? ???????????? ?????? ??????
    public void insertDataTable(int i)
    {
        //Typeface typeface = Typeface.createFromAsset(getAssets(), "font/welcome_regular.ttf"); // font ???????????? ?????? jua.ttf ????????? typeface??? ??????


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
            //textView.setTypeface(typeface,Typeface.NORMAL); // messsage??? TextView ??????
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

        // request code??? ???????????? ?????? ??????
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
                Toast.makeText(this.getApplicationContext(), "????????? ?????????????????????", Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
        }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        ActivityCache.clearApplicationData(ActivityMarketInfo.this);
        finishAndRemoveTask();						// ???????????? ?????? + ????????? ??????????????? ?????????
        android.os.Process.killProcess(android.os.Process.myPid());	// ??? ???????????? ??????
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
