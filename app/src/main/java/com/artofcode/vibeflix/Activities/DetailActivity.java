package com.artofcode.vibeflix.Activities;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import com.artofcode.vibeflix.Adapters.CastListAdapter;
import com.artofcode.vibeflix.Domains.Film;
import com.artofcode.vibeflix.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();

        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void setVariable() {
        Film item =(Film) getIntent().getSerializableExtra("object");
        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop(), new GranularRoundedCorners(0,0,50,50));

        Glide.with(this)
                .load(item.getPoster())
                .apply(requestOptions)
                .into(binding.filmPic);

        binding.titleTxt.setText(item.getTitle());
        binding.imdbTxt.setText("IMDB "+item.getImdb());
        binding.movieTimesTxt.setText(item.getYear()+" - "+item.getTime());
        binding.movieDescription.setText(item.getDescription());
///////////////////////////////////////////////////////////////////////////////////////////////
        binding.watchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open the VideoPlayer activity
                Intent intent = new Intent(DetailActivity.this, VideoPlayer.class);

                // Pass the Film object to the VideoPlayer activity
                intent.putExtra("link", item.getTrailer());

                // Start the VideoPlayer activity
                startActivity(intent);
            }
        });


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.backImg.setOnClickListener(v -> finish());
        float radius=10f;
        View decorView= getWindow().getDecorView();
        ViewGroup rootView= (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowsBackground= decorView.getBackground();

        if(item.getCasts()!=null){
            binding.castView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.castView.setAdapter(new CastListAdapter(item.getCasts()));
        }

    }


}