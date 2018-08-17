package kotlinc.main.example

import java.util.*

/**
 * Hello world in kotlin
 * <p/>
 * Created by Billin on 2017/5/20.
 */
fun main(args: Array<String>) {
    // 控制台功能选择器
    Selector("Welcome to kotlin, choose fun according to below number", true) show {

        item("计算 2^30") {
            // 计算 2^30
            val a = 2
            println(a.pow(30))
        }

        show {
            item("从 0 打印到字符串包含的字符数") {
                // 从 0 打印到字符串包含的字符数
                "world".charCount { (0..it).forEach({ print(it) }) }
                println()
            }
        }

        item("jump to second") {
            Selector("second catalog", true) show {
                item("print hello") {
                    println("hello")
                }

                item("print hello world") {
                    println("hello world")
                }
            }
        }
    }
}

fun Int.pow(other: Int): Int = if (other == 1) this else this * this.pow(other - 1)

fun String.charCount(action: (charNumber: Int) -> Unit): Unit {
    action(length)
}

class Selector(val welcome: String = "hello", val isLoop: Boolean = false) {

    private var stop = false
    private var addItemIndex = 1

    private val chooseTips = mutableListOf<String>()
    private val chooseListener = mutableListOf<(() -> Unit)>()

    private fun addChooseListener(index: Int, msg: String, l: () -> Unit) {
        chooseTips.add("$index: $msg")
        chooseListener.add(l)
    }

    fun item(msg: String, l: () -> Unit) {
        addChooseListener(addItemIndex++, msg, l)
    }

    private fun show(): Unit {
        stop = !isLoop

        println(welcome)

        val scanner = Scanner(System.`in`)

        do {
            (0..0).forEach({ println() })
            println("-------------------------------------")
            if (!stop) println("0: exit this operation")
            chooseTips.forEach({ println(it) })

            var num = -1
            try {
                num = scanner.nextInt()
            } catch (e: InputMismatchException) {
                scanner.nextLine()
            }

            if (num == 0) {
                if (!stop) {
                    println("exit")
                    stop = true
                } else {
                    println("invalid input")
                }
            } else if (num in 1..chooseTips.size) {
                chooseListener[num - 1]()
            } else {
                println("Invalid input")
            }
        } while (!stop)
    }

    infix fun show(block: Selector.() -> Unit) {
        invoke(block)
    }

    private operator fun invoke(block: Selector.() -> Unit) {
        create(welcome, isLoop, block)
    }

    private companion object {
        fun create(welcome: String, isLoop: Boolean, block: Selector.() -> Unit) {
            val selector = Selector(welcome, isLoop)
            block(selector)
            selector.show()
        }
    }
}