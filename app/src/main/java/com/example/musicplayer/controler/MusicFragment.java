package com.example.musicplayer;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Repositories.SongRepository;
import model.Music;


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
        TextView title;
        TextView artist;

        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_music_title);
            artist = itemView.findViewById(R.id.textView_music_artist);
        }

        public void bind(Music music) {
            title.setText(music.getTitle());
            artist.setText(music.getArtist());
        }
    }

    private class MusicAdapter extends RecyclerView.Adapter<MusicHolder> {
        List<Music> musicList;

        public void setMusicList(List<Music> list) {
            musicList = list;
        }

        MusicAdapter(List<Music> list) {
            musicList = list;
        }

        @NonNull
        @Override
        public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.music_item, parent, false);

            return new MusicHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
            holder.bind(musicList.get(position));
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }
}
