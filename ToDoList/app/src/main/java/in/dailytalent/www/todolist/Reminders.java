package in.dailytalent.www.todolist;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Shankar on 10/05/2016.
 */
public class Reminders {

    //private variables
    String _id,_status;
    String _title,_description,_date;
    Date _date1;

    // Empty constructor
    public Reminders(){

    }
    // constructor
    public Reminders(String id, String _title, String _description, String _date, String _status){
        this._id = id;
        this._title = _title;
        this._description = _description;
        this._date=_date;
        this._status=_status;
    }

    // constructor
    public Reminders(String _title, String _description, String _date, String _status){
        this._title = _title;
        this._description = _description;
        this._date=_date;
        this._status=_status;
    }
    // getting ID
    public String getID(){
        return this._id;
    }

    // setting id
    public void setID(String id){
        this._id = id;
    }

    // getting name
    public String getTitle(){
        return this._title;
    }

    // setting name
    public void setTitle(String title){
        this._title = title;
    }

    public String getDescription(){
        return this._description;
    }

    public void setDescription(String description){
        this._description = description;
    }
    // getting phone number
    public String getDate(){
        return this._date;
    }

    // setting phone number
    public void setDate(String date){
        this._date = date;
        SimpleDateFormat sdf=new SimpleDateFormat("d/M/yyyy");
        ParsePosition pos = new ParsePosition(0);
        _date1=sdf.parse(_date,pos);
    }

    public String getStatus(){
        return this._status;
    }

    // setting id
    public void setStatus(String status){
        this._status = status;
    }
}
