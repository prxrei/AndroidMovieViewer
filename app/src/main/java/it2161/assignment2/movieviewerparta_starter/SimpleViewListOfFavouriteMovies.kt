package it2161.assignment2.movieviewerparta_starter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import it2161.assignment2.movieviewerparta_starter.entity.SimpleMovieItem
import kotlinx.android.synthetic.main.activity_simple_view_list_of_favourite_movies.favmovieslist
import kotlinx.android.synthetic.main.activity_simple_view_list_of_movies.noitemslist
import kotlinx.android.synthetic.main.listviewitem.view.displaydate
import kotlinx.android.synthetic.main.listviewitem.view.displaytitle

class SimpleViewListOfFavouriteMovies : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_view_list_of_favourite_movies)

        retrieveFavoriteMovies()

        val actionbar = supportActionBar

        actionbar?.title = "MovieViewerPartA_235008L"

        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun retrieveFavoriteMovies() {
        val favouriteMovieList : ArrayList<SimpleMovieItem>
        val mc = MovieViewerApplication()
        favouriteMovieList = mc.retrieveAllFavourites(applicationContext)

        val customizedAdapter = object : BaseAdapter() {
            override fun getCount(): Int {
                return favouriteMovieList.size
            }

            override fun getItem(position: Int): Any {
                return favouriteMovieList[position]
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val v: View = layoutInflater.inflate(R.layout.listviewitem, parent, false)

                val titleTextView: TextView = v.displaytitle
                val dateTextView: TextView = v.displaydate

                val currentMovieItem: SimpleMovieItem = getItem(position) as SimpleMovieItem

                titleTextView.text = currentMovieItem.title // Title
                dateTextView.text = currentMovieItem.release_date // Release Date

                return v
            }

        }

        favmovieslist.adapter = customizedAdapter

        favmovieslist.setOnItemClickListener { _, _, position, _ ->
            val selectedFavoriteItem: SimpleMovieItem = favouriteMovieList[position]

            val myIntent = Intent(this@SimpleViewListOfFavouriteMovies, SimpleFavouriteItemDetailActivity::class.java)
            myIntent.putExtra("title", selectedFavoriteItem.title)
            myIntent.putExtra("releaseDate", selectedFavoriteItem.release_date)
            myIntent.putExtra("originalLanguage", selectedFavoriteItem.original_langauge)
            myIntent.putExtra("overview", selectedFavoriteItem.overview)
            startActivity(myIntent)
        }
    }

    private fun toggleVisibility() {
        if (favmovieslist?.count ?: 0 > 0) { 
            noitemslist?.visibility = View.GONE
            favmovieslist?.visibility = View.VISIBLE
        } else {
            favmovieslist?.visibility = View.GONE
            noitemslist?.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed(){
        super.onBackPressed()
        finish()
    }

    override fun onResume(){
        //CAll retrieveFavouriteMovies here to update and display latest database
        retrieveFavoriteMovies()
        toggleVisibility()
        super.onResume()
    }
}
