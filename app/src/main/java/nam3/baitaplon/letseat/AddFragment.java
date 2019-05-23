package nam3.baitaplon.letseat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Tạo giao diện Favorite
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        Log.d("myLog", "Add fragment!");
        return view;
    }
}