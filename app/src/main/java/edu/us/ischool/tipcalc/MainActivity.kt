package edu.us.ischool.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.doAfterTextChanged

class MainActivity : AppCompatActivity() {

    lateinit var textEdit : EditText
    lateinit var button : Button
    lateinit var dollarSign : TextView
    lateinit var spinner: Spinner
    var ignore = false
    var tipPercentage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textEdit = findViewById(R.id.textInput)
        button = findViewById(R.id.btnTip)
        dollarSign = findViewById(R.id.dollarSign)
        spinner = findViewById(R.id.spnTip)

        ArrayAdapter.createFromResource(this,
                                        R.array.tip_percentage,
                                        android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0 != null) {
                    tipPercentage = p0.getItemAtPosition(p2).toString().substring(0, 2).toInt()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        textEdit.setOnClickListener {
            if(textEdit.text.toString() == getString(R.string.default_text)){
                textEdit.setText("")
            }
        }

        textEdit.doAfterTextChanged {
            button.isEnabled = textEdit.text.toString() != ""
            dollarSign.visibility = View.VISIBLE
            if (!ignore) {
                var input = textEdit.text.toString()
                if (input.contains(".")) {
                    if (input.substring(input.indexOf(".") + 1).length > 2) {
                        ignore = true
                        textEdit.setText(input.substring(0, input.length - 1))
                        textEdit.setSelection(textEdit.length())
                        ignore = false
                    }
                }
            }
        }

        button.setOnClickListener {
            var input = textEdit.text.toString().toDouble()
            var inputString = String.format("%.2f", input)
            var tip = String.format("%.2f", input * tipPercentage / 100)
            textEdit.setText(inputString)
            textEdit.setSelection(textEdit.length())
            Toast.makeText(this, getString(R.string.toast_message, tip), Toast.LENGTH_LONG)
                .show()
        }
    }
}