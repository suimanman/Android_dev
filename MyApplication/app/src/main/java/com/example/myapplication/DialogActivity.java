package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
}