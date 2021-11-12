package com.devsiele.roomdatabase.main.listitems

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsiele.roomdatabase.R
import com.devsiele.roomdatabase.database.NoteDatabase
import com.devsiele.roomdatabase.databinding.ListFragmentBinding
import com.devsiele.roomdatabase.model.Note


class ListFragment : Fragment(), ClickListener {


    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ListFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.list_fragment,
            container,
            false
        )

        val database = NoteDatabase.getInstance(requireContext()).noteDao
        val viewModelFactory = ListViewModelFactory(database)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)

        val adapter = RecyclerAdapter(this)
        binding.listRecyclerview.adapter = adapter


        val mDividerItemDecoration = DividerItemDecoration(
            binding.listRecyclerview.context,
            LinearLayoutManager(requireContext()).orientation
        )

        binding.listRecyclerview.addItemDecoration(mDividerItemDecoration)

        viewModel.notelist.observe(viewLifecycleOwner, {
            it?.let {
                adapter.noteList = it
            }
        })

        binding.btnNew.setOnClickListener {
            this.findNavController()
                .navigate(ListFragmentDirections.actionListFragmentToAddNewFragment())
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.rv_search -> {

            }
        }
        return true
    }

    private fun itemOnClick(note: Note) {
        this.findNavController()
            .navigate(
                ListFragmentDirections.actionListFragmentToDetailFragment(
                    note.noteId,
                    note.noteCategory,
                    note.noteTitle,
                    note.noteText
                )
            )
    }

    override fun onClick(view: View, note: Note) {
        when (view.id) {
            R.id.rvItem -> itemOnClick(note)
        }
    }
}