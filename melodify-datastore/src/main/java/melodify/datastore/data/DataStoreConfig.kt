package melodify.datastore.data

object DataStoreConfig {
    private var config = Config()

    data class Config(
        val enableCaching: Boolean = true,
        val cacheSize: Int = 10
    )

    fun initialize(builder: Config.() -> Unit) {
        config = Config().apply(builder)
    }

    internal fun getConfig() = config
}