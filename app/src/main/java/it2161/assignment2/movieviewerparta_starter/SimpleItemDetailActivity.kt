package it2161.assignment2.movieviewerparta_starter

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_simple_item_detail.languagedata
import kotlinx.android.synthetic.main.activity_simple_item_detail.movietitle
import kotlinx.android.synthetic.main.activity_simple_item_detail.overviewdata
import kotlinx.android.synthetic.main.activity_simple_item_detail.releasedatedata

class SimpleItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_item_detail)

        val title = intent.getStringExtra("title")
        val releaseDate = intent.getStringExtra("releaseDate")
        val originalLanguage = intent.getStringExtra("originalLanguage")
        val overview = intent.getStringExtra("overview")

        movietitle.text = title
        releasedatedata.text = releaseDate
        languagedata.text = originalLanguage
        overviewdata.text = overview

        val actionbar = supportActionBar

        actionbar?.title = "MovieViewerPartA_235008L"

        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favourites, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addfavourite -> {
                val mc = MovieViewerApplication.appInstance
                val addMovieOverview = overviewdata.text.toString().trim()
                val addMovieReleaseDate = releasedatedata.text.toString().trim()
                val addMovieOriginalLanguage = languagedata.text.toString().trim()
                val addMovieTitle = movietitle.text.toString().trim()
                mc.addToFavDatabase(addMovieOverview, addMovieReleaseDate, addMovieOriginalLanguage, addMovieTitle, applicationContext)
                showToast("Movie Added to Favourites")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed(){
        super.onBackPressed()
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}