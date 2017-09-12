package com.example.reduxsample.data;

import android.app.Application;


import com.example.reduxsample.data.api.DebugApiModule;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.inject.Named;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import timber.log.Timber;

@Module(includes = {DataModule.class, DebugApiModule.class})
public final class DebugDataModule {

}
