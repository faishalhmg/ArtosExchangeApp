package com.fhmg.artosexchangeapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.fhmg.artosexchangeapp.create.DompetActivity;
import com.fhmg.artosexchangeapp.create.PendapatanCreateActivity;
import com.fhmg.artosexchangeapp.edit.EditDialogFragment2;
import com.fhmg.artosexchangeapp.edit.EditDialogFragment3;
import com.fhmg.artosexchangeapp.pendapatan.PendapatanActivity;
import com.fhmg.artosexchangeapp.pengeluaran.PengeluaranActivity;
import com.fhmg.artosexchangeapp.utils.FunctionHelper;
import com.fhmg.artosexchangeapp.utils.database.DaoHandler;
import com.fhmg.artosexchangeapp.utils.database.DaoSession;

import com.fhmg.artosexchangeapp.utils.database.TblDompet;
import com.fhmg.artosexchangeapp.utils.database.TblPendapatan;
import com.fhmg.artosexchangeapp.utils.database.TblPengeluaran;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements EditDialogFragment3.EditDialogListener{
    private final static String DATE_PICKER_TAG = "DatePicker";

    @BindView(R.id.tvTotal3)
    TextView tvTotal3;
    @BindView(R.id.tvTotal4)
    TextView tvTotal4;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageFilterButton4)
    ImageFilterButton imagebutton1;
    @BindView(R.id.imageFilterButton3)
    ImageFilterButton imagebutton2;
    @BindView(R.id.imageFilterButton)
    ImageFilterButton imagebutton;
    @BindView(R.id.imageFilterButton2)
    ImageFilterButton imagebutton3;

    @BindView(R.id.cardview)
    CardView cardview;



private DaoSession daoSession;


    SharedPref sharedpref;
    private List<TblPendapatan> tblPendapatanList;
    private List<TblPengeluaran> tblPengeluaranList;
    private List<TblDompet> tblDompetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else  setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        daoSession = DaoHandler.getInstance(this);
        tblPendapatanList = daoSession.getTblPendapatanDao().queryBuilder().list();
        tblPengeluaranList = daoSession.getTblPengeluaranDao().queryBuilder().list();
        tblDompetList = daoSession.getTblDompetDao().queryBuilder().list();
        imageView.setImageResource(R.drawable.dompet);


        if (getTotal()>=getTotal3()&&getTotal3()!=0) {//ganti jadi uang dompet target//
            showNotification(MainActivity.this, getResources().getString(R.string.notification_title), getResources().getString(R.string.notification_message)+", \nTarget Waktu = "+tblDompetList.get(0).getTanggal()+", \nTercapai Pada Tanggal = " + getDateTime(), 110);
        }
//        else if(getTotal()<getTotal3()&&getTotal3()!=0){
//            showNotification(MainActivity.this, "Yah, Dompetmu belum mencapai target waktu mu", "Mulai berhemat yuk!"+"\nDompet mu = "+getTotal()+"\nTarget Dompet = "+getTotal3(), 110);
//        }


        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getTotal3()==0) {
                    Intent nextActivity = new Intent(MainActivity.this, DompetActivity.class);
                    startActivity(nextActivity);
                }
                else{
                    long id = tblDompetList.get(0).getIdTblDompet();
                    String pengingat = tblDompetList.get(0).getPengingat();
                    int nominal = tblDompetList.get(0).getNominal();
                    String date = tblDompetList.get(0).getTanggal();

                    FragmentManager fm = getSupportFragmentManager();
                    EditDialogFragment3 editDialogFragment3 = EditDialogFragment3.newInstance(id, pengingat, nominal, date);
                    editDialogFragment3.show(fm, "dialog_edit");
                }
            }
        });
        imagebutton1.setImageResource(R.drawable.pendapatan);
        imagebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this,PendapatanActivity.class);
                startActivity(nextActivity);
            }
        });
        imagebutton2.setImageResource(R.drawable.moneyspend);
        imagebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this,PengeluaranActivity.class);
                startActivity(nextActivity);
            }
        });
        imagebutton.setImageResource(R.drawable.moneycurrency);
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this,MoneyCurrencyConverter.class);
                startActivity(nextActivity);
            }
        });
        imagebutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(nextActivity);
            }
        });

        tvTotal3.setText(FunctionHelper.convertRupiah(getTotal()));
        tvTotal4.setText(FunctionHelper.convertRupiah(getTotal3()));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_pendapatan:
                Intent nextActivity = new Intent(this, PendapatanActivity.class);
                startActivity(nextActivity);
                break;
            case R.id.action_pengeluaran:
                Intent nextActivity2 = new Intent(this, PengeluaranActivity.class);
                startActivity(nextActivity2);
                break;

            case R.id.money_converter:
                Intent nextActivity3 = new Intent(this, MoneyCurrencyConverter.class);
                startActivity(nextActivity3);
                break;



        }
        return super.onOptionsItemSelected(item);
    }

    private int getTotal1(){
        int total = 0;
        for (int i = 0; i < tblPendapatanList.size(); i++){
            int nominal = tblPendapatanList.get(i).getNominal();
            total = total + nominal;
        }
        return total;
    }
    private int getTotal2(){
        int total = 0;
        for (int i = 0; i < tblPengeluaranList.size(); i++){
            int nominal = tblPengeluaranList.get(i).getNominal();
            total = total + nominal;
        }
        return total;
    }
    private int getTotal(){
        int total = 0;

        total = getTotal1()-getTotal2();

        return total;
    }
    private int getTotal3(){
        int total = 0;
        for (int i = 0; i < tblDompetList.size(); i++){
            int nominal = tblDompetList.get(i).getNominal();
            total = total + nominal;
        }
        return total;
    }
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private void showNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Navigation channel";
        Intent notifDetailIntent = new Intent(this, DetailActivity.class);
        notifDetailIntent.putExtra(DetailActivity.EXTRA_TITLE, title);
        notifDetailIntent.putExtra(DetailActivity.EXTRA_MESSAGE, message);
        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addParentStack(DetailActivity.class)
                .addNextIntent(notifDetailIntent)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.baseline_email_black_24)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }


    @Override
    public void requestUpdate(long id, String pengingat, int nominal, String date) {
        TblDompet tblDompet = daoSession.getTblDompetDao().load(id);
        tblDompet.setPengingat(pengingat);
        tblDompet.setNominal(nominal);
        tblDompet.setTanggal(date);
        daoSession.getTblDompetDao().update(tblDompet);
    }
    public void restartApp () {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }
}
