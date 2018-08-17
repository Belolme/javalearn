package javax.main.io.net.okhttp

import okhttp3.*
import java.io.IOException

private var mClient = OkHttpClient()

/**
 * 这个测试样例是测试 OkHttp cancel 语句是否可行的
 * 测试结果显示 cancel 确实是可行的，如果需要自己对 callback 封装多一层操作，那么
 * 在调用自己封装的 callback 操作时需要在每个不同的线程中判断一下 call 是否执行了 cancel，
 * 如果是那么应当执行封装 callback 的 onFail 方法。
 */
private fun cancelText() {
    val request = Request.Builder()
            .url("http://square.github.io/okhttp/")
            .build()

    println("create call")
    val call = mClient.newCall(request)
    call.enqueue(object : Callback {
        override fun onFailure(p0: Call?, p1: IOException) {
            println("onFailure " + p1.message)
        }

        override fun onResponse(p0: Call?, p1: Response?) {
            println("onResponse")
        }
    })
    println("enqueue call")

    Thread.sleep(300L)
    call.cancel()
    println("canceled call")
}


fun main(args: Array<String>) {
    cancelText()
}