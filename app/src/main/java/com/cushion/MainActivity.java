package com.cushion;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends Activity {

    private static String MAP_URL = "file:///android_asset/login.html";
//    private static final String MAP_URL = "http://192.168.58.100:8080/CushionProject/login.html";
//    private static final String MAP_URL = "http://192.168.58.100:8080/CushionProject/loginListServlet";
    
    public static  String uID="";
  
    
    private WebView webView;

    @Override
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setTitle("查看地址:");
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);



        Intent intent=this.getIntent();
        String webHtml=intent.getStringExtra("webHtml");
        System.out.println("webHtml="+webHtml);
        if(webHtml!=null && !webHtml.equals(""))
        	MAP_URL="file:///android_asset/"+webHtml;

        setupWebView();												
//      this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    /** Sets up the WebView object and loads the URL of the page **/
    private void setupWebView() {

        webView = (WebView) findViewById(R.id.webview);
        
        //保持webView设置的编码与html设置编码一致
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        
        /**
         * Allows JavaScript calls to access application resources
         * 把JavaScriptInterface()映射为android 这样在js中通过
         * window.android.getLatitude()获取值
         **/
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        
        
        //支持浏览器单窗口打开
        webView.setWebViewClient(new HelloWebViewClient());
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 设置优先使用缓存防止浪费流量

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        
         
       
       
        webView.loadUrl(MAP_URL);
       
       
        
       
        
        //16以后版本
        //webView.addJavascriptInterface(this, "android");
       
        //16以前版本
        //webView.addJavascriptInterface(new JavaScriptInterface(), "android");

        
        webView.addJavascriptInterface(new JsObject(),"jsObject");
    }

    public class JsObject {
        @JavascriptInterface
        public void getMessage0(String userID) {
            System.out.println("获取uID...."+userID);
            Intent intent=new Intent(MainActivity.this,ContentActivity.class);
            startActivity(intent);
        }
    }


    private class HelloWebViewClient extends WebViewClient{

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

    }
    
    
    
    
    /** 
     * 继承WebChromeClient类 
     * 对js弹出框时间进行处理 
     * 
     */  
    final class MyWebChromeClient extends WebChromeClient {  
        /** 
         * 处理alert弹出框 
         */  
        @Override  
        public boolean onJsAlert(WebView view,String url,  
                                 String message,JsResult result) {  

            //textView.setText("Alert:"+message);  
            //对alert的简单封装  
            new AlertDialog.Builder(MainActivity.this).  
                    setTitle("Alert").setMessage(message).setPositiveButton("OK",  
                    new DialogInterface.OnClickListener() {  
                        @Override  
                        public void onClick(DialogInterface arg0, int arg1) {  
                            //TODO  
                        }  
                    }).create().show();  
            result.confirm();  
            return true;  
        }  

        /** 
         * 处理confirm弹出框 
         */  
        @Override  
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {  
            //textView.setText("Confirm:"+message);  

           //下面几行是加进去的,目的就是把confirm变化成alert,且去掉英文page  
           //当然,由于是一个Dialog,你可以将其改变成你需要的Dialog即可,这里就不详细谈了  
            new AlertDialog.Builder(MainActivity.this).  
                    setTitle("Alert").setMessage(message).setPositiveButton("OK",  
                    new DialogInterface.OnClickListener() {  
                        @Override  
                        public void onClick(DialogInterface arg0, int arg1) {  
                            //TODO  
                        }  
                    }).create().show();  
            result.confirm();//congfirm是必须的,因为要确认  
            //下面注释的两句是原本confirm就需要的  
            //result.confirm();  
           // return super.onJsConfirm(view, url, message, result);  
            return true;  
        }  
         
        @Override  
        public boolean onJsPrompt(WebView view, String url, String message,String defaultValue, JsPromptResult result) {  
            //textView.setText("Prompt input is :"+message);  
            result.confirm();  
            return super.onJsPrompt(view, url, message, message, result);  
        }  
    }  
    


}


