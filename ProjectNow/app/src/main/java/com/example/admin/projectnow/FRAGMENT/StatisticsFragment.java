package com.example.admin.projectnow.FRAGMENT;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.projectnow.ADAPTER.StatisticsAdapter;
import com.example.admin.projectnow.DAO.statisticsDAO;
import com.example.admin.projectnow.DAO.storeDAO;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.MODEL.statistics;
import com.example.admin.projectnow.MODEL.store;
import com.example.admin.projectnow.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsFragment extends Fragment {
    private static StatisticsFragment instance;
    private account account;
    private store store;
    private List<statistics> statisticsList;
    private StatisticsAdapter statisticsAdapter;
    private double totalPrice;

    //region BindView
    @BindView(R.id.datePicker)EditText DateTime;
    @BindView(R.id.rv_statistics)RecyclerView rv_Statistics;
    @BindView(R.id.statistics_totalPrice)TextView tvTotalPrice;
    //endregion
    //region Instance
    public static StatisticsFragment Instance()
    {
        if (instance == null)
        {
            instance = new StatisticsFragment();
        }
        return instance;
    }
    private void Instance(StatisticsFragment instance)
    {
        StatisticsFragment.instance = instance;
    }
    //endregion

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GetData();
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);
        setDateTimePicker();
        SetInterface();
        ShowStatistics();
        return view;
    }

    //region Method
    private void GetData()
    {
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            account = (account) bundle.getSerializable("account");
            store = storeDAO.Instance(getContext()).GetStoreByUserName(account.UserName());
        }
    }
    private void updateLabel(Calendar myCalendar) {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
        DateTime.setText(sdf.format(myCalendar.getTime()));
    }
    private void SetInterface()
    {
        totalPrice = 0;
        statisticsList = statisticsDAO.Instance(getContext()).GetStatistics(DateTime.getText().toString(), store.Id());
        for(statistics statistics : statisticsList)
        {
            totalPrice += statistics.TotalPrice();
        }
        tvTotalPrice.setText("Tổng Tiền: " + totalPrice + " VNĐ");
        statisticsAdapter = new StatisticsAdapter(statisticsList, this);
        rv_Statistics.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rv_Statistics.setHasFixedSize(true);
        rv_Statistics.setAdapter(statisticsAdapter);
    }
    //endregion

    //region Event
    private void setDateTimePicker()
    {
        final Calendar myCalendar = Calendar.getInstance();
        updateLabel(myCalendar);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };

        DateTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(StatisticsFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void ShowStatistics()
    {
        DateTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                totalPrice = 0;
                List<statistics> list = statisticsDAO.Instance(getContext()).GetStatistics(charSequence.toString(), store.Id());
                for (statistics statistics : list)
                {
                    totalPrice += statistics.TotalPrice();
                }
                tvTotalPrice.setText("Tổng Tiền: " + totalPrice + " VNĐ");
                statisticsAdapter.UpdateAllItem(list);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    //endregion
}
