package com.example.musicplayer.controler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.musicplayer.R;
import com.example.musicplayer.model.Music;

public class PlayPageActivity extends AppCompatActivity {
    public static final String Id_MUSIC_RECIVED = "music_recived";
    // private Object Music;
int musicId;
    public static Intent newIntent(Context context,int id) {
        Intent intent = new Intent(context, PlayPageActivity.class);
        intent.putExtra(Id_MUSIC_RECIVED,id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_page);
       musicId= getIntent().getIntExtra(Id_MUSIC_RECIVED,1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container_play, PlayPageFragment.newInstance(musicId)).commit();

    }
}
