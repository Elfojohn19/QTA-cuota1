package com.example.sihoy



import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los EditText, Button y TextView
        val editTextNumberDecimal3 = findViewById<EditText>(R.id.editTextNumberDecimal3) // Valor de la Propiedad
        val editTextNumberDecimal4 = findViewById<EditText>(R.id.editTextNumberDecimal4) // Valor del Préstamo
        val editTextNumberDecimal2 = findViewById<EditText>(R.id.editTextNumberDecimal2) // Número de Años
        val editTextNumberDecimal = findViewById<EditText>(R.id.editTextNumberDecimal) // TAE
        val textView12 = findViewById<TextView>(R.id.textView12) // Resultado
        val calculateButton = findViewById<Button>(R.id.button2) // Botón para calcular

        // Configurar el botón para calcular la cuota mensual
        calculateButton.setOnClickListener {
            // Obtener los valores ingresados en los EditText
            val propertyValueText = editTextNumberDecimal3.text.toString()
            val loanAmountText = editTextNumberDecimal4.text.toString()
            val yearsText = editTextNumberDecimal2.text.toString()
            val taeText = editTextNumberDecimal.text.toString()

            // Convertir los valores a Double o Int, usando valores por defecto si es necesario
            val propertyValue = propertyValueText.toDoubleOrNull() ?: 0.0
            val loanAmount = loanAmountText.toDoubleOrNull() ?: 0.0
            val years = yearsText.toIntOrNull() ?: 0
            val tae = taeText.toDoubleOrNull() ?: 0.0

            // Validaciones
            if (propertyValue < 70000000) {
                textView12.text = "El valor de la propiedad debe ser al menos $70,000,000."
                return@setOnClickListener
            }

            if (loanAmount < 50000000) {
                textView12.text = "El valor del préstamo debe ser al menos $50,000,000."
                return@setOnClickListener
            }

            // Para créditos hipotecarios
            val maxLoanAmountHipotecario = propertyValue * 0.70
            // Para leasing habitacional
            val maxLoanAmountLeasing = propertyValue * 0.80

            // Verificar si el valor del préstamo excede el límite permitido
            if (loanAmount > maxLoanAmountHipotecario) {
                textView12.text = "El valor del préstamo no puede exceder el 70% del valor de la propiedad para créditos hipotecarios."
                return@setOnClickListener
            }

            // Puedes descomentar esta línea si deseas usar leasing habitacional
            // if (loanAmount > maxLoanAmountLeasing) {
            //     textView12.text = "El valor del préstamo no puede exceder el 80% del valor de la propiedad para leasing habitacional."
            //     return@setOnClickListener
            // }

            if (years !in 5..20) {
                textView12.text = "El plazo debe estar entre 5 y 20 años."
                return@setOnClickListener
            }

            // Convertir TAE a tasa mensual (i)
            val monthlyInterestRate = tae / 100 / 12

            // Calcular el número total de pagos (meses)
            val totalPayments = years * 12

            // Calcular la cuota mensual usando la fórmula proporcionada
            val monthlyPayment = if (monthlyInterestRate > 0) {
                loanAmount * (monthlyInterestRate * (1 + monthlyInterestRate).pow(totalPayments)) /
                        ((1 + monthlyInterestRate).pow(totalPayments) - 1)
            } else {
                loanAmount / totalPayments
            }

            // Mostrar el resultado en el TextView
            textView12.text = "Cuota Mensual: ${String.format("%.2f", monthlyPayment)}"
        }
    }
}
