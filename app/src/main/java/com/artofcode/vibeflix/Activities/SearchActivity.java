package com.artofcode.vibeflix.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.artofcode.vibeflix.Adapters.FilmListAdapter;
import com.artofcode.vibeflix.Domains.Film;
import com.artofcode.vibeflix.databinding.ActivitySearchBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ActivitySearchBinding binding;
    private FirebaseDatabase database;
    private ArrayList<Film> allMovies = new ArrayList<>();
    private FilmListAdapter topMoviesAdapter;
    private FilmListAdapter upcomingMoviesAdapter;
    private void filterMovies(String query, RecyclerView recyclerView, ArrayList<Film> allMovies, FilmListAdapter adapter) {
        ArrayList<Film> filteredList = new ArrayList<>();
        for (Film film : allMovies) {
            if (film.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(film);
            }
        }
        adapter.updateList(filteredList);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseDatabase.getInstance();

        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AllMovies();

        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovies(s.toString(), binding.recyclerViewTop, allMovies, topMoviesAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void AllMovies() {
        DatabaseReference myRef = database.getReference("Items");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        Film film = issue.getValue(Film.class);
                        allMovies.add(film);
                    }
                    if (!allMovies.isEmpty()) {
                        topMoviesAdapter = new FilmListAdapter(allMovies);
                        binding.recyclerViewTop.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        binding.recyclerViewTop.setAdapter(topMoviesAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
