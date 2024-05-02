package com.example.mobileapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileapp.ConcretePlant
import com.example.mobileapp.LastWatered_timings
import com.example.mobileapp.PlantDatabaseHelper
import com.example.mobileapp.databinding.FragmentHomeBinding
import java.util.Date

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var intervals = arrayOf("once every two days", "once a week", "twice a week", "once a month")
    private lateinit var autoCompleteTextView: AutoCompleteTextView;
    private lateinit var IntervalsAdapter: ArrayAdapter<String>;
    private lateinit var addPlantBtn: Button;
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        autoCompleteTextView = binding.autoCompleteTextView
        IntervalsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, intervals)
        autoCompleteTextView.setAdapter(IntervalsAdapter)

        autoCompleteTextView.setOnItemClickListener() { parent: AdapterView<*>, view: View, position: Int, id: Long ->
            val selectedItem = parent.getItemAtPosition(position).toString()

        }

        val dbHelper = PlantDatabaseHelper(requireContext())


        addPlantBtn = binding.addPlantBtn
        addPlantBtn.setOnClickListener {
            val plantName = binding.plantName.text.toString()
            val plantInterval = autoCompleteTextView.text.toString()
            val lastWaterd = Date().toString()
            println("Plant Name: $lastWaterd")
            val plant = ConcretePlant(plantName, plantInterval , lastWaterd)
            dbHelper.insertPlant(plant)
            val intent = Intent(context, LastWatered_timings::class.java)
            context?.startService(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}