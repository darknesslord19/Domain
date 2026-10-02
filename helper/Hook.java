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
    static final String HTML = "PCFET0NUWVBFIGh0bWw+CjxodG1sIGxhbmc9InRyIj4KPGhlYWQ+CjxtZXRhIGNoYXJzZXQ9InV0Zi04Ij4KPG1ldGEgbmFtZT0idmlld3BvcnQiIGNvbnRlbnQ9IndpZHRoPWRldmljZS13aWR0aCwgaW5pdGlhbC1zY2FsZT0xLCB2aWV3cG9ydC1maXQ9Y292ZXIiPgo8dGl0bGU+RGFya25lc3MgTG9yZDwvdGl0bGU+CjxzdHlsZT4KICA6cm9vdHsKICAgIC0tYmc6IzA5MDcwYjsgLS1jYXJkOiMxMzBmMTc7IC0tZmllbGQ6IzBjMGEwZjsgLS1pbms6I2Y0ZWZmNjsgLS1tdXRlOiNhMzk3YWM7IC0tbGluZTojMmUyMzMzOwogICAgLS1hY2NlbnQ6I2ZmM2I1NzsgLS1hY2NlbnQyOiMzM2UxZmY7IC0tb2s6IzQ2ZTZhNjsgLS1iYWQ6I2ZmOTE1ODsKICAgIGNvbG9yLXNjaGVtZTpkYXJrOwogIH0KICAqe2JveC1zaXppbmc6Ym9yZGVyLWJveH0KICBodG1sLGJvZHl7bWFyZ2luOjA7bWluLWhlaWdodDoxMDAlO2JhY2tncm91bmQ6dmFyKC0tYmcpO2NvbG9yOnZhcigtLWluayk7Zm9udDoxNnB4LzEuNSBzeXN0ZW0tdWksLWFwcGxlLXN5c3RlbSwiU2Vnb2UgVUkiLFJvYm90byxzYW5zLXNlcmlmfQogIGJvZHl7CiAgICBiYWNrZ3JvdW5kOgogICAgICByYWRpYWwtZ3JhZGllbnQoMTIwJSA1NSUgYXQgNTAlIC04JSwgcmdiYSgyNTUsNTksODcsLjIwKSwgdHJhbnNwYXJlbnQgNjIlKSwKICAgICAgcmFkaWFsLWdyYWRpZW50KDcwJSA0MCUgYXQgMTAwJSAxMDAlLCByZ2JhKDUxLDIyNSwyNTUsLjA3KSwgdHJhbnNwYXJlbnQgNzAlKSwKICAgICAgdmFyKC0tYmcpOwogICAgbWluLWhlaWdodDoxMDB2aDsKICB9CiAgLyogaW5jZSB0YXJhbWEgw6dpemdpbGVyaSAqLwogIGJvZHk6YmVmb3Jle2NvbnRlbnQ6IiI7cG9zaXRpb246Zml4ZWQ7aW5zZXQ6MDtwb2ludGVyLWV2ZW50czpub25lO29wYWNpdHk6LjM1OwogICAgYmFja2dyb3VuZDpyZXBlYXRpbmctbGluZWFyLWdyYWRpZW50KDBkZWcsIHJnYmEoMjU1LDI1NSwyNTUsLjAyNSkgMCAxcHgsIHRyYW5zcGFyZW50IDFweCAzcHgpfQogIGJ1dHRvbntmb250OmluaGVyaXQ7Y29sb3I6aW5oZXJpdH0KICBtYWlue3Bvc2l0aW9uOnJlbGF0aXZlO21heC13aWR0aDo0NDBweDttYXJnaW46MCBhdXRvO3BhZGRpbmc6Y2FsYyhtYXgoZW52KHNhZmUtYXJlYS1pbnNldC10b3AsMHB4KSwzNnB4KSArIDE4cHgpIDIwcHggY2FsYygzMnB4ICsgZW52KHNhZmUtYXJlYS1pbnNldC1ib3R0b20sMHB4KSl9CgogIC8qIC0tLS0gYmHFn2zEsWsgZWZla3RpIC0tLS0gKi8KICAuYnJhbmR7dGV4dC1hbGlnbjpjZW50ZXI7bWFyZ2luLWJvdHRvbToyOHB4fQogIC5sb2dvewogICAgcG9zaXRpb246cmVsYXRpdmU7ZGlzcGxheTppbmxpbmUtYmxvY2s7bWFyZ2luOjA7CiAgICBmb250LXNpemU6Y2xhbXAoMS42cmVtLDkuMnZ3LDIuNXJlbSk7Zm9udC13ZWlnaHQ6ODUwO2xldHRlci1zcGFjaW5nOi4wMTVlbTtsaW5lLWhlaWdodDoxLjE1O2NvbG9yOiNmZmY7d2hpdGUtc3BhY2U6bm93cmFwOwogICAgdGV4dC1zaGFkb3c6MCAwIDEwcHggcmdiYSgyNTUsNTksODcsLjY1KSwwIDAgMzBweCByZ2JhKDI1NSw1OSw4NywuMzUpOwogICAgYW5pbWF0aW9uOmdsb3cgMy40cyBlYXNlLWluLW91dCBpbmZpbml0ZTsKICB9CiAgLmxvZ286YmVmb3JlLC5sb2dvOmFmdGVye2NvbnRlbnQ6YXR0cihkYXRhLXRleHQpO3Bvc2l0aW9uOmFic29sdXRlO2xlZnQ6MDt0b3A6MDt3aWR0aDoxMDAlO292ZXJmbG93OmhpZGRlbjtwb2ludGVyLWV2ZW50czpub25lO3RleHQtc2hhZG93Om5vbmV9CiAgLmxvZ286YmVmb3Jle2NvbG9yOnZhcigtLWFjY2VudCk7dHJhbnNmb3JtOnRyYW5zbGF0ZVgoLTNweCk7Y2xpcC1wYXRoOmluc2V0KDAgMCAxMDAlIDApO2FuaW1hdGlvbjpnbGl0Y2hBIDVzIHN0ZXBzKDEpIGluZmluaXRlfQogIC5sb2dvOmFmdGVye2NvbG9yOnZhcigtLWFjY2VudDIpO3RyYW5zZm9ybTp0cmFuc2xhdGVYKDNweCk7Y2xpcC1wYXRoOmluc2V0KDEwMCUgMCAwIDApO2FuaW1hdGlvbjpnbGl0Y2hCIDVzIHN0ZXBzKDEpIGluZmluaXRlfQogIEBrZXlmcmFtZXMgZ2xvd3sKICAgIDAlLDEwMCV7dGV4dC1zaGFkb3c6MCAwIDEwcHggcmdiYSgyNTUsNTksODcsLjY1KSwwIDAgMzBweCByZ2JhKDI1NSw1OSw4NywuMzApfQogICAgNTAle3RleHQtc2hhZG93OjAgMCAxNHB4IHJnYmEoMjU1LDU5LDg3LC45MCksMCAwIDQ0cHggcmdiYSgyNTUsNTksODcsLjUwKX0KICB9CiAgQGtleWZyYW1lcyBnbGl0Y2hBewogICAgMCUsODQlLDEwMCV7Y2xpcC1wYXRoOmluc2V0KDAgMCAxMDAlIDApfQogICAgODUle2NsaXAtcGF0aDppbnNldCg4JSAwIDYyJSAwKX0gODcle2NsaXAtcGF0aDppbnNldCg1OCUgMCAxNCUgMCl9CiAgICA4OSV7Y2xpcC1wYXRoOmluc2V0KDMwJSAwIDM4JSAwKX0gOTEle2NsaXAtcGF0aDppbnNldCgwIDAgMTAwJSAwKX0gOTMle2NsaXAtcGF0aDppbnNldCg3MiUgMCA2JSAwKX0gOTUle2NsaXAtcGF0aDppbnNldCgwIDAgMTAwJSAwKX0KICB9CiAgQGtleWZyYW1lcyBnbGl0Y2hCewogICAgMCUsODUlLDEwMCV7Y2xpcC1wYXRoOmluc2V0KDEwMCUgMCAwIDApfQogICAgODYle2NsaXAtcGF0aDppbnNldCg0OCUgMCAyNCUgMCl9IDg4JXtjbGlwLXBhdGg6aW5zZXQoMTAlIDAgNjYlIDApfQogICAgOTAle2NsaXAtcGF0aDppbnNldCg2NiUgMCA4JSAwKX0gOTIle2NsaXAtcGF0aDppbnNldCgxMDAlIDAgMCAwKX0gOTQle2NsaXAtcGF0aDppbnNldCgyMiUgMCA1MiUgMCl9IDk2JXtjbGlwLXBhdGg6aW5zZXQoMTAwJSAwIDAgMCl9CiAgfQogIC5zd2VlcHtoZWlnaHQ6MnB4O3dpZHRoOm1pbigyMjBweCw2MCUpO21hcmdpbjoxMnB4IGF1dG8gMTBweDtib3JkZXItcmFkaXVzOjJweDsKICAgIGJhY2tncm91bmQ6bGluZWFyLWdyYWRpZW50KDkwZGVnLHRyYW5zcGFyZW50LHZhcigtLWFjY2VudCksdmFyKC0tYWNjZW50MiksdHJhbnNwYXJlbnQpO2JhY2tncm91bmQtc2l6ZToyMDAlIDEwMCU7YW5pbWF0aW9uOnN3ZWVwIDMuMnMgbGluZWFyIGluZmluaXRlfQogIEBrZXlmcmFtZXMgc3dlZXB7ZnJvbXtiYWNrZ3JvdW5kLXBvc2l0aW9uOjIwMCUgMH10b3tiYWNrZ3JvdW5kLXBvc2l0aW9uOi0yMDAlIDB9fQogIC50YWd7bWFyZ2luOjA7Y29sb3I6dmFyKC0tbXV0ZSk7Zm9udC1zaXplOi45cmVtO2xldHRlci1zcGFjaW5nOi4wNGVtfQoKICAvKiAtLS0tIGthcnQgLS0tLSAqLwogIC5jYXJke2JhY2tncm91bmQ6bGluZWFyLWdyYWRpZW50KDE4MGRlZyxyZ2JhKDI1NSwyNTUsMjU1LC4wMyksdHJhbnNwYXJlbnQgNDAlKSx2YXIoLS1jYXJkKTtib3JkZXI6MXB4IHNvbGlkIHZhcigtLWxpbmUpO2JvcmRlci1yYWRpdXM6MThweDtwYWRkaW5nOjIwcHg7CiAgICBib3gtc2hhZG93OjAgMCAwIDFweCByZ2JhKDI1NSw1OSw4NywuMDUpLDAgMThweCA1MHB4IHJnYmEoMCwwLDAsLjQ1KX0KICAuaGVhZHtkaXNwbGF5OmZsZXg7anVzdGlmeS1jb250ZW50OnNwYWNlLWJldHdlZW47YWxpZ24taXRlbXM6Y2VudGVyO2dhcDoxMHB4fQogIC5re2ZvbnQtc2l6ZTouODVyZW07Y29sb3I6dmFyKC0tbXV0ZSl9CiAgLmNoaXB7ZGlzcGxheTppbmxpbmUtZmxleDthbGlnbi1pdGVtczpjZW50ZXI7Z2FwOjdweDtwYWRkaW5nOjRweCAxMXB4O2JvcmRlci1yYWRpdXM6OTk5cHg7Ym9yZGVyOjFweCBzb2xpZCB2YXIoLS1saW5lKTtiYWNrZ3JvdW5kOnRyYW5zcGFyZW50O2NvbG9yOnZhcigtLW11dGUpO2ZvbnQtc2l6ZTouNzhyZW07Zm9udC13ZWlnaHQ6NjAwO2N1cnNvcjpwb2ludGVyfQogIC5jaGlwIGl7d2lkdGg6N3B4O2hlaWdodDo3cHg7Ym9yZGVyLXJhZGl1czo1MCU7YmFja2dyb3VuZDpjdXJyZW50Q29sb3I7Ym94LXNoYWRvdzowIDAgOHB4IGN1cnJlbnRDb2xvcn0KICAuY2hpcC5va3tjb2xvcjp2YXIoLS1vayk7Ym9yZGVyLWNvbG9yOnJnYmEoNzAsMjMwLDE2NiwuNSl9CiAgLmNoaXAuYmFke2NvbG9yOnZhcigtLWJhZCk7Ym9yZGVyLWNvbG9yOnJnYmEoMjU1LDE0NSw4OCwuNSl9CiAgLmN1cntmb250OjYwMCAxcmVtLzEuNCB1aS1tb25vc3BhY2UsU0ZNb25vLVJlZ3VsYXIsTWVubG8sbW9ub3NwYWNlO292ZXJmbG93LXdyYXA6YW55d2hlcmU7bWFyZ2luOjEwcHggMCAxOHB4O2NvbG9yOiNmZmZ9CiAgaHJ7Ym9yZGVyOjA7Ym9yZGVyLXRvcDoxcHggc29saWQgdmFyKC0tbGluZSk7bWFyZ2luOjAgMCAxOHB4fQoKICBsYWJlbHtkaXNwbGF5OmJsb2NrO2ZvbnQtc2l6ZTouODVyZW07Y29sb3I6dmFyKC0tbXV0ZSk7bWFyZ2luLWJvdHRvbTo3cHh9CiAgaW5wdXR7d2lkdGg6MTAwJTttaW4taGVpZ2h0OjUwcHg7cGFkZGluZzoxMnB4IDE0cHg7Ym9yZGVyLXJhZGl1czoxMnB4O2JvcmRlcjoxcHggc29saWQgdmFyKC0tbGluZSk7YmFja2dyb3VuZDp2YXIoLS1maWVsZCk7Y29sb3I6dmFyKC0taW5rKTtmb250OmluaGVyaXQ7dHJhbnNpdGlvbjpib3JkZXItY29sb3IgLjE1cyxib3gtc2hhZG93IC4xNXN9CiAgaW5wdXQ6Zm9jdXN7b3V0bGluZTowO2JvcmRlci1jb2xvcjp2YXIoLS1hY2NlbnQpO2JveC1zaGFkb3c6MCAwIDAgM3B4IHJnYmEoMjU1LDU5LDg3LC4yKX0KICBidXR0b246Zm9jdXMtdmlzaWJsZXtvdXRsaW5lOjJweCBzb2xpZCB2YXIoLS1hY2NlbnQyKTtvdXRsaW5lLW9mZnNldDoycHh9CgogIC5hY3Rpb25ze2Rpc3BsYXk6ZmxleDtnYXA6MTBweDttYXJnaW4tdG9wOjE0cHh9CiAgLmJ0bntmbGV4OjE7bWluLWhlaWdodDo1MHB4O3BhZGRpbmc6MTBweCAxNHB4O2JvcmRlci1yYWRpdXM6MTJweDtib3JkZXI6MXB4IHNvbGlkIHZhcigtLWxpbmUpO2JhY2tncm91bmQ6dHJhbnNwYXJlbnQ7Zm9udC13ZWlnaHQ6NjUwO2N1cnNvcjpwb2ludGVyfQogIC5idG4ucHJpbWFyeXtib3JkZXI6MDtjb2xvcjojZmZmO2JhY2tncm91bmQ6bGluZWFyLWdyYWRpZW50KDEzNWRlZywjZmYzYjU3LCNjNDE2M2EpO2JveC1zaGFkb3c6MCA2cHggMjRweCByZ2JhKDI1NSw1OSw4NywuMzUpfQogIC5idG46ZGlzYWJsZWR7b3BhY2l0eTouNX0KICAjbXNne21pbi1oZWlnaHQ6MS40ZW07bWFyZ2luOjEycHggMCAwO2ZvbnQtc2l6ZTouODhyZW07Y29sb3I6dmFyKC0tbXV0ZSl9CiAgI21zZy5lcnJ7Y29sb3I6dmFyKC0tYmFkKX0gI21zZy5va3tjb2xvcjp2YXIoLS1vayl9CgogIC50Z3tkaXNwbGF5OmZsZXg7YWxpZ24taXRlbXM6Y2VudGVyO2p1c3RpZnktY29udGVudDpjZW50ZXI7Z2FwOjEwcHg7d2lkdGg6MTAwJTttYXJnaW4tdG9wOjE0cHg7bWluLWhlaWdodDo1MHB4O2JvcmRlci1yYWRpdXM6MTRweDtib3JkZXI6MXB4IHNvbGlkIHJnYmEoNTEsMjI1LDI1NSwuNDUpO2JhY2tncm91bmQ6cmdiYSg1MSwyMjUsMjU1LC4wNSk7Y29sb3I6dmFyKC0tYWNjZW50Mik7Zm9udC13ZWlnaHQ6NjUwO2N1cnNvcjpwb2ludGVyfQoKICBAbWVkaWEgKHByZWZlcnMtcmVkdWNlZC1tb3Rpb246cmVkdWNlKXsKICAgIC5sb2dvLC5sb2dvOmJlZm9yZSwubG9nbzphZnRlciwuc3dlZXB7YW5pbWF0aW9uOm5vbmV9CiAgICAubG9nbzpiZWZvcmUsLmxvZ286YWZ0ZXJ7ZGlzcGxheTpub25lfQogIH0KPC9zdHlsZT4KPC9oZWFkPgo8Ym9keT4KPG1haW4+CiAgPGRpdiBjbGFzcz0iYnJhbmQiPgogICAgPGgxIGNsYXNzPSJsb2dvIiBkYXRhLXRleHQ9IkRhcmtuZXNzIExvcmQiPkRhcmtuZXNzIExvcmQ8L2gxPgogICAgPGRpdiBjbGFzcz0ic3dlZXAiIGFyaWEtaGlkZGVuPSJ0cnVlIj48L2Rpdj4KICAgIDxwIGNsYXNzPSJ0YWciPlNpdGUgYWRyZXNpPC9wPgogIDwvZGl2PgoKICA8c2VjdGlvbiBjbGFzcz0iY2FyZCI+CiAgICA8ZGl2IGNsYXNzPSJoZWFkIj4KICAgICAgPHNwYW4gY2xhc3M9ImsiPkFrdGlmIGFkcmVzPC9zcGFuPgogICAgICA8YnV0dG9uIGlkPSJjaGlwIiBjbGFzcz0iY2hpcCIgdHlwZT0iYnV0dG9uIiBhcmlhLWxhYmVsPSJFcmnFn2ltaSB5ZW5pZGVuIHRlc3QgZXQiPjxpPjwvaT48c3BhbiBpZD0iY2hpcFRleHQiPlRlc3QgZWRpbGl5b3I8L3NwYW4+PC9idXR0b24+CiAgICA8L2Rpdj4KICAgIDxwIGNsYXNzPSJjdXIiIGlkPSJjdXIiPi08L3A+CiAgICA8aHI+CiAgICA8bGFiZWwgZm9yPSJ1cmwiPlllbmkgYWRyZXM8L2xhYmVsPgogICAgPGlucHV0IGlkPSJ1cmwiIHR5cGU9InVybCIgaW5wdXRtb2RlPSJ1cmwiIGF1dG9jb21wbGV0ZT0ib2ZmIiBhdXRvY2FwaXRhbGl6ZT0ib2ZmIiBzcGVsbGNoZWNrPSJmYWxzZSIgcGxhY2Vob2xkZXI9Imh0dHBzOi8vb3JuZWsuY29tIj4KICAgIDxkaXYgY2xhc3M9ImFjdGlvbnMiPgogICAgICA8YnV0dG9uIGlkPSJwdWxsIiBjbGFzcz0iYnRuIiB0eXBlPSJidXR0b24iPlJlcG9kYW4gw6dlazwvYnV0dG9uPgogICAgICA8YnV0dG9uIGlkPSJzYXZlIiBjbGFzcz0iYnRuIHByaW1hcnkiIHR5cGU9ImJ1dHRvbiI+S2F5ZGV0PC9idXR0b24+CiAgICA8L2Rpdj4KICAgIDxwIGlkPSJtc2ciIHJvbGU9InN0YXR1cyI+PC9wPgogIDwvc2VjdGlvbj4KCiAgPGJ1dHRvbiBpZD0idGciIGNsYXNzPSJ0ZyIgdHlwZT0iYnV0dG9uIj4KICAgIDxzdmcgd2lkdGg9IjIwIiBoZWlnaHQ9IjIwIiB2aWV3Qm94PSIwIDAgMjQgMjQiIGZpbGw9ImN1cnJlbnRDb2xvciIgYXJpYS1oaWRkZW49InRydWUiPjxwYXRoIGQ9Ik0yMS41IDMuNiAyLjkgMTAuOGMtMS4zLjUtMS4zIDEuMi0uMiAxLjVsNC43IDEuNSAxLjggNS42Yy4yLjYuMS44LjcuOC41IDAgLjctLjIgMS0uNWwyLjMtMi4yIDQuOCAzLjVjLjkuNSAxLjUuMiAxLjctLjhsMy4xLTE0LjhjLjMtMS4yLS41LTEuOC0xLjMtMS4zek04LjYgMTMuMmw5LjctNi4xYy41LS4zLjktLjEuNS4ybC04LjEgNy4zLS4zIDMuMy0xLjgtNC43eiIvPjwvc3ZnPgogICAgVGVsZWdyYW0ga2FuYWzEsQogIDwvYnV0dG9uPgo8L21haW4+Cgo8c2NyaXB0Pgpjb25zdCAkID0gaWQgPT4gZG9jdW1lbnQuZ2V0RWxlbWVudEJ5SWQoaWQpOwpjb25zdCBtZW0gPSB7fTsKY29uc3QgbHMgPSB7IGdldCgpeyB0cnkgeyByZXR1cm4gbG9jYWxTdG9yYWdlLmQ7IH0gY2F0Y2goZSl7IHJldHVybiBtZW0uZDsgfSB9LCBzZXQodil7IHRyeSB7IGxvY2FsU3RvcmFnZS5kID0gdjsgfSBjYXRjaChlKXsgbWVtLmQgPSB2OyB9IH0gfTsKCi8vIEFuZHJvaWQga8O2cHLDvHPDvCB5b2tzYSB0YXJhecSxY8SxZGEgw7ZuaXpsZW1lIGnDp2luIHNhaHRlIG5lc25lCmNvbnN0IEEgPSB3aW5kb3cuQW5kcm9pZCB8fCB7CiAgZ2V0RG9tYWluOiAoKSA9PiBscy5nZXQoKSB8fCAnaHR0cHM6Ly93d3cuaGRmaWxtY2VoZW5uZW1pLm5sJywKICBmZXRjaEZyb21SZXBvOiAoKSA9PiBzZXRUaW1lb3V0KCgpID0+IHdpbmRvdy5vbkZldGNoZWQoJ2h0dHBzOi8vd3d3LmhkZmlsbWNlaGVubmVtaS5jb20nLCAnJyksIDYwMCksCiAgc2F2ZTogdSA9PiBscy5zZXQodSksCiAgb3BlblRlbGVncmFtOiAoKSA9PiB7fQp9OwoKbGV0IGN1cnJlbnQgPSAnJzsKY29uc3QgbXNnID0gKHQsIGMpID0+IHsgJCgnbXNnJykudGV4dENvbnRlbnQgPSB0IHx8ICcnOyAkKCdtc2cnKS5jbGFzc05hbWUgPSBjIHx8ICcnOyB9Owpjb25zdCBjaGlwID0gKHN0YXRlLCB0ZXh0KSA9PiB7ICQoJ2NoaXAnKS5jbGFzc05hbWUgPSAnY2hpcCAnICsgc3RhdGU7ICQoJ2NoaXBUZXh0JykudGV4dENvbnRlbnQgPSB0ZXh0OyB9OwoKYXN5bmMgZnVuY3Rpb24gcHJvYmUodXJsKXsKICBjb25zdCBjID0gbmV3IEFib3J0Q29udHJvbGxlcigpOwogIGNvbnN0IHQgPSBzZXRUaW1lb3V0KCgpID0+IGMuYWJvcnQoKSwgNjAwMCk7CiAgdHJ5IHsgYXdhaXQgZmV0Y2godXJsLCB7IG1vZGU6ICduby1jb3JzJywgY2FjaGU6ICduby1zdG9yZScsIHNpZ25hbDogYy5zaWduYWwgfSk7IHJldHVybiB0cnVlOyB9CiAgY2F0Y2goZSl7IHJldHVybiBmYWxzZTsgfQogIGZpbmFsbHkgeyBjbGVhclRpbWVvdXQodCk7IH0KfQphc3luYyBmdW5jdGlvbiB0ZXN0KCl7CiAgY29uc3QgdSA9IGN1cnJlbnQ7CiAgaWYgKCF1KSByZXR1cm4gY2hpcCgnJywgJ0FkcmVzIHlvaycpOwogIGNoaXAoJycsICdUZXN0IGVkaWxpeW9yJyk7CiAgY29uc3Qgb2sgPSBhd2FpdCBwcm9iZSh1KTsKICBpZiAodSAhPT0gY3VycmVudCkgcmV0dXJuOwogIGNoaXAob2sgPyAnb2snIDogJ2JhZCcsIG9rID8gJ0VyacWfaWxlYmlsaXInIDogJ0VyacWfaWxlbWl5b3InKTsKfQpmdW5jdGlvbiBzaG93KHUpewogIGN1cnJlbnQgPSB1IHx8ICcnOwogICQoJ2N1cicpLnRleHRDb250ZW50ID0gY3VycmVudCB8fCAnLSc7CiAgJCgndXJsJykudmFsdWUgPSBjdXJyZW50OwogIHRlc3QoKTsKfQpzaG93KEEuZ2V0RG9tYWluKCkpOwokKCdjaGlwJykub25jbGljayA9IHRlc3Q7CgppZiAodHlwZW9mIEEuaGFzVGVsZWdyYW0gPT09ICdmdW5jdGlvbicgJiYgIUEuaGFzVGVsZWdyYW0oKSkgJCgndGcnKS5zdHlsZS5kaXNwbGF5ID0gJ25vbmUnOwppZiAodHlwZW9mIEEuaGFzUmVtb3RlID09PSAnZnVuY3Rpb24nICYmICFBLmhhc1JlbW90ZSgpKSAkKCdwdWxsJykuc3R5bGUuZGlzcGxheSA9ICdub25lJzsKCi8vIEtvdGxpbiB0YXJhZsSxIGJ1IGZvbmtzaXlvbnUgw6dhxJ/EsXLEsXIKd2luZG93Lm9uRmV0Y2hlZCA9ICh1cmwsIGVycm9yKSA9PiB7CiAgJCgncHVsbCcpLmRpc2FibGVkID0gZmFsc2U7CiAgaWYgKGVycm9yKSByZXR1cm4gbXNnKGVycm9yLCAnZXJyJyk7CiAgJCgndXJsJykudmFsdWUgPSB1cmw7CiAgbXNnKCdSZXBvZGFuIMOnZWtpbGRpLiBLYXlkZXRcJ2UgYmFzYXJhayB1eWd1bGEuJywgJ29rJyk7Cn07CgokKCdwdWxsJykub25jbGljayA9ICgpID0+IHsgJCgncHVsbCcpLmRpc2FibGVkID0gdHJ1ZTsgbXNnKCfDh2VraWxpeW9yLi4uJyk7IEEuZmV0Y2hGcm9tUmVwbygpOyB9OwoKJCgnc2F2ZScpLm9uY2xpY2sgPSAoKSA9PiB7CiAgY29uc3QgdSA9ICQoJ3VybCcpLnZhbHVlLnRyaW0oKS5yZXBsYWNlKC9cLyskLywgJycpOwogIGlmICghL15odHRwcz86XC9cL1teXHMvXStcLlteXHMvXSsvLnRlc3QodSkpIHJldHVybiBtc2coJ0dlw6dlcmxpIGJpciBhZHJlcyBnaXIgKGh0dHBzOi8vLi4uKS4nLCAnZXJyJyk7CiAgQS5zYXZlKHUpOyBzaG93KHUpOyBtc2coJ0theWRlZGlsZGkuIFRhbSBnZcOnZXJsaSBvbG1hc8SxIGnDp2luIHV5Z3VsYW1hecSxIGthcGF0xLFwIHllbmlkZW4gYcOnLicsICdvaycpOwp9OwoKJCgndGcnKS5vbmNsaWNrID0gKCkgPT4gQS5vcGVuVGVsZWdyYW0oKTsKPC9zY3JpcHQ+CjwvYm9keT4KPC9odG1sPgo=";
    static final boolean DEBUG = false;
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
            if (Looper.myLooper() != Looper.getMainLooper()) {
                // saglayici olusmadan once guncel domains.json'u al
                try { DomainStore.fetchRemote(3000); } catch (Throwable t) { }
            } else {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            if (DomainStore.fetchRemote(8000) == null) DomainStore.applyLive();
                        } catch (Throwable t) { }
                    }
                }).start();
            }
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
            if (field == null) { toast("Domain hook: openSettings bulunamadi, ust sinif: " + String.valueOf(plugin.getClass().getSuperclass())); return; }
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
                    w.setBackgroundColor(0xFF09070B);
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
            DomainStore.applyLive();
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
