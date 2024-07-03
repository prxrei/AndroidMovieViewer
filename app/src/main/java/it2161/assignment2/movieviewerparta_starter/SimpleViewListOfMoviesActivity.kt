package it2161.assignment2.movieviewerparta_starter

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import it2161.assignment2.movieviewerparta_starter.entity.SimpleMovieItem
import kotlinx.android.synthetic.main.activity_simple_view_list_of_movies.movieslist
import kotlinx.android.synthetic.main.activity_simple_view_list_of_movies.noitemslist
import kotlinx.android.synthetic.main.listviewitem.view.displaydate
import kotlinx.android.synthetic.main.listviewitem.view.displaytitle

class SimpleViewListOfMoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_view_list_of_movies)
        retrieveMovies()
    }

    private fun retrieveMovies() {
        val movieList: ArrayList<SimpleMovieItem>
        val mc = MovieViewerApplication()
        movieList = mc.retrieveAll(applicationContext)

        val customizedAdapter = object : BaseAdapter() {
            override fun getCount(): Int {
                return movieList.size
            }

            override fun getItem(position: Int): Any {
                return movieList[position]
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
        movieslist.adapter = customizedAdapter

        movieslist.setOnItemClickListener { _, _, position, _ ->
            val selectedItem: SimpleMovieItem = movieList[position]

            val myIntent = Intent(this@SimpleViewListOfMoviesActivity, SimpleItemDetailActivity::class.java)
            myIntent.putExtra("title", selectedItem.title)
            myIntent.putExtra("releaseDate", selectedItem.release_date)
            myIntent.putExtra("originalLanguage", selectedItem.original_langauge)
            myIntent.putExtra("overview", selectedItem.overview)

            startActivity(myIntent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Signout -> {
                val myIntent = Intent(this, LoginScreen::class.java)
                startActivity(myIntent)
            }
            R.id.viewfav -> {
                val myIntent = Intent(this, SimpleViewListOfFavouriteMovies::class.java)
                startActivity(myIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggleVisibility() {
        if (movieslist.count > 0) {
            noitemslist.visibility = View.GONE
            movieslist.visibility = View.VISIBLE
        } else {
            movieslist.visibility = View.GONE
            noitemslist.visibility = View.VISIBLE
        }
    }

    override fun onResume(){
        //CAll retrieveMovies here to update and display latest database
        retrieveMovies()
        toggleVisibility()
        super.onResume()
    }
}
