/*
 * Copyright (C) 2016 The beasontk Android Source Project
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

package tk.beason.common.utils.http.rest;

/**
 * Created by bright.yu on 2017/2/28.
 * Request mediatype
 */

public class MediaType {
    /**
     * JSON 格式
     */
    public static final MediaType JSON = new MediaType("application/json;charset=UTF-8");
    /**
     * Stream 格式
     */
    public static final MediaType STREAM = new MediaType("application/octet-stream");
    /**
     * content
     */
    private String body;

    @SuppressWarnings("WeakerAccess")
    public MediaType(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    @SuppressWarnings("unused")
    public void setBody(String body) {
        this.body = body;
    }
}
