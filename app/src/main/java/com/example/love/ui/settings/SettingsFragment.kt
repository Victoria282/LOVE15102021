package com.example.love.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.love.R
import com.example.love.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // загружаем выбор темы
        loadSettings()

        binding.switchTheme.setOnCheckedChangeListener { compoundButton, isChecked ->
            binding.switchTheme.isChecked = isChecked
            setSettings(binding.switchTheme.isChecked)

            val action = SettingsFragmentDirections.toHome(binding.switchTheme.isChecked.toString())
            findNavController().navigate(action)
        }
    }


    private fun setSettings(theme: Boolean) {
        binding.switchTheme.isChecked = theme
        save(theme)
    }

    private fun loadSettings() {
        val sharedPref: SharedPreferences = (context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE) ?: "empty") as SharedPreferences
        val savedTheme = sharedPref.getBoolean("theme", false)
        restoreData(savedTheme)
    }

    private fun save(theme: Boolean) {
        val sharedPref = (context?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE) ?: "empty") as SharedPreferences
        val editor = sharedPref.edit()
        editor.apply() {
            putBoolean("theme", theme).apply()
        }
        setThemeApp(theme)
    }
    private fun restoreData(savedTheme: Boolean) {
        binding.switchTheme.isChecked = savedTheme
        setThemeApp(savedTheme)
    }

    private fun setThemeApp(theme: Boolean) {
        if(theme) {
            binding.settingsContainer.setBackgroundResource(R.drawable.light_gradient_theme)
        } else {
            binding.settingsContainer.setBackgroundResource(R.drawable.dark_gradient_theme)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}