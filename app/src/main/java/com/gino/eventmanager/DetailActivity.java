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

    EditText etName, etDate, etLocation, etOrganiser;
    RadioGroup rgType;
    RadioButton rbTypeConference, rbTypeWorkshop, rbTypeSeminar, rbTypeSocial, rbTypeOther;

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Event Detail - Add");
        }

        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etLocation = findViewById(R.id.etLocation);
        etOrganiser = findViewById(R.id.etOrganiser);
        rgType = findViewById(R.id.rgType);
        rbTypeConference = findViewById(R.id.rbTypeConference);
        rbTypeWorkshop = findViewById(R.id.rbTypeWorkshop);
        rbTypeSeminar = findViewById(R.id.rbTypeSeminar);
        rbTypeSocial = findViewById(R.id.rbTypeSocial);
        rbTypeOther = findViewById(R.id.rbTypeOther);

        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(v -> {
            inputClear();
        });

        Button btnSave = findViewById(R.id.btnSave);
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
        etName.setText("");
        etDate.setText("");
        etLocation.setText("");
        etOrganiser.setText("");
        rgType.clearCheck();
    }

    private Boolean inputValidation() {
        if (etName.getText().toString().isEmpty()) {
            showErrorMessage("Please input event name");
            return false;
        }
        if (etDate.getText().toString().isEmpty()) {
            showErrorMessage("Please input event date");
            return false;
        }
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            sdf.setLenient(false);
            try {
                sdf.parse(etDate.getText().toString());
            } catch (ParseException e) {
                showErrorMessage("Invalid date format");
                return false;
            }
        }
        if (etLocation.getText().toString().isEmpty()) {
            showErrorMessage("Please input event location");
            return false;
        }
        if (etOrganiser.getText().toString().isEmpty()) {
            showErrorMessage("Please input event organiser");
            return false;
        }
        if (rgType.getCheckedRadioButtonId() == -1) {
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
        if (rbTypeConference.isChecked()) {
            eType = "Conference";
        }
        else if (rbTypeWorkshop.isChecked()) {
            eType = "Workshop";
        }
        else if (rbTypeSeminar.isChecked()) {
            eType = "Seminar";
        }
        else if (rbTypeSocial.isChecked()) {
            eType = "Social";
        }
        else {
            eType = "Other";
        }

        EventModel evt = new EventModel(etName.getText().toString(), etDate.getText().toString(), etLocation.getText().toString(),
                etOrganiser.getText().toString(), eType);
        GlobalData.evtList.add(evt);

        finish();
    }
}