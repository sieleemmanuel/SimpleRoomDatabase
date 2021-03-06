package com.devsiele.roomdatabase.main.newitem


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.devsiele.roomdatabase.R
import com.devsiele.roomdatabase.database.NoteDatabase
import com.devsiele.roomdatabase.databinding.AddNewFragmentBinding
import com.devsiele.roomdatabase.data.model.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewFragment : Fragment(), AdapterView.OnItemClickListener {

    private lateinit var viewModel: AddNewViewModel
    private lateinit var binding: AddNewFragmentBinding
    private lateinit var argument: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_new_fragment,
            container,
            false
        )
        setUpToolbar()

        viewModel = ViewModelProvider(this).get(AddNewViewModel::class.java)

        val adapter = ArrayAdapter(requireContext(), R.layout.item_categories, viewModel.categories)
        with(binding.editTextCategory) {
            setAdapter(adapter)
            onItemClickListener = this@AddNewFragment
        }

        argument = requireArguments()
        if (!argument.isEmpty) {
            binding.textInputLayoutId.visibility = View.VISIBLE
            binding.editNoteId.setText(argument.getLong("id").toString())
            binding.editTextCategory.setText(argument.getString("category"), false)
            binding.editTextTitle.setText(argument.getString("title"))
            binding.editTextText.setText(argument.getString("note"))

            binding.btnSave.text = getString(R.string.btn_update_text)
            binding.btnSave.setOnClickListener {
                Toast.makeText(requireContext(), binding.editTextCategory.text, Toast.LENGTH_SHORT)
                    .show()
                Log.i("MainActivity", binding.editTextCategory.text.toString())

                viewModel.updateNote(
                    binding.editNoteId.text.toString().toLong(),
                    binding.editTextCategory.text.toString(),
                    binding.editTextTitle.text.toString(),
                    binding.editTextText.text.toString()

                )
                Toast.makeText(requireContext(), "Note Updated Successfully", Toast.LENGTH_SHORT)
                    .show()
                resetValues(adapter)
                this.findNavController().navigate(
                    AddNewFragmentDirections.actionAddNewFragmentToListFragment()
                )

            }
        } else {
            binding.textInputLayoutId.visibility = View.GONE
            binding.btnSave.setOnClickListener {
                if (binding.editTextTitle.text?.isNotEmpty() == true) {
                    if (binding.editTextText.text?.isNotEmpty() == true) {
                        if (binding.editTextCategory.text.toString() != adapter.getItem(0)
                                .toString()
                        ) {
                            val newNote = Note(
                                noteCategory = binding.editTextCategory.text.toString(),
                                noteTitle = binding.editTextTitle.text.toString(),
                                noteText = binding.editTextText.text.toString()
                            )
                            viewModel.insertNewNote(newNote)
                            resetValues(adapter)
                            this.findNavController().navigate(
                                AddNewFragmentDirections.actionAddNewFragmentToListFragment()
                            )

                        } else {
                            binding.editTextCategory.error = "No category selected"
                        }
                    } else {
                        binding.editTextText.error = "Field is empty"
                    }
                } else {
                    binding.editTextTitle.error = "Title is empty"
                }
            }
        }

        binding.btnClose.setOnClickListener {
            this.findNavController().navigate(
                AddNewFragmentDirections.actionAddNewFragmentToListFragment()
            )
        }
        return binding.root
    }

    private fun setUpToolbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.newToolbar.setupWithNavController(navController,appBarConfiguration)
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(binding.newToolbar)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) requireActivity().onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun resetValues(adapter: ArrayAdapter<String>) {
        binding.editNoteId.text = null
        binding.editTextCategory.setText(adapter.getItem(0).toString(), false)
        binding.editTextTitle.text = null
        binding.editTextText.text = null
        binding.btnSave.text = getString(R.string.btn_text_save)
    }

    override fun onItemClick(parent: AdapterView<*>?, p1: View?, position: Int, id: Long) {
    }

}