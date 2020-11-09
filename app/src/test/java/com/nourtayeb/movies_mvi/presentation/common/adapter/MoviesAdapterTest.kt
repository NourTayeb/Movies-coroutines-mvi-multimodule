package com.nourtayeb.movies_mvi.presentation.common.adapter

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nourtayeb.movies_mvi.common.dummyMovies
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P ])
class MoviesAdapterTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @SpyK
    lateinit var moviesAdapter: MoviesAdapter

    @RelaxedMockK
    lateinit var viewholder :MoviesAdapter.RepositoriesViewHolder

    @Before
    fun init(){
//        activity = Robolectric.setupActivity(FragmentActivity::class.java)
//        viewholder = mockk()
        moviesAdapter = spyk(MoviesAdapter())
        MockKAnnotations.init(this)
        moviesAdapter.addData(dummyMovies)
        moviesAdapter.list.addAll(dummyMovies)
    }

    @Test
    fun `changeItemIsFav notifies item changed` (){
        moviesAdapter.changeItemIsFav(true,1)
        verify { moviesAdapter.notifyItemChanged(any()) }
    }
    @Test
    fun `onBindViewHolder binds viewholder for getItem at passed position`(){
        val position =2
        val item = moviesAdapter.getItem(position)
        moviesAdapter.onBindViewHolder(viewholder,2)
        verify { viewholder.bind(item,any(),any()) }
    }


}