package com.khawarjk.karobar;

/**
 * Created by khawarjk on 18/5/2020.
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private DatabaseHelper dbHelper;

    final String[] from = new String[]{dbHelper.ID_COLUMN, dbHelper.TITLE_COLUMN, dbHelper.DESCRIPTION_COLUMN, dbHelper.TIMESTAMP_COLUMN};
    final int[] to = new int[]{R.id.id, R.id.listTitle, R.id.listDescription, R.id.timestamp};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DatabaseManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.myListView);

        adapter = new SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to, 0);
        listView.setAdapter(adapter);


        try {
            Intent intent = getIntent();
            Boolean ItemDeleted = intent.getExtras().getBoolean("ItemDeleted");
            ModifyActivity modifyActivity = new ModifyActivity();
            if (ItemDeleted) {
                Snackbar.make(listView, "ItemDeleted!", Snackbar.LENGTH_LONG).show();
                modifyActivity.setItemDeleted(false);
            }
        } catch (Exception e) {
            if (adapter.isEmpty()) {
                Snackbar.make(listView, "Click on fab to add list", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(listView, "Hold on item to modify", Snackbar.LENGTH_LONG).show();
            }
        }
        //click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView itemID = (TextView) view.findViewById(R.id.id);
                TextView itemTitle = (TextView) view.findViewById(R.id.listTitle);
                TextView itemDesc = (TextView) view.findViewById(R.id.listDescription);
                TextView itemTime = (TextView) view.findViewById(R.id.timestamp);

                String myId = itemID.getText().toString();
                String myTitle = itemTitle.getText().toString();
                String myDesc = itemDesc.getText().toString();
                String myTime = itemTime.getText().toString();


                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra("Id", myId);
                intent.putExtra("Title", myTitle);
                intent.putExtra("Description", myDesc);
                intent.putExtra("Timestamp", myTime);
                startActivity(intent);
            }
        });
        //long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TextView itemID = (TextView) view.findViewById(R.id.id);
                TextView itemTitle = (TextView) view.findViewById(R.id.listTitle);
                TextView itemDesc = (TextView) view.findViewById(R.id.listDescription);
                TextView itemTime = (TextView) view.findViewById(R.id.timestamp);

                String myId = itemID.getText().toString();
                String myTitle = itemTitle.getText().toString();
                String myDesc = itemDesc.getText().toString();
                String myTime = itemTime.getText().toString();

                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra("Id", myId);
                intent.putExtra("Title", myTitle);
                intent.putExtra("Description", myDesc);
                intent.putExtra("Time", myTime);
                startActivity(intent);

                return false;
            }
        });
    }

    public void onClickAddItem(View view) {
        Intent intent = new Intent(getApplicationContext(), AddItem.class);
        startActivity(intent);
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
        if (id == R.id.action_clear_all) {
            return true;
        }
        if (id == R.id.users) {
            Intent intent = new Intent(getApplicationContext(), Users.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
