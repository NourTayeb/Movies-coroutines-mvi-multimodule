package com.nourtayeb.movies_mvi.domain.entity

import com.nourtayeb.movies_mvi.common.config.IMAGES_BASE_URL
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieUnitTest {

    lateinit var movie: Movie

    val imageName= "image.jpg"

    @Before
    fun init(){
        movie = Movie(imageName)
    }
    @Test
    fun `getImage in movie concatentes with baseurl`(){
        Assert.assertEquals(movie.getImage(),IMAGES_BASE_URL+imageName)
    }
}