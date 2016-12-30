package com.mingle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                MainActivity.this.startActivity(new Intent(MainActivity.this, SJTViewActivity.class));
                break;
            case R.id.button2:
                MainActivity.this.startActivity(new Intent(MainActivity.this, SJTDialogActivity.class));
                break;
            case R.id.button3:
                MainActivity.this.startActivity(new Intent(MainActivity.this, FrameViewActivity.class));
                break;
            case R.id.button4:
                MainActivity.this.startActivity(new Intent(MainActivity.this, WUBAViewActivity.class));
                break;
            case R.id.button5:
                MainActivity.this.startActivity(new Intent(MainActivity.this, SJTDialogActivity.class));
                break;
            default:
                break;
        }
    }
}
