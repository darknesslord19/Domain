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
    static final String HTML = "PCFET0NUWVBFIGh0bWw+CjxodG1sIGxhbmc9InRyIj4KPGhlYWQ+CjxtZXRhIGNoYXJzZXQ9InV0Zi04Ij4KPG1ldGEgbmFtZT0idmlld3BvcnQiIGNvbnRlbnQ9IndpZHRoPWRldmljZS13aWR0aCwgaW5pdGlhbC1zY2FsZT0xLCB2aWV3cG9ydC1maXQ9Y292ZXIiPgo8dGl0bGU+RG9tYWluIEF5YXJsYXLEsTwvdGl0bGU+CjxzdHlsZT4KICA6cm9vdHstLWJnOiMwZDE4MjA7LS1yYWlsOiMxNDIzMmQ7LS1maWVsZDojMGYxYzI1Oy0taW5rOiNlN2VmZjM7LS1tdXRlOiM4YWEwYWU7LS1saW5lOiMyNzQwNGY7LS1hY2NlbnQ6IzM4ZDNiODstLWFjY2VudC1pbms6IzA1MmEyNDstLXRnOiMyYWEzZTA7LS1lcnI6I2ZmODA4MDtjb2xvci1zY2hlbWU6ZGFya30KICBAbWVkaWEgKHByZWZlcnMtY29sb3Itc2NoZW1lOmxpZ2h0KXs6cm9vdHstLWJnOiNlZWYzZjY7LS1yYWlsOiNmZmY7LS1maWVsZDojZjRmOGZhOy0taW5rOiMxNDIzMmU7LS1tdXRlOiM1YjczODQ7LS1saW5lOiNjZGRiZTM7LS1hY2NlbnQ6IzBiOGY3YjstLWFjY2VudC1pbms6I2ZmZjstLXRnOiMxYjg2YmQ7LS1lcnI6I2MyM2IzYjtjb2xvci1zY2hlbWU6bGlnaHR9fQogICp7Ym94LXNpemluZzpib3JkZXItYm94fQogIGh0bWwsYm9keXttYXJnaW46MDtiYWNrZ3JvdW5kOnZhcigtLWJnKTtjb2xvcjp2YXIoLS1pbmspO2ZvbnQ6MTZweC8xLjUgc3lzdGVtLXVpLC1hcHBsZS1zeXN0ZW0sIlNlZ29lIFVJIixSb2JvdG8sc2Fucy1zZXJpZn0KICBtYWlue21heC13aWR0aDo1MjBweDttYXJnaW46MCBhdXRvO3BhZGRpbmc6Y2FsYygxOHB4ICsgZW52KHNhZmUtYXJlYS1pbnNldC10b3AsMHB4KSkgMTZweCBjYWxjKDMycHggKyBlbnYoc2FmZS1hcmVhLWluc2V0LWJvdHRvbSwwcHgpKX0KICBoZWFkZXJ7bWFyZ2luLWJvdHRvbToxOHB4fQogIGgxe2ZvbnQtc2l6ZToxLjJyZW07bWFyZ2luOjAgMCAycHg7Zm9udC13ZWlnaHQ6NjAwfQogIGhlYWRlciBwe21hcmdpbjowO2NvbG9yOnZhcigtLW11dGUpO2ZvbnQtc2l6ZTouODhyZW19CiAgLmNhcmR7YmFja2dyb3VuZDp2YXIoLS1yYWlsKTtib3JkZXI6MXB4IHNvbGlkIHZhcigtLWxpbmUpO2JvcmRlci1yYWRpdXM6NnB4O3BhZGRpbmc6MTZweDttYXJnaW4tYm90dG9tOjEycHh9CiAgLmt7ZGlzcGxheTpibG9jaztjb2xvcjp2YXIoLS1tdXRlKTtmb250LXNpemU6LjgycmVtfQogIC52e2ZvbnQtc2l6ZToxLjA1cmVtO2ZvbnQtd2VpZ2h0OjYwMDtvdmVyZmxvdy13cmFwOmFueXdoZXJlO21hcmdpbjoycHggMCA4cHh9CiAgLnRhZ3tkaXNwbGF5OmlubGluZS1ibG9jaztmb250LXNpemU6Ljc4cmVtO3BhZGRpbmc6MnB4IDhweDtib3JkZXItcmFkaXVzOjRweDtib3JkZXI6MXB4IHNvbGlkIHZhcigtLWFjY2VudCk7Y29sb3I6dmFyKC0tYWNjZW50KX0KICAuZGVme21hcmdpbi10b3A6MTBweDtmb250LXNpemU6LjgycmVtO2NvbG9yOnZhcigtLW11dGUpO292ZXJmbG93LXdyYXA6YW55d2hlcmV9CiAgbGFiZWx7ZGlzcGxheTpibG9jaztmb250LXNpemU6Ljg2cmVtO2ZvbnQtd2VpZ2h0OjUwMDttYXJnaW4tYm90dG9tOjZweH0KICBpbnB1dFt0eXBlPXVybF17d2lkdGg6MTAwJTttaW4taGVpZ2h0OjQ0cHg7cGFkZGluZzoxMHB4IDEycHg7Ym9yZGVyLXJhZGl1czo2cHg7Ym9yZGVyOjFweCBzb2xpZCB2YXIoLS1saW5lKTtiYWNrZ3JvdW5kOnZhcigtLWZpZWxkKTtjb2xvcjp2YXIoLS1pbmspO2ZvbnQ6aW5oZXJpdH0KICBpbnB1dDpmb2N1cy12aXNpYmxlLGJ1dHRvbjpmb2N1cy12aXNpYmxle291dGxpbmU6MnB4IHNvbGlkIHZhcigtLWFjY2VudCk7b3V0bGluZS1vZmZzZXQ6MnB4fQogIC5yb3d7ZGlzcGxheTpmbGV4O2dhcDo4cHg7bWFyZ2luLXRvcDoxMHB4fQogIGJ1dHRvbnttaW4taGVpZ2h0OjQ0cHg7cGFkZGluZzoxMHB4IDE0cHg7Ym9yZGVyLXJhZGl1czo2cHg7Ym9yZGVyOjFweCBzb2xpZCB2YXIoLS1saW5lKTtiYWNrZ3JvdW5kOnRyYW5zcGFyZW50O2NvbG9yOnZhcigtLWluayk7Zm9udDo1MDAgLjk1cmVtIHN5c3RlbS11aSxzYW5zLXNlcmlmO2N1cnNvcjpwb2ludGVyfQogIGJ1dHRvbi5wcmltYXJ5e2JhY2tncm91bmQ6dmFyKC0tYWNjZW50KTtib3JkZXItY29sb3I6dmFyKC0tYWNjZW50KTtjb2xvcjp2YXIoLS1hY2NlbnQtaW5rKTtmb250LXdlaWdodDo2MDA7ZmxleDoxfQogIGJ1dHRvbi5mdWxse3dpZHRoOjEwMCV9CiAgYnV0dG9uLnRne3dpZHRoOjEwMCU7Ym9yZGVyLWNvbG9yOnZhcigtLXRnKTtjb2xvcjp2YXIoLS10Zyk7bWFyZ2luLWJvdHRvbToxMnB4fQogIC5zdy1yb3d7ZGlzcGxheTpmbGV4O2FsaWduLWl0ZW1zOmNlbnRlcjtqdXN0aWZ5LWNvbnRlbnQ6c3BhY2UtYmV0d2VlbjtnYXA6MTJweDttYXJnaW4tYm90dG9tOjEycHh9CiAgLnN3LXJvdyBzbWFsbHtkaXNwbGF5OmJsb2NrO2NvbG9yOnZhcigtLW11dGUpO2ZvbnQtc2l6ZTouOHJlbX0KICAuc3d7cG9zaXRpb246cmVsYXRpdmU7ZmxleDpub25lO3dpZHRoOjQ4cHg7aGVpZ2h0OjI4cHh9CiAgLnN3IGlucHV0e3Bvc2l0aW9uOmFic29sdXRlO2luc2V0OjA7b3BhY2l0eTowO3dpZHRoOjEwMCU7aGVpZ2h0OjEwMCU7bWFyZ2luOjA7Y3Vyc29yOnBvaW50ZXJ9CiAgLnN3IHNwYW57cG9zaXRpb246YWJzb2x1dGU7aW5zZXQ6MDtib3JkZXItcmFkaXVzOjE0cHg7YmFja2dyb3VuZDp2YXIoLS1saW5lKTt0cmFuc2l0aW9uOmJhY2tncm91bmQgLjE1c30KICAuc3cgc3BhbjphZnRlcntjb250ZW50OiIiO3Bvc2l0aW9uOmFic29sdXRlO2xlZnQ6M3B4O3RvcDozcHg7d2lkdGg6MjJweDtoZWlnaHQ6MjJweDtib3JkZXItcmFkaXVzOjUwJTtiYWNrZ3JvdW5kOiNmZmY7dHJhbnNpdGlvbjp0cmFuc2Zvcm0gLjE1c30KICAuc3cgaW5wdXQ6Y2hlY2tlZCArIHNwYW57YmFja2dyb3VuZDp2YXIoLS1hY2NlbnQpfQogIC5zdyBpbnB1dDpjaGVja2VkICsgc3BhbjphZnRlcnt0cmFuc2Zvcm06dHJhbnNsYXRlWCgyMHB4KX0KICAuc3cgaW5wdXQ6Zm9jdXMtdmlzaWJsZSArIHNwYW57b3V0bGluZToycHggc29saWQgdmFyKC0tYWNjZW50KTtvdXRsaW5lLW9mZnNldDoycHh9CiAgI21zZ3ttaW4taGVpZ2h0OjEuNGVtO2ZvbnQtc2l6ZTouODhyZW07bWFyZ2luOjRweCAwIDhweH0KICAjbXNnLm9re2NvbG9yOnZhcigtLWFjY2VudCl9ICNtc2cuZXJye2NvbG9yOnZhcigtLWVycil9CiAgLm5vdGV7Y29sb3I6dmFyKC0tbXV0ZSk7Zm9udC1zaXplOi44cmVtO21hcmdpbjowIDAgMTZweH0KPC9zdHlsZT4KPC9oZWFkPgo8Ym9keT4KPG1haW4+CiAgPGhlYWRlcj4KICAgIDxoMT5Eb21haW4gYXlhcmxhcsSxPC9oMT4KICAgIDxwPktheW5hayBzaXRlbmluIGFkcmVzaW5pIGJ1cmFkYW4gecO2bmV0LjwvcD4KICA8L2hlYWRlcj4KCiAgPHNlY3Rpb24gY2xhc3M9ImNhcmQiPgogICAgPHNwYW4gY2xhc3M9ImsiPkFrdGlmIGRvbWFpbjwvc3Bhbj4KICAgIDxkaXYgY2xhc3M9InYiIGlkPSJjdXIiPuKAlDwvZGl2PgogICAgPHNwYW4gY2xhc3M9InRhZyIgaWQ9InNyYyIgaGlkZGVuPjwvc3Bhbj4KICAgIDxkaXYgY2xhc3M9ImRlZiI+Q1MzIHZhcnNhecSxbGFuxLE6IDxzcGFuIGlkPSJkZWYiPuKAlDwvc3Bhbj48L2Rpdj4KICA8L3NlY3Rpb24+CgogIDxzZWN0aW9uIGNsYXNzPSJjYXJkIj4KICAgIDxsYWJlbCBmb3I9ImRvbSI+RG9tYWluJ2kgZWxsZSBkZcSfacWfdGlyPC9sYWJlbD4KICAgIDxpbnB1dCBpZD0iZG9tIiB0eXBlPSJ1cmwiIGlucHV0bW9kZT0idXJsIiBwbGFjZWhvbGRlcj0iaHR0cHM6Ly95ZW5pLWRvbWFpbi5jb20iIGF1dG9jYXBpdGFsaXplPSJvZmYiIGF1dG9jb3JyZWN0PSJvZmYiIHNwZWxsY2hlY2s9ImZhbHNlIj4KICAgIDxkaXYgY2xhc3M9InJvdyI+CiAgICAgIDxidXR0b24gY2xhc3M9InByaW1hcnkiIGlkPSJzYXZlIj5LYXlkZXQ8L2J1dHRvbj4KICAgICAgPGJ1dHRvbiBpZD0icmVzZXQiPlZhcnNhecSxbGFuYSBkw7ZuPC9idXR0b24+CiAgICA8L2Rpdj4KICA8L3NlY3Rpb24+CgogIDxzZWN0aW9uIGNsYXNzPSJjYXJkIiBpZD0icmVtb3RlQ2FyZCIgaGlkZGVuPgogICAgPGRpdiBjbGFzcz0ic3ctcm93Ij4KICAgICAgPGRpdj48Yj5PdG9tYXRpayBnw7xuY2VsbGU8L2I+PHNtYWxsPmRvbWFpbnMuanNvbiBoZXIgYcOnxLFsxLHFn3RhIG9rdW51cjwvc21hbGw+PC9kaXY+CiAgICAgIDxsYWJlbCBjbGFzcz0ic3ciIGFyaWEtbGFiZWw9Ik90b21hdGlrIGfDvG5jZWxsZSI+PGlucHV0IHR5cGU9ImNoZWNrYm94IiBpZD0iYXV0byI+PHNwYW4+PC9zcGFuPjwvbGFiZWw+CiAgICA8L2Rpdj4KICAgIDxidXR0b24gY2xhc3M9ImZ1bGwiIGlkPSJmZXRjaCI+ZG9tYWlucy5qc29uJ2RhbiDFn2ltZGkgZ8O8bmNlbGxlPC9idXR0b24+CiAgPC9zZWN0aW9uPgoKICA8YnV0dG9uIGNsYXNzPSJ0ZyIgaWQ9InRnIiBoaWRkZW4+VGVsZWdyYW0ga2FuYWzEsW5hIGdpdDwvYnV0dG9uPgoKICA8cCBpZD0ibXNnIiByb2xlPSJzdGF0dXMiPjwvcD4KICA8cCBjbGFzcz0ibm90ZSI+RGXEn2nFn2lrbGlrbGVyIGVrbGVudGkgeWVuaWRlbiB5w7xrbGVuaW5jZSB2ZXlhIHV5Z3VsYW1hIHllbmlkZW4gYcOnxLFsxLFuY2EgZ2XDp2VybGkgb2x1ci48L3A+CiAgPGJ1dHRvbiBjbGFzcz0iZnVsbCIgaWQ9ImNsb3NlIj5LYXBhdDwvYnV0dG9uPgo8L21haW4+CjxzY3JpcHQ+CihmdW5jdGlvbigpewogIHZhciBBID0gd2luZG93LkFuZHJvaWQ7CiAgdmFyICQgPSBmdW5jdGlvbihpKXsgcmV0dXJuIGRvY3VtZW50LmdldEVsZW1lbnRCeUlkKGkpOyB9OwogIHZhciBzdCA9IHt9OwogIGZ1bmN0aW9uIG1zZyh0LCBjKXsgdmFyIG0gPSAkKCdtc2cnKTsgbS50ZXh0Q29udGVudCA9IHQgfHwgJyc7IG0uY2xhc3NOYW1lID0gYyB8fCAnJzsgfQogIGZ1bmN0aW9uIHJlbmRlcigpewogICAgdHJ5IHsgc3QgPSBKU09OLnBhcnNlKEEuZ2V0U3RhdGUoKSB8fCAne30nKTsgfSBjYXRjaCAoZSkgeyBzdCA9IHt9OyB9CiAgICAkKCdkZWYnKS50ZXh0Q29udGVudCA9IHN0LmRlZiB8fCAnaGVuw7x6IG9rdW5tYWTEsSc7CiAgICAkKCdjdXInKS50ZXh0Q29udGVudCA9IHN0LmRlZiA/IChzdC5kb21haW4gfHwgc3QuZGVmKSA6ICfigJQnOwogICAgdmFyIHMgPSAhc3QuZGVmID8gJycgOiAoc3QubWFudWFsID8gJ0VsbGUgZ2lyaWxkaScgOiAoc3QuZG9tYWluICYmIHN0LmRvbWFpbiAhPT0gc3QuZGVmID8gJ2RvbWFpbnMuanNvbicgOiAnQ1MzIHZhcnNhecSxbGFuxLEnKSk7CiAgICAkKCdzcmMnKS50ZXh0Q29udGVudCA9IHM7ICQoJ3NyYycpLmhpZGRlbiA9ICFzOwogICAgJCgnZG9tJykudmFsdWUgPSBzdC5tYW51YWwgPyAoc3QuZG9tYWluIHx8ICcnKSA6ICcnOwogICAgJCgnYXV0bycpLmNoZWNrZWQgPSAhIXN0LmF1dG87CiAgICAkKCdyZW1vdGVDYXJkJykuaGlkZGVuID0gIXN0LnJlbW90ZTsKICAgICQoJ3RnJykuaGlkZGVuID0gIXN0LnRnOwogIH0KICBpZiAoIUEpIHsgbXNnKCdVeWd1bGFtYSBrw7ZwcsO8c8O8IGJ1bHVuYW1hZMSxLicsICdlcnInKTsgcmV0dXJuOyB9CiAgJCgnc2F2ZScpLm9uY2xpY2sgPSBmdW5jdGlvbigpewogICAgdmFyIHYgPSAkKCdkb20nKS52YWx1ZS50cmltKCk7CiAgICBpZiAoIXYpIHsgbXNnKCdCaXIgZG9tYWluIHlhei4nLCAnZXJyJyk7IHJldHVybjsgfQogICAgaWYgKCFzdC5kZWYpIHsgbXNnKCdEb21haW4gaGVuw7x6IG9rdW5tYWTEsS4gRWtsZW50aXlpIGJpciBrZXoga3VsbGFuxLFwIHRla3JhciBkZW5lLicsICdlcnInKTsgcmV0dXJuOyB9CiAgICBBLnNhdmUodik7IG1zZygnS2F5ZGVkaWxkaS4nLCAnb2snKTsgcmVuZGVyKCk7CiAgfTsKICAkKCdyZXNldCcpLm9uY2xpY2sgPSBmdW5jdGlvbigpeyBBLnNhdmUoJycpOyBtc2coJ1ZhcnNhecSxbGFuYSBkw7Zuw7xsZMO8LicsICdvaycpOyByZW5kZXIoKTsgfTsKICAkKCdhdXRvJykub25jaGFuZ2UgPSBmdW5jdGlvbigpeyBBLnNldEF1dG8oJCgnYXV0bycpLmNoZWNrZWQpOyBtc2coJCgnYXV0bycpLmNoZWNrZWQgPyAnT3RvbWF0aWsgZ8O8bmNlbGxlbWUgYcOnxLFrLicgOiAnT3RvbWF0aWsgZ8O8bmNlbGxlbWUga2FwYWzEsS4nLCAnb2snKTsgcmVuZGVyKCk7IH07CiAgJCgnZmV0Y2gnKS5vbmNsaWNrID0gZnVuY3Rpb24oKXsgbXNnKCdPa3VudXlvcuKApicpOyBBLmZldGNoRnJvbVJlcG8oKTsgfTsKICAkKCd0ZycpLm9uY2xpY2sgPSBmdW5jdGlvbigpeyBBLm9wZW5UZWxlZ3JhbSgpOyB9OwogICQoJ2Nsb3NlJykub25jbGljayA9IGZ1bmN0aW9uKCl7IEEuY2xvc2UoKTsgfTsKICB3aW5kb3cub25GZXRjaGVkID0gZnVuY3Rpb24oZCwgZSl7IGlmIChlKSBtc2coZSwgJ2VycicpOyBlbHNlIHsgbXNnKCdHw7xuY2VsbGVuZGkuJywgJ29rJyk7IHJlbmRlcigpOyB9IH07CiAgcmVuZGVyKCk7Cn0pKCk7Cjwvc2NyaXB0Pgo8L2JvZHk+CjwvaHRtbD4K";
    static boolean started = false;

    public static void init(final Object plugin, Context ctx) {
        try { if (ctx != null) DomainStore.app = ctx.getApplicationContext(); } catch (Throwable t) { }
        try { bind(plugin); } catch (Throwable t) { }
        try {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public void run() { try { bind(plugin); } catch (Throwable t) { } }
            }, 2500);
        } catch (Throwable t) { }
        if (!started) {
            started = true;
            new Thread(new Runnable() {
                public void run() { try { DomainStore.fetchRemote(); } catch (Throwable t) { } }
            }).start();
        }
    }

    static void bind(Object plugin) throws Exception {
        Method setter = null;
        for (Method m : plugin.getClass().getMethods()) {
            if (m.getName().equals("setOpenSettings") && m.getParameterTypes().length == 1) setter = m;
        }
        if (setter == null) return;
        Class<?> fn = setter.getParameterTypes()[0];
        final Object unit = Class.forName("kotlin.Unit").getField("INSTANCE").get(null);
        Object proxy = Proxy.newProxyInstance(fn.getClassLoader(), new Class<?>[] { fn }, new InvocationHandler() {
            public Object invoke(Object p, Method m, Object[] a) {
                String n = m.getName();
                if (n.equals("invoke")) {
                    if (a != null && a.length > 0) {
                        Activity act = activity(a[0]);
                        if (act != null) show(act);
                    }
                    return unit;
                }
                if (n.equals("hashCode")) return Integer.valueOf(0);
                if (n.equals("equals")) return Boolean.FALSE;
                if (n.equals("toString")) return "DomainHook";
                return null;
            }
        });
        setter.invoke(plugin, proxy);
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
                    w.setBackgroundColor(0xFF0D1820);
                    w.addJavascriptInterface(new Bridge(act, w, d), "Android");
                    String html = new String(Base64.decode(HTML, Base64.DEFAULT), "UTF-8");
                    w.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
                    d.setContentView(w);
                    d.show();
                    d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                } catch (Throwable t) { }
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

        @JavascriptInterface public String getState() {
            try {
                String def = DomainStore.def();
                JSONObject o = new JSONObject();
                o.put("def", def);
                o.put("domain", DomainStore.read(def));
                o.put("manual", DomainStore.hasManual());
                o.put("auto", DomainStore.auto());
                o.put("tg", TG.length() > 0);
                o.put("remote", DomainStore.RAW.length() > 0);
                return o.toString();
            } catch (Throwable t) { return "{}"; }
        }

        @JavascriptInterface public void save(String u) { DomainStore.setManual(u); }

        @JavascriptInterface public void setAuto(boolean b) { DomainStore.setAuto(b); }

        @JavascriptInterface public void openTelegram() {
            try { act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(TG))); } catch (Throwable t) { }
        }

        @JavascriptInterface public void fetchFromRepo() {
            new Thread(new Runnable() {
                public void run() {
                    String err = DomainStore.fetchRemote();
                    String dom = DomainStore.read(DomainStore.def());
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
