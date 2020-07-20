
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
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.chaddy.mareu.R;
import com.chaddy.mareu.di.DI;
import com.chaddy.mareu.model.Reunion;
import com.chaddy.mareu.service.ReunionApiServiceInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private int logo;
    private Reunion reunion;
    Calendar cal;
    private Long currentTime;
    private Date date1;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleDateFormat1;
    private Date date;
    private String CurrentTime1;
    private String CurrentDate;
    private int i3;
    private int i4;
    private final static Pattern EmailLamzonePattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
            + "lamzone.com"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getReunionApiService();
        cal = Calendar.getInstance();
        randomLogo();
        reunion = new Reunion(
                System.currentTimeMillis(),
                sujetLyt.getEditText().getText().toString(),
                datePickerInput.getText().toString(),
                timePickerInput.getText().toString(),
                salleLyt.getEditText().getText().toString(),
                participantsLyt.getEditText().getText().toString(),
                logo);
        currentTime = System.currentTimeMillis();
        date1 = cal.getTime();
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        date = new Date(currentTime);
        CurrentTime1 = simpleDateFormat.format(date);
        CurrentDate = simpleDateFormat1.format(date1);

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

                /** Verification on the format of the date */
                if (i2 < 10) {
                    Date = "0" + Date;
                }
                if (i1 < 10) {
                    Date = i2 + "/0" + (i1 + 1) + "/" + i;
                }
                if (i2 < 10 && i1 < 10) {
                    Date = "0" + i2 + "/0" + (i1 + 1) + "/" + i;
                }
                datePickerInput.setText(Date);
                checkDate();
                validateEmail();
                checkRoomIsSet();
                timePickerInput.requestFocus();
                reunion.setDate(Date);
            }
        };
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String Time = i + ":" + i1;

                /**Verification on the format of the time*/
                if (i < 10) {
                    Time = "0" + Time;
                }
                if (i1 < 10) {
                    Time = i + ":0" + i1;
                }
                if (i < 10 && i1 < 10) {
                    Time = "0" + i + ":0" + i1;
                }
                timePickerInput.setText(Time);
                System.out.println("Time is " + CurrentTime1);
                System.out.println("Time is " + CurrentDate);
                System.out.println(Time);
                /** Integer used to check the hour and the minute*/
                i3 = i;
                i4 = i1;
                checkDate();
                validateEmail();
                checkRoomIsSet();
                reunion.setHoraire(Time);
                salleLyt.requestFocus();
            }
        };
    }
    /**Show calendar date dialog*/
    public void showCalendarDatePicker() {

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(
                AddReunionActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog, mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));
        dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        dialog.show();
    }
    /**Show calendar Time dialog*/
    public void showCalendarTimePicker() {
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(
                AddReunionActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog, mTimeSetListener,
                hour, minute, true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.CYAN));
        dialog.show();
    }
    /**Verify if the email format is @lamzone.com or not empty*/
    private boolean validateEmail() {
        String emailInput = participantsLyt.getEditText().getText().toString();
        String[] Split = emailInput.split("," + " ");
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
            participantsLyt.setError("Format invalide");
            addButton.setEnabled(false);
            return false;
        } else if (!bool) {
            participantsLyt.setError("Le format doit être @lamzone.com");
            addButton.setEnabled(false);
            return false;
        } else {
            participantsLyt.setError(null);
            addButton.setEnabled(true);
            return true;
        }
    }
    private void init() {
        avatar.setImageResource(logo);
        sujetLyt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reunion.setSujet(sujetLyt.getEditText().getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        salleLyt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateEmail();
                checkRoomIsSet();
                reunion.setSalle(salleLyt.getEditText().getText().toString());
                for (Reunion reunionList : mApiService.getReunion()) {

                    if (reunion.getSalle().equals(reunionList.getSalle()) && reunion.getDate().equals(reunionList.getDate())
                            && reunion.getHoraire().equals(reunionList.getHoraire())) {
                        salleLyt.setError("La salle est déjà utilisée à cette horaire");
                        addButton.setEnabled(false);
                    } else {
                        addButton.setEnabled(true);
                        salleLyt.setError(null);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        participantsLyt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                validateEmail();
                checkDate();
                checkRoomIsSet();
                reunion.setParticipants(participantsLyt.getEditText().getText().toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    @OnClick(R.id.create)
    void addReunion() {
        mApiService.createReunion(reunion);
        finish();
    }

    /** Generate a random logo.  @return int*/
    public int randomLogo() {
        int[] images = {R.drawable.circle, R.drawable.circle_red, R.drawable.circle, R.drawable.circle_red};
        Random rand = new Random();
        return logo = images[rand.nextInt(images.length)];
    }
    /**Verification on the time of the Meeting*/
    public void checkDate() {
        if (datePickerInput.getEditableText().toString().equals(CurrentDate) &&
                (i3 <= cal.get(Calendar.HOUR_OF_DAY) && i4 <= cal.get(Calendar.MINUTE))) {
            timePickerInput.setError("Veuillez choisir un horaire plus tard que l'heure actuelle");
            addButton.setEnabled(false);
        } else if (!datePickerInput.getEditableText().toString().equals(CurrentDate) ||
                (i3 >= cal.get(Calendar.HOUR_OF_DAY) && i4 >= cal.get(Calendar.MINUTE))) {
            timePickerInput.setError(null);
            addButton.setEnabled(true);
        }
    }
    public void checkRoomIsSet() {
        if (salleLyt.getEditText().getText().length() <= 0) {
            addButton.setEnabled(false);
        } else {
            addButton.setEnabled(true);
            validateEmail();
        }
    }
    /**Used to navigate to this activity   @param activity*/
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddReunionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}