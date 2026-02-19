public class func{
public void writeURI(PrintWriter w,URI u,String msg){
        if (DEBUG.META) writeField(w, "hashCode",       Integer.toHexString(u.hashCode()));
        writeField(w, "scheme",               u.getScheme());
        writeField(w, "scheme-specific",      u.getSchemeSpecificPart(), u.getRawSchemeSpecificPart());
        writeField(w, "authority",            u.getAuthority(), u.getRawAuthority());
        writeField(w, "userInfo",             u.getUserInfo(), u.getRawUserInfo());
        writeField(w, "host",                 u.getHost());
        if (u.getPort() != -1)
            writeField(w, "port",  u.getPort());
        writeField(w, "path",         u.getPath(), u.getRawPath());
        writeField(w, "query",        u.getQuery(), u.getRawQuery());
        writeField(w, "fragment",     u.getFragment(), u.getRawFragment());
}
}
