package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

 import static com.example.android.justjava.R.id.quantity_text_view;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int liczba = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (liczba == 100) {
            Toast.makeText(this, "That is a bit too much, don't you think?", Toast.LENGTH_SHORT).show();
            return;
        }
        liczba = liczba + 1;
        display(liczba);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (liczba == 1) {
            Toast.makeText(this, "You need to order at least 1 coffee.", Toast.LENGTH_SHORT).show();
            return;
        }
        liczba = liczba - 1;
        display(liczba);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText txtname = (EditText) findViewById(R.id.name);
        String name = txtname.getText().toString();
        CheckBox smietankaCheckBox = (CheckBox) findViewById(R.id.dodatki_checkbox);
        boolean dodanoSmietanke = smietankaCheckBox.isChecked();
        CheckBox czekoladaCheckBox = (CheckBox) findViewById(R.id.czekolada_checkbox);
        boolean dodanoCzekolade = czekoladaCheckBox.isChecked();
        int price = calculatePrice(dodanoSmietanke, dodanoCzekolade);
        String priceMessage = createOrderSummary(price, dodanoSmietanke, dodanoCzekolade, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Zamówienie kawy od " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);


        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addSmietana, boolean addCzekolada) {
        int basePrice = 5;
        if (addSmietana) {
            basePrice = basePrice + 1;
        }
        if (addCzekolada) {
            basePrice = basePrice + 2;
        }

        return liczba * basePrice;
    }

    /**
     * Creates order summary.
     *
     * @return order summary
     */
    private String createOrderSummary(int basePrice, boolean zeSmietanka, boolean zCzekolada, String name) {
        String priceMessage = "Imię: " + name;
        priceMessage += "\nNapój zawiera śmietankę? " + zeSmietanka;
        priceMessage += "\nNapój zawiera czekolade? " + zCzekolada;
        priceMessage = priceMessage + "\nLiczba:" + liczba;
        priceMessage = priceMessage + "\nCałkowity koszt " + basePrice + " zł";
        priceMessage = priceMessage + "\nDziękuję!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                quantity_text_view);
        quantityTextView.setText("" + number);
    }
}