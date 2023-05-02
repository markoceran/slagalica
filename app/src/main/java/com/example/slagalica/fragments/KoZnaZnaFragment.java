package com.example.slagalica.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.slagalica.R;

public class KoZnaZnaFragment extends Fragment {

    public static KoZnaZnaFragment newInstance(String someParam){
        Bundle args = new Bundle();
        args.putString("key","test");

        KoZnaZnaFragment fragment = new KoZnaZnaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ko_zna_zna, container, false);

        Bundle bundle = getArguments();
        /*if (bundle != null){
            String param = bundle.getString("SOME_PARAM_KEY", "Some random name");
            TextView textView = view.findViewById(R.id.name_content);
            textView.setText(param);
        }
        TextView textView = view.findViewById(R.id.relativeTitle);
        textView.setText(R.string.relativelayout);
        */

        TextView btnDalje = view.findViewById(R.id.dalje);
        btnDalje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpojniceFragment spojniceFragment = SpojniceFragment.newInstance("test");
                getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout,spojniceFragment).commit();
            }
        });

        return view;
    }
}
