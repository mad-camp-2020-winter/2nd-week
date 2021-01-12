package com.example.rentalservice.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.rentalservice.R;
import com.example.rentalservice.activity.UserMainActivity;

public class UserRentalInfoFragment_0 extends Fragment {



    public UserRentalInfoFragment_0() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_fragment_rental_info_0, container, false);

        EditText search_input = v.findViewById(R.id.user_rental_search_bar);
        Button apply_btn = v.findViewById(R.id.user_rental_apply_button);

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(apply_btn.getWindowToken(), 0);
                String input_number = search_input.getText().toString();
                if(input_number.length() == 0){
                    Toast.makeText(getContext(), "전화번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    UserMainActivity usermainActivity = (UserMainActivity) getActivity();
                    usermainActivity.onFragmentChange(1,input_number);
                }
            }
        });


        return v;
    }
}