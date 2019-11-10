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
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
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
//        RetriveData retriveData = new RetriveData();
//        retriveData.execute();


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


    //    public class RetriveData extends AsyncTask {
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            getSongs();
//            return null;
//        }
//    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void getSongs() {
        Long id;
        String picpath;
        String title;
        String artist;
        String album;
        int duration;
        Uri musiUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor_music = null;
        Cursor cursor_album = null;
        if (Build.VERSION.SDK_INT < 26) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                String[] per = {Manifest.permission.READ_EXTERNAL_STORAGE};
                Log.d(MainActivity.TAG, "music aded" + "<26");


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(per, 1);

                }

            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            cursor_music = getContentResolver().query(musiUri, null, null, null);

        } else {


            cursor_music = getContentResolver().query(musiUri, null, null, null, null, null);
        }
        // cursor_album = getContentResolver().query(albumUri, null, null, null, null, null);

        if (cursor_music.getCount() != 0 && cursor_music != null) {
            int idColumn = cursor_music.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleColumn = cursor_music.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = cursor_music.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumColumn = cursor_music.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int columnDuration = cursor_music.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int i = 0;
            cursor_music.moveToFirst();
            while (!cursor_music.isAfterLast() && i < 100) {
                Long albumId = cursor_music.getLong(cursor_music.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                cursor_album = getContentResolver().query(albumUri, new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID + "=" + albumId,
                        null,
                        null);

                if (cursor_album != null && cursor_album.moveToFirst()) {
                    id = cursor_music.getLong(idColumn);
                    picpath = cursor_album.getString(cursor_album.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                    title = cursor_music.getString(titleColumn);
                    artist = cursor_music.getString(artistColumn);
                    album = cursor_music.getString(albumColumn);
                    duration = cursor_music.getInt(columnDuration);
                    Uri uri1 = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);


                    SongRepository.getInstance().addMusic(new Music(id, title, artist, album, uri1, picpath, duration));
                    i++;
//                    if (SongRepository.getInstance().getMusicList().size() > 0) {
//                        Log.d(MainActivity.TAG, "music :" + SongRepository.getInstance().getMusicList().get(i).getTitle());
//                        i++;
//                    }
                    // cursor_album.moveToNext();
                }
                cursor_music.moveToNext();
            }
            cursor_album.close();
        }
        cursor_music.close();


    }
}
