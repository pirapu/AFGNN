public class func{
public void writeDividend(CSVPrinter printer,AccountTransaction transaction,DateFormat dateFormat){
        printer.print(dateFormat.format(transaction.getDate()));
        printer.print(CSVExporter.escapeNull(transaction.getSecurity().getIsin()));
        printer.print(CSVExporter.escapeNull(transaction.getSecurity().getName()));
        printer.print("Dividende");
        printer.print(Values.Amount.format(transaction.getAmount()));
        printer.print("");
        printer.println();
}
}
