package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toolbar;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileNeighbourActivity extends AppCompatActivity {





    @BindView(R.id.profile_avatar)
    public ImageView mProfileAvatar;
    @BindView(R.id.profile_toolbar)
    public Toolbar mProfileToolbar;
    @BindView(R.id.profile_name)
    public EditText mProfileName;
    @BindView(R.id.profile_name2)
    public EditText mProfileName2 = mProfileName;
    @BindView(R.id.profile_address)
    public EditText mProfileAddress;
    @BindView(R.id.profile_link)
    public EditText mProfileLink;
    @BindView(R.id.profile_description)
    public EditText mProfileDescription;
    @BindView(R.id.AddFavoriteButton)
    public FloatingActionButton mAddFavoriteButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_neighbour);


        setSupportActionBar(mProfileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }


    private void setSupportActionBar(Toolbar profileToolbar) {
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public abstract class ActionBar extends Object {

    }


}