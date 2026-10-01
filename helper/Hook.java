package com.example.domainpatch;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.json.JSONObject;

public class Hook {
    static final String TG = "https://t.me/darknes_lord";
    static final String HTML = "PCFET0NUWVBFIGh0bWw+CjxodG1sIGxhbmc9InRyIj4KPGhlYWQ+CjxtZXRhIGNoYXJzZXQ9InV0Zi04Ij4KPG1ldGEgbmFtZT0idmlld3BvcnQiIGNvbnRlbnQ9IndpZHRoPWRldmljZS13aWR0aCwgaW5pdGlhbC1zY2FsZT0xIj4KPHRpdGxlPkRvbWFpbiBBeWFyxLE8L3RpdGxlPgo8c3R5bGU+CiAgOnJvb3R7LS1iZzojMTYyMjJjOy0tcGFuZWw6IzFlMmUzYjstLWluazojZWFmMWY0Oy0tbXV0ZTojOGZhNWIzOy0tbGluZTojMzI0OTVhOy0tYWNjZW50OiMyZmQwYjU7LS10ZzojMmFhM2UwOy0tZXJyOiNmZjdhN2E7Y29sb3Itc2NoZW1lOmRhcmt9CiAgQG1lZGlhIChwcmVmZXJzLWNvbG9yLXNjaGVtZTpsaWdodCl7OnJvb3R7LS1iZzojZWVmM2Y1Oy0tcGFuZWw6I2ZmZjstLWluazojMTQyMzJlOy0tbXV0ZTojNWQ3Njg2Oy0tbGluZTojY2RkYWUxOy0tYWNjZW50OiMwYzlhODU7LS10ZzojMWI4NmJkOy0tZXJyOiNjNjNhM2E7Y29sb3Itc2NoZW1lOmxpZ2h0fX0KICAqe2JveC1zaXppbmc6Ym9yZGVyLWJveH0KICBodG1sLGJvZHl7bWFyZ2luOjA7YmFja2dyb3VuZDp2YXIoLS1iZyk7Y29sb3I6dmFyKC0taW5rKTtmb250OjE2cHgvMS40NSBzeXN0ZW0tdWksc2Fucy1zZXJpZn0KICBtYWlue21heC13aWR0aDo0MjBweDttYXJnaW46MCBhdXRvO3BhZGRpbmc6MjBweCAxNnB4IDI4cHh9CiAgaDF7Zm9udC1zaXplOjEuMnJlbTttYXJnaW46MCAwIDRweH0KICBwLnN1YnttYXJnaW46MCAwIDE4cHg7Y29sb3I6dmFyKC0tbXV0ZSk7Zm9udC1zaXplOi45cmVtfQogIC5ib3h7YmFja2dyb3VuZDp2YXIoLS1wYW5lbCk7Ym9yZGVyOjFweCBzb2xpZCB2YXIoLS1saW5lKTtib3JkZXItcmFkaXVzOjEwcHg7cGFkZGluZzoxNHB4fQogIGxhYmVse2Rpc3BsYXk6YmxvY2s7Zm9udC1zaXplOi44NXJlbTtjb2xvcjp2YXIoLS1tdXRlKTttYXJnaW4tYm90dG9tOjZweH0KICAuY3Vye2ZvbnQ6Ljg1cmVtIHVpLW1vbm9zcGFjZSxtb25vc3BhY2U7d29yZC1icmVhazpicmVhay1hbGw7bWFyZ2luOjAgMCAxNHB4fQogIGlucHV0e3dpZHRoOjEwMCU7cGFkZGluZzoxMnB4O2JvcmRlci1yYWRpdXM6OHB4O2JvcmRlcjoxcHggc29saWQgdmFyKC0tbGluZSk7YmFja2dyb3VuZDp0cmFuc3BhcmVudDtjb2xvcjp2YXIoLS1pbmspO2ZvbnQ6aW5oZXJpdH0KICBpbnB1dDpmb2N1cy12aXNpYmxlLGJ1dHRvbjpmb2N1cy12aXNpYmxle291dGxpbmU6MnB4IHNvbGlkIHZhcigtLWFjY2VudCk7b3V0bGluZS1vZmZzZXQ6MnB4fQogIC5yb3d7ZGlzcGxheTpmbGV4O2dhcDoxMHB4O21hcmdpbi10b3A6MTJweH0KICBidXR0b257ZmxleDoxO3BhZGRpbmc6MTJweDtib3JkZXItcmFkaXVzOjhweDtib3JkZXI6MXB4IHNvbGlkIHZhcigtLWxpbmUpO2JhY2tncm91bmQ6dHJhbnNwYXJlbnQ7Y29sb3I6dmFyKC0taW5rKTtmb250OmluaGVyaXQ7Zm9udC13ZWlnaHQ6NjAwO2N1cnNvcjpwb2ludGVyfQogIGJ1dHRvbi5zYXZle2JhY2tncm91bmQ6dmFyKC0tYWNjZW50KTtib3JkZXItY29sb3I6dmFyKC0tYWNjZW50KTtjb2xvcjojMDYyNDFmfQogIGJ1dHRvbi50Z3t3aWR0aDoxMDAlO21hcmdpbi10b3A6MTJweDtib3JkZXItY29sb3I6dmFyKC0tdGcpO2NvbG9yOnZhcigtLXRnKX0KICBidXR0b246ZGlzYWJsZWR7b3BhY2l0eTouNX0KICAjbXNne21pbi1oZWlnaHQ6MS40ZW07bWFyZ2luOjEycHggMCAwO2ZvbnQtc2l6ZTouOXJlbX0KICAjbXNnLmVycntjb2xvcjp2YXIoLS1lcnIpfSAjbXNnLm9re2NvbG9yOnZhcigtLWFjY2VudCl9Cjwvc3R5bGU+CjwvaGVhZD4KPGJvZHk+CjxtYWluPgogIDxoMT5TaXRlIGFkcmVzaTwvaDE+CiAgPHAgY2xhc3M9InN1YiI+RWtsZW50aW5pbiBrdWxsYW5kxLHEn8SxIGRvbWFpbidpIGJ1cmFkYW4gZGXEn2nFn3RpcmViaWxpcnNpbi48L3A+CgogIDxkaXYgY2xhc3M9ImJveCI+CiAgICA8bGFiZWw+xZ51IGFua2kgYWRyZXM8L2xhYmVsPgogICAgPHAgY2xhc3M9ImN1ciIgaWQ9ImN1ciI+LTwvcD4KCiAgICA8bGFiZWwgZm9yPSJ1cmwiPlllbmkgYWRyZXM8L2xhYmVsPgogICAgPGlucHV0IGlkPSJ1cmwiIHR5cGU9InVybCIgaW5wdXRtb2RlPSJ1cmwiIGF1dG9jb21wbGV0ZT0ib2ZmIiBwbGFjZWhvbGRlcj0iaHR0cHM6Ly9vcm5lay5jb20iPgoKICAgIDxkaXYgY2xhc3M9InJvdyI+CiAgICAgIDxidXR0b24gaWQ9InB1bGwiIHR5cGU9ImJ1dHRvbiI+UmVwb2RhbiDDp2VrPC9idXR0b24+CiAgICAgIDxidXR0b24gaWQ9InNhdmUiIGNsYXNzPSJzYXZlIiB0eXBlPSJidXR0b24iPktheWRldDwvYnV0dG9uPgogICAgPC9kaXY+CiAgICA8cCBpZD0ibXNnIiByb2xlPSJzdGF0dXMiPjwvcD4KICA8L2Rpdj4KCiAgPGJ1dHRvbiBpZD0idGciIGNsYXNzPSJ0ZyIgdHlwZT0iYnV0dG9uIj5UZWxlZ3JhbTwvYnV0dG9uPgo8L21haW4+Cgo8c2NyaXB0Pgpjb25zdCAkID0gaWQgPT4gZG9jdW1lbnQuZ2V0RWxlbWVudEJ5SWQoaWQpOwpjb25zdCBtc2cgPSAodCwgYykgPT4geyAkKCdtc2cnKS50ZXh0Q29udGVudCA9IHQ7ICQoJ21zZycpLmNsYXNzTmFtZSA9IGMgfHwgJyc7IH07CgovLyBBbmRyb2lkIGvDtnByw7xzw7wgeW9rc2EgdGFyYXnEsWPEsWRhIHRlc3QgacOnaW4gc2FodGUgbmVzbmUKY29uc3QgQSA9IHdpbmRvdy5BbmRyb2lkIHx8IHsKICBnZXREb21haW46ICgpID0+IGxvY2FsU3RvcmFnZS5kIHx8ICdodHRwczovL29ybmVrLmNvbScsCiAgZmV0Y2hGcm9tUmVwbzogKCkgPT4gc2V0VGltZW91dCgoKSA9PiB3aW5kb3cub25GZXRjaGVkKCdodHRwczovL3llbmktb3JuZWsuY29tJywgJycpLCA1MDApLAogIHNhdmU6IHUgPT4geyBsb2NhbFN0b3JhZ2UuZCA9IHU7IH0sCiAgb3BlblRlbGVncmFtOiAoKSA9PiB3aW5kb3cub3BlbignaHR0cHM6Ly90Lm1lLycpCn07CgpmdW5jdGlvbiBzaG93KHUpeyAkKCdjdXInKS50ZXh0Q29udGVudCA9IHUgfHwgJy0nOyAkKCd1cmwnKS52YWx1ZSA9IHUgfHwgJyc7IH0Kc2hvdyhBLmdldERvbWFpbigpKTsKCi8vIExpbmsgdGFuxLFtbMSxIGRlxJ9pbHNlIGlsZ2lsaSBidXRvbmxhciBnaXpsZW5pcgppZiAodHlwZW9mIEEuaGFzVGVsZWdyYW0gPT09ICdmdW5jdGlvbicgJiYgIUEuaGFzVGVsZWdyYW0oKSkgJCgndGcnKS5zdHlsZS5kaXNwbGF5ID0gJ25vbmUnOwppZiAodHlwZW9mIEEuaGFzUmVtb3RlID09PSAnZnVuY3Rpb24nICYmICFBLmhhc1JlbW90ZSgpKSAkKCdwdWxsJykuc3R5bGUuZGlzcGxheSA9ICdub25lJzsKCi8vIEtvdGxpbiB0YXJhZsSxIGJ1IGZvbmtzaXlvbnUgw6dhxJ/EsXLEsXIKd2luZG93Lm9uRmV0Y2hlZCA9ICh1cmwsIGVycm9yKSA9PiB7CiAgJCgncHVsbCcpLmRpc2FibGVkID0gZmFsc2U7CiAgaWYgKGVycm9yKSByZXR1cm4gbXNnKGVycm9yLCAnZXJyJyk7CiAgJCgndXJsJykudmFsdWUgPSB1cmw7CiAgbXNnKCdSZXBvZGFuIMOnZWtpbGRpLiBLYXlkZXRcJ2UgYmFzYXJhayB1eWd1bGEuJywgJ29rJyk7Cn07CgokKCdwdWxsJykub25jbGljayA9ICgpID0+IHsgJCgncHVsbCcpLmRpc2FibGVkID0gdHJ1ZTsgbXNnKCfDh2VraWxpeW9yLi4uJyk7IEEuZmV0Y2hGcm9tUmVwbygpOyB9OwoKJCgnc2F2ZScpLm9uY2xpY2sgPSAoKSA9PiB7CiAgY29uc3QgdSA9ICQoJ3VybCcpLnZhbHVlLnRyaW0oKS5yZXBsYWNlKC9cLyskLywgJycpOwogIGlmICghL15odHRwcz86XC9cL1teXHMvXStcLlteXHMvXSsvLnRlc3QodSkpIHJldHVybiBtc2coJ0dlw6dlcmxpIGJpciBhZHJlcyBnaXIgKGh0dHBzOi8vLi4uKS4nLCAnZXJyJyk7CiAgQS5zYXZlKHUpOyBzaG93KHUpOyBtc2coJ0theWRlZGlsZGkuJywgJ29rJyk7Cn07CgokKCd0ZycpLm9uY2xpY2sgPSAoKSA9PiBBLm9wZW5UZWxlZ3JhbSgpOwo8L3NjcmlwdD4KPC9ib2R5Pgo8L2h0bWw+Cg==";
    static final boolean DEBUG = true;
    static boolean started = false;
    static int binds = 0;

    public static void init(Object plugin) { init(plugin, null); }

    public static void init(final Object plugin, Context ctx) {
        try { if (ctx != null) DomainStore.app = ctx.getApplicationContext(); } catch (Throwable t) { }
        toast("Domain hook yuklendi");
        try { bind(plugin); } catch (Throwable t) { toast("Domain hook hata: " + t); }
        try {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public void run() { try { bind(plugin); } catch (Throwable t) { toast("Domain hook hata: " + t); } }
            }, 2500);
        } catch (Throwable t) { }
        if (!started) {
            started = true;
            new Thread(new Runnable() {
                public void run() { try { DomainStore.fetchRemote(); } catch (Throwable t) { } }
            }).start();
        }
    }

    static void toast(final String msg) {
        if (!DEBUG) return;
        try {
            final Context c = DomainStore.ctx();
            if (c == null) return;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    try { android.widget.Toast.makeText(c, msg, android.widget.Toast.LENGTH_LONG).show(); } catch (Throwable t) { }
                }
            });
        } catch (Throwable t) { }
    }

    static void bind(Object plugin) throws Exception {
        Method setter = null;
        for (Method m : plugin.getClass().getMethods()) {
            if (m.getName().equals("setOpenSettings") && m.getParameterTypes().length == 1) setter = m;
        }
        java.lang.reflect.Field field = null;
        Class<?> fn;
        if (setter != null) {
            fn = setter.getParameterTypes()[0];
        } else {
            for (Class<?> c = plugin.getClass(); c != null && field == null; c = c.getSuperclass()) {
                try { field = c.getDeclaredField("openSettings"); } catch (NoSuchFieldException e) { }
            }
            if (field == null) { toast("Domain hook: openSettings bulunamadi"); return; }
            field.setAccessible(true);
            fn = field.getType();
        }
        final Object unit = Class.forName("kotlin.Unit").getField("INSTANCE").get(null);
        Object proxy = Proxy.newProxyInstance(fn.getClassLoader(), new Class<?>[] { fn }, new InvocationHandler() {
            public Object invoke(Object p, Method m, Object[] a) {
                String n = m.getName();
                if (n.equals("invoke")) {
                    Activity act = (a != null && a.length > 0) ? activity(a[0]) : null;
                    if (act != null) show(act);
                    else toast("Domain hook: Activity bulunamadi");
                    return unit;
                }
                if (n.equals("hashCode")) return Integer.valueOf(0);
                if (n.equals("equals")) return Boolean.FALSE;
                if (n.equals("toString")) return "DomainHook";
                return null;
            }
        });
        if (setter != null) setter.invoke(plugin, proxy); else field.set(plugin, proxy);
        binds++;
        if (binds == 1) toast("Domain hook: ayar butonu baglandi");
    }

    static Activity activity(Object o) {
        Context c = (o instanceof Context) ? (Context) o : null;
        while (c instanceof ContextWrapper) {
            if (c instanceof Activity) return (Activity) c;
            c = ((ContextWrapper) c).getBaseContext();
        }
        return (c instanceof Activity) ? (Activity) c : null;
    }

    static void show(final Activity act) {
        act.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    Dialog d = new Dialog(act, android.R.style.Theme_DeviceDefault_NoActionBar);
                    WebView w = new WebView(act);
                    w.getSettings().setJavaScriptEnabled(true);
                    w.setBackgroundColor(0xFF16222C);
                    w.addJavascriptInterface(new Bridge(act, w, d), "Android");
                    String html = new String(Base64.decode(HTML, Base64.DEFAULT), "UTF-8");
                    w.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
                    d.setContentView(w);
                    d.show();
                    d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                } catch (Throwable t) { toast("Popup hatasi: " + t); }
            }
        });
    }

    static class Bridge {
        final Activity act;
        final WebView web;
        final Dialog dlg;

        Bridge(Activity a, WebView w, Dialog d) { act = a; web = w; dlg = d; }

        void js(final String code) {
            act.runOnUiThread(new Runnable() {
                public void run() { try { web.evaluateJavascript(code, null); } catch (Throwable t) { } }
            });
        }

        @JavascriptInterface public String getDomain() { return DomainStore.current(); }

        @JavascriptInterface public boolean hasTelegram() { return TG.length() > 0; }

        @JavascriptInterface public boolean hasRemote() { return DomainStore.RAW.length() > 0; }

        // domains.json'daki degerle ayniysa elle girilen kaydi sil: otomatik guncelleme calismaya devam etsin
        @JavascriptInterface public void save(String u) {
            String r = DomainStore.remoteFor();
            if (u != null && r != null && DomainStore.norm(u).equals(DomainStore.norm(r))) DomainStore.setManual("");
            else DomainStore.setManual(u);
        }

        @JavascriptInterface public void setAuto(boolean b) { DomainStore.setAuto(b); }

        @JavascriptInterface public void openTelegram() {
            try { act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(TG))); } catch (Throwable t) { }
        }

        @JavascriptInterface public void fetchFromRepo() {
            new Thread(new Runnable() {
                public void run() {
                    String err = DomainStore.fetchRemote();
                    String dom = "";
                    if (err == null) {
                        String r = DomainStore.remoteFor();
                        if (r == null) err = "domains.json icinde bu domain icin kayit yok";
                        else dom = r.trim();
                    }
                    js("onFetched(" + JSONObject.quote(dom) + "," + JSONObject.quote(err == null ? "" : err) + ")");
                }
            }).start();
        }

        @JavascriptInterface public void close() {
            act.runOnUiThread(new Runnable() {
                public void run() { try { dlg.dismiss(); } catch (Throwable t) { } }
            });
        }
    }
}
