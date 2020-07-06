package com.greycart.greycart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Appointment_fragment extends Fragment {
    Button latest,previous;
    FrameLayout frameLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.appointment_fragment, container,
                false);
        latest=(Button)view.findViewById(R.id.latest);
        previous=(Button)view.findViewById(R.id.previous);
        frameLayout=(FrameLayout)view.findViewById(R.id.frame_base2);
        //latest.setPressed(true);
        getChildFragmentManager().beginTransaction().replace(R.id.frame_base2,new latest_Fragment())
                .commit();
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    latest.setPressed(false);

              //  previous.setPressed(true);
                getChildFragmentManager().beginTransaction().replace(R.id.frame_base2,new previous_Fragment())
                        .commit();
            }
        });
        latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // previous.setPressed(false);
                //latest.setPressed(true);
                getChildFragmentManager().beginTransaction().replace(R.id.frame_base2,new latest_Fragment())
                        .commit();
            }
        });
        return view;
    }
    void onclicklatest()
    {
        latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //previous.setPressed(false);
                //latest.setPressed(true);
               // Toast.makeText(getActivity(),"i am clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
