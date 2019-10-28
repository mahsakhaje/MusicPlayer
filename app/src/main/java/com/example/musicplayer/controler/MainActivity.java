package com.example.musicplayer.controler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.MediaStore;
import android.util.Log;

import com.example.musicplayer.controler.MusicFragment;
import com.example.musicplayer.R;
import com.example.musicplayer.Repositories.SongRepository;
import com.example.musicplayer.model.Music;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    public static final int count_pages = 3;
    public static final String TAG = "tag";
    ViewPager viewPager;
    TabLayout tabLayout;
    PagerAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSongs();
        viewPager = findViewById(R.id.container_viewpager);

        tabLayout = findViewById(R.id.tab_layout);
        adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    public Intent newIntent() {
        return null;
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MusicFragment.newInstance();
                case 1:
                    return AlbumFragment.newInstance();
                case 2:
                    return MusicFragment.newInstance();
            }

            return MusicFragment.newInstance();

        }

        @Override
        public int getCount() {
            return count_pages;

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MUSICS";
                case 1:
                    return "ALBUMS";
                case 2:
                    return "ARTISTS";


            }
            return null;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void getSongs() {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        if (Build.VERSION.SDK_INT < 26) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                String[] per = {Manifest.permission.READ_EXTERNAL_STORAGE};
                Log.d(TAG, "music aded" + "<26");


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(per, 1);

                }

            }
        }
        Cursor cursor = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cursor = getContentResolver().query(uri, null, null, null);
        } else {


            cursor = getContentResolver().query(uri, null, null, null, null, null);
        }

        if (cursor.getCount() != 0) {
            int i = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                Long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String album=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                SongRepository.getInstance().addMusic(new Music(id, title, artist,album));
                if(SongRepository.getInstance().getMusicList().size()>0){
                Log.d(TAG, "music :" + SongRepository.getInstance().getMusicList().get(i).getTitle());
                i++;}
                cursor.moveToNext();

            }
            cursor.close();
        }


    }

}
