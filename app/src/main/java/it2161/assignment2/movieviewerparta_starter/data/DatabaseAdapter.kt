package it2161.assignment2.movieviewerparta_starter.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieDbHelper.Companion.DATABASE_NAME
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieDbHelper.Companion.DATABASE_TABLE
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieDbHelper.Companion.DATABASE_VERSION
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieDbHelper.Companion.FAVOURITE_DATABASE_TABLE
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieDbHelper.Companion.KEY_ID
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieDbHelper.Companion.MOVIE_ORIGINAL_LANGUAGE
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieDbHelper.Companion.MOVIE_OVERVIEW
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieDbHelper.Companion.MOVIE_RELEASE_DATE
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieDbHelper.Companion.MOVIE_TITLE
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieDbHelper.Companion.MYDBADAPTER_LOG_CAT
import it2161.assignment2.movieviewerparta_starter.data.SimpleMovieDbHelper.Companion._db

class DatabaseAdapter(c: Context) {
    val COLUMN_MOVIE_OVERVIEW = 1
    val COLUMN_MOVIE_RELEASE_DATE = 2
    val COLUMN_MOVIE_ORIGINAL_LANGUAGE = 3
    val COLUMN_MOVIE_TITLE = 4

    private var dbHelper: SimpleMovieDbHelper? = null

    init {
        dbHelper = SimpleMovieDbHelper(c, DATABASE_NAME, DATABASE_VERSION)
    }

    fun open(){
        try {
            _db = dbHelper?.getWritableDatabase()
        }
        catch(e: SQLiteException) {
            _db = dbHelper?.getReadableDatabase()
        }
    }

    fun close(){
        _db?.close()
    }

    fun insertEntry(movieOverview: String, movieReleaseDate: String, movieOriginalLanguage: String, movieTitle: String): Long? {
        val newEntryValues = ContentValues()

        newEntryValues.put(MOVIE_OVERVIEW, movieOverview)
        newEntryValues.put(MOVIE_RELEASE_DATE, movieReleaseDate)
        newEntryValues.put(MOVIE_ORIGINAL_LANGUAGE, movieOriginalLanguage)
        newEntryValues.put(MOVIE_TITLE, movieTitle)

        return _db?.insert(DATABASE_TABLE, null, newEntryValues)
    }

    fun insertFavoriteEntry(movieOverview: String, movieReleaseDate: String, movieOriginalLanguage: String, movieTitle: String): Long? {
        val newEntryValues = ContentValues()

        newEntryValues.put(MOVIE_OVERVIEW, movieOverview)
        newEntryValues.put(MOVIE_RELEASE_DATE, movieReleaseDate)
        newEntryValues.put(MOVIE_ORIGINAL_LANGUAGE, movieOriginalLanguage)
        newEntryValues.put(MOVIE_TITLE, movieTitle)

        return _db?.insert(FAVOURITE_DATABASE_TABLE, null, newEntryValues)
    }

    fun removeFavoriteEntry(movieTitle: String): Boolean {
        val condition = "$MOVIE_TITLE = '$movieTitle'"

        if (_db!!.delete(
                FAVOURITE_DATABASE_TABLE,
                condition,
                null) <= 0) {
            Log.w(MYDBADAPTER_LOG_CAT, "Removing favorite entry where title = $movieTitle failed")
            return false
        }
        return true
    }

    fun retrieveEntryByTitle(movieTitle: String): Cursor? {
        var c: Cursor? = null
        try {
            c = _db?.query(
                DATABASE_TABLE,
                arrayOf(KEY_ID, MOVIE_OVERVIEW, MOVIE_RELEASE_DATE, MOVIE_ORIGINAL_LANGUAGE, MOVIE_TITLE),
                "$MOVIE_TITLE = ?",
                arrayOf(movieTitle),
                null,
                null,
                null
            )
        } catch (e: SQLiteException) {
            Log.w(DATABASE_TABLE, "Retrieve by title failed", e)
        }
        return c
    }

    fun retrieveFavEntryByTitle(movieTitle: String): Cursor? {
        var c: Cursor? = null
        try {
            c = _db?.query(
                FAVOURITE_DATABASE_TABLE,
                arrayOf(KEY_ID, MOVIE_OVERVIEW, MOVIE_RELEASE_DATE, MOVIE_ORIGINAL_LANGUAGE, MOVIE_TITLE),
                "$MOVIE_TITLE = ?",
                arrayOf(movieTitle),
                null,
                null,
                null
            )
        } catch (e: SQLiteException) {
            Log.w(DATABASE_TABLE, "Retrieve by title failed", e)
        }
        return c
    }

    fun retrieveAllEntriesCursor(): Cursor?{
        var c: Cursor? = null
        try {
            c = _db?.query(DATABASE_TABLE,
                arrayOf(KEY_ID, MOVIE_OVERVIEW, MOVIE_RELEASE_DATE, MOVIE_ORIGINAL_LANGUAGE, MOVIE_TITLE),
                null, null, null, null, null)
        }
        catch (e: SQLiteException){
            Log.w(DATABASE_TABLE, "Retrieve fail")
        }
        return c
    }

    fun retrieveAllFavouritesCursor(): Cursor? {
        var c: Cursor? = null
        try {
            c = _db?.query(
                FAVOURITE_DATABASE_TABLE,
                arrayOf(KEY_ID, MOVIE_OVERVIEW, MOVIE_RELEASE_DATE, MOVIE_ORIGINAL_LANGUAGE, MOVIE_TITLE),
                null, null, null, null, null
            )
        } catch (e: SQLiteException) {
            Log.w(FAVOURITE_DATABASE_TABLE, "Retrieve favorites fail", e)
        }
        return c
    }
}