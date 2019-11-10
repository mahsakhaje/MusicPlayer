package com.example.musicplayer.controler;


import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.Repositories.SongRepository;
import com.example.musicplayer.model.Music;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment implements Serializable {
    RecyclerView recyclerView;
    SongRepository musicRepository;
    MusicAdapter adapter;
    ControlMusic mMediaPlayer;
    boolean load;
    ProgressBar progressBar;
    public static final String SEND_MUSIC = "send_music";

    public static MusicFragment newInstance() {

        Bundle args = new Bundle();

        MusicFragment fragment = new MusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_music, container, false);
        recyclerView = v.findViewById(R.id.recycler_music);
        mMediaPlayer = ControlMusic.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        musicRepository = SongRepository.getInstance();
        adapter = new MusicAdapter(musicRepository.getMusicList());
       // progressBar = v.findViewById(R.id.progress_load_musics);

        //  setAdapter(musicRepository.getMusicList());
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!load) {
//            final GetMusicAsync async = new GetMusicAsync();
//            async.execute();
//        }
//

    }

    private class MusicHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView artist;
        private ImageView imageMusic;
        private Music mMusic;
        int position;

        public MusicHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_music_title);
            artist = itemView.findViewById(R.id.textView_music_artist);
            imageMusic = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //   try {
                    // mMediaPlayer.reset();
                    //  mMediaPlayer.play(mMusic, getActivity());
                    mMediaPlayer.setCurrentPosition(position);
                    Intent intent = PlayPageActivity.newIntent(getContext(), mMediaPlayer.getCurrentPosition());

                    getContext().startActivity(intent);

                    // } catch (IOException e) {
                    //  Log.d("tag", "problem in playing");
                    // e.printStackTrace();
                    //}
                }
            });
        }

        public void bind(Music music, int position) {
            mMusic = music;
            this.position = position;

            title.setText(music.getTitle());
            artist.setText(music.getArtist());
            imageMusic.setImageBitmap(music.getBitmap());
            //  Picasso.get().load(mMusic.getPicPath().).into(imageMusic);
        }
    }

    private class MusicAdapter extends RecyclerView.Adapter<MusicHolder> {
        private List<Music> musicList;

        public MusicAdapter(List<Music> musicList) {
            this.musicList = musicList;
        }

        public void setMusicList(List<Music> list) {
            musicList = list;
        }

        @NonNull
        @Override
        public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.music_item, parent, false);

            return new MusicHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MusicHolder holder, final int position) {
            holder.bind(musicList.get(position), position);

        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }





//    public class GetMusicAsync extends AsyncTask {
//
//        @Override
//        protected void onProgressUpdate(Object[] values) {
//            super.onProgressUpdate(values);
//            //   progressBar.setVisibility(View.VISIBLE);
//            adapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            getSongs();
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//            progressBar.setVisibility(View.GONE);
//            load = true;
//            adapter.notifyDataSetChanged();
//        }
//    }


}
