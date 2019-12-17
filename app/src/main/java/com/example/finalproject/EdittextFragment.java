package com.example.finalproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

interface IEdittextListener {
    void LocationtoEdittext();
}

public class EdittextFragment extends Fragment implements IEdittextListener {


    private EditText editText_location;
    private IEdittextListener mListener;
    private Button chooseDateButton;
    private Calendar C;
    private DatePickerDialog dpd;
    private TextView textView_date;
    private String choosedDate;
    private String location;

    public EdittextFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edittext, container, false);


        textView_date = v.findViewById(R.id.textView_date);
        editText_location = v.findViewById(R.id.edittext_location);

        chooseDateButton = v.findViewById(R.id.button_choosedata);

        chooseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity)getActivity()).hideKeyboard();

                C = Calendar.getInstance();
                int day = C.get(Calendar.DAY_OF_MONTH);
                int month = C.get(Calendar.MONTH);
                int year = C.get(Calendar.YEAR);

                dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        textView_date.setText(i + "-" + (i1+1) + "-" + i2);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (this instanceof IEdittextListener) {
            mListener = this;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement LocationtoEdittext");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void LocationtoEdittext() {
        choosedDate = textView_date.getText().toString();
        location = editText_location.getText().toString();

        MainActivity.myBundle.putString("choosedDate", choosedDate);
        MainActivity.myBundle.putString("location", location);
        textView_date.setText("");
        editText_location.setText("");
    }
}
