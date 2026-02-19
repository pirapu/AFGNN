public void correctUsage() {
    IntStream.iterate(0, i -> i + 1)
        .limit()
        .forEach(System.out::println);
}
