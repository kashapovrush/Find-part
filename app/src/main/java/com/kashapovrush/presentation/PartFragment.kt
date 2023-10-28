package com.kashapovrush.presentation

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kashapovrush.R
import com.kashapovrush.databinding.FragmentItemBinding
import com.kashapovrush.domain.Part.Companion.UNDEFINDED_ID
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class PartFragment : Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener
    private var screenMode: String = MODE_UNDEFINDED
    private var partId: Int = UNDEFINDED_ID
    private lateinit var partViewModel: PartViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentItemBinding

    private val component by lazy {
        (requireActivity().application as FindPartApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implementation OnEditingFinishedListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        partViewModel = ViewModelProvider(this, viewModelFactory)[PartViewModel::class.java]
        setupMode()
        setupObserve()
        addTextChangedListener()


    }

    private fun addTextChangedListener() {
        binding.etNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                partViewModel.resetValueInputNumber()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                partViewModel.resetValueInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                partViewModel.resetValueInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun setupObserve() {
        partViewModel.errorInputNumber.observe(viewLifecycleOwner) {
            val message = if (it) {
                ("Неверный номер заказа")
            } else {
                null
            }

            binding.tilNumber.error = message
        }

        partViewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                ("Неверное имя")
            } else {
                null
            }

            binding.tilName.error = message
        }

        partViewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                ("Неверное количество")
            } else {
                null
            }

            binding.tilCount.error = message
        }

        partViewModel.errorInputLocation.observe(viewLifecycleOwner) {
            val message = if (it) {
                ("Неверное место")
            } else {
                null
            }

            binding.tilLocation.error = message
        }

        partViewModel.closeActivity.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun setupMode() {
        when (screenMode) {
            MODE_EDIT -> launchScreenEditMode()
            MODE_ADD -> launchScreenAddMode()
        }

    }

    private fun launchScreenAddMode() {
        val date: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val time: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        binding.buttonSave.setOnClickListener {
            partViewModel.addPartItem(
                binding.etNumber.text?.toString(),
                binding.etName.text?.toString(),
                binding.etCount.text?.toString(),
                date,
                time,
                binding.etLocation.text?.toString()
            )
        }
    }

    private fun launchScreenEditMode() {
        val date: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val time: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        partViewModel.getPartItem(partId)
        partViewModel.showItem.observe(viewLifecycleOwner) {
            binding.etNumber.setText(it.number.toString())
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())
            binding.tvDate.text = it.date
            binding.tvTime.text = it.time
            binding.etLocation.setText(it.location)
        }

        binding.buttonSave.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Вы точно хотите изменить данные?")
                .setPositiveButton("Да") {dialog, which ->
                    partViewModel.editPartItem(
                        binding.etNumber.text?.toString(),
                        binding.etName.text?.toString(),
                        binding.etCount.text?.toString(),
                        date,
                        time,
                        binding.etLocation.text?.toString()
                    )
                }
                .setNegativeButton("Нет") {dialog, which ->
                    dialog.dismiss()
                }.create().show()

        }

    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MOD)) {
            throw RuntimeException("Mode not found")
        }

        val mode = args.getString(EXTRA_SCREEN_MOD)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Mode unknown")
        }

        screenMode = mode
        if (mode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_PART_ID)) {
                throw RuntimeException("ID not found")
            }

            partId = args.getInt(EXTRA_PART_ID, UNDEFINDED_ID)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {

        private const val EXTRA_PART_ID = "part_id"
        private const val EXTRA_SCREEN_MOD = "extra_mod"
        private const val MODE_EDIT = "mod_edit"
        private const val MODE_ADD = "mod_add"
        private const val MODE_UNDEFINDED = ""

        fun newItemAdd(): PartFragment {
            return PartFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MOD, MODE_ADD)
                }
            }
        }

        fun newItemEdit(id: Int): PartFragment {
            return PartFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MOD, MODE_EDIT)
                    putInt(EXTRA_PART_ID, id)
                }
            }
        }
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

}