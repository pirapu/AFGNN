public class func{
void pattern(HttpMethod method) throws AuthenticationException {
  AuthState authstate = method.getProxyAuthState();
  if (authstate.isPreemptive()) {
    authstate.invalidate();
    authstate.setAuthRequested(true);
  }
}
}
