package com.example.musicplayer.controler;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.Repositories.SongRepository;
import com.example.musicplayer.model.Music;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {
    RecyclerView recyclerView;
    AlbumAdapter adapter;
    SongRepository repository;

    public static AlbumFragment newInstance() {

        Bundle args = new Bundle();

        AlbumFragment fragment = new AlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music, container, false);
        recyclerView = v.findViewById(R.id.recycler_music);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        repository = SongRepository.getInstance();
        adapter = new AlbumAdapter(repository.getMusicList());
        recyclerView.setAdapter(adapter);
        return v;
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        TextView albumName, artistName;
        ImageView songImgView;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            songImgView = itemView.findViewById(R.id.image_album_imageview);
            albumName = itemView.findViewById(R.id.album_name_textView);
            artistName = itemView.findViewById(R.id.artist_name_textView);
        }

        public void bind(Music music) {
            albumName.setText(music.getAlbum());
            songImgView.setImageBitmap(music.getBitmap());
        }
    }

    public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {
        List<Music> albumList;

        public AlbumAdapter(List<Music> albumlist) {
            this.albumList = albumlist;

        }

        public void setAdapter(List<Music> list) {
            albumList = list;


        }


        @NonNull
        @Override
        public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.album_item, parent, false);

            return new AlbumViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
            holder.bind(albumList.get(position));
        }

        @Override
        public int getItemCount() {
            return albumList.size();
        }
    }

}
