package tk.beason.noah.modules.sample.pulltorefresh;

import android.content.Context;

import java.util.List;

import tk.beason.noah.modules.sample.entries.Job;
import tk.beason.common.mvp.base.IPresenter;
import tk.beason.common.mvp.base.IView;
import tk.beason.common.mvp.base.IViewStatus;

interface SamplePullToRefreshContract {

    interface View extends IView, IViewStatus {
        void updateView(List<Job> list, int count);
    }

    interface Presenter extends IPresenter {
        void getList(Context context, int pageNumber, boolean loadingView);
    }
}