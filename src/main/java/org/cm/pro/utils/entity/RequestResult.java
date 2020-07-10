package org.cm.pro.utils.entity;
import lombok.Getter;
import lombok.Setter;
import org.cm.pro.utils.ZTWIOMethod;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class RequestResult {
    private Map<String, List<String>> headers;
    private byte[] buffer;
    private int statusCode;
    public String getContent() throws IOException {
        return new String(buffer,"utf-8");
    }
}
