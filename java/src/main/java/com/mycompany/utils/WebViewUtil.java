package org.mdback.util;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebViewUtil {

    private static final Logger log = LoggerFactory.getLogger(WebViewUtil.class);

    public static Boolean isWebView (HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent != null && userAgent.contains("ModernDance-WebView");
    }
}
