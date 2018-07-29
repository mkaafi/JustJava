package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int coffeeCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(coffeeCount == 100){
            //error shows as a toast
            Toast.makeText(this, "Not good for your health", Toast.LENGTH_SHORT).show();
            // end this method early coz there nothing left to do
            return;
        }
        coffeeCount = coffeeCount + 1;
        displayQuantity(coffeeCount);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (coffeeCount < 2) {
            // error message shows as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // end this method early coz there nothing left to do
            return;

        }
        coffeeCount = coffeeCount - 1;
        displayQuantity(coffeeCount);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
//        // takes the user name and displays it in the order summary
//
        EditText nameField = (EditText) findViewById(R.id.enter_field);
        String name = nameField.getText().toString();

        // find out if the customer wants whipped cream
        CheckBox whippedcreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedcreamCheckbox.isChecked();

        //finds out if the customer wants chocolate
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage =createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @returns the total price

     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // First calculate the price of one cup of coffee
        int basePrice = 5;

        // If the user wants whipped cream, add $1 per cup
        if(addWhippedCream) {
            basePrice = basePrice + 1;
        }
        //if user wants chocolate, add $2 per cup
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        // Calculate the total order price by multiplying by the quantity
        return basePrice * coffeeCount;
    }
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChcolate){
        String priceMessage = "Name" + name;
        priceMessage = priceMessage + "\nAdd whipped cream? " + addWhippedCream;
        priceMessage = priceMessage + "\nAdd chocolate? " + addChcolate;
        priceMessage = priceMessage + "\nQuantity: " + coffeeCount;
        priceMessage = priceMessage + "\nTotal : $" + price;
        priceMessage = priceMessage + "\n" + getString(R.string.Thank_you) ;
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
