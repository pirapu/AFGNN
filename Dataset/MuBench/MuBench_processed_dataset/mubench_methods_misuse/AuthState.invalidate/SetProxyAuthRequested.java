void pattern(HttpMethod method) throws AuthenticationException {
  AuthState authstate = method.getProxyAuthState();
  
    authstate.invalidate();
    authstate.setAuthRequested(true);
  
}
