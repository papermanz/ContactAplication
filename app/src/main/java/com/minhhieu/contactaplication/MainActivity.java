package com.minhhieu.contactaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
            }
            //cập nhật lại adapter
            adapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "Thêm danh bạ thành công", Toast.LENGTH_SHORT).show();
        edtName.setText("");
        edtNumber.setText("");
    }
}