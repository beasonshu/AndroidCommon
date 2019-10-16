package tk.beason.common.utils.http.rest.okhttp.cookie;

import android.content.Context;

import tk.beason.common.utils.http.rest.okhttp.cookie.store.OkFileCookieStore;
import tk.beason.common.utils.http.rest.okhttp.cookie.store.IOkCookieStore;
import tk.beason.common.utils.http.rest.okhttp.cookie.store.OkMemoryCookieStore;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 */
public class OkCookiesManager implements CookieJar {
    private IOkCookieStore mCookieStore;

    public OkCookiesManager(Context context, @tk.beason.common.utils.http.rest.Cookie.Type int type) {
        switch (type) {
            case tk.beason.common.utils.http.rest.Cookie.Type.MEMORY:
                mCookieStore = new OkMemoryCookieStore();
                break;
            case tk.beason.common.utils.http.rest.Cookie.Type.FILE:
                mCookieStore = new OkFileCookieStore(context);
                break;
            case tk.beason.common.utils.http.rest.Cookie.Type.NONE:
                break;
        }
    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (mCookieStore != null) {
            mCookieStore.add(url, cookies);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if (mCookieStore != null) {
            return mCookieStore.get(url);
        } else {
            return new ArrayList<>();
        }
    }

    public void clearCookie() {
        if (mCookieStore != null) {
            mCookieStore.removeAll();
        }
    }
}