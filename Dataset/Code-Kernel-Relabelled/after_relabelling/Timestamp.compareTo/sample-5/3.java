public class func{
public void createManufacturingOrder(String facilityId,Date date,String workEffortName,String description,String routingId,String orderId,String orderItemSeqId,String shipGroupSeqId,String shipmentId,boolean useSubstitute,boolean ignoreSupplierProducts){
                    if (maxEndDate == null) {
                        maxEndDate = childEndDate;
                    }
                    if (childEndDate != null && maxEndDate.compareTo(childEndDate) < 0) {
                        maxEndDate = childEndDate;
                    }
                    if (childProductionRunId != null) {
                        childProductionRuns.add(childProductionRunId);
                    }
            Timestamp startDate = UtilDateTime.toTimestamp(UtilDateTime.toDateTimeString(date));
}
}
