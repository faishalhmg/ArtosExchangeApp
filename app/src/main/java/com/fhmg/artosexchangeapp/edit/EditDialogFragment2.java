package com.fhmg.artosexchangeapp.edit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.fhmg.artosexchangeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditDialogFragment2 extends DialogFragment {

    @BindView(R.id.etPemasukan)
    EditText etPemasukan;
    @BindView(R.id.etNominal2)
    EditText etNominal2;
    @BindView(R.id.btnSimpan2)
    Button btnSimpan2;

    private Unbinder unbinder;
    private EditDialogListener editDialogListener2;

    private static final String ARGS_ID2 = "args_id";
    private static final String ARGS_PEMASUKAN = "args_pemasukan";
    private static final String ARGS_NOMINAL2 = "args_nominal";

    private long mId2;
    private String mPemasukan;
    private int mNominal2;

    public EditDialogFragment2() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        editDialogListener2 = (EditDialogListener) context;
    }

    public static EditDialogFragment2 newInstance(long id, String pemasukan, int nominal) {
        EditDialogFragment2 editDialogFragment2 = new EditDialogFragment2();
        Bundle args = new Bundle();
        args.putLong(ARGS_ID2, id);
        args.putString(ARGS_PEMASUKAN, pemasukan);
        args.putInt(ARGS_NOMINAL2, nominal);
        editDialogFragment2.setArguments(args);
        return editDialogFragment2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Light_Dialog_Alert);

        if (getArguments() != null){
            mId2 = getArguments().getLong(ARGS_ID2);
            mPemasukan = getArguments().getString(ARGS_PEMASUKAN);
            mNominal2 = getArguments().getInt(ARGS_NOMINAL2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_edit2, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etPemasukan.setText(mPemasukan);
        etNominal2.setText(String.valueOf(mNominal2));

        btnSimpan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pemasukan = etPemasukan.getText().toString();
                String nominal2 = etNominal2.getText().toString();


                editDialogListener2.requestUpdate(mId2, pemasukan, Integer.parseInt(nominal2));
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface EditDialogListener {
        void requestUpdate(long id, String pembelian, int nominal);
    }
}