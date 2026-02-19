public class func{
int comparePaths(Object o1, Object o2) {
    List<String> path1 = 
        Model.getModelManagementHelper().getPathList(o1);
    Collections.reverse(path1);
    List<String> path2 = 
        Model.getModelManagementHelper().getPathList(o2);
    Collections.reverse(path2);
    Iterator<String> i2 = path2.iterator();
    Iterator<String> i1 = path1.iterator();
    int caseSensitiveComparison = 0;
    while (i2.hasNext()) {
        String name2 = i2.next();
        if (!i1.hasNext()) {
            return -1;
        }
        String name1 = i1.next();
        int comparison;
        if (name1 == null) {
            if (name2 == null) {
                comparison = 0; 
            } else {
                comparison = -1;
            }
        } else if (name2 == null) {
            comparison = 1;
        } else {
            comparison = collator.compare(name1, name2);
        }
        if (comparison != 0) {
            return comparison;
        }
        if (caseSensitiveComparison == 0) {
            if (name1 == null) {
                if (name2 == null) {
                    caseSensitiveComparison = 0;
                } else {
                    caseSensitiveComparison = -1;
                }
            } else if (name2 == null) {
                caseSensitiveComparison = 1;
            } else {
                caseSensitiveComparison = name1.compareTo(name2);
            }
        }
    }
    if (i2.hasNext()) {
        return 1;
    }
    if (caseSensitiveComparison != 0) {
        return caseSensitiveComparison;
    }
    return o1.toString().compareTo(o2.toString());
}
}
