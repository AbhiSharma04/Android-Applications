package com.example.android.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TemperatureConverter.newInstance] factory method to
 * create an instance of this fragment.
 */
class TemperatureConverter : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_temperature_converter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = activity?.intent?.getStringExtra("USER")
        val inputtemp = activity?.findViewById<EditText>(R.id.tempinput)
        val outputtemp = activity?.findViewById<TextView>(R.id.output_temp)
        val submitbtn = activity?.findViewById<Button>(R.id.submit_btn)
        var cels = 0.0
        var fahh = 0.0
        lateinit var temp: String
        when (position) {
            "0" -> {

                    submitbtn?.setOnClickListener {
                        temp = inputtemp?.text.toString()
                        if(temp.isEmpty()){
                            Toast.makeText(activity, "Enter Temperature !!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                        cels = inputtemp?.text.toString().toDouble()
                        fahh = (cels * 1.8) + 32
                        val fahh_2 = String.format("%.2f", fahh)
                        val message = "Temperature $cels°C is $fahh_2°F "
                        outputtemp?.text = message
                        inputtemp?.text?.clear()}
                    }

            }
            "1" -> {

                submitbtn?.setOnClickListener {
                    temp = inputtemp?.text.toString()
                    if(temp.isEmpty()){
                        Toast.makeText(activity, "Enter Temperature !!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        fahh = inputtemp?.text.toString().toDouble()
                        var cels = ((fahh - 32) * 5) / 9
                        val cel_2 = String.format("%.2f", cels)
                        val message = "Temperature $fahh°F is $cel_2°C "
                        outputtemp?.text = message
                        inputtemp?.text?.clear()
                    }

                }
//
            }

        }


    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TemperatureConverter.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TemperatureConverter().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}