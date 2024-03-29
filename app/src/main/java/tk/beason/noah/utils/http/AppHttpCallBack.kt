package tk.beason.noah.utils.http

import android.content.Context
import com.alibaba.fastjson.JSON
import tk.beason.common.utils.http.rest.HttpError
import tk.beason.common.utils.http.rest.callback.CallBack
import tk.beason.common.utils.http.rest.config.HttpConfig
import tk.beason.common.utils.http.rest.exception.HttpErrorException
import tk.beason.common.utils.http.rest.request.Request
import tk.beason.common.utils.http.rest.response.Response
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by beasontk on 2017/5/2.
 * 对应项目的处理
 */

abstract class AppHttpCallBack<T> protected constructor(context: Context) : CallBack<T>(context) {


    @Throws(HttpErrorException::class)
    override fun parseResponse(request: Request<*>, response: Response): T? {
        val headers = response.headers
        HttpConfig.setHeader(tk.beason.noah.constant.Configure.HTTP_TOKEN, headers.get(tk.beason.noah.constant.Configure.HTTP_TOKEN))

        val genType = javaClass.genericSuperclass
        val type: Type
        if (genType is ParameterizedType) {
            val params = genType.actualTypeArguments
            type = params[0]
        } else {
            type = String::class.java
        }


        val body = String(response.body)
        response.debugString = body

        val result = JSON.parseObject(body, tk.beason.noah.entries.http.AppHttpResult::class.java)
        if (result.isSuccess) {
            val value = result.value ?: return null

            var valueString = value.toString()
            if (!valueString.startsWith("[") && !valueString.startsWith("{")) {
                valueString = JSON.toJSONString(value)
            }
            return JSON.parseObject<T>(valueString, type)
        } else {
            val error = HttpError.makeError(request)
            error.code = result.code
            error.message = result.message
            val e = HttpErrorException()
            e.httpError = error
            throw e
        }
    }

}
