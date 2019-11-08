package tk.beason.noah.modules.sample.main;

import tk.beason.noah.R;
import tk.beason.noah.modules.sample.SampleStatusViewActivity;
import tk.beason.noah.modules.sample.dialog.normal.SampleBaseDialogActivity;

import tk.beason.noah.modules.sample.imageloader.SampleImageLoaderActivity;
import tk.beason.noah.modules.sample.permission.SamplePermissionActivity;
import tk.beason.noah.modules.sample.pickview.SamplePickerActivity;
import tk.beason.noah.modules.sample.pulltorefresh.SamplePullToRefreshActivity;
import tk.beason.noah.modules.sample.recycle.animation.SampleAnimationRecycleViewActivity;
import tk.beason.noah.modules.sample.webview.loading.SampleLoadingWebViewActivity;
import tk.beason.noah.modules.sample.dialog.select.SampleSelectDialogAndPickerDialog;
import tk.beason.noah.modules.sample.edittextpro.SampleEditTextProActivity;
import tk.beason.noah.modules.sample.entries.Sample;
import tk.beason.noah.modules.sample.http.SampleHttpActivity;
import tk.beason.noah.modules.sample.photo.select.SampleSelectPhotoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhaiyang on 2017/10/12.
 * 管理器
 */

public class SampleManager {


    public static List<Sample> getSampleCate() {
        List<Sample> list = new ArrayList<>();
        list.add(new Sample(R.string.sample_widget_list, SampleMainActivity.class));
        return list;
    }

    public static List<Sample> getSamples() {
        return getSamples(Sample.Cate.Widget);
    }


    @SuppressWarnings("WeakerAccess")
    public static List<Sample> getSamples(@Sample.Cate int cate) {
        switch (cate) {
            case Sample.Cate.Widget:
                return getWidgetSamples();
            default:
                return new ArrayList<>();
        }
    }

    private static List<Sample> getWidgetSamples() {
        List<Sample> list = new ArrayList<>();
        list.add(Sample.newInstance(R.string.sample_pick_view, SamplePickerActivity.class));
        list.add(Sample.newInstance(R.string.sample_select_photo, SampleSelectPhotoActivity.class));
        list.add(Sample.newInstance(R.string.sample_permission, SamplePermissionActivity.class));
        list.add(Sample.newInstance(R.string.sample_edittextpro, SampleEditTextProActivity.class));
        list.add(Sample.newInstance(R.string.sample_http, SampleHttpActivity.class));
        list.add(Sample.newInstance(R.string.sample_imageloader, SampleImageLoaderActivity.class));
        list.add(Sample.newInstance(R.string.sample_ani_recycle, SampleAnimationRecycleViewActivity.class));
        list.add(Sample.newInstance(R.string.sample_picker_and_select, SampleSelectDialogAndPickerDialog.class));
        list.add(Sample.newInstance(R.string.sample_loading_webview, SampleLoadingWebViewActivity.class));
        list.add(Sample.newInstance(R.string.sample_pull_to_refresh, SamplePullToRefreshActivity.class));
        list.add(Sample.newInstance(R.string.sample_base_dialog, SampleBaseDialogActivity.class));
        list.add(Sample.newInstance(R.string.sample_status_view, SampleStatusViewActivity.class));
        return list;
    }
}
