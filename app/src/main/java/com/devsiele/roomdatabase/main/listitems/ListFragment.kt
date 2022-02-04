package com.devsiele.roomdatabase.main.listitems

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsiele.roomdatabase.R
import com.devsiele.roomdatabase.adapters.KeyProvider
import com.devsiele.roomdatabase.database.NoteDatabase
import com.devsiele.roomdatabase.databinding.ListFragmentBinding
import com.devsiele.roomdatabase.model.Note
import java.util.*


class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: RecyclerAdapter
    private var tracker:SelectionTracker<Note>? = null
    private var actionMode: ActionMode? = null
    private var selectedItems:Int? = null

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

        adapter = RecyclerAdapter(RecyclerAdapter.ItemClickListener{note ->
            findNavController()
                .navigate(
                    ListFragmentDirections.actionListFragmentToDetailFragment(
                        note.noteId,
                        note.noteCategory,
                        note.noteTitle,
                        note.noteText
                    )
                )
        })
        binding.listRecyclerview.adapter = adapter

        tracker = SelectionTracker.Builder(
            "selection-1",
            binding.listRecyclerview,
            KeyProvider(adapter),
            ItemLookUp(binding.listRecyclerview),
            StorageStrategy.createParcelableStorage(Note::class.java))
            .withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()
        
        if (savedInstanceState!=null)
            tracker?.onRestoreInstanceState(savedInstanceState)

            adapter.setTracker(tracker!!)
            
            tracker?.addObserver(object:SelectionTracker.SelectionObserver<Note>(){
                override fun onSelectionChanged() {
                  selectedItems = tracker?.selection?.size()
                    
                    if (selectedItems!=null && selectedItems!!>0){
                        requireActivity().startActionMode(actionModeCallback)
                        actionMode?.title = "$selectedItems/${adapter.itemCount}"
                    }else{
                        actionMode?.finish()
                    }
                }
            })

        

        val mDividerItemDecoration = DividerItemDecoration(
            binding.listRecyclerview.context,
            LinearLayoutManager(requireContext()).orientation
        )

        binding.listRecyclerview.addItemDecoration(mDividerItemDecoration)
        viewModel.notelist.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
        binding.btnNew.setOnClickListener {
           findNavController()
                .navigate(ListFragmentDirections.actionListFragmentToAddNewFragment())
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker?.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)

        //getting searchView menu item
        val searchView:SearchView = (menu.findItem(R.id.rv_search).actionView) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                /**calling a method to filter our recycler view. */
                filter(newText)
                return false
            }
        })
    }

    private fun filter(newText: String) {

        viewModel.notelist.observe(viewLifecycleOwner, {

            val filteredList: MutableList<Note> = mutableListOf()
            for (note in it!!) {
                // checking if the entered string matched with any item of our recycler view.
                if (note.noteCategory.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault())) ||note.noteTitle.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                ) {
                    filteredList.add(note)
                }

            }
            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No match found", Toast.LENGTH_SHORT).show()
                adapter.submitList(filteredList)
            } else {
                adapter.submitList(filteredList)
            }
        })
    }

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            actionMode = mode
            val inflater:MenuInflater? = actionMode?.menuInflater
            inflater?.inflate(R.menu.menu_action_mode,menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false //returning false if nothing is done
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when(item?.itemId){
                R.id.deleteSelected -> {
                    deleteSelected()
                    mode?.finish()
                    true
                }
                else->false
            }
        }
        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }
    }

    private fun deleteSelected() {
        Log.d("ListFragment", "${tracker?.selection} ")
        tracker?.selection?.forEach {
            viewModel.deleteSelectedNotes(it)
        }

        if (selectedItems!=null)
        adapter.clearTracker(tracker!!)
    }

}