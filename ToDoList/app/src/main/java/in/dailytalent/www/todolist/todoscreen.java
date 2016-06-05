package in.dailytalent.www.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Shankar on 10/05/2016.
 */
public class todoscreen extends ActionBarActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    Database db=new Database(this);
    ListView listView;
    MyAdapter adapter;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new MyAdapter(this, generateData());
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.updateStatus(generateData().get(position));
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Calendar cal= Calendar.getInstance();
                cal.setTime(generateData().get(position)._date1);
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText Title = (EditText) promptsView.findViewById(R.id.et_dialog1);
                final EditText Description = (EditText) promptsView.findViewById(R.id.et_dialog2);
                final DatePicker Date = (DatePicker) promptsView.findViewById(R.id.datePicker);
                Title.setText(generateData().get(position).getTitle());
                Description.setText(generateData().get(position).getDescription());
                Date.updateDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Title.getText().toString().trim().length() == 0 || Description.getText().toString().trim().length() == 0) {
                                    Toast.makeText(context, "Set a Title & Description", Toast.LENGTH_LONG).show();
                                } else {
                                    String s = String.valueOf(Date.getDayOfMonth()) + "/" + String.valueOf(Date.getMonth() + 1) + "/" + String.valueOf(Date.getYear());
                                    Reminders reminder = new Reminders();
                                    reminder._id=generateData().get(position)._id;
                                    reminder._title = Title.getText().toString();
                                    reminder._description = Description.getText().toString();
                                    reminder._date = s;
                                    db.updateReminder(reminder);
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                alertDialogBuilder.show();
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
                reminders.setID(cursor.getString(0));
                reminders.setTitle(cursor.getString(1));
                reminders.setDescription(cursor.getString(2));
                reminders.setDate(cursor.getString(3));
                reminders.setStatus(cursor.getString(4));
                // Adding contact to list
                items.add(reminders);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Collections.sort(items, new Comparator<Reminders>()
                {
                    public int compare(Reminders lhs, Reminders rhs)
                    {
                        return lhs._date1.compareTo(rhs._date1);
                    }
                }
        );
        return items;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setView(promptsView);
                final EditText Title = (EditText) promptsView .findViewById(R.id.et_dialog1);
                final EditText Description=(EditText)promptsView.findViewById(R.id.et_dialog2);
                final DatePicker Date=(DatePicker)promptsView.findViewById(R.id.datePicker);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Title.getText().toString().trim().length() == 0 || Description.getText().toString().trim().length() == 0) {
                                    Toast.makeText(context, "Set a Title & Description", Toast.LENGTH_LONG).show();
                                } else {
                                    String s = String.valueOf(Date.getDayOfMonth()) + "/" + String.valueOf(Date.getMonth() + 1) + "/" + String.valueOf(Date.getYear());
                                    Reminders reminder = new Reminders();
                                    reminder._title = Title.getText().toString();
                                    reminder._description = Description.getText().toString();
                                    reminder._date = s;
                                    reminder._status = "0";
                                    db.addReminder(reminder);
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                alertDialogBuilder.show();
                return true;
            case R.id.status:
                Intent intent=new Intent(todoscreen.this,todoscreen2.class);
                startActivityForResult(intent, 1);
                return true;
        }
        return true;
    }

}
