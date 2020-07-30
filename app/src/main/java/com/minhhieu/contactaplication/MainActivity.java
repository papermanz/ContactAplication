package com.minhhieu.contactaplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.minhhieu.contactaplication.adapter.ContactAdapter;
import com.minhhieu.contactaplication.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Contact> arrayContact;
    private ContactAdapter adapter;
    private EditText edtName;
    private EditText edtNumber;
    private RadioButton rdMale;
    private RadioButton rdFemale;
    private Button btnAddContact;
    private ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidget();
        arrayContact = new ArrayList<>();
        adapter = new ContactAdapter(this,R.layout.item_contact_listview,arrayContact);
        lvContact.setAdapter(adapter);
        checkAndRequestPermissions();
        //bắt sự kiện khi click vào item listview hiện dialog
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                show_Dialog(position);
            }
        });

    }
    // xin quyền mở cuộc gọi và nhắn tin
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            //kiểm tra xem ứng dụng đã cho phép quyền truy cập chưa
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
    public void setWidget(){
        edtName = (EditText) findViewById(R.id.edt_name);
        edtNumber = (EditText) findViewById(R.id.edt_number);
        rdMale = (RadioButton) findViewById(R.id.rd_male);
        rdFemale = (RadioButton) findViewById(R.id.rd_female);
        btnAddContact = (Button) findViewById(R.id.btn_addcontact);
        lvContact = (ListView) findViewById(R.id.lv_contact);
    }
    //bắt sự kiện khi nhấn button add
    public void addContact(View view){
        if(view.getId()==R.id.btn_addcontact){
            String name = edtName.getText().toString().trim();
            String number = edtNumber.getText().toString().trim();
            Boolean isMale = true;
            if(rdMale.isChecked()){
                isMale = true;
            }else{
                isMale = false;
            }
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(number)){
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }else{
                Contact contact = new Contact(isMale,name,number);
                arrayContact.add(contact);
                Toast.makeText(this, "Thêm danh bạ thành công", Toast.LENGTH_SHORT).show();
                edtName.setText("");
                edtNumber.setText("");
            }
            //cập nhật lại adapter
            adapter.notifyDataSetChanged();
        }

    }

    //đặt tên nên có dấu _ ngăn cách

    public void show_Dialog(final int position){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        Button btncall = (Button) dialog.findViewById(R.id.btn_call);
        Button btnsendmessage = (Button)dialog.findViewById(R.id.btn_send_message);

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentCall(position);
            }
        });

        btnsendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSend(position);
            }
        });
        dialog.show();

    }

    private void intentSend(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("sms:"+arrayContact.get(position).getmNumber()));//gọi đến màn hình messages
        startActivity(intent);//thực hiện hành động khai báo
    }

    private void intentCall(int position) {
        Intent intent = new Intent();//gọi hành động trao đổi giữa các activity..
        intent.setAction(Intent.ACTION_CALL); // chuyển đến màn hình gọi điện của hệ thống
        intent.setData(Uri.parse("tel:"+arrayContact.get(position).getmNumber()));
        startActivity(intent);//thực hiện hành động khai báo
    }


}