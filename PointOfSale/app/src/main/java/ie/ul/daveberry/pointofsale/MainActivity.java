package ie.ul.daveberry.pointofsale;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mNameTextView, mQuantityTextView, mDateTextView;
    private Item mCurrentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameTextView = findViewById(R.id.name_text);
        mQuantityTextView = findViewById(R.id.quantity_text);
        mDateTextView = findViewById(R.id.date_text);

        //Boilerplate
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Practice with showing items on the screen.
                mCurrentItem = Item.getDefaultItem();
                showCurrentItem();

            }
        });
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
