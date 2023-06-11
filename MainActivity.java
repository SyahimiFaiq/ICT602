package com.example.assignment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText unitUsedEditText;
    private EditText rebatePercentageEditText;
    private Button calculateButton;
    private TextView totalChargeTextView;
    private TextView finalCostTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unitUsedEditText = findViewById(R.id.unitUsedEditText);
        rebatePercentageEditText = findViewById(R.id.rebatePercentageEditText);
        calculateButton = findViewById(R.id.calculateButton);
        totalChargeTextView = findViewById(R.id.totalChargeTextView);
        finalCostTextView = findViewById(R.id.finalCostTextView);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBill();
            }
        });
    }

    private void calculateBill() {
        String unitUsedString = unitUsedEditText.getText().toString();
        String rebatePercentageString = rebatePercentageEditText.getText().toString();

        if (unitUsedString.isEmpty() || rebatePercentageString.isEmpty()) {
            totalChargeTextView.setText("");
            Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double unitUsed = Double.parseDouble(unitUsedString);
        double rebatePercentage = Double.parseDouble(rebatePercentageString);

        double totalCharges = 0;
        double rate;

        if (unitUsed <= 200) {
            rate = 0.218;
            totalCharges = unitUsed * rate;
        } else if (unitUsed <= 300) {
            rate = 0.334;
            totalCharges = (200 * 0.218) + ((unitUsed - 200) * rate);
        } else if (unitUsed <= 600) {
            rate = 0.516;
            totalCharges = (200 * 0.218) + (100 * 0.334) + ((unitUsed - 300) * rate);
        } else if (unitUsed > 600) {
            rate = 0.546;
            totalCharges = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((unitUsed - 600) * rate);
        }

        double finalCost = totalCharges - (totalCharges * rebatePercentage / 100);

        // Round the values to 2 decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String roundedTotalCharges = decimalFormat.format(totalCharges);
        String roundedFinalCost = decimalFormat.format(finalCost);

        totalChargeTextView.setText("Total Charge: RM " + roundedTotalCharges);
        finalCostTextView.setText("Final Cost: RM " + roundedFinalCost);
    }
}
