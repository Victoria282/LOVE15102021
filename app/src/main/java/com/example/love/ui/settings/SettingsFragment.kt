package com.example.love.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.love.other.animation.Constants.THEME_ALARM_ID
import com.example.love.R
import com.example.love.SharedPreferences.SharedPreferences.customPreference
import com.example.love.SharedPreferences.SharedPreferences.theme
import com.example.love.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSettings()
        with(binding.switchTheme) {
            setOnCheckedChangeListener { _, checked ->
                setSettings(checked)
                val action = SettingsFragmentDirections.toHome()
                findNavController().navigate(action)
            }
        }
    }

    private fun setSettings(theme: Boolean) {
        binding.switchTheme.isChecked = theme
        save(theme)
    }

    private fun loadSettings() {
        val prefs = customPreference(requireContext(), "SharedPreferences")
        val savedTheme = prefs.getBoolean(THEME_ALARM_ID, false)
        restoreData(savedTheme)
    }

    private fun save(theme: Boolean) {
        val prefs = customPreference(requireContext(), "SharedPreferences")
        prefs.theme = theme
        setThemeApp(theme)
    }
    private fun restoreData(savedTheme: Boolean) {
        binding.switchTheme.isChecked = savedTheme
        setThemeApp(savedTheme)
    }

    private fun setThemeApp(theme: Boolean) {
        with(binding.settingsContainer) {
            if(theme)
                setBackgroundResource(R.drawable.light)
            else
                setBackgroundResource(R.drawable.dark)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}