package edu.uncw.seahawktours;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView url = findViewById(R.id.archivesURL);
        url.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //action to take when the action create order button is tapped
            case R.id.menu:
                Intent intent = new Intent (this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.up:
                Intent intentUp = new Intent (AboutActivity.this, MainActivity.class);
                startActivity(intentUp);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
