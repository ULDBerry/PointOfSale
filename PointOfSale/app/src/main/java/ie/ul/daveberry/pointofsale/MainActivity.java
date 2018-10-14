package ie.ul.daveberry.pointofsale;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private TextView mNameTextView, mQuantityTextView, mDateTextView;
    private Item mCurrentItem;
    private Item mClearedItem;
    private ArrayList<Item> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameTextView = findViewById(R.id.name_text);
        mQuantityTextView = findViewById(R.id.quantity_text);
        mDateTextView = findViewById(R.id.date_text);
        mItems = new ArrayList<>();

        mItems.add(new Item("Order 1",30, new GregorianCalendar()));
        mItems.add(new Item("Order 2",40, new GregorianCalendar()));
        mItems.add(new Item("Order 3",50, new GregorianCalendar()));

        //Boilerplate
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addItem();

            }
        });
    }

    private void addItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Make your dialog
        //Optional Title with string resource added
        // builder.setTitle(R.string.add_item);
        View view = getLayoutInflater().inflate(R.layout.dialog_add,null,false);
        builder.setView(view);

        //Capture input from the dialog
        final EditText nameEditText = view.findViewById(R.id.edit_name);
        final EditText quantityEditText = view.findViewById(R.id.edit_quantity);
        final CalendarView deliveryDateView = view.findViewById(R.id.calendar_view);
        final GregorianCalendar calendar = new GregorianCalendar();

        deliveryDateView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
            }
        });

        builder.setNegativeButton(android.R.string.cancel,null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEditText.getText().toString();
                int quantity = Integer.parseInt(quantityEditText.getText().toString());
                mCurrentItem = new Item(name, quantity, calendar);
                mItems.add(mCurrentItem);
                showCurrentItem();
            }
        });
        builder.create().show();
    }

    private void showCurrentItem() {
        mNameTextView.setText(mCurrentItem.getName());
        mQuantityTextView.setText(getString(R.string.quantity_format, mCurrentItem.getQuantity()));
        mDateTextView.setText(getString(R.string.date_format, mCurrentItem.getDeliveryDateString()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.menu_reset:
                mClearedItem = mCurrentItem;
                mCurrentItem = new Item();
                showCurrentItem();

                //Snack bar

                final Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_layout),
                        "Item cleared",Snackbar.LENGTH_LONG );
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //undo
                        mCurrentItem = mClearedItem;
                        showCurrentItem();
                        Snackbar.make(findViewById(R.id.coordinator_layout),
                                "Item Restored", snackbar.LENGTH_SHORT).show();

                    }
                });
                snackbar.show();
                return true;
            case R.id.action_search:
                showSearchDialog();
                return true;
            case R.id.action_settings:
                //This can be any settings
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
            case R.id.action_clearall:
                showClearDialog();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    private void showClearDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clear List");
        builder.setMessage("Are you sure you want to clear all items?");
        builder.setNegativeButton(android.R.string.cancel,null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mItems.clear();
                mCurrentItem = new Item();
                showCurrentItem();
            }
        });

        builder.create().show();

    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.choose_an_item);
        builder.setItems(getNames(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCurrentItem = mItems.get(which);
                showCurrentItem();

            }
        });
        // Add cancel button to search dialog
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    private String[] getNames() {
        String[] names = new String[mItems.size()];
        for (int i=0; i < mItems.size(); i++){
            names[i] = mItems.get(i).getName();
        }
        return names;
    }
}
