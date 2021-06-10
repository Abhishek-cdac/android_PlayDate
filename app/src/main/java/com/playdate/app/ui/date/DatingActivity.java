//package com.playdate.app.ui.date;
//
//import android.os.Bundle;
//import android.os.PersistableBundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.playdate.app.R;
//
//public class DatingActivity extends AppCompatActivity {
//    DatingViewModel viewModel;
//
//
//    @Override
//    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        viewModel=new DatingViewModel();
//        setContentView(R.layout.activity_dating);
//        ImageView iv_close=findViewById(R.id.iv_close);
//        iv_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               finish();
//            }
//        });
//    }
//}
