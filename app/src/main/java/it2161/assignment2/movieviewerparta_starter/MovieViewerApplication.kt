package it2161.assignment2.movieviewerparta_starter

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.util.Log
import it2161.assignment2.movieviewerparta_starter.data.DatabaseAdapter
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieSampleData
import it2161.assignment2.movieviewerparta_starter.entity.SimpleMovieItem

class MovieViewerApplication : Application() {

    companion object {
        var MovieItemArray : ArrayList<SimpleMovieItem> ?= null
        val appInstance = MovieViewerApplication()
    }
    init{
        MovieItemArray = SimpleMovieSampleData.simpleMovieitemArray
    }

    override fun onCreate() {
        super.onCreate()
        val movieViewerApplication = appInstance
        movieViewerApplication.populatedatabase(this)
    }

    private fun populatedatabase(c: Context): ArrayList<SimpleMovieItem>? {
        val db = DatabaseAdapter(c)
        db.open()

        for (movieItem in MovieItemArray!!){
            addToDatabase(
                movieItem.overview.toString(),
                movieItem.release_date.toString(),
                movieItem.original_langauge.toString(),
                movieItem.title.toString(),
                c
            )
        }
        db.close()
        return MovieItemArray
    }

    private fun addToDatabase(movieOverview: String, movieReleaseDate: String, movieOriginalLanguage: String, movieTitle: String, c: Context): Long? {
        val db = DatabaseAdapter(c)
        db.open()

        // Checking if the movie with the same title exists in the database using cursor
        val existingMovieTitle = db.retrieveEntryByTitle(movieTitle)

        if (existingMovieTitle != null && existingMovieTitle.count > 0) {
            // If Movie already exists in Database, print logcat msg, stop cursor and close database
            Log.w("Database Check", "Movie already exists in the database: $movieTitle")
            existingMovieTitle.close()
            db.close()
            return null
        }

        val rowIDofInsertedEntry = db.insertEntry(
            movieOverview,
            movieReleaseDate,
            movieOriginalLanguage,
            movieTitle
        )
        Log.w("Database Addition", "Movie added to Database: ${movieTitle}")
        existingMovieTitle?.close()
        db.close()

        return rowIDofInsertedEntry
    }

    fun addToFavDatabase(movieOverview: String, movieReleaseDate: String, movieOriginalLanguage: String, movieTitle: String, c: Context): Long? {
        val db = DatabaseAdapter(c)
        db.open()

        // Checking if the movie with the same title exists in the database using cursor
        val existingMovieTitle = db.retrieveFavEntryByTitle(movieTitle)

        if (existingMovieTitle != null && existingMovieTitle.count > 0) {
            // If Movie already exist in Favourite Database, print logcat msg, stop cursor and close database
            Log.w("Database Check", "Movie already exists in the favourites database: $movieTitle")
            existingMovieTitle.close()
            db.close()
            return null
        }

        val rowIDofInsertedEntry = db.insertFavoriteEntry(
            movieOverview,
            movieReleaseDate,
            movieOriginalLanguage,
            movieTitle
        )
        Log.w("Database Addition", "Movie added to Favourite Database: ${movieTitle}")
        existingMovieTitle?.close()
        db.close()
        return rowIDofInsertedEntry
    }

    fun deleteFrmFavDatabase(movieTitle: String, c: Context): Boolean {
        val db = DatabaseAdapter(c)
        db.open()
        val updateStatus = db.removeFavoriteEntry(movieTitle)
        Log.w("Database Removal", "Movie removed from Favourite Database: ${movieTitle}")
        db.close()
        return updateStatus
    }

    fun retrieveAll(c: Context): ArrayList<SimpleMovieItem> {
        val myCursor: Cursor?
        val movieItemList = ArrayList<SimpleMovieItem>()
        val db = DatabaseAdapter(c)
        db.open()
        myCursor = db.retrieveAllEntriesCursor()
        if (myCursor != null && myCursor.count > 0) {
            myCursor.moveToFirst()
            do {
                val movieItem = SimpleMovieItem(
                    myCursor.getString(db.COLUMN_MOVIE_OVERVIEW),
                    myCursor.getString(db.COLUMN_MOVIE_RELEASE_DATE),
                    myCursor.getString(db.COLUMN_MOVIE_ORIGINAL_LANGUAGE),
                    myCursor.getString(db.COLUMN_MOVIE_TITLE)
                )
                movieItemList.add(movieItem)
            } while (myCursor.moveToNext())
        }
        db.close()

        return movieItemList
    }

    fun retrieveAllFavourites(c: Context): ArrayList<SimpleMovieItem> {
        val myCursor: Cursor?
        val db = DatabaseAdapter(c)
        db.open()
        val movieItemList = ArrayList<SimpleMovieItem>()
        myCursor = db.retrieveAllFavouritesCursor()
        if (myCursor != null && myCursor.count > 0) {
            myCursor.moveToFirst()
            do {
                val movieItem = SimpleMovieItem(
                    myCursor.getString(db.COLUMN_MOVIE_OVERVIEW),
                    myCursor.getString(db.COLUMN_MOVIE_RELEASE_DATE),
                    myCursor.getString(db.COLUMN_MOVIE_ORIGINAL_LANGUAGE),
                    myCursor.getString(db.COLUMN_MOVIE_TITLE)
                )
                movieItemList.add(movieItem)
            } while (myCursor.moveToNext())
        }
        db.close()

        return movieItemList
    }
}
