package com.example.tipapp2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tipapp2.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener { calculateTip() }
        binding.costOfInputEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }
    private fun calculateTip(){
        val stringInTextField = binding.costOfInputEditText.text.toString()
        var cost = stringInTextField.toDoubleOrNull()
        if (cost == null){
            cost = 0.00
        }
        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.option_twenty_percent -> 0.2
            R.id.option_fifteen_percent -> 0.15
            else -> 0.10
        }
        var tip = cost*tipPercentage
        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp){
            tip = kotlin.math.ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)

    }
    private fun handleKeyEvent (view: View, keyCode: Int): Boolean{
        if (keyCode == KeyEvent.KEYCODE_ENTER){
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}