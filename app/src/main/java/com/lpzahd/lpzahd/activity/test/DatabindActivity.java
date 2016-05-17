package com.lpzahd.lpzahd.activity.test;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lpzahd.lpzahd.R;
import com.lpzahd.lpzahd.databinding.ActivityDatabindBinding;

public class DatabindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_databind);

        ActivityDatabindBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_databind);
        User user = new User("Test", "User");
        binding.setUser(user);

        user = new User("-,-", "}}");
        binding.setUser(user);

        Person person = new Person();
        person.age = 2 + "";
        person.name = "ooo Lpzahd";
        binding.setPerson(person);
    }

}
