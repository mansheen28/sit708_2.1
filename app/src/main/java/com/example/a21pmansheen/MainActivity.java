package com.example.a21pmansheen;


import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;


public class MainActivity extends AppCompatActivity {


    private Spinner sourceSpinner, destinationSpinner;
    private EditText value;
    private Button btn;
    private TextView result;


    private HashMap<String, String> map = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //map for temp
        map.put("Kelvin", "Temperature");
        map.put("Celsius", "Temperature");
        map.put("Fahrenheit", "Temperature");
        
        
        //map for weight
        map.put("Pound", "Weight");
        map.put("Top", "Weight");
        map.put("Ounce", "Weight");

        //map for length
        map.put("Mile", "Length");
        map.put("Inch", "Length");
        map.put("Yard", "Length");
        map.put("Foot", "Length");
        
        

        sourceSpinner = findViewById(R.id.spinner);
        destinationSpinner = findViewById(R.id.spinner2);
        value = findViewById(R.id.textInputEditText);
        btn = findViewById(R.id.button);
        result = findViewById(R.id.textView);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitConversion();
            }
        });
    }


    private void unitConversion() {
        if (value.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Invalid Value Entered", Toast.LENGTH_SHORT).show();
            return;
        }
        String source = sourceSpinner.getSelectedItem().toString();
        String destination = destinationSpinner.getSelectedItem().toString();
        double input = Double.parseDouble(value.getText().toString());

        double ans = 0;

        if(source.equalsIgnoreCase(destination)) {
            Toast.makeText(getApplicationContext(), "Cannot convert to the same Unit" , Toast.LENGTH_SHORT).show();
            return;
        }


        if (!map.get(source).equalsIgnoreCase(map.get(destination))) {
            String error = "Cant convert " +source + " to " +destination;
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            return;
        }


        if (map.get(source).equalsIgnoreCase("Length")) {
            ans = convertLength(input, source, destination);
        }else if (map.get(source).equalsIgnoreCase("Weight")){
            ans = convertWeight(input, source, destination);
        }else {
            ans = convertTemperature(input, source, destination);
        }


        result.setText(String.valueOf(ans));
    }


    private double convertLength(double value, String source, String destination) {
        // Converting source unit to inches
        double inches;
        switch (source) {
            case "Yard":
                inches = value * 36;
                break;
            case "Mile":
                inches = value * 63360;
                break;
            case "Inch":
                inches = value;
                break;
            case "Foot":
                inches = value * 12;
                break;
            default:
                return 0;
        }


        // Converting inches to destination unit
        double result;
        switch (destination) {
            case "Inch":
                result = inches;
                break;
            case "Mile":
                result = inches / 63360;
                break;
            case "Foot":
                result = inches / 12;
                break;
            case "Yard":
                result = inches / 36;
                break;
            default:
                return 0;
        }
        return result;
    }


    private double convertWeight(double value, String source, String destination) {
        // Convert all units to pounds first
        double pounds = 0;


        switch (source) {
            case "Pound":
                pounds = value;
                break;
            case "Ounce":
                pounds = value / 16;
                break;
            case "Ton":
                pounds = value * 2000;
                break;
        }


        // Convert pounds to destination unit
        switch (destination) {
            case "Pound":
                return pounds;
            case "Ounce":
                return pounds * 16;
            case "Ton":
                return pounds / 2000;
            default:
                return 0;
        }
    }


    private double convertTemperature(double value, String source, String destination) {
        // Convert to Celsius first
        double celsiusValue = 0;
        switch (source) {
            case "Celsius":
                celsiusValue = value;
                break;
            case "Fahrenheit":
                celsiusValue = (value - 32) / 1.8;
                break;
            case "Kelvin":
                celsiusValue = value - 273.15;
                break;
        }


        // Convert from Celsius to destination unit
        switch (destination) {
            case "Celsius":
                return celsiusValue;
            case "Fahrenheit":
                return (celsiusValue * 1.8) + 32;
            case "Kelvin":
                return celsiusValue + 273.15;
            default:
                return 0; // Invalid destination unit
        }
    }
}



