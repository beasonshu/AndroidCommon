package tk.beason.common.utils.location;


import tk.beason.common.entries.Location;

/**
 * modify by y.haiyang @2018-6-22
 * 位置信息监听
 */
public interface OnLocationListener {
    /**
     * 获取到了位置信息
     */
    void onReceiveLocation(Location location);
}
