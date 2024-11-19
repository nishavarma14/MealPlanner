package com.example.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class musicFragment extends Fragment {

    TextView tvstartsong,tvendsong;
    ImageView ivsongimage;
    SeekBar sbsongprogress;
    ImageView ivprevious,ivback,ivplay,ivnext,ivnextsong;


    private int currentIndex = 0;
    MediaPlayer mMediaPlayer;

    private static int sTime = 0,tTime = 0, oTime = 0,bTime = 5000, fTime = 5000;
    Handler handler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        tvstartsong = view.findViewById(R.id.tvstartsong);
        tvendsong = view.findViewById(R.id.tvendsong);
        ivsongimage = view.findViewById(R.id.ivsongimage);
        sbsongprogress = view.findViewById(R.id.sbsongprogress);
        ivprevious = view.findViewById(R.id.ivprevious);
        ivback = view.findViewById(R.id.ivback);
        ivplay = view.findViewById(R.id.ivplay);
        ivnext = view.findViewById(R.id.ivnext);
        ivnextsong = view.findViewById(R.id.ivnextsong);


        ArrayList<Integer> songArrayList = new ArrayList<>();
        songArrayList.add(0,R.raw.samay);
        songArrayList.add(1,R.raw.believer);
        songArrayList.add(2,R.raw.mardaani);

        mMediaPlayer = MediaPlayer.create(getActivity(),songArrayList.get(currentIndex));

                ivplay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMediaPlayer!=null && mMediaPlayer.isPlaying())
                        {
                            mMediaPlayer.pause();
                            ivplay.setImageResource(R.drawable.play);
                        }
                        else
                        {
                            mMediaPlayer.start();
                            ivplay.setImageResource(R.drawable.stop);
                        }
                        tTime = mMediaPlayer.getDuration();
                        sTime = mMediaPlayer.getCurrentPosition();

                        if (oTime == 0)
                        {
                            sbsongprogress.setMax(tTime);
                            oTime = 1;
                        }

                        tvendsong.setText(String.format("%d:%d",
                                TimeUnit.MILLISECONDS.toMinutes(tTime),
                                TimeUnit.MILLISECONDS.toSeconds(tTime)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tTime))));

                        tvstartsong.setText(String.format("%d:%d",
                                TimeUnit.MILLISECONDS.toMinutes(sTime),
                                TimeUnit.MILLISECONDS.toSeconds(sTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));

                        handler.postDelayed(UpdateSongProgress,1000);
                        songDetails();

                    }
                });


sbsongprogress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        if (fromUser)
        {
            mMediaPlayer.seekTo(progress);
            sbsongprogress.setProgress(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});
ivprevious.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (currentIndex > 0)
        {
            currentIndex--;
        }
        else {
            currentIndex = songArrayList.size()-1;
        }

        if (mMediaPlayer.isPlaying())
        {
            mMediaPlayer.stop();
        }

        if (mMediaPlayer!=null)
        {
            ivplay.setImageResource(R.drawable.stop);
        }

        mMediaPlayer = MediaPlayer.create(getActivity(),songArrayList.get(currentIndex));

        tTime = mMediaPlayer.getDuration();
        sTime = mMediaPlayer.getCurrentPosition();
        oTime = 0;
        if (oTime == 0)
        {
            sbsongprogress.setMax(tTime);
            oTime = 1;
        }
        tvendsong.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(tTime),
                TimeUnit.MILLISECONDS.toSeconds(tTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tTime))));

        tvstartsong.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(sTime),
                TimeUnit.MILLISECONDS.toSeconds(sTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));

        handler.postDelayed(UpdateSongProgress,1000);
        mMediaPlayer.start();
        songDetails();
    }
});

ivback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if ((sTime - bTime) > 0)
        {
            sTime =sTime-bTime;
            mMediaPlayer.seekTo(sTime);
        }
        else
        {
            Toast.makeText(getActivity(), "Cannot Jump Backword for 5 second", Toast.LENGTH_SHORT).show();
        }
    }
});
        ivnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sTime + bTime) < tTime)
                {
                    sTime =sTime + fTime;
                    mMediaPlayer.seekTo(sTime);
                }
                else
                {
                    Toast.makeText(getActivity(), "Cannot Jump forward for 5 second", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivnextsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex < songArrayList.size()-1)
                {
                    currentIndex++;
                }
                else {
                    currentIndex = 0;
                }

                if (mMediaPlayer.isPlaying())
                {
                    mMediaPlayer.stop();
                }

                if (mMediaPlayer!=null)
                {
                    ivplay.setImageResource(R.drawable.stop);
                }

                mMediaPlayer = MediaPlayer.create(getActivity(),songArrayList.get(currentIndex));

                tTime = mMediaPlayer.getDuration();
                sTime = mMediaPlayer.getCurrentPosition();
                oTime = 0;
                if (oTime == 0)
                {
                    sbsongprogress.setMax(tTime);
                    oTime = 1;
                }
                tvendsong.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(tTime),
                        TimeUnit.MILLISECONDS.toSeconds(tTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tTime))));

                tvstartsong.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(sTime),
                        TimeUnit.MILLISECONDS.toSeconds(sTime)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));

                handler.postDelayed(UpdateSongProgress,1000);
                mMediaPlayer.start();
                songDetails();
            }
        });

    return view;
    }

    private void songDetails() {
        if(currentIndex == 0)
        {
            ivsongimage.setImageResource(R.drawable.song);
        } else if (currentIndex == 1) {
            ivsongimage.setImageResource(R.drawable.img);
        } else if (currentIndex == 2) {
           ivsongimage.setImageResource(R.drawable.mardaani);
        }

    }

    private Runnable UpdateSongProgress = new Runnable() {
        @Override
        public void run() {
            sTime = mMediaPlayer.getCurrentPosition();

            tvstartsong.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes(sTime),
                    TimeUnit.MILLISECONDS.toSeconds(sTime)-
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));

            sbsongprogress.setProgress(sTime);

            handler.postDelayed(this,1000);

        }
    };
}