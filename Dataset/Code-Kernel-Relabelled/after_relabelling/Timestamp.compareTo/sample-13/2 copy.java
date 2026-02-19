public class func{
public void timeSheetChecker(HttpServletRequest request,HttpServletResponse response){
                        for (int i = 0; i < 5; i++) {
                            Timestamp realTimeDate = UtilDateTime.addDaysToTimestamp(timesheetDate, i);
                            Timestamp nowStartDate = UtilDateTime.getDayStart(now);
                            if ((timesheetDate.compareTo(weekStart) <= 0) && (realTimeDate.compareTo(nowStartDate) < 0)) {
                                List<GenericValue> timeEntryList = timesheetMap.getRelated("TimeEntry", UtilMisc.toMap("partyId", partyId, "timesheetId",timesheetId, "fromDate",realTimeDate), null, false);
                                List<GenericValue> emplLeaveList = EntityQuery.use(delegator).from("EmplLeave").where("partyId", partyId, "fromDate", realTimeDate).cache(true).queryList();
                                if (UtilValidate.isEmpty(timeEntryList) && UtilValidate.isEmpty(emplLeaveList)) {
                                    Map<String, Object> noEntryMap = new HashMap<String, Object>();
                                    noEntryMap.put("timesheetId", timesheetId);
                                    noTimeEntryList.add(noEntryMap);
                                    break;
                                }
                            }
                        }
}
}
