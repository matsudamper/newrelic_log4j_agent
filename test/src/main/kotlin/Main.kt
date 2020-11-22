import com.newrelic.api.agent.NewRelic

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("noticeError start")

            NewRelic.noticeError("notice")
            NewRelic.noticeError(IllegalStateException("notice"))
            NewRelic.setRequestAndResponse(null,null)
            println("metricName -> ${NewRelic.getAgent().tracedMethod.metricName}")

            println("noticeError finish")
        }
    }
}