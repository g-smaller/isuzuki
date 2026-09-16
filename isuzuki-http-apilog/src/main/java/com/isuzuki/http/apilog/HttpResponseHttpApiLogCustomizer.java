package com.isuzuki.http.apilog;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class HttpResponseHttpApiLogCustomizer implements  HttpApiLogCustomizer {

    @Override
    public void customize(HttpServletRequest request, HttpServletResponse response, HttpApiLogBuilder builder) {
        builder.statusCode(response.getStatus())
                .response(readResponse(response));
    }

    private String readResponse(HttpServletResponse response) {
        if (!ContentCachingResponseWrapper.class.isInstance(response)) {
            return "";
        }

        String contentDisposition = response.getHeader("Content-Disposition");
        boolean isDownload = contentDisposition != null
                && contentDisposition.toLowerCase().startsWith("attachment");
        if (isDownload) {
            return "File Download!";
        }

        int contentSize = ((ContentCachingResponseWrapper)response).getContentSize();
        // 1024 * 64
        int limit = 65536;
        if (contentSize > limit) {
            return "Response Length is too large: limit: "+ limit + " length: " + contentSize;
        }
        String contentType = response.getContentType();
        if (StringUtils.isBlank(contentType)) {
            return "";
        }
        byte[] contentAsByteArray = ((ContentCachingResponseWrapper)response).getContentAsByteArray();
        return new String(contentAsByteArray);
    }

}
