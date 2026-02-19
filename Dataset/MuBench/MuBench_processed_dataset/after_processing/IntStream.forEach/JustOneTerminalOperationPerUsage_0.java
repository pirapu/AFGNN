public class func{
public void correctUsage() {
    int[] array = new int[]{1, 2};
    IntStream stream = Arrays.stream(array);
    stream.forEach(System.out::println);
    array[0] = 2;
    stream = Arrays.stream(array);
    stream.forEach(System.out::println);
}
}
