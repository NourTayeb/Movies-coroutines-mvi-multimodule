package com.nourtayeb.movies_mvi.data.repository

import com.nourtayeb.movies_mvi.common.di.LocaleStorageModule
import com.nourtayeb.movies_mvi.common.dumMovie
import com.nourtayeb.movies_mvi.common.dumUser
import com.nourtayeb.movies_mvi.common.movies1999
import com.nourtayeb.movies_mvi.data.local.db.AppDatabase
import com.nourtayeb.movies_mvi.data.local.db.MoviesDao
import com.nourtayeb.movies_mvi.data.local.db.UserDao
import com.nourtayeb.movies_mvi.data.mapper.MovieMapper
import com.nourtayeb.movies_mvi.data.network.ApiService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class MoviesRepositoryImp_InsTest {
    @Inject
    lateinit var moviesRepositoryImp: MoviesRepositoryImp

    @Inject
    lateinit var userRepositoryImp: UserRepositoryImp


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var moviesDao: MoviesDao
    @Inject
    lateinit var movieMapper: MovieMapper

    @Inject
    lateinit var apiService: ApiService
    @Inject
    lateinit var userDao:UserDao


    @Before
    fun init(){
        hiltRule.inject()
    }
    @Test
    fun `searchMovies_fromLocal_searches_in_sqlite`(){
        runBlocking {
            val key = "1999 movie"
            val fromRemote = false
            val userId = 3
            moviesDao.insertMovies(movies1999.map { movieMapper.DomainToRoom(it) })
            val retrievedMovies = moviesRepositoryImp.searchMovies(key,fromRemote,userId)
            val localMovies = moviesDao.search("%"+key+"%").map { movieMapper.RoomToDomain(it) }
            Assert.assertEquals(retrievedMovies,localMovies)
        }
    }
    @Test
    fun `searchMovies_fromRemote_inserts_results_in_sqlite`(){
        runBlocking {
            val key = "mile"
            val fromRemote = true
            val userId = 3
            val retrievedMovies = moviesRepositoryImp.searchMovies(key,fromRemote,userId)
            val localMovies = moviesDao.search("%"+key+"%").map { movieMapper.RoomToDomain(it) }
            Assert.assertTrue(localMovies.containsAll(retrievedMovies))
        }
    }
    @Test
    fun `searchMovies_fromRemote_for_mile_should_return_nonempty_result`(){
        runBlocking {
            val key = "mile"
            val fromRemote = true
            val userId = 3
            val retrievedMovies = moviesRepositoryImp.searchMovies(key,fromRemote,userId)
            Assert.assertFalse(retrievedMovies.isEmpty())
        }
    }
    @Test
    fun `toggle_favorite_true_adds_then_toggle_false_removes_it`(){
        runBlocking {
            var isFav = true
            userRepositoryImp.addLoggedInUser(dumUser)
            moviesRepositoryImp.toggleFavorite(isFav, dumMovie.id, dumUser.id)
            moviesDao.insertMovies(listOf(movieMapper.DomainToRoom(dumMovie)))
            val userMovies = userDao.getUserMovies(dumUser.id)
            Assert.assertTrue(userMovies.size==1)
            Assert.assertTrue(userMovies[0].userLocal.id== dumUser.id)
            val favoriteMovies = userMovies[0].movies
            Assert.assertTrue(favoriteMovies.map { it.id }.contains(dumMovie.id))
            isFav = false
            moviesRepositoryImp.toggleFavorite(isFav, dumMovie.id, dumUser.id)

            val userMoviesAfterDelete = userDao.getUserMovies(dumUser.id)
            if(userMoviesAfterDelete.size==1){
                val favoriteMoviesAfterDelete = userMoviesAfterDelete[0].movies
                Assert.assertFalse(favoriteMoviesAfterDelete.map { it.id }.contains(dumMovie.id))
            }
        }
    }
}