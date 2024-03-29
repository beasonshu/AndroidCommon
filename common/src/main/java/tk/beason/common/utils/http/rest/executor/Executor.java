/*
 * Copyright (C) 2016 The beason Android Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.beason.common.utils.http.rest.executor;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import tk.beason.common.entries.KeyValue;
import tk.beason.common.utils.StringUtils;
import tk.beason.common.utils.http.rest.Headers;
import tk.beason.common.utils.http.rest.HttpError;
import tk.beason.common.utils.http.rest.Method;
import tk.beason.common.utils.http.rest.RequestParams;
import tk.beason.common.utils.http.rest.callback.CallBack;
import tk.beason.common.utils.http.rest.config.HttpConfig;
import tk.beason.common.utils.http.rest.exception.CanceledException;
import tk.beason.common.utils.http.rest.request.Request;
import tk.beason.common.utils.http.rest.response.Response;
import tk.beason.common.utils.log.LogManager;

import java.util.List;
import java.util.Map;


/**
 * Execuor
 */

public abstract class Executor {
    /**
     * 初始化
     */
    public abstract void init(Context context);

    /**
     * 执行
     */
    public abstract <T> void execute(Request request, CallBack<T> callBack);

    /**
     * 移除Cookie
     */
    public abstract void clearCookie(Context context);

    /**
     * Format Url
     */
    protected String formatUrl(Request request) {
        String url = request.getUrl();
        if (TextUtils.isEmpty(url)) {
            throw new IllegalStateException("need a url");
        }

        String paramsString = buildParams(request);
        if (TextUtils.isEmpty(paramsString)) {
            return url;
        }

        if (url.contains("?")) {
            return StringUtils.plusString(url, "&", paramsString);
        } else {
            return StringUtils.plusString(url, "?", paramsString);
        }
    }

    /**
     * parsms to string
     */
    private String buildParams(Request request) {
        List<KeyValue> paramList = request.getParams().getNormalParams();
        if (request.getMethod()==Method.POST){
            return StringUtils.EMPTY;
        }
        return buildParams(request, paramList);
    }


    private String buildDebugParams(@NonNull List<KeyValue> paramList) {
        if (paramList.isEmpty()) {
            return StringUtils.EMPTY;
        }

        StringBuilder builder = new StringBuilder();
        for (KeyValue param : paramList) {
            String key = param.getKey();
            Object value = param.getValue();

            if (value instanceof String) {
                builder.append("&");
                builder.append(key);
                builder.append("=");
                builder.append(String.valueOf(value));
            }
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    private String buildDebugParams(@NonNull Map<String, Object> paramList) {
        StringBuilder builder = new StringBuilder();
        // 先添加通用参数
        for (Map.Entry<String, Object> entry : HttpConfig.getCommonParams().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                builder.append("&");
                builder.append(key);
                builder.append("=");
                builder.append(String.valueOf(value));
            }
        }
        return builder.toString();
    }

    private String buildParams(Request request, @NonNull List<KeyValue> paramList) {
        StringBuilder builder = new StringBuilder();
        // 先添加通用参数
        if (request.isAddCommonParams()) {
            for (Map.Entry<String, Object> entry : HttpConfig.getCommonParams().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    builder.append("&");
                    builder.append(key);
                    builder.append("=");
                    builder.append(String.valueOf(value));
                }
            }
        }

        if (paramList.isEmpty()) {
            return builder.toString();
        }

        for (KeyValue param : paramList) {
            String key = param.getKey();
            Object value = param.getValue();

            if (value instanceof String) {
                builder.append("&");
                builder.append(key);
                builder.append("=");
                builder.append(String.valueOf(value));
            }
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    /**
     * Debug this
     * <p>
     * TODO ：
     * 1. 请求的参数和Mediatype打印
     */
    @SuppressWarnings("WeakerAccess")
    protected void debugRequest(@NonNull final Request request) {
        LogManager.d(request.getLogTag(), StringUtils.plusString("=================== ", request.getMethod(), ":", request.getId(), " ==================="));
        LogManager.d(request.getLogTag(), StringUtils.plusString(request.getId(), " URL     = ", request.getFinalUrl()));

        Headers headers = HttpConfig.getHeaders();
        if (headers.size() > 0) {
            LogManager.d(request.getLogTag(), StringUtils.plusString(request.getId(), " HEADERS_DEFAULT = ", headers.toString()));
        }

        headers = request.getHeaders();
        if (headers != null && headers.size() > 0) {
            LogManager.d(request.getLogTag(), StringUtils.plusString(request.getId(), " HEADERS = ", headers.toString()));
        }

        LogManager.d(request.getLogTag(), StringUtils.plusString(request.getId(), " TIMEOUT = ", request.getConnTimeOut(true)));

        RequestParams params = request.getParams();

        String normalParsms = buildDebugParams(params.getNormalParams());
        if (!TextUtils.isEmpty(normalParsms)) {
            LogManager.d(request.getLogTag(), StringUtils.plusString(request.getId(), " PARAMS = ", normalParsms));
        }

        String commonParams = buildDebugParams(HttpConfig.getCommonParams());
        if (!TextUtils.isEmpty(commonParams) && request.isAddCommonParams()) {
            LogManager.d(request.getLogTag(), StringUtils.plusString(request.getId(), " PARAMS COMMON = ", commonParams));
        }

        Object body = params.getBody();
        if (body instanceof String) {
            LogManager.d(request.getLogTag(), StringUtils.plusString(request.getId(), " PARAMS = ", body.toString()));
        }
    }


    /**
     * 请求是否已经被取消掉
     */
    @SuppressWarnings("WeakerAccess")
    protected boolean isCanceled(@NonNull Request request, @NonNull Response response, @NonNull CallBack callBack) {
        boolean canceled = response.isCanceled();
        if (canceled) {
            sendCanceledResult(request, callBack);
        }
        return canceled;
    }

    @SuppressWarnings("WeakerAccess")
    protected void sendCanceledResult(@NonNull Request request, CallBack callBack) {
        if (callBack == null) {
            return;
        }
        HttpError error = HttpError.makeError(request);
        error.setCode(HttpError.ERROR_CANCELED);
        error.setMessage("call is canceled");
        error.setException(new CanceledException());
        callBack.runOnUiThreadFailed(error);
    }


    protected void sendFailed(CallBack callBack, HttpError error) {
        if (callBack == null) {
            return;
        }

        callBack.runOnUiThreadFailed(error);
    }

    /**
     * 请求是否成功
     */
    @SuppressWarnings("WeakerAccess")
    protected boolean isSuccessful(@NonNull Request request, @NonNull Response response, @NonNull CallBack callBack) {
        boolean successful = response.isSuccessful();
        if (!successful) {
            sendResponseCodeErrorResult(request, response, callBack);
        }
        return successful;

    }

    @SuppressWarnings("WeakerAccess")
    protected void sendResponseCodeErrorResult(@NonNull Request request, @NonNull Response response, CallBack callBack) {
        if (callBack == null) {
            return;
        }
        HttpError error = HttpError.makeError(request);
        error.setCode(response.getCode());
        error.setMessage("request failed , reponse's code is :" + response.getCode());
        error.setException(new IllegalStateException());
        callBack.runOnUiThreadFailed(error);
    }

    /**
     * 取消请求
     */
    public abstract void cancel(@NonNull Object tag);
}
