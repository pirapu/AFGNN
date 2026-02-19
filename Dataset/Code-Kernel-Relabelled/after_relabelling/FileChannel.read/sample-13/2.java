public class func{
public void testAddRecord(){
      long offset = Long.parseLong(parts[0]);
      int len = Integer.parseInt(parts[1]);
      ByteBuffer bb = ByteBuffer.wrap(gz);
      int amt = fc.read(bb, offset);
      assertEquals(amt,len);
      ByteArrayInputStream bais = new ByteArrayInputStream(gz);
      GZIPMemberSeries gzms = new GZIPMemberSeries(new SimpleStream(bais));
      GZIPSeriesMember m = gzms.getNextMember();
      m.skipMember();
}
}
