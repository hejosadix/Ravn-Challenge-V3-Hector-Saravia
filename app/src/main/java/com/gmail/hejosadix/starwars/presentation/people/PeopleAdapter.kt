package com.gmail.hejosadix.starwars.presentation.people

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hejosadix.starwars.R
import com.gmail.hejosadix.starwars.databinding.ItemPeopleBinding
import com.gmail.hejosadix.starwars.domain.models.Person
import com.gmail.hejosadix.starwars.utils.extencions.ifNullOrBlankUnknown
import java.util.LinkedList

class PeopleAdapter constructor(
    private val clickAction: (Person) -> Unit,
) : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    private var textFilter: String = ""
    private val items: LinkedList<Person> = LinkedList()
    private var itemsOnScreen: MutableList<Person> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PeopleViewHolder(
        ItemPeopleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        clickAction,
    )

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(itemsOnScreen[position])
    }

    fun addItems(
        items: List<Person>,
    ) {
        this.items.addAll(items)
        notifyChanges()
    }

    fun filter(
        name: String,
    ) {
        textFilter = name
        notifyChanges()
    }

    private fun notifyChanges() {
        val filterResult = this.items.filter { it.name.contains(textFilter, ignoreCase = true) }
        val result = DiffUtil.calculateDiff(
            ItemsDiffCallback(
                this.itemsOnScreen, filterResult,
            )
        )
        this.itemsOnScreen.clear()
        this.itemsOnScreen.addAll(filterResult)
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = itemsOnScreen.size

    inner class PeopleViewHolder(
        private val binding: ItemPeopleBinding,
        private val clickAction: (Person) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.nextImageButton.setOnClickListener(this)
        }

        lateinit var item: Person

        fun bind(
            person: Person,
        ) {
            item = person
            with(binding) {
                nameTextView.text = person.name
                fromTextView.text = root.resources.getString(
                    R.string.from,
                    person.species.name.ifBlank {
                        root.resources.getString(R.string.unknown_specie)
                    },
                    person.species.homeWorld.name.ifNullOrBlankUnknown(
                        resources = root.resources,
                    ),
                )
            }

        }

        override fun onClick(view: View) {
            clickAction.invoke(item)
        }


    }

    private class ItemsDiffCallback(
        private val oldData: List<Person>,
        private val newData: List<Person>,
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