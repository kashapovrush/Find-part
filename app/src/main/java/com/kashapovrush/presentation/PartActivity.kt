package com.kashapovrush.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kashapovrush.R
import com.kashapovrush.databinding.ActivityPartBinding
import com.kashapovrush.domain.Part.Companion.UNDEFINDED_ID
import com.kashapovrush.presentation.PartFragment.Companion.newItemAdd
import com.kashapovrush.presentation.PartFragment.Companion.newItemEdit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PartActivity : AppCompatActivity(), PartFragment.OnEditingFinishedListener {

    var partId = UNDEFINDED_ID
    var screenMode = MODE_UNDEFINDED

    private lateinit var binding: ActivityPartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        if (savedInstanceState == null) {
            setupMode()
        }
    }

    private fun setupMode() {
        val fragment = when (screenMode) {
            MODE_ADD -> newItemAdd()
            MODE_EDIT -> newItemEdit(partId)
            else -> throw RuntimeException("Mode undefinded")
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.part_container, fragment)
            .commit()
    }

    fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MOD)) {
            throw RuntimeException("Mode not found")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MOD)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException ("Mode unknown")
        }
        screenMode = mode
        if (mode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_PART_ID)) {
                throw RuntimeException("ID not found")
            }
            partId = intent.getIntExtra(EXTRA_PART_ID, UNDEFINDED_ID)
        }
    }

    companion object {
        private const val EXTRA_PART_ID = "part_id"
        private const val EXTRA_SCREEN_MOD = "extra_mod"
        private const val MODE_EDIT = "mod_edit"
        private const val MODE_ADD = "mod_add"
        private const val MODE_UNDEFINDED = ""

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, PartActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MOD, MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, id: Int): Intent {
            val intent = Intent(context, PartActivity::class.java)
            intent.putExtra(EXTRA_PART_ID, id)
            intent.putExtra(EXTRA_SCREEN_MOD, MODE_EDIT)
            return intent
        }
    }

    override fun onEditingFinished() {
        finish()
    }
}