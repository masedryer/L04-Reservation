package sg.edu.rp.c346.id22020860.l04_reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    DatePicker dp;
    TimePicker tp;
    Button btnReset;
    Button btnReserve;
    EditText Name;
    EditText Phone;
    EditText group;
    RadioGroup smoking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dp = findViewById(R.id.datePicker);
        tp = findViewById(R.id.timePicker);
        btnReset = findViewById(R.id.Reset);
        btnReserve = findViewById(R.id.Reserve);
        Name = findViewById(R.id.editName);
        Phone = findViewById(R.id.editPhone);
        group = findViewById(R.id.editGroup);
        smoking = findViewById(R.id.smokingGroup);

        dp.updateDate(2023,5,1);
        tp.setCurrentHour(19);
        tp.setCurrentMinute(30);


        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String total = "Name: " + Name.getText() + "\n" + "Phone: " + Phone.getText() + "\n"+ "Group: " + group.getText();
               int checkedRadioId = smoking.getCheckedRadioButtonId();
               String Date = "Date: " + dp.getDayOfMonth()+ "/" + dp.getMonth() + "/" + dp.getYear();
               String time = "Time: " + tp.getCurrentHour() + ":" + tp.getCurrentMinute();
              String smokeType="";
               if(checkedRadioId == R.id.Smoking){
                   smokeType = "Smoking";
               }
               else{
                    smokeType = "Non-Smoking";
               }

               if (Name.getText().toString().trim().length() == 0 && Phone.getText().toString().trim().length() != 0 && group.getText().toString().trim().length() != 0) {
                   Toast.makeText(MainActivity.this,"Name is required!",Toast.LENGTH_SHORT).show();
               }
               else if(Name.getText().toString().trim().length() != 0 && Phone.getText().toString().trim().length() == 0 &&  group.getText().toString().trim().length() != 0){
                   Toast.makeText(MainActivity.this,"Phone number is required!",Toast.LENGTH_SHORT).show();
               }
               else if(Name.getText().toString().trim().length() != 0 && Phone.getText().toString().trim().length() != 0 &&  group.getText().toString().trim().length() == 0){
                   Toast.makeText(MainActivity.this,"Group number is required!",Toast.LENGTH_SHORT).show();
                }
                else if(Name.getText().toString().trim().length() == 0 && Phone.getText().toString().trim().length() == 0 &&  group.getText().toString().trim().length() != 0) {
                   Toast.makeText(MainActivity.this, "Name and Phone number is required!", Toast.LENGTH_SHORT).show();
               }
               else if(Name.getText().toString().trim().length() == 0 && Phone.getText().toString().trim().length() != 0 &&  group.getText().toString().trim().length() == 0) {
                   Toast.makeText(MainActivity.this, "Name and Group number is required!", Toast.LENGTH_SHORT).show();
               }
               else if(Name.getText().toString().trim().length() != 0 && Phone.getText().toString().trim().length() == 0 &&  group.getText().toString().trim().length() == 0) {
                   Toast.makeText(MainActivity.this, "Phone Number and Group number is required!", Toast.LENGTH_SHORT).show();
               }
               else if(Name.getText().toString().trim().length() == 0 && Phone.getText().toString().trim().length() == 0 &&  group.getText().toString().trim().length() == 0) {
                   Toast.makeText(MainActivity.this, "Name, Phone Number and Group number is required!", Toast.LENGTH_SHORT).show();
               }
               else{
                   Toast.makeText(MainActivity.this,total + "\n"+Date +"\n"+time + "\n Smoking:"+smokeType  ,Toast.LENGTH_SHORT).show();
               }

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dp.updateDate(2023,5,1);
                tp.setCurrentHour(19);
                tp.setCurrentMinute(30);
                Name.setText("");
                Phone.setText("");
                group.setText("");

            }
        });
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker v, int hour, int minute) {
                if (tp.getCurrentHour() < 8  ){
                    tp.setCurrentHour(8);
                    tp.setCurrentMinute(0);
                    Toast.makeText(MainActivity.this,"Store only open after 8am",Toast.LENGTH_SHORT).show();
                }
                else if(tp.getCurrentHour() >= 21){
                    tp.setCurrentHour(20);
                    tp.setCurrentMinute(59);
                    Toast.makeText(MainActivity.this,"Store closed after 9PM",Toast.LENGTH_SHORT).show();
                }
            }
        });
       /* dp.getCalendarView().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override

            public void onSelectedDayChange( CalendarView calendarView, int year, int month, int dayOfMonth) {
               int selectYear = dp.getYear();
               int selectMonth = dp.getMonth();
               int selectDay = dp.getDayOfMonth();

               if(year < selectYear){
                   dp.updateDate(year,month,dayOfMonth);
                   Toast.makeText(MainActivity.this,"Date has been already past.Select a date that is available",Toast.LENGTH_SHORT);
               }
               else if(month < selectMonth && year == selectYear){
                   dp.updateDate(year,month,dayOfMonth);
                   Toast.makeText(MainActivity.this,"Date has been already past.Select a date that is available",Toast.LENGTH_SHORT);
               }
               else if (dayOfMonth < selectDay && month == selectMonth && year == selectYear){
                   dp.updateDate(year,month,dayOfMonth);
                   Toast.makeText(MainActivity.this,"Date has been already past.Select a date that is available",Toast.LENGTH_SHORT);
               }
            }
        });*/
        dp.init(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Create a Calendar instance for the selected date
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        // Get the current date
                        Calendar currentDate = Calendar.getInstance();

                        // Compare the selected date with the current date
                        if (selectedDate.after(currentDate)) {
                          dp.updateDate(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
                        } else {
                            dp.updateDate(year,monthOfYear,dayOfMonth);
                            Toast.makeText(MainActivity.this,"Date has been already past.Select a date that is available",Toast.LENGTH_SHORT);
                        }
                    }
                });


    }
}