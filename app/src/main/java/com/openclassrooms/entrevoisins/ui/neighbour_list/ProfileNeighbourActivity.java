package com.openclassrooms.entrevoisins.ui.neighbour_list;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.Objects;



public class ProfileNeighbourActivity extends AppCompatActivity {

    long idNeighbour;
    String name;
    Boolean isFavorite;
    private ImageButton mBackButton;
    private ImageView mProfileAvatar;
    private TextView mProfilPhone;
    private TextView mProfileName;
    private TextView mProfileName2;
    private TextView mProfileAddress;
    private TextView mProfileSocialMedia;
    private TextView mProfileDescription;
    private FloatingActionButton mAddFavoriteButton;

    private Neighbour mNeighbour;
    private NeighbourApiService mFavoriteNeighbour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFavoriteNeighbour = DI.getNeighbourApiService();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_neighbour);

        mBackButton = findViewById(R.id.back_button);
        mProfileAvatar = findViewById(R.id.profile_avatar);
        mProfileName = findViewById(R.id.profile_name);
        mProfileName2 = findViewById(R.id.profile_name2);
        mProfilPhone = findViewById(R.id.profile_phone);
        mProfileAddress = findViewById(R.id.profile_address);
        mProfileSocialMedia = findViewById(R.id.profile_socialmedia);
        mProfileDescription = findViewById(R.id.profile_description);
        mAddFavoriteButton = findViewById(R.id.AddFavoriteButton);

        Objects.requireNonNull(getSupportActionBar()).hide(); // Cache l'action BAR

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mNeighbour = (Neighbour) getIntent().getSerializableExtra("NEIGHBOUR");
        name = mNeighbour.getName();
        mProfileAddress.setText(mNeighbour.getAddress());
        mProfileName.setText(mNeighbour.getName());
        mProfileName2.setText(mNeighbour.getName());
        idNeighbour = mNeighbour.getId();
        mProfileDescription.setText(mNeighbour.getAboutMe());
        mProfileSocialMedia.append("www.facebook.com/" + name);
        mProfilPhone.setText(mNeighbour.getPhoneNumber());
        isFavorite = mNeighbour.isFavorite();

        Glide.with(this)
                .load(mNeighbour.getAvatarUrl())
                .into(mProfileAvatar);





        mAddFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFavorite) {
                    mAddFavoriteButton.setImageResource(R.drawable.ic_baseline_star_24);
                    addFavoriteNeighbour(mNeighbour);
                    Toast.makeText(getApplicationContext(), "Ajouté aux favoris", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAddFavoriteButton.setImageResource(R.drawable.ic_star_white_24dp);
                    deleteFavorite(mNeighbour);
                    Toast.makeText(getApplicationContext(), "Supprimé des favoris", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addFavoriteNeighbour (Neighbour neighbour) {
        isFavorite = true;
        mNeighbour.setIsFavorite(isFavorite);
        mFavoriteNeighbour.modifyNeighbour(neighbour);
        mFavoriteNeighbour.createFavoriteNeighbour(neighbour);
    }

    private void deleteFavorite (Neighbour neighbour) {
        isFavorite = false;
        mNeighbour.setIsFavorite(isFavorite);
        mFavoriteNeighbour.modifyNeighbour(neighbour);
        mFavoriteNeighbour.deleteFavoriteNeighbour(neighbour);
    }








}
