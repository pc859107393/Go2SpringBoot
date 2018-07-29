package cn.acheng1314.base.utils.okHi.model;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;
import okio.Okio;
import okio.Source;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by pc on 2017/8/9.
 */
public class MyPostBody extends RequestBody {

    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public void writeTo(BufferedSink sink) {

    }


    public static RequestBody create(final MediaType contentType, final byte[] content) {
        if (content == null) throw new NullPointerException("content == null");

        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return content.length;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(new ByteArrayInputStream(content));
                    ByteString boundary = ByteString.encodeUtf8(UUID.randomUUID().toString());
                    byte[] CRLF = {'\r', '\n'};
                    sink.write(boundary);
                    sink.write(CRLF);
                    sink.writeAll(source);
                    sink.write(CRLF);
                    sink.write(boundary);
                    sink.write(CRLF);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }

}
