package com.gmail.hejosadix.starwars.presentation.people.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.hejosadix.starwars.R
import com.gmail.hejosadix.starwars.data.common.StarWarsResult
import com.gmail.hejosadix.starwars.data.local.entity.Favorite
import com.gmail.hejosadix.starwars.databinding.FragmentPersonDetailsBinding
import com.gmail.hejosadix.starwars.domain.models.Person
import com.gmail.hejosadix.starwars.utils.extencions.ifNullOrBlankUnknown
import com.gmail.hejosadix.starwars.utils.extencions.setSupportActionBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PersonDetailsFragment : Fragment(), View.OnClickListener {

    private val args: PersonDetailsFragmentArgs by navArgs()
    private var id: String = ""
    private var person: Person? = null
    private var favorite: Favorite? = null
    private lateinit var vehicleAdapter: VehicleAdapter
    private val viewModel: PersonDetailsViewModel by viewModel()
    private lateinit var binding: FragmentPersonDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = args.id
        setupCollectors()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonDetailsBinding.inflate(inflater)
        setupAppBar()
        setupAdapter()
        setupListener()
        return binding.root
    }

    private fun setupListener() {
        binding.favoriteImageButton.setOnClickListener(this)
    }

    private fun setupAppBar() {
        binding.appBarDetails.toolbar.setSupportActionBar(
            activity = requireActivity(),
        )
        binding.appBarDetails.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.appBarDetails.titleTextView.text = resources.getString(R.string.loading)
    }

    private fun setupAdapter() {
        vehicleAdapter = VehicleAdapter()
        with(binding.vehiclesRecycleView) {
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            this.adapter = vehicleAdapter
            layoutManager =
                LinearLayoutManager(
                    this.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
    }

    private fun setupCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPerson(id = id).collectLatest { data ->
                    when (data) {
                        StarWarsResult.Loading -> {
                            binding.loadingView.root.visibility = View.VISIBLE
                        }
                        is StarWarsResult.Success -> {
                            setFieldsValues(data.data)
                            binding.loadingView.root.visibility = View.GONE
                        }
                        else -> {
                            binding.loadingView.root.visibility = View.GONE
                        }
                    }

                }
                viewModel.getFavorite(id = id).collectLatest { data ->
                    favorite = data
                    when (data) {
                        null -> {
                            binding.favoriteImageButton.setImageResource(R.drawable.baseline_star_outline_24)
                        }
                        else -> {
                            binding.favoriteImageButton.setImageResource(R.drawable.baseline_star_rate_24)
                        }
                    }

                }
            }
        }
    }

    private fun setFieldsValues(person: Person) {
        this.person = person
        with(binding) {
            binding.appBarDetails.titleTextView.text =
                person.name.ifNullOrBlankUnknown(
                    resources = resources,
                )
            eyeColorTextView.text = person.eyeColor.ifNullOrBlankUnknown(
                resources = resources,
            )
            hairColorTextView.text = person.hairColor.ifNullOrBlankUnknown(
                resources = resources,
            )
            skinColorTextView.text = person.skinColor.ifNullOrBlankUnknown(
                resources = resources,
            )
            birthYearTextView.text = person.birthYear.ifNullOrBlankUnknown(
                resources = resources,
            )
        }
        vehicleAdapter.setItems(
            items = person.vehicleConnection.vehicles,
        )
    }

    override fun onClick(view: View) {
        when (view) {
            binding.favoriteImageButton -> {
                favorite.run {
                    if (this == null) {
                        person?.run {
                            viewModel.savePersonInFavorite(person = this)
                        }
                    } else {
                        viewModel.deletePersonInFavorite(id = id)
                    }
                }

            }
            else -> {

            }
        }
    }
}