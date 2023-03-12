package com.gmail.hejosadix.starwars.presentation.people.details

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hejosadix.starwars.databinding.ItemVehicleBinding
import com.gmail.hejosadix.starwars.domain.models.Vehicle
import com.gmail.hejosadix.starwars.utils.extencions.ifNullOrBlankUnknown
import java.util.LinkedList

class VehicleAdapter : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    private val items: MutableList<Vehicle> = LinkedList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VehicleViewHolder(
        ItemVehicleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
    )

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(
        items: List<Vehicle>,
    ) {
        val result = DiffUtil.calculateDiff(
            ItemsDiffCallback(
                this.items, items,
            )
        )
        this.items.clear()
        this.items.addAll(items)
        result.dispatchUpdatesTo(this)
    }


    override fun getItemCount() = items.size

    inner class VehicleViewHolder(
        private val binding: ItemVehicleBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: Vehicle,
        ) {

            with(binding) {
                nameTextView.text = item.name.ifNullOrBlankUnknown(root.resources)
            }

        }

    }

    private class ItemsDiffCallback(
        private val oldData: List<Vehicle>,
        private val newData: List<Vehicle>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldData.size

        override fun getNewListSize(): Int = newData.size

        override fun areItemsTheSame(
            oldItemPosition: Int, newItemPosition: Int,
        ): Boolean {
            return oldData[oldItemPosition].id == newData[newItemPosition].id
        }

        override fun areContentsTheSame(
            oldItemPosition: Int, newItemPosition: Int,
        ): Boolean {
            return oldData[oldItemPosition] == newData[newItemPosition]
        }
    }

}