package com.example.slagalica.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.slagalica.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SkockoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkockoFragment extends Fragment {

    public static SkockoFragment newInstance(String someParam){
        Bundle args = new Bundle();
        args.putString("key","test");

        SkockoFragment fragment = new SkockoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skocko, container, false);

        Bundle bundle = getArguments();
        /*if (bundle != null){
            String param = bundle.getString("SOME_PARAM_KEY", "Some random name");
            TextView textView = view.findViewById(R.id.name_content);
            textView.setText(param);
        }
        TextView textView = view.findViewById(R.id.relativeTitle);
        textView.setText(R.string.relativelayout);
        */
        TextView btnDalje = view.findViewById(R.id.red_player);
        btnDalje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KorakPoKorakFragment korakPoKorakFragment = KorakPoKorakFragment.newInstance("test");
                getParentFragmentManager().beginTransaction().replace(R.id.activityMainLayout,korakPoKorakFragment).commit();
            }
        });

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}