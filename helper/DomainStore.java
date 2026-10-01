package com.example.domainpatch;

import android.content.Context;
import android.content.SharedPreferences;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;
import org.json.JSONObject;

public class DomainStore {
    public static Context app;
    static final String RAW = "https://raw.githubusercontent.com/darknesslord19/domain/main/domains.json";
    static final String TOKEN = "";
    static String cur = "";

    static Context ctx() {
        if (app != null) return app;
        try {
            Object o = Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            if (o instanceof Context) app = (Context) o;
        } catch (Throwable t) { }
        return app;
    }

    static SharedPreferences sp() {
        Context c = ctx();
        return c == null ? null : c.getSharedPreferences("domain_prefs", 0);
    }

    static String norm(String u) {
        if (u == null) return "";
        u = u.trim().toLowerCase();
        while (u.endsWith("/")) u = u.substring(0, u.length() - 1);
        return u;
    }

    // Orijinal string sonunda "/" varsa aynisini koru
    static String same(String def, String v) {
        if (v == null) return def;
        v = v.trim();
        if (v.length() == 0) return def;
        while (v.endsWith("/")) v = v.substring(0, v.length() - 1);
        return def.endsWith("/") ? v + "/" : v;
    }

    public static String def() { return cur; }

    public static String read(String def) {
        cur = def;
        try {
            SharedPreferences p = sp();
            if (p == null) return def;
            String m = p.getString("m:" + norm(def), "");
            if (m.length() > 0) return same(def, m);
            if (p.getBoolean("auto", true)) {
                String r = lookup(p.getString("remote", ""), def);
                if (r != null) return same(def, r);
            }
        } catch (Throwable t) { }
        return def;
    }

    static String lookup(String json, String def) {
        try {
            if (json == null || json.length() == 0) return null;
            JSONObject o = new JSONObject(json);
            Iterator<String> it = o.keys();
            String want = norm(def);
            while (it.hasNext()) {
                String k = it.next();
                if (norm(k).equals(want)) {
                    String v = o.optString(k, "");
                    if (v.length() > 0) return v;
                }
            }
            String d = o.optString("domain", "");
            if (d.length() > 0) return d;
        } catch (Throwable t) { }
        return null;
    }

    public static boolean hasManual() {
        SharedPreferences p = sp();
        return p != null && p.getString("m:" + norm(cur), "").length() > 0;
    }

    public static void setManual(String u) {
        SharedPreferences p = sp();
        if (p == null || cur.length() == 0) return;
        u = u == null ? "" : u.trim();
        if (u.length() > 0 && !u.toLowerCase().startsWith("http")) u = "https://" + u;
        if (u.length() == 0) p.edit().remove("m:" + norm(cur)).apply();
        else p.edit().putString("m:" + norm(cur), u).apply();
    }

    public static boolean auto() {
        SharedPreferences p = sp();
        return p == null || p.getBoolean("auto", true);
    }

    public static void setAuto(boolean b) {
        SharedPreferences p = sp();
        if (p != null) p.edit().putBoolean("auto", b).apply();
    }

    // null = basarili, aksi halde hata metni
    public static String fetchRemote() {
        if (RAW.length() == 0) return "domains.json linki tanimli degil";
        try {
            String u = RAW + (RAW.indexOf('?') >= 0 ? "&" : "?") + "t=" + System.currentTimeMillis();
            HttpURLConnection c = (HttpURLConnection) new URL(u).openConnection();
            if (TOKEN.length() > 0) c.setRequestProperty("Authorization", "token " + TOKEN);
            c.setConnectTimeout(8000);
            c.setReadTimeout(8000);
            Scanner s = new Scanner(c.getInputStream(), "UTF-8").useDelimiter("\\A");
            String body = s.hasNext() ? s.next() : "";
            s.close();
            new JSONObject(body);
            SharedPreferences p = sp();
            if (p == null) return "Uygulama baglami yok";
            p.edit().putString("remote", body).apply();
            return null;
        } catch (Exception e) {
            return "Okunamadi: " + e.getMessage();
        }
    }
}
