package tk.beason.noah.modules.main.tab2;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.beason.noah.R;
import tk.beason.noah.modules.base.AppBaseFragment;
import tk.beason.common.widget.StatusView;

import tk.beason.noah.modules.base.AppBaseFragment;

/**
 * Created by yuhaiyang on 2017/4/21.
 * Home Fragment
 */

public class Tab2Fragment extends AppBaseFragment {

    private View mRootView;

    public static Tab2Fragment newInstance() {

        Bundle args = new Bundle();

        Tab2Fragment fragment = new Tab2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        }

        mRootView = inflater.inflate(R.layout.fragement_tab_2, container, false);

        StatusView statusView = (StatusView) mRootView.findViewById(R.id.status_view);
        statusView.showLoading();
        return mRootView;
    }
}
