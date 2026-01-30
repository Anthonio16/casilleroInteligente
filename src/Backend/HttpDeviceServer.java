package Backend;

import Infrastructure.AppException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class HttpDeviceServer {

    private static final String DEFAULT_DEVICE_KEY = "123";
    private static final String DEVICE_KEY = System.getenv().getOrDefault("DEVICE_KEY", DEFAULT_DEVICE_KEY);

    // Si quieres deshabilitar auth para pruebas rápidas: setea AUTH_ENABLED=false
    private static final boolean AUTH_ENABLED = true;

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try { port = Integer.parseInt(args[0]); } catch (Exception ignored) {}
        }

        try {
            DeviceBridge bridge = new DeviceBridge();
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            server.createContext("/health", ex -> sendText(ex, 200, "OK"));
            server.createContext("/api/pin",   new DeviceHandler(bridge, "PIN"));
            server.createContext("/api/token", new DeviceHandler(bridge, "TOKEN"));

            server.setExecutor(null);
            server.start();

            System.out.println("[HTTP] Device server running on http://0.0.0.0:" + port);
            System.out.println("[HTTP] Health: GET /health -> OK");
            System.out.println("[HTTP] Auth: X-Device-Key " + (AUTH_ENABLED ? "ENABLED" : "DISABLED"));
            if (AUTH_ENABLED) {
                System.out.println("[HTTP] Expected DEVICE_KEY='" + DEVICE_KEY + "' len=" + DEVICE_KEY.length());
            }

        } catch (Exception e) {
            System.out.println("No se pudo iniciar el servidor HTTP: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class DeviceHandler implements HttpHandler {
        private final DeviceBridge bridge;
        private final String mode;

        DeviceHandler(DeviceBridge bridge, String mode) {
            this.bridge = bridge;
            this.mode = mode;
        }

        @Override
        public void handle(HttpExchange ex) throws IOException {
            try {
                if (!"POST".equalsIgnoreCase(ex.getRequestMethod())) {
                    sendText(ex, 405, "ERR:METHOD_NOT_ALLOWED");
                    return;
                }

                if (AUTH_ENABLED) {
                    Headers h = ex.getRequestHeaders();
                    String incoming = firstNonNull(h.getFirst("X-Device-Key"), h.getFirst("x-device-key"));
                    incoming = incoming == null ? null : incoming.trim();

                    if (incoming == null || !incoming.equals(DEVICE_KEY)) {
                        System.out.println("[HTTP] Unauthorized: incoming X-Device-Key='" + incoming + "'");
                        sendText(ex, 401, "ERR:UNAUTHORIZED");
                        return;
                    }
                }

                String body = readAll(ex.getRequestBody());
                Map<String, String> params = parseParams(ex, body);

                String idCas = firstNonNull(params.get("idCasillero"), params.get("casillero"), params.get("id"));
                String idUsr = firstNonNull(params.get("idUsuario"), params.get("usuario"));

                String value;
                if ("PIN".equals(mode)) value = firstNonNull(params.get("pin"), params.get("value"));
                else value = firstNonNull(params.get("token"), params.get("value"));

                if (idCas == null || idUsr == null || value == null) {
                    sendText(ex, 400, "ERR:BAD_REQUEST");
                    return;
                }

                String msg = mode + ";" + idCas + ";" + idUsr + ";" + value;
                String result = bridge.onMessage(msg);

                sendText(ex, 200, result);

            } catch (AppException ae) {
                sendText(ex, 500, "ERR:APP_EXCEPTION");
            } catch (Exception e) {
                sendText(ex, 500, "ERR:SERVER_EXCEPTION");
            }
        }
    }

    private static Map<String, String> parseParams(HttpExchange ex, String body) {
        Map<String, String> params = new HashMap<>();

        // Content-Type: application/x-www-form-urlencoded
        if (body != null && !body.isBlank()) {
            String[] pairs = body.split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    params.put(urlDecode(kv[0]), urlDecode(kv[1]));
                }
            }
        }

        // query string (?a=b)
        String q = ex.getRequestURI().getRawQuery();
        if (q != null && !q.isBlank()) {
            String[] pairs = q.split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    params.putIfAbsent(urlDecode(kv[0]), urlDecode(kv[1]));
                }
            }
        }

        return params;
    }

    private static String urlDecode(String s) {
        try { return URLDecoder.decode(s, StandardCharsets.UTF_8); } catch (Exception e) { return s; }
    }

    private static String readAll(InputStream in) throws IOException {
        if (in == null) return "";
        byte[] data = in.readAllBytes();
        return new String(data, StandardCharsets.UTF_8);
    }

    private static void sendText(HttpExchange ex, int code, String text) throws IOException {
        byte[] out = (text == null ? "" : text).getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        ex.sendResponseHeaders(code, out.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(out); }
    }

    @SafeVarargs
    private static <T> T firstNonNull(T... values) {
        if (values == null) return null;
        for (T v : values) if (v != null) return v;
        return null;
    }
}
