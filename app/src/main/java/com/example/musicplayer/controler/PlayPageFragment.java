package com.example.musicplayer.controler;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.Repositories.SongRepository;
import com.example.musicplayer.model.Music;

import java.io.IOException;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayPageFragment extends Fragment {
    public static final String SENDED_MUSIC = "sended_music_";
    ImageButton preve, play, next, repeat, shuffle;
    SeekBar seekBar;
    TextView title;
    ImageView image;

    Music mMusic;
    ControlMusic mMediaPlayer;
    SongRepository mMusicRepository;
    boolean isShuffle;
    boolean isRepeatOne;
    Long id;
    boolean isPlaying = true;
    int mCurrentSongPositon;
    HandleTime handleTime;

    public PlayPageFragment() {
        // Required empty public constructor
    }

    public static PlayPageFragment newInstance(int id) {


        Bundle args = new Bundle();
        args.putInt(SENDED_MUSIC, id);
        PlayPageFragment fragment = new PlayPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_play_page, container, false);


        id = getArguments().getLong(SENDED_MUSIC);
        mMusicRepository = SongRepository.getInstance();
        mMediaPlayer = ControlMusic.getInstance();
        mCurrentSongPositon = getArguments().getInt(SENDED_MUSIC);
        mMusic = mMusicRepository.getMusicList().get(mCurrentSongPositon);
        handleTime = new HandleTime();
        playSong();
        initUI(v);
        setListerners();
        setDetails();
        changeSeekbar();
        return v;
    }

    private void playSong() {
        try {
            mMediaPlayer.play(mMusic, getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMusicRepository.setmCurrentMusic(mMusic);
    }

    @Override
    public void onResume() {
        super.onResume();
        // image.setImageBitmap(mMusic.getBitmap());
    }

    private void setListerners() {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    {
                        mMediaPlayer.pause();
                        play.setBackground(getResources().getDrawable(R.drawable.play_image));
                        isPlaying = !isPlaying;

                    }
                } else {
                    if (mMediaPlayer.getMediaPlayer() != null) {
                        play.setBackground(getResources().getDrawable(R.drawable.pause_image));
                        mMediaPlayer.start();
                        isPlaying = !isPlaying;
                    }

                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShuffle) {
                    try {

                        mMediaPlayer.play(mMusicRepository.getNextMusic(mCurrentSongPositon), getActivity());
                        if (mCurrentSongPositon == mMusicRepository.getMusicList().size() - 1) {
                            mCurrentSongPositon = 0;
                        } else {
                            mCurrentSongPositon++;
                        }
                        setDetails();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Random random=new Random();
                    mCurrentSongPositon=random.nextInt()%(mMusicRepository.getMusicList().size()-1);
                    try {
                        mMediaPlayer.play(mMusicRepository.getNextMusic(mCurrentSongPositon),getContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        preve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mMediaPlayer.play(mMusicRepository.getPriveMusic(mCurrentSongPositon), getActivity());
                    if (mCurrentSongPositon == 0) {
                        mCurrentSongPositon = mMusicRepository.getMusicList().size() - 1;
                    } else {
                        mCurrentSongPositon--;
                    }
                    setDetails();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isRepeatOne) {
                    repeat.setBackground(getResources().getDrawable(R.drawable.repeat_image));
                    isRepeatOne = !isRepeatOne;
                } else {
                    repeat.setBackground(getResources().getDrawable(R.drawable.repeatone_image));
                    isRepeatOne = !isRepeatOne;
                }
            }
        });
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShuffle) {
                    shuffle.setBackground(getResources().getDrawable(R.drawable.shuffle_image));
                    isShuffle = !isShuffle;
                } else {
                    shuffle.setBackground(getResources().getDrawable(R.drawable.shufflechoosed_image));
                    isShuffle = !isShuffle;
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mMediaPlayer.getMediaPlayer() != null && b)
                    mMediaPlayer.getMediaPlayer().seekTo(i);
                if (mMediaPlayer.getMediaPlayer().getDuration() <= i && !isRepeatOne) {
                    try {
                        mMediaPlayer.play(mMusicRepository.getNextMusic(mCurrentSongPositon), getContext());
                        mCurrentSongPositon++;
                        setDetails();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (mMediaPlayer.getMediaPlayer().getDuration() <= i && isRepeatOne) {
                    try {
                        mMediaPlayer.play(mMusicRepository.getMusicList().get(mCurrentSongPositon), getContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initUI(View v) {
        preve = v.findViewById(R.id.preve);
        next = v.findViewById(R.id.next);
        play = v.findViewById(R.id.play);
        repeat = v.findViewById(R.id.repeat);
        shuffle = v.findViewById(R.id.shuffle);
        title = v.findViewById(R.id.textView_title_music_play_page);
        seekBar = v.findViewById(R.id.seekbar);
        image = v.findViewById(R.id.image_music_play_fragment);

    }

    public void setDetails() {

        image.setImageBitmap(mMusicRepository.getMusicList().get(mCurrentSongPositon).getBitmap());
        title.setText(mMusicRepository.getMusicList().get(mCurrentSongPositon).getTitle()
                + "\n" + mMusicRepository.getMusicList().get(mCurrentSongPositon).getArtist());
    }

    public void changeSeekbar() {
        final Handler mHandler = new Handler();

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mMediaPlayer != null) {
                    if (mMediaPlayer.getMediaPlayer().isPlaying()) {
                        seekBar.setMax(mMediaPlayer.getMediaPlayer().getDuration());
                        int mCurrentPosition = mMediaPlayer.getMediaPlayer().getCurrentPosition();
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
                mHandler.postDelayed(this, 1);
            }
        });
    }
}
