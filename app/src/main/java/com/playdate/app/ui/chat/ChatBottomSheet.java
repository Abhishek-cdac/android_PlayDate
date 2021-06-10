package com.playdate.app.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.playdate.app.R;

public class ChatBottomSheet extends BottomSheetDialogFragment {
    String text;
    FragChatMain ref;

    public ChatBottomSheet(String text, FragChatMain ref) {
        this.text = text;
        this.ref = ref;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        if (text.equals("Extra")) {
            view = inflater.inflate(R.layout.frag_chat_bottom_sheet, container, false);
            LinearLayout ll_location;

            ll_location = view.findViewById(R.id.ll_location);
            ll_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ref.onLocationSelect();
                }
            });


        } else {
            view = inflater.inflate(R.layout.frag_image_select_bottom_sheet, container, false);
            LinearLayout ll_camera, ll_gallery;

            ll_gallery = view.findViewById(R.id.ll_gallery);
            ll_camera = view.findViewById(R.id.ll_camera);

            ll_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ref.onGallerySelect();
                }
            });
            ll_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ref.onCameraSelect();
                }
            });
        }
        return view;
    }
}