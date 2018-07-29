package cn.acheng1314.base.utils.okHi.model;


/**
 * Http请求中的参数(包括请求头参数)封装
 *
 * @author kymjs (http://www.kymjs.com/) on 12/17/15.
 */
public class HttpParamsEntry implements Comparable<HttpParamsEntry> {
    public String k;
    public String v;

    @Override
    public boolean equals(Object o) {
        if (o instanceof HttpParamsEntry) {
            return k.equals(((HttpParamsEntry) o).k);
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        return k.hashCode();
    }

    public HttpParamsEntry(String key, String value) {
        k = key;
        v = value;
    }

    @Override
    public int compareTo(HttpParamsEntry another) {
        if (k == null) {
            return -1;
        } else {
            return k.compareTo(another.k);
        }
    }
}
