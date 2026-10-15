package com.gino.eventmanager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DetailActivity extends AppCompatActivity {

    EditText etEventName, etEventDate, etEventLocation, etEventOrganiser;
    RadioGroup rgEventType;
    RadioButton rbEventTypeConference, rbEventTypeWorkshop, rbEventTypeSeminar, rbEventTypeSocial, rbEventTypeOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ===== initial
        etEventName = findViewById(R.id.etEventName);
        etEventDate = findViewById(R.id.etEventDate);
        etEventLocation = findViewById(R.id.etEventLocation);
        etEventOrganiser = findViewById(R.id.etEventOrganiser);
        rgEventType = findViewById(R.id.rgEventType);
        rbEventTypeConference = findViewById(R.id.rbEventTypeConference);
        rbEventTypeWorkshop = findViewById(R.id.rbEventTypeWorkshop);
        rbEventTypeSeminar = findViewById(R.id.rbEventTypeSeminar);
        rbEventTypeSocial = findViewById(R.id.rbEventTypeSocial);
        rbEventTypeOther = findViewById(R.id.rbEventTypeOther);

        Button btnClear = findViewById(R.id.btnClear);
        Button btnSave = findViewById(R.id.btnSave);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Event Detail - Add");
        }

        // ===== set listener
        btnClear.setOnClickListener(v -> {
            inputClear();
        });

        btnSave.setOnClickListener(v -> {
            if (inputValidation()) {
                saveData();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void inputClear() {
        etEventName.setText("");
        etEventDate.setText("");
        etEventLocation.setText("");
        etEventOrganiser.setText("");
        rgEventType.clearCheck();
    }

    private Boolean inputValidation() {
        if (etEventName.getText().toString().isEmpty()) {
            showErrorMessage("Please input event name");
            return false;
        }
        if (etEventDate.getText().toString().isEmpty()) {
            showErrorMessage("Please input event date");
            return false;
        }
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            sdf.setLenient(false);
            try {
                sdf.parse(etEventDate.getText().toString());
            } catch (ParseException e) {
                showErrorMessage("Invalid date format");
                return false;
            }
        }
        if (etEventLocation.getText().toString().isEmpty()) {
            showErrorMessage("Please input event location");
            return false;
        }
        if (etEventOrganiser.getText().toString().isEmpty()) {
            showErrorMessage("Please input event organiser");
            return false;
        }
        if (rgEventType.getCheckedRadioButtonId() == -1) {
            showErrorMessage("Please select event type");
            return false;
        }


        return true;
    }
    private void showErrorMessage(String msg) {
        Snackbar scb = Snackbar.make(findViewById(R.id.main), msg, Snackbar.LENGTH_LONG);
        scb.setAnchorView(findViewById(R.id.btnSave));
        scb.show();
    }

    private void saveData() {
        String eType = "";
        if (rbEventTypeConference.isChecked()) {
            eType = "Conference";
        }
        else if (rbEventTypeWorkshop.isChecked()) {
            eType = "Workshop";
        }
        else if (rbEventTypeSeminar.isChecked()) {
            eType = "Seminar";
        }
        else if (rbEventTypeSocial.isChecked()) {
            eType = "Social";
        }
        else {
            eType = "Other";
        }

        EventModel evt = new EventModel(etEventName.getText().toString(), etEventDate.getText().toString(), etEventLocation.getText().toString(),
                etEventOrganiser.getText().toString(), eType);
        GlobalData.evtList.add(evt);

        finish();
    }
}