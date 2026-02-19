public class func{
void pattern(HttpMethod method) throws AuthenticationException {
  AuthState authstate = method.getHostAuthState();
  if (authstate.isPreemptive()) {
    authstate.invalidate();
  }
}
}
