package com.patrones.u2;

public class PdfHeaderFooter implements ReportHeaderFooter {

    @Override
    public String renderHeader(String institutionName) {
        return "[PDF:encabezado] " + institutionName;
    }

    @Override
    public String renderFooter(int pageNumber) {
        return "[PDF:pie] Pagina " + pageNumber + "\n";
    }
}