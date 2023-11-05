package com.kashapovrush.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kashapovrush.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var partAdapter: PartAdapter
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as FindPartApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        setupRecyclerView()
        viewModel.partList.observe(this) {
            partAdapter.submitList(it)
        }

    }

    private fun setupRecyclerView() {
        val rvPart = binding.rvPartItems
        partAdapter = PartAdapter()
        rvPart.adapter = partAdapter

        setupOnCLickListener()
        setupOnTouchHelper(rvPart)
        setSearchView()
    }

    private fun setSearchView() {
        binding.searchParts.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (text != null) {
                    searchList(text)
                }
                return true
            }
        })
    }

    fun searchList(text: String) {
        val text = "%$text%"

        viewModel.getPartByName(text).observe(this) {
            it.let {
                partAdapter.submitList(it)
            }
        }
    }

    private fun setupOnCLickListener() {

        partAdapter.onPartClickListener = {
            startActivity(PartActivity.newIntentEdit(this, it.id))
        }

        binding.buttonAddPart.setOnClickListener {
            startActivity(PartActivity.newIntentAdd(this))
        }
    }

    private fun setupOnTouchHelper(rvPart: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder
                    .setMessage("Вы точно хотите удалить?")
                    .setPositiveButton("Да") { dialog, which ->
                        val item = partAdapter.currentList[viewHolder.adapterPosition]
                        viewModel.deletePartItem(item)

                    }
                    .setNegativeButton("Нет") {dialog, which ->
                        startActivity(Intent(this@MainActivity, MainActivity::class.java))
                    }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }

        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(rvPart)
    }
}