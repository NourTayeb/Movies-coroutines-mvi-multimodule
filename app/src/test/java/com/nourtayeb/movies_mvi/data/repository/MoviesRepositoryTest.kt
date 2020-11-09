package com.nourtayeb.movies_mvi.data.repository

import com.nourtayeb.movies_mvi.common.config.TMDB_API_KEY
import com.nourtayeb.movies_mvi.common.dummyMovies
import com.nourtayeb.movies_mvi.common.dummyUserMovies
import com.nourtayeb.movies_mvi.common.emptyMovies
import com.nourtayeb.movies_mvi.common.emptyUserMovies
import com.nourtayeb.movies_mvi.data.local.db.MoviesDao
import com.nourtayeb.movies_mvi.data.local.db.UserDao
import com.nourtayeb.movies_mvi.data.local.db.entities.UserMovie
import com.nourtayeb.movies_mvi.data.mapper.MovieMapper
import com.nourtayeb.movies_mvi.data.network.ApiService
import com.nourtayeb.movies_mvi.data.network.reponse.searchmovies.SearchMoviesResponse
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MoviesRepositoryTest {
    @MockK
    lateinit var api : ApiService


    @RelaxedMockK
    lateinit var moviesDao: MoviesDao

    @RelaxedMockK
    lateinit var userDao:UserDao


    val mapper = MovieMapper()

    lateinit var repoistory: MoviesRepositoryImp
    @Before
    fun init(){
        MockKAnnotations.init(this)
        repoistory = MoviesRepositoryImp(api,mapper,moviesDao,userDao)
    }

    @Test
    fun `search movies from remote calls database dao insertMovies for resulted movies`(){
        val key = "mile"
        val userId = 2
        coEvery { userDao.getUserMovies(userId) } returns emptyUserMovies
        coEvery { api.searchMovie(TMDB_API_KEY,key) } returns SearchMoviesResponse(dummyMovies.map { mapper.DomainToRetrofit(it) })
        runBlockingTest {
            val movies = repoistory.searchMovies(key,true,userId)
            coVerify { moviesDao.insertMovies(movies.map { mapper.DomainToRoom(it) }) }
        }
    }

    @Test
    fun `search movies from remote calls database dao insertMovies for resulted movies - non-empty values`(){
       val key = "mile"
        val userId = 2
        coEvery { userDao.getUserMovies(userId) } returns emptyUserMovies
        coEvery { api.searchMovie(TMDB_API_KEY,key) } returns SearchMoviesResponse(dummyMovies.map { mapper.DomainToRetrofit(it) })
        runBlockingTest {
            val movies = repoistory.searchMovies(key,true,userId)
            coVerify (exactly = 0) { moviesDao.insertMovies(emptyMovies .map { mapper.DomainToRoom(it) }) }
        }
    }

    @Test
    fun `search movies from local doesnt interact with remote api, only calls search dao`(){
        val key = "mile"
        val fromRemote = false
        val userId = 2
        coEvery { moviesDao.search("%"+key+"%") } returns listOf()
        runBlockingTest {
            repoistory.searchMovies(key,fromRemote,userId)
            verify { api wasNot Called }
        }
    }

    @Test
    fun `toggleFavorite calls dao addToFav with isFav true`(){
        val isFav = true
        val id = 2
        val  userId = 2
        coEvery { moviesDao.addToFavorite(UserMovie(userId,id) ) } returns 1
        runBlockingTest {
            repoistory.toggleFavorite(isFav , id,userId)
        }
        coVerify { moviesDao.addToFavorite( UserMovie(userId,id)) }
    }
    @Test
    fun `toggleFavorite calls dao removeFromFavorite with isFav false`(){
        val isFav = false
        val id = 2
        val  userId = 2
        coEvery { moviesDao.removeFromFavorite(userId,id ) } returns 1
        runBlockingTest {
            repoistory.toggleFavorite(isFav , id,userId)
        }
        coVerify { moviesDao.removeFromFavorite( userId,id) }
    }

    @Test
    fun `get favorite gets favorite from dao getFavorite`(){
        val userId =2
        coEvery { userDao.getUserMovies(userId) } returns dummyUserMovies(dummyMovies,mapper)
        runBlockingTest {
            val favorite = repoistory.getFavorite(userId)
            Assert.assertEquals(favorite, dummyMovies.onEach { it.isFav =true })
            coVerify { userDao.getUserMovies(userId) }
        }
    }
}