package it2161.assignment2.movieviewerparta_starter.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SimpleMovieDbHelper (c: Context, db_name: String, ver_no:Int) : SQLiteOpenHelper(c, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_NAME = "Movies.db"
        val DATABASE_TABLE = "MoviesDb"
        val FAVOURITE_DATABASE_TABLE = "FavouriteMoviesDb"
        val DATABASE_VERSION = 3
        var _db: SQLiteDatabase? = null
        val context: Context?= null

        val KEY_ID = "_id"
        val MOVIE_OVERVIEW = "_overview"
        val MOVIE_RELEASE_DATE = "_release_date"
        val MOVIE_ORIGINAL_LANGUAGE = "_original_langauge"
        val MOVIE_TITLE = "_title"

        protected val DATABASE_CREATE =
            ("create table " + DATABASE_TABLE + " "
                + "(" + KEY_ID + " integer primary key autoincrement, "
                + MOVIE_OVERVIEW + " Text, "
                + MOVIE_RELEASE_DATE + " Text, "
                + MOVIE_ORIGINAL_LANGUAGE + " Text, "
                + MOVIE_TITLE + " Text)" )

        val MYDBADAPTER_LOG_CAT = "MY_LOG"

        protected val DATABASE_CREATE_FAVORITE =
            ("create table " + FAVOURITE_DATABASE_TABLE + " "
                    + "(" + KEY_ID + " integer primary key autoincrement, "
                    + MOVIE_OVERVIEW + " Text, "
                    + MOVIE_RELEASE_DATE + " Text, "
                    + MOVIE_ORIGINAL_LANGUAGE + " Text, "
                    + MOVIE_TITLE + " Text)")
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(DATABASE_CREATE)
        db!!.execSQL(DATABASE_CREATE_FAVORITE)
        Log.w(MYDBADAPTER_LOG_CAT, "HELPER : DB $DATABASE_TABLE , $FAVOURITE_DATABASE_TABLE created!")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) {
            //To drop existing tables and recreate them, if database was created with an error(used in debugging to remove and recreate tables)
            db?.execSQL("DROP TABLE IF EXISTS $DATABASE_TABLE")
            db?.execSQL("DROP TABLE IF EXISTS $FAVOURITE_DATABASE_TABLE")
            onCreate(db)
        }
    }


}