package in.dailytalent.www.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Shankar on 10/05/2016.
 */
public class todoscreen2 extends ActionBarActivity {
    Database db=new Database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        MyAdapter adapter = new MyAdapter(this, generateData());
        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.deleteReminder(generateData().get(position));
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return false;
            }
        });
    }

    private ArrayList<Reminders> generateData(){
        ArrayList<Reminders> items = new ArrayList<Reminders>();
        String selectQuery = "SELECT  * FROM reminders";

        SQLiteDatabase db1 = db.getWritableDatabase();
        Cursor cursor = db1.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Reminders reminders = new Reminders();
                if(Integer.parseInt(cursor.getString(4))==1) {
                    reminders.setID(cursor.getString(0));
                    reminders.setTitle(cursor.getString(1));
                    reminders.setDescription(cursor.getString(2));
                    reminders.setDate(cursor.getString(3));
                    reminders.setStatus(cursor.getString(4));
                    // Adding contact to list
                    items.add(reminders);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        Collections.sort(items, new Comparator<Reminders>() {
                    public int compare(Reminders lhs, Reminders rhs) {
                        return lhs._date1.compareTo(rhs._date1);
                    }
                }
        );
        return items;
    }
}
