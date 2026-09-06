package com.patrones.u2;

import java.util.List;

public class ExcelReportBody implements ReportBody {

    @Override
    public String render(List<GradeRecord> records) {
        StringBuilder out = new StringBuilder();
        out.append("[EXCEL:cuerpo]\n");

        for (GradeRecord record : records) {
            out.append(record.getStudentId())
               .append(" | ")
               .append(record.getStudentName())
               .append(" | ")
               .append(record.getCourseCode())
               .append(" | ")
               .append(record.getGrade())
               .append("\n");
        }

        return out.toString();
    }
}