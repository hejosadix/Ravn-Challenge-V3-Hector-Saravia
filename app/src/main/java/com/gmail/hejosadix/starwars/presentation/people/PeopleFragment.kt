package com.gmail.hejosadix.starwars.presentation.people

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.hejosadix.starwars.R
import com.gmail.hejosadix.starwars.databinding.FragmentPeopleBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : Fragment() {
    private lateinit var binding: FragmentPeopleBinding
    private val viewModel: StarWarsPeopleViewModel by viewModel()
    private lateinit var peopleAdapter: PeopleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupCollectors()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPeopleBinding.inflate(inflater)
        setupAppBar()
        setupTextListener()
        setupAdapter()
        return binding.root
    }

    private fun setupAppBar() {
        binding.appBar.titleTextView.text = resources.getString(R.string.people_of_star_wars)
    }

    private fun setupTextListener() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchPeople(text = s.toString())
            }
        })
    }

    private fun setupAdapter() {
        peopleAdapter = PeopleAdapter(
            clickAction = { person ->
                val action =
                    PeopleFragmentDirections.actionPeopleFragmentToPersonDetailsFragment(person.id)
                findNavController().navigate(action)
            },
        )
        with(binding.peopleRecycleView) {
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            )
            this.adapter = peopleAdapter
            layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        }

        binding.peopleRecycleView.adapter = peopleAdapter.withLoadStateHeaderAndFooter(
            header = PeopleLoadStateAdapter(),
            footer = PeopleLoadStateAdapter(),
        )
    }

    private fun setupCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAllPeople().collectLatest { people ->
                    peopleAdapter.submitData(people)
                }
            }
        }
    }
}