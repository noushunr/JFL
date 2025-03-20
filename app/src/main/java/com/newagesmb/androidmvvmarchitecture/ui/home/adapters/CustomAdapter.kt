package com.newagesmb.androidmvvmarchitecture.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.newagesmb.androidmvvmarchitecture.databinding.ListItemBinding

class CustomAdapter(private val onItemClick:()->Unit):ListAdapter<String,RecyclerView.ViewHolder>(listDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
           val item=getItem(position)
        (holder as ListViewHolder).bind(item,position,onItemClick)

    }


}

class ListViewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(item:String,position: Int,click:()->Unit){
        binding.apply {
            this.itemTittle.text = item
        }
        binding.root.setOnClickListener {
            if (position != RecyclerView.NO_POSITION){
                 click.invoke()
            }
        }
    }


}

private class listDiffCallback:DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return  oldItem.length==newItem.length
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
     return oldItem==newItem
    }

}