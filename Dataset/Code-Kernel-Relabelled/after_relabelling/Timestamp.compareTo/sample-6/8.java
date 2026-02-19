public class func{
public void add(final PatchSetApproval ca){
    approvals.add(ca);
    final Timestamp g = ca.getGranted();
    if (g != null && g.compareTo(sortOrder) < 0) {
      sortOrder = g;
    }
    if (ca.getValue() != 0) {
      hasNonZero = 1;
    }
}
}
