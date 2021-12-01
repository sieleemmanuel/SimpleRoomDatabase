package com.devsiele.roomdatabase.main.detail

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.devsiele.roomdatabase.R
import com.devsiele.roomdatabase.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DetailFragmentBinding
    private lateinit var noteCategory: String
    private lateinit var noteTitle: String
    private lateinit var noteText: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val argument: Bundle = requireArguments()
        noteCategory = argument.getString("category")!!
        noteTitle = argument.getString("title")!!
        noteText = argument.getString("content_text")!!
        binding.txtCategory.text = noteCategory
        binding.txtTitle.text = noteTitle
        binding.txtContent.text = noteText

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.read_option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home->{
                requireActivity().onBackPressed()
            }
            R.id.updateNote -> {
                val noteId: Long = requireArguments().getLong("id")
                this.findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToAddNewFragment(
                        noteId,
                        noteCategory,
                        noteTitle,
                        noteText
                    )
                )
            }
        }
        return true
    }
}