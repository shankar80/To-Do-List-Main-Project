package in.dailytalent.www.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/05/2016.
 */
public class MyAdapter extends ArrayAdapter<Reminders> {
    private final Context context;
    private final ArrayList<Reminders> itemsArrayList;

    public MyAdapter(Context context, ArrayList<Reminders> itemsArrayList) {

        super(context, R.layout.listview, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.listview, parent, false);

        // 3. Get the two text view from the rowView
        TextView listTitle=(TextView) rowView.findViewById(R.id.textView);
        TextView Title = (TextView) rowView.findViewById(R.id.textView2);
        TextView Description = (TextView) rowView.findViewById(R.id.textView3);
        TextView Date = (TextView) rowView.findViewById(R.id.textView4);
        ImageView image=(ImageView)rowView.findViewById(R.id.imageView);

        // 4. Set the text for textView
        listTitle.setText(itemsArrayList.get(position).getDate());
        Title.setText(itemsArrayList.get(position).getTitle());
        Description.setText(itemsArrayList.get(position).getDescription());
        Date.setText(itemsArrayList.get(position).getDate());
        if(itemsArrayList.get(position).getStatus().equals("0"))
        {
            image.setImageResource(R.drawable.index);
        }

        // 5. return rowView
        return rowView;

    }
}
