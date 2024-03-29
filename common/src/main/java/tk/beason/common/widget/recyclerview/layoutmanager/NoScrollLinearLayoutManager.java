package tk.beason.common.widget.recyclerview.layoutmanager;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by beasontk on 2018/9/12.
 * 禁止滚动的LinearLayoutManager
 */
public class NoScrollLinearLayoutManager extends LinearLayoutManager{
    public NoScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public NoScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public NoScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }
}
