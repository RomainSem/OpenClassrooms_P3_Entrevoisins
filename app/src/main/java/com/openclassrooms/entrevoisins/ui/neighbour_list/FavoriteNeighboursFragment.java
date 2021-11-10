package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class FavoriteNeighboursFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mFavoriteNeighbours;
    private RecyclerView mRecyclerView;

    public static FavoriteNeighboursFragment newInstance() {
        return new FavoriteNeighboursFragment();
    }


    public static FavoriteNeighboursFragment newInstance(String param1, String param2) {
        FavoriteNeighboursFragment fragment = new FavoriteNeighboursFragment();
        return fragment;
    }

    public FavoriteNeighboursFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_neighbours, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        initFavoriteList();
        return view;
    }

    private void initFavoriteList(){
        mFavoriteNeighbours = mApiService.getFavoriteNeighbours();
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mFavoriteNeighbours));
    }

    @Override
    public void onResume() {
        super.onResume();
        initFavoriteList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        initFavoriteList();
    }
}