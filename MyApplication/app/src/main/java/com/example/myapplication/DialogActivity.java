package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dialog);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void showAD(View view) {

        new AlertDialog.Builder(this)
                .setTitle("删除数据")
                .setMessage("你确定删除吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"删除数据！",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"取消删除！",Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void showLD(View view) {
        final String[] items={"红","蓝"};
        new AlertDialog.Builder(this)
                .setTitle("指定背景颜色")
                .setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,items[which],Toast.LENGTH_SHORT).show();;
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void showCD(View view) {
        //动态加载view对象
        View view1=View.inflate(this,R.layout.dialoglayout,null);
        //获得一个动态view的子view对象的组件
        EditText nameET=view1.findViewById(R.id.edit_dialog_user);
        EditText pwdET=view1.findViewById(R.id.edit_dialog_password);
        new AlertDialog.Builder(this)
                .setView(view1)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name=nameET.getText().toString();
                        String password=pwdET.getText().toString();

                        Toast.makeText(DialogActivity.this,"欢迎"+name+"！！！",Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
//显示圆形进度条progressDialog
    public void showDD(View view) {
        ProgressDialog dialog=ProgressDialog.show(this,"数据加载","数据加载中......");
        //调分线程执行睡眠
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //必须在分线程中执行，不然主线程会直接执行而导致不显示dialog
                dialog.dismiss();
                //ui更新必须在主线程执行，所以这里调用runOnThread接口实现run方法，此时的run方法是在主线程执行。
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      Toast.makeText(DialogActivity.this,"执行完成",Toast.LENGTH_SHORT).show();;
                                  }
                              }
                );
            }
        }.start();

    }
//显示水平进度条
    public void showED(View view) {
        ProgressDialog pd=new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 100;
                pd.setMax(count);
                for (int i = 0; i <= count; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    pd.setProgress(pd.getProgress() + 1);
                }
                pd.dismiss();
            }
        }).start();

    }

    public void showDate(View view) {
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int monthOfYear=calendar.get(Calendar.MONTH);
        final int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);

        Log.e("TAG",year+"-"+monthOfYear+"-"+dayOfMonth);

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.e("TAG",year+"--"+monthOfYear+"--"+dayOfMonth);
            }
        },year,monthOfYear,dayOfMonth).show();
    }

    public void showTime(View view) {
        Calendar calendar=Calendar.getInstance();
        int hourOfDay=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);

        Log.e("TAG",hourOfDay+":"+minute+":"+second);

        new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.e("TAG",hourOfDay+":"+minute);
            }
        },hourOfDay,minute,true).show();
    }
}