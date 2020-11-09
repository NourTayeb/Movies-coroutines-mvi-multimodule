package com.nourtayeb.movies_mvi.common.base
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter <DomainEntity,VH:RecyclerView.ViewHolder>: RecyclerView.Adapter<VH>() {

    var onItemClick:(Any)->Unit ={}



    val list:MutableList<DomainEntity> = mutableListOf()

    override fun getItemCount(): Int {
        return  list.size
    }

    fun addData(list: List<DomainEntity>?){
        list?.let {
            this.list.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clearData(){
        list.clear()
        notifyDataSetChanged()
    }


    fun getItem(position: Int ) = list[position]

}