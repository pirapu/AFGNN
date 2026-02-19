public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Dependency1");
        list.add("Dependency2");

       if (indexToAccess >= 0 && indexToAccess < list.size()) {
            return list.get(indexToAccess);
        } else {
            return "Index " + indexToAccess + " is out of bounds for the list size of " + list.size();
        }
    }