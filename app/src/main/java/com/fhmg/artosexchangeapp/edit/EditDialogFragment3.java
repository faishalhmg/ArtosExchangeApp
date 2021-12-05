package com.fhmg.artosexchangeapp.edit;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.fhmg.artosexchangeapp.DatePickerFragment;
import com.fhmg.artosexchangeapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditDialogFragment3 extends DialogFragment implements DatePickerFragment.DialogDateListener{
    private final static String DATE_PICKER_TAG = "DatePicker";
    @BindView(R.id.etPengigat)
    EditText etPengingat;
    @BindView(R.id.etNominal3)
    EditText etNominal3;
    @BindView(R.id.btnSimpan3)
    Button btnSimpan3;
    @BindView(R.id.btn_once_date)
    ImageButton btn_once_date;
    @BindView(R.id.tv_once_date)
    TextView tv_once_date;

    private Unbinder unbinder;
    private EditDialogListener editDialogListener3;

    private static final String ARGS_ID3 = "args_id";
    private static final String ARGS_PENGINGAT= "args_pengingat";
    private static final String ARGS_NOMINAL3 = "args_nominal";
    private static final String ARGS_TANGGAL= "args_tanggal";

    private long mId3;
    private String mPengingat;
    private int mNominal3;
    private String mDate;

    public EditDialogFragment3() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        editDialogListener3 = (EditDialogListener) context;
    }

    public static EditDialogFragment3 newInstance(long id, String pemasukan, int nominal, String date) {
        EditDialogFragment3 editDialogFragment3 = new EditDialogFragment3();
        Bundle args = new Bundle();
        args.putLong(ARGS_ID3, id);
        args.putString(ARGS_PENGINGAT, pemasukan);
        args.putInt(ARGS_NOMINAL3, nominal);
        args.putString(ARGS_TANGGAL, date);
        editDialogFragment3.setArguments(args);
        return editDialogFragment3;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Light_Dialog_Alert);

        if (getArguments() != null){
            mId3 = getArguments().getLong(ARGS_ID3);
            mPengingat = getArguments().getString(ARGS_PENGINGAT);
            mNominal3 = getArguments().getInt(ARGS_NOMINAL3);
            mDate = getArguments().getString(ARGS_TANGGAL);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_edit3, container);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etPengingat.setText(mPengingat);
        etNominal3.setText(String.valueOf(mNominal3));
        tv_once_date.setVisibility(View.INVISIBLE);
        btn_once_date.setVisibility(View.INVISIBLE);

        btnSimpan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pengingat = etPengingat.getText().toString();
                String nominal3 = etNominal3.getText().toString();
                String date = tv_once_date.getText().toString();
                /*
                Fungsi ini iuntuk mengirim data berupa id, pembelian, dan nominal ke
                activity/fragment yang di implementasinya.
                 */
                editDialogListener3.requestUpdate(mId3, pengingat, Integer.parseInt(nominal3),date);
                getDialog().dismiss();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        // Siapkan date formatter-nya terlebih dahulu
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        // Set text dari textview once
        tv_once_date.setText(dateFormat.format(calendar.getTime()));
    }

    public interface EditDialogListener {
        void requestUpdate(long id, String pengingat, int nominal, String date);
    }
}