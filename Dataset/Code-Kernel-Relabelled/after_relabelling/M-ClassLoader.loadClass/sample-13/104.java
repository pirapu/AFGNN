public class func{
public void process(@Nonnull ClassLoader classLoader,@Nonnull Class<?> type,@Nonnull String line){
                String transformationClassName = parts[1].trim();
                    Class<?> transformationClass = classLoader.loadClass(transformationClassName);
                    transformations.put(annotationClassName, (Class<? extends ASTTransformation>) transformationClass);
}
}
