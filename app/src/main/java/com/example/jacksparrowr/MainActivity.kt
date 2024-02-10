package com.example.jacksparrowr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var tvShowsRecyclerView: RecyclerView
    private lateinit var animeRecyclerView: RecyclerView
    private lateinit var moviesAdapter: CustomAdapter
    private lateinit var tvShowsAdapter: CustomAdapter
    private lateinit var animeAdapter: CustomAdapter
    private lateinit var moviesList: MutableList<Movie>
    private lateinit var tvShowsList: MutableList<TvShow>
    private lateinit var animeList: MutableList<Anime>
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsButton: Button = findViewById(R.id.settingsButton)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        moviesRecyclerView = findViewById(R.id.moviesRecycler)
        tvShowsRecyclerView = findViewById(R.id.tvShowsRecycler)
        animeRecyclerView = findViewById(R.id.animeRecycler)

        moviesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        tvShowsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        animeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        moviesList = mutableListOf()
        tvShowsList = mutableListOf()
        animeList = mutableListOf()

        moviesAdapter = CustomAdapter(this, moviesList)
        tvShowsAdapter = CustomAdapter(this, tvShowsList)
        animeAdapter = CustomAdapter(this, animeList)

        moviesRecyclerView.adapter = moviesAdapter
        tvShowsRecyclerView.adapter = tvShowsAdapter
        animeRecyclerView.adapter = animeAdapter

        database = FirebaseDatabase.getInstance().reference

        // Fetch data for Movies
        database.child("movies").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (movieSnapshot in snapshot.children) {
                        val movie = movieSnapshot.getValue(Movie::class.java)
                        movie?.let {
                            moviesList.add(it)
                        }
                    }
                    moviesAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        // Fetch data for TvShows
        database.child("tvshows").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (tvShowSnapshot in snapshot.children) {
                        val tvShow = tvShowSnapshot.getValue(TvShow::class.java)
                        tvShow?.let {
                            tvShowsList.add(it)
                        }
                    }
                    tvShowsAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        // Fetch data for Anime
        database.child("anime").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (animeSnapshot in snapshot.children) {
                        val anime = animeSnapshot.getValue(Anime::class.java)
                        anime?.let {
                            animeList.add(it)
                        }
                    }
                    animeAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
