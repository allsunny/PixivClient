package com.example.zero.pixivclient.Jsoup;


import android.util.Log;

import com.example.zero.pixivclient.model.ImageInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class JsoupTool {
    private static JsoupTool instance = null;
    private static final String URL = "http://www.pixiv.net";

    private JsoupTool() {
        trustEveryone();
    }

    public static JsoupTool getInstance() {    //单例
        if (instance == null) {
            synchronized (JsoupTool.class) {
                instance = new JsoupTool();
            }
        }

        return instance;
    }


    //ssl安全认证
    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ImageInfo> getAllImages(String pageUrl) {
        try {
            Document doc = Jsoup.connect(pageUrl)
                    .timeout(10000)
                    .post();

            Elements urls = doc.getElementsByAttributeValue("class", "title ")
                            .select("a[href]");
         //   Elements urls = doc.select("img[src$=.jpg]");

            List<ImageInfo> imgList = new ArrayList<ImageInfo>();
            ImageInfo imageInfo;
            for (Element url : urls) {
                imageInfo = new ImageInfo();

                imageInfo.setImageTitle(url.attr("a"));
                imageInfo.setImageUrl(getImageUrl(url.attr("href")));
                imgList.add(imageInfo);
            }

            return imgList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getImageUrl(String url) throws IOException {
        Log.e("url",URL + url);
        Document doc = Jsoup.connect(URL + url)
                .timeout(10000)
                .post();

        String m = url.split("&uarea")[0].split("&")[1];
//        Log.e("mmm",m);


        String s = doc.getElementsByAttributeValueContaining("href",m).toString();
//        Log.e("s",s);

        String mode = doc.getElementsByAttributeValueContaining("onclick","mode=big")
                .select("img[src$=.jpg]").attr("src");
        return  mode;
    }

}
