package com.example.myapplicationf.presentation.adapter
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
class NoSSLVerificationSocketFactory : SSLSocketFactory() {

    private val trustAllCerts: Array<TrustManager> = arrayOf(
        object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
        }
    )

    override fun getDefaultCipherSuites(): Array<String> = arrayOf()

    override fun getSupportedCipherSuites(): Array<String> = arrayOf()

    override fun createSocket(socket: java.net.Socket?, host: String?, port: Int, autoClose: Boolean): java.net.Socket {
        return sslContext.socketFactory.createSocket(socket, host, port, autoClose)
    }

    override fun createSocket(host: String?, port: Int): java.net.Socket {
        return sslContext.socketFactory.createSocket(host, port)
    }

    override fun createSocket(host: String?, port: Int, localHost: java.net.InetAddress?, localPort: Int): java.net.Socket {
        return sslContext.socketFactory.createSocket(host, port, localHost, localPort)
    }

    override fun createSocket(host: java.net.InetAddress?, port: Int): java.net.Socket {
        return sslContext.socketFactory.createSocket(host, port)
    }

    override fun createSocket(address: java.net.InetAddress?, port: Int, localAddress: java.net.InetAddress?, localPort: Int): java.net.Socket {
        return sslContext.socketFactory.createSocket(address, port, localAddress, localPort)
    }

    private val sslContext: SSLContext by lazy {
        SSLContext.getInstance("TLS").apply {
            init(null, trustAllCerts, java.security.SecureRandom())
        }
    }
}