package org.eclipse.paho.android

import android.content.Context
import kotlin.Throws
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import android.util.Log
import java.util.*

internal class TestProperties(private val context: Context) {

    private val properties = Properties()

    @Throws(IOException::class)
    private fun getPropertyFileAsStream(fileName: String): InputStream? {
        var stream: InputStream? = null
        try {
            stream = context.resources.assets.open(fileName)
        } catch (exception: Exception) {
            Log.e("TestProperties", "Property file: '$fileName' not found")
        }
        return stream
    }

    private fun getProperty(key: String): String {
        return properties.getProperty(key)
    }

    fun getBooleanProperty(key: String): Boolean {
        val value = getProperty(key)
        return java.lang.Boolean.parseBoolean(value)
    }

    private fun getIntProperty(key: String): Int {
        val value = getProperty(key)
        return value.toInt()
    }

    val clientKeyStore: String
        get() = getProperty(KEY_CLIENT_KEY_STORE)

    val clientKeyStorePassword: String
        get() = getProperty(KEY_CLIENT_KEY_STORE_PASSWORD)

    val serverSSLURI: String
        get() = getProperty(KEY_SERVER_SSL_URI)

    val serverURI: String
        get() = getProperty(KEY_SERVER_URI)
    val waitForCompletionTime: Int
        get() = getIntProperty(KEY_WAIT_FOR_COMPLETION_TIME)

    /**
     * Reads properties from a properties file
     */
    init {
        var stream: InputStream? = null
        try {
            val filename = "test.properties"
            stream = getPropertyFileAsStream(filename)

            // Read the properties from the property file
            if (stream != null) {
                Log.i("TestProperties", "Loading properties from: '$filename'")
                properties.load(stream)
            }
        } catch (e: Exception) {
            Log.e("TestProperties", "caught exception:", e)
        } finally {
            if (stream != null) {
                try {
                    stream!!.close()
                } catch (e: IOException) {
                    Log.e("TestProperties", "caught exception:", e)
                }
            }
        }
    }

    companion object {
        private val KEY_SERVER_URI = "SERVER_URI"
        private val KEY_CLIENT_KEY_STORE = "CLIENT_KEY_STORE"
        private val KEY_CLIENT_KEY_STORE_PASSWORD = "CLIENT_KEY_STORE_PASSWORD"
        private val KEY_SERVER_SSL_URI = "SERVER_SSL_URI"
        private val KEY_WAIT_FOR_COMPLETION_TIME = "WAIT_FOR_COMPLETION_TIME"
    }
}