package com.patrones.u2;

public class HtmlHeaderFooter implements ReportHeaderFooter {

    @Override
    public String renderHeader(String institutionName) {
        return "[HTML:encabezado] " + institutionName;
    }

    @Override
    public String renderFooter(int pageNumber) {
        return "[HTML:pie] Pagina " + pageNumber + "\n";
    }
}