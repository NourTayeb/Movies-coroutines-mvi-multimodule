package com.nourtayeb.movies_mvi.presentation.common.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nourtayeb.movies_mvi.R
import com.nourtayeb.movies_mvi.common.base.BaseRecyclerViewAdapter
import com.nourtayeb.movies_mvi.common.utility.getBitmapOfUrl
import com.nourtayeb.movies_mvi.common.utility.loadImageFromUrl
import com.nourtayeb.movies_mvi.domain.entity.Movie
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MoviesAdapter: BaseRecyclerViewAdapter<Movie, MoviesAdapter.RepositoriesViewHolder>() {
    var onFavoriteClicked: (Boolean, Int) -> Unit = { _,_ -> }
     var onShareClicked:(bitmap:Bitmap) -> Unit  = { _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
        return RepositoriesViewHolder(itemView = itemView)
    }

    fun changeItemIsFav(isFav : Boolean,id:Int){
        val index = list.indexOfFirst { it.id == id }
        if(index != -1){
            list.get(index).isFav = isFav
            notifyItemChanged(index)
        }
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        holder.bind(getItem(position),onFavoriteClicked,onShareClicked)
    }


     class RepositoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Movie, onFavoriteClicked: (Boolean, Int) -> Unit = { _, _ ->}, onShareClicked:(bitmap:Bitmap) -> Unit= {}){
            itemView.image?.loadImageFromUrl(item.getImage())
            itemView.title.text = item.title
            itemView.year.text = "("+item.release_date.split("-")[0]+")"
            itemView.rating.text = item.vote_average.toString()+"/10"
            itemView.released.text = item.release_date
            itemView.fav.isSelected = item.isFav
            itemView.fav.setOnClickListener {
                onFavoriteClicked?.let {
                    item.isFav = !item.isFav
                    itemView.fav.isSelected = item.isFav
                    onFavoriteClicked(itemView.fav.isSelected, item.id)
                }
            }
            itemView.share.setOnClickListener {
                item.getImage().getBitmapOfUrl(itemView.image) {
                    onShareClicked(it)
                }
            }
        }
    }

}
