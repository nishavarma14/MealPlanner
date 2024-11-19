package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;


public class homeFragment extends Fragment {
    ImageSlider imageSlider;
    VideoView videoView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.isshomeslider);
        videoView = view.findViewById(R.id.vvhomevideo);


        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();
        slideModelArrayList.add(new SlideModel(R.drawable.consult, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.tips, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.emergency, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModelArrayList);
        imageSlider.setSlideAnimation(AnimationTypes.CUBE_OUT);

        String videoPath = "android.resource://" + getActivity().getPackageName() + "/raw/hospital";

        videoView.setVideoPath(videoPath);
        videoView.start();

        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);

        imageSlider.setImageList(slideModelArrayList);
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_IN);
        imageSlider.setImageList(slideModelArrayList);
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_IN);

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                Intent i;
                switch (position) {
                    case 0:
                        i = new Intent(getActivity(),
                                healthActivity.class);
                        startActivity(i);
                        Toast.makeText(getActivity(), "health consult", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
            public void doubleClick(int position){

            }

        });
        return view;
    }
}