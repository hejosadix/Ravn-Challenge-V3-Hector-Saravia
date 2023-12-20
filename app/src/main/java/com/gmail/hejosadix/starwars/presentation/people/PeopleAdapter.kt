package com.gmail.hejosadix.starwars.presentation.people

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hejosadix.starwars.R
import com.gmail.hejosadix.starwars.databinding.ItemPeopleBinding
import com.gmail.hejosadix.starwars.databinding.ItemPeopleLoadStateFooterBinding
import com.gmail.hejosadix.starwars.domain.models.Person
import com.gmail.hejosadix.starwars.utils.extencions.ifNullOrBlankUnknown
class PeopleAdapter constructor(
    private val clickAction: (Person) -> Unit,
) : PagingDataAdapter<Person, PeopleAdapter.PeopleViewHolder>(ItemsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PeopleViewHolder(
        ItemPeopleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        clickAction,
    )

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PeopleViewHolder(
        private val binding: ItemPeopleBinding,
        private val clickAction: (Person) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.nextImageButton.setOnClickListener(this)
        }

        private var item: Person? = null

        fun bind(
            person: Person?,
        ) {
            item = person
            with(binding) {
                nameTextView.text = person?.name ?: ""
                fromTextView.text = root.resources.getString(
                    R.string.from,
                    person?.species?.name?.ifBlank {
                        root.resources.getString(R.string.unknown_specie)
                    },
                    person?.species?.homeWorld?.name.ifNullOrBlankUnknown(
                        resources = root.resources,
                    ),
                )
            }

        }

        override fun onClick(view: View) {
            item?.run {
                clickAction.invoke(this)
            }
        }


    }

    class ItemsDiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }

}


class PeopleLoadStateAdapter : LoadStateAdapter<PeopleLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PeopleLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PeopleLoadStateViewHolder {
        return PeopleLoadStateViewHolder.create(parent)
    }
}


class PeopleLoadStateViewHolder(
    private val binding: ItemPeopleLoadStateFooterBinding,
) : RecyclerView.ViewHolder(binding.root) {
    init {
    }

    fun bind(loadState: LoadState) {
        binding.errorView.root.visibility = if (loadState is LoadState.Error) {
            View.VISIBLE
        } else {
            View.GONE
        }

        binding.loadingView.root.visibility = if (loadState is LoadState.Loading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    companion object {
        fun create(parent: ViewGroup): PeopleLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_people_load_state_footer, parent, false)
            val binding = ItemPeopleLoadStateFooterBinding.bind(view)
            return PeopleLoadStateViewHolder(binding)
        }
    }
}