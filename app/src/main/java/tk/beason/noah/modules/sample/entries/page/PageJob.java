package tk.beason.noah.modules.sample.entries.page;


import tk.beason.noah.modules.sample.entries.Job;

import java.util.ArrayList;
import java.util.List;

import tk.beason.noah.modules.sample.entries.Job;

/**
 * Created by beasontk on 2017/9/19.
 * 分页
 */

public class PageJob {
    public int totalRows;
    public List<Job> results;

    public List<Job> getResults() {
        if (results == null) {
            return new ArrayList<>();
        }
        return results;
    }

    public int getTotalRows() {
        return totalRows;
    }
}
