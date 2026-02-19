void pattern(HttpConnection conn, HttpMethod method, HttpState state) throws IOException, HttpException {
    while (true) {
        if (!conn.isOpen()) {
            conn.open();
            if (conn.isProxied() && conn.isSecure() && !(method instanceof ConnectMethod)) {
                // initialize connect method...
            }
        }
        try {
            method.execute(state, conn);
            break;
        } catch (HttpRecoverableException httpre) {
         
        }
    }

}

