public class func{
public void createAsciidoctor(){
        asciidoctor = es.submit(new Callable<Asciidoctor>() {
            public Asciidoctor call() throws Exception {
                return Asciidoctor.Factory.create();
            }
        });
        es.shutdown();
}
}
