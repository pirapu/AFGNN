public class func{
public void correctUsage() {
    IntStream.iterate(0, i -> i + 1)
        .limit(10)
        .forEach(System.out::println);
}
}
