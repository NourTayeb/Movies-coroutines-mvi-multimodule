package com.nourtayeb.movies_mvi.presentation.common.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import com.nourtayeb.movies_mvi.common.dummyMovies
import com.nourtayeb.movies_mvi.common.utility.loadImageFromUrl
import com.nourtayeb.movies_mvi.presentation.common.adapter.MoviesAdapter.RepositoriesViewHolder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.android.synthetic.main.list_item_movie.view.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class RepositoriesViewHolderTest {


    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    lateinit var viewHolder: RepositoriesViewHolder

    @RelaxedMockK
    lateinit var view: View


    lateinit var imageView: ImageView
    @Before
    fun init() {
        MockKAnnotations.init(this)
        val activity = Robolectric.setupActivity(FragmentActivity::class.java)
        setUpDummyViewholder(activity)
        viewHolder = RepositoriesViewHolder(view)//spyk(RepositoriesViewHolder(view))
    }//android.widget.ImageView{18ef2c11 V.ED..... ........ 0,0-0,0}  ,  //  https://image.tmdb.org/t/p/w500/url
    //android.widget.ImageView{18ef2c11 V.ED..... ........ 0,0-0,0}
    fun setUpDummyViewholder(activity:Context){
        every { view.image } returns ImageView(activity)
        every { view.fav } returns ImageView(activity)
        every { view.share } returns ImageView(activity)
        every { view.title } returns TextView(activity)
        every { view.year } returns TextView(activity)
        every { view.rating } returns TextView(activity)
        every { view.released } returns TextView(activity)
    }

    @Test
    fun `RepositoriesViewHolder bind calls extention function loadImageFromUrl`() {
        mockkStatic("com.nourtayeb.movies_mvi.common.utility.ImageUtilityKt")
        viewHolder.bind(dummyMovies[0])
        verify { any<ImageView>().loadImageFromUrl(dummyMovies[0].getImage()) }
    }
    @Test
    fun `RepositoriesViewHolder bind calls sets title to movie title`() {
        viewHolder.bind(dummyMovies[0])
        Assert.assertEquals(viewHolder.itemView.title.text, dummyMovies[0].title)
    }
    @Test
    fun `RepositoriesViewHolder bind calls sets year to movie release_date split dash between brackets`() {
        viewHolder.bind(dummyMovies[0])
        Assert.assertEquals(viewHolder.itemView.year.text,"("+dummyMovies[0].release_date.split("-")[0]+")")
    }

    @Test
    fun `RepositoriesViewHolder bind calls sets rating to movie vote_average slash 10`() {
        viewHolder.bind(dummyMovies[0])
        Assert.assertEquals(viewHolder.itemView.rating.text, dummyMovies[0].vote_average.toString()+"/10")
    }
    @Test
    fun `RepositoriesViewHolder bind calls sets released to movie release_date`() {
        viewHolder.bind(dummyMovies[0])
        Assert.assertEquals(viewHolder.itemView.released.text, dummyMovies[0].release_date)
    }
}