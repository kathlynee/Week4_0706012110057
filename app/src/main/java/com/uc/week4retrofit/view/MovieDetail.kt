package com.uc.week4retrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.uc.week4retrofit.adapter.CompanyAdapter
import com.uc.week4retrofit.adapter.CountryAdapter
import com.uc.week4retrofit.adapter.GenreAdapter
import com.uc.week4retrofit.adapter.LanguageAdapter
import com.uc.week4retrofit.databinding.ActivityMovieDetailBinding
import com.uc.week4retrofit.helper.Const
import com.uc.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetail : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: GenreAdapter
    private lateinit var adapter_company: CompanyAdapter
    private lateinit var adapter_country: CountryAdapter
    private lateinit var adapter_language: LanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movie_id", 0)
        Toast.makeText(applicationContext, "Movie ID: ${movieId}",
            Toast.LENGTH_SHORT).show()

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        viewModel.getMovieDetail(Const.API_KEY, movieId)
        viewModel.movieDetails.observe(this, Observer{
            response->

            if (response != null){
                binding.loading.visibility = View.INVISIBLE
            }
            binding.tvTitleMovieDetail.apply {
                text = response.title
            }
            Glide.with(applicationContext).load(Const.IMG_URL+response.backdrop_path).into(binding.imgPosterMovieDetail)
            binding.tvOverview.text = response.overview
            binding.tvStatus.text = response.status

            binding.rvGenre.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = GenreAdapter(response.genres)
            binding.rvGenre.adapter = adapter

            binding.rvCompany.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter_company = CompanyAdapter(response.production_companies)
            binding.rvCompany.adapter = adapter_company

            binding.rvCountry.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter_country = CountryAdapter(response.production_countries)
            binding.rvCountry.adapter = adapter_country

            binding.rvLanguage.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter_language = LanguageAdapter(response.spoken_languages)
            binding.rvLanguage.adapter = adapter_language

//    Toast.makeText(this, response.genres[0].name.toString(), Toast.LENGTH_SHORT).show()
        })
    }
}