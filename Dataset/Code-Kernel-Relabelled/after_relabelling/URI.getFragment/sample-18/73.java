public class func{
public void append(String base,Map<String,?> query,Map<String,String> keys,boolean fragment){
        if (keys != null && keys.containsKey(key)) {
          name = keys.get(key);
        }
        values.append(name + "={" + key + "}");
      if (values.length() > 0) {
        template.fragment(values.toString());
      }
      UriComponents encoded = template.build().expand(query).encode();
      builder.fragment(encoded.getFragment());
      template.fragment(redirectUri.getFragment());
      UriComponents encoded = template.build().expand(query).encode();
      builder.query(encoded.getQuery());
    return builder.build().toUriString();
}
}
