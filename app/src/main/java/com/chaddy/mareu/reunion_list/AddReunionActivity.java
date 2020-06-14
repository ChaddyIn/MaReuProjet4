package com.chaddy.mareu.reunion_list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chaddy.mareu.R;
import com.chaddy.mareu.di.DI;
import com.chaddy.mareu.model.Reunion;

import com.chaddy.mareu.service.ReunionApiServiceInterface;

import java.util.Calendar;
import java.util.Random;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddReunionActivity extends AppCompatActivity {

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.sujetLyt)
    TextInputLayout sujetLyt;

    @BindView(R.id.salleMeetingLayout)
    TextInputLayout salleLyt;
    @BindView(R.id.participantsMeetingLayout)
    TextInputLayout participantsLyt;
    @BindView(R.id.create)
    MaterialButton addButton;

    @BindView(R.id.datePicker)
    TextInputEditText datePickerInput;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @BindView(R.id.timePicker)
    TextInputEditText timePickerInput;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    private ReunionApiServiceInterface mApiService;

    private int logo ;

    Calendar cal;

    private final static Pattern EmailLamzonePattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
            + "lamzone.com"
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_neighbour);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getReunionApiService();
        cal = Calendar.getInstance();

        randomLogo();



        init();


        datePickerInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showCalendarDatePicker();

            }

        });
        datePickerInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showCalendarDatePicker();
                }
            }
        });

        timePickerInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showCalendarTimePicker();
                }
            }
        });

        timePickerInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendarTimePicker();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String Date = i2 + "/" + (i1 + 1) + "/" + i;
                datePickerInput.setText(Date);
                timePickerInput.requestFocus();
            }
        };

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String Time = i + ":" + i1;
                timePickerInput.setText(Time);
                salleLyt.requestFocus();
            }
        };


    }

    /**
     * Show calendar date dialog
     */

    public void showCalendarDatePicker() {

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                AddReunionActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog, mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));
        dialog.show();

    }


    /**
     * Show calendar Time dialog
     */

    public void showCalendarTimePicker() {

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);


        TimePickerDialog dialog = new TimePickerDialog(
                AddReunionActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog, mTimeSetListener,
                hour, minute, true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));
        dialog.show();

    }


    private boolean validateEmail() {
        String emailInput = participantsLyt.getEditText().getText().toString();
        String[] Split = emailInput.split(",");
        Boolean bool = true;
        for (String split : Split) {
            if (
                    !EmailLamzonePattern.matcher(split.trim()).matches()
            ) {
                bool = false;
                break;
            }
        }

        if (emailInput.isEmpty()) {
            participantsLyt.setError("invalide format");
            return false;
        } else if (!bool) {
            participantsLyt.setError("format must be @lamzone.com");
            return false;
        } else {
            participantsLyt.setError(null);
            return true;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {

        avatar.setImageResource(logo);

        sujetLyt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                addButton.setEnabled(s.length() > 0);
            }
        });

        participantsLyt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                validateEmail();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @OnClick(R.id.create)
    void addReunion() {
        Reunion reunion = new Reunion(
                System.currentTimeMillis(),
                sujetLyt.getEditText().getText().toString(),
                datePickerInput.getText().toString(),
                timePickerInput.getText().toString(),
                salleLyt.getEditText().getText().toString(),
                participantsLyt.getEditText().getText().toString(),
                this.logo);





        mApiService.createReunion(reunion);
        finish();
    }

    /**
     * Generate a random logo. Useful to mock image picker
     *
     * @return int
     */
    public int randomLogo(){
        int[] images = {R.drawable.circle,R.drawable.circle_red,R.drawable.circle,R.drawable.circle_red};
        Random rand = new Random();
       return logo = images[rand.nextInt(images.length)];
    }






    /**
     * Used to navigate to this activity
     *
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddReunionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
