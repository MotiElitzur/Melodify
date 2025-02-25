package melodify.core.domain

object Logger {
    
    fun log(message: String) {
        println(message)
    }

    fun d(s: String, s1: String) {
        log("$s: $s1")
    }
}