public class func{
public void latestObs_shouldReturnTheMostRecentObsGivenThePassedConceptId(){
        Obs earliestWeight = functions.latestObs(5089);
        Assert.assertEquals(61, earliestWeight.getValueNumeric().intValue());
        Assert.assertEquals("2008-08-19", df.format(earliestWeight.getObsDatetime()));
}
}
