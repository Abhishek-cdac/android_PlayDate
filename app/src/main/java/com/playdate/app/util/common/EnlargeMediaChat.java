//package com.playdate.app.util.common;
//
//import android.app.Dialog;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.playdate.app.R;
//import com.playdate.app.ui.chat.ChatAdapter;
//import com.squareup.picasso.Picasso;
//
//import java.io.InputStream;
//
//public class EnlargeMediaChat extends Dialog {
//
//
//    public static EnlargeMediaChat dialog;
//    String chat_image;
//    ChatAdapter chatAdapter;
//
//
//    public EnlargeMediaChat(Context context, String img, ChatAdapter chatAdapter) {
//
//        super(context, R.style.My_Dialog);
//        this.chat_image = img;
//        this.chatAdapter = chatAdapter;
//
//        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
//
//        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
//        getWindow().setAttributes(wlmp);
//        setTitle(null);
//        setCancelable(true);
//        setOnCancelListener(null);
//        View view = LayoutInflater.from(context).inflate(
//                R.layout.enlarge_media, null);
//        ImageView image_view = view.findViewById(R.id.image_view);
//
//        Picasso.get().load(chat_image).into(image_view);
////        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
//        image_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chatAdapter.resizeToNrmal();
//            }
//        });
//        setContentView(view);
//    }
//
//}
