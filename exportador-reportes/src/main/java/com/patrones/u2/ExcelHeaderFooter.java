package com.patrones.u2;

public class ExcelHeaderFooter implements ReportHeaderFooter {

    @Override
    public String renderHeader(String institutionName) {
        return "[EXCEL:encabezado] " + institutionName;
    }

    @Override
    public String renderFooter(int pageNumber) {
        return "[EXCEL:pie] Pagina " + pageNumber + "\n";
    }
}