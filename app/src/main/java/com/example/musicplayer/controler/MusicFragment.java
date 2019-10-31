package com.example.musicplayer.controler;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.Repositories.SongRepository;
import com.example.musicplayer.model.Music;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {
    RecyclerView recyclerView;
    SongRepository musicRepository;
    MusicAdapter adapter;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        musicRepository = SongRepository.getInstance();
        adapter = new MusicAdapter(musicRepository.getMusicList());
        recyclerView.setAdapter(adapter);

        return v;
    }

    private class MusicHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView artist;
        private Music mMusic;

        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_music_title);
            artist = itemView.findViewById(R.id.textView_music_artist);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ControlMusic controlMusic = new ControlMusic();

                    try {
                        controlMusic.stop();
                        controlMusic.play(mMusic, getActivity());
                    } catch (IOException e) {
                        Log.d("tag", "problem in playing");
                        e.printStackTrace();
                    }
                }
            });
        }

        public void bind(Music music) {
            mMusic = music;
            title.setText(music.getTitle());
            artist.setText(music.getArtist());
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
            holder.bind(musicList.get(position));
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }
}
