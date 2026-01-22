package org.finos.waltz.web.endpoints.extracts;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.finos.waltz.web.MimeTypes;
import org.jooq.DSLContext;
import org.jooq.JSONFormat;
import org.jooq.Result;
import org.jooq.Select;
import org.jooq.lambda.tuple.Tuple3;
import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.finos.waltz.common.EnumUtilities.readEnum;
import static org.finos.waltz.common.ListUtilities.map;
import static org.finos.waltz.web.endpoints.extracts.ExtractorUtilities.convertExcelToByteArray;
import static org.finos.waltz.web.endpoints.extracts.ExtractorUtilities.sanitizeSheetName;
import static org.jooq.lambda.fi.util.function.CheckedConsumer.unchecked;
import static org.jooq.lambda.tuple.Tuple.tuple;

public class DirectQueryBasedExtractorUtilities {
    public DirectQueryBasedExtractorUtilities() {
        super();
    }

    public static Object writeExtract(String suggestedFilenameStem,
                                  Select<?> qry,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
        ExtractFormat extractFormat = parseExtractFormat(request);
        return writeSupportedExtract(extractFormat, suggestedFilenameStem, qry, response);

    }

    public static ExtractFormat parseExtractFormat(HttpServletRequest request) {
        return readEnum(
                request.getParameter("format"),
                ExtractFormat.class,
                v -> ExtractFormat.CSV);
    }

    public static Object writeSupportedExtract(ExtractFormat extractFormat,
                                         String suggestedFilenameStem,
                                         Select<?> qry,
                                         HttpServletResponse response) throws IOException{
        switch (extractFormat) {
            case XLSX:
                return writeAsExcel(suggestedFilenameStem, qry, response);
            case CSV:
                return writeAsCSV(suggestedFilenameStem, qry, response);
            case JSON:
                return writeAsJson(qry, response);
            default:
                throw new IllegalArgumentException("Cannot write extract using unknown format: " + extractFormat);
        }
    }

    public static String writeAsJson(Select<?> qry,
                               HttpServletResponse response) {
        response.setContentType(MimeTypes.APPLICATION_JSON_UTF_8);
        return qry
                .fetch()
                .formatJSON(JSONFormat.DEFAULT_FOR_RECORDS
                        .header(false)
                        .recordFormat(JSONFormat.RecordFormat.OBJECT));
    }

    public static Object writeAsCSV(String suggestedFilenameStem,
                              Select<?> qry,
                              HttpServletResponse response) {
        String csv = qry.fetch().formatCSV();
        response.setContentType(MimeTypes.TEXT_PLAIN);
        response.setHeader("Content-disposition", "attachment; filename=" + suggestedFilenameStem + ".csv");
        return csv;
    }

    private static Object writeAsExcel(String suggestedFilenameStem,
                                       Select<?> qry,
                                       HttpServletResponse response) throws IOException {
        SXSSFWorkbook workbook = new SXSSFWorkbook(2000);
        SXSSFSheet sheet = workbook.createSheet(ExtractorUtilities.sanitizeSheetName(suggestedFilenameStem));

        writeExcelHeader(qry, sheet);
        writeExcelBody(qry, sheet, null);

        int endFilterColumnIndex = qry.fields().length == 0
                ? 0
                : qry.fields().length - 1;

        sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, endFilterColumnIndex));
        sheet.createFreezePane(0, 1);

        return writeExcelToResponse(suggestedFilenameStem, response, workbook);
    }

    private static HttpServletResponse writeExcelToResponse(String suggestedFilenameStem,
                                                                               HttpServletResponse httpResponse,
                                                                               SXSSFWorkbook workbook) throws IOException {
        byte[] bytes = ExtractorUtilities.convertExcelToByteArray(workbook);


        httpResponse.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        httpResponse.setHeader("Content-Disposition", "attachment; filename=" + suggestedFilenameStem + ".xlsx");
        httpResponse.setHeader("Content-Transfer-Encoding", "7bit");

        httpResponse.setContentLength(bytes.length);
        httpResponse.getOutputStream().write(bytes);
        httpResponse.getOutputStream().flush();
        httpResponse.getOutputStream().close();

        return httpResponse;
    }

    private static void writeExcelBody(Select<?> qry,
                                       SXSSFSheet sheet,
                                       DSLContext dsl) {
        AtomicInteger rowCounter = new AtomicInteger(1);

        Result<?> records = dsl == null
                ? qry.fetch()
                : dsl.fetch(dsl.renderInlined(qry));

        int colCount = qry.fields().length;
        records.forEach(r -> {
            int rowNum = rowCounter.getAndIncrement();
            Row row = sheet.createRow(rowNum);
            for (int col = 0; col < colCount; col++) {
                Cell cell = row.createCell(col);
                Object val = r.get(col);
                if (val != null) {
                    cell.setCellValue(val.toString());
                }
            }
        });
    }


    public static void writeExcelHeader(Select<?> qry, SXSSFSheet sheet) {
        Row headerRow = sheet.createRow(0);
        AtomicInteger colNum = new AtomicInteger();
        qry.fieldStream().forEach(f -> {
            Cell cell = headerRow.createCell(colNum.getAndIncrement());
            cell.setCellValue(Objects.toString(f.getName()));
        });
    }

    public static Object writeReportResults(HttpServletResponse httpResponse, Tuple3<ExtractFormat, String, byte[]> reportResult) throws IOException {
        String templateName = reportResult.v2;


        switch (reportResult.v1) {
            case CSV:
                httpResponse.setContentType(MimeTypes.TEXT_PLAIN);
                httpResponse.setHeader("Content-disposition", "attachment; filename=" + templateName + ".csv");
                break;
            case JSON:
                httpResponse.setHeader("Content-Type", MimeTypes.APPLICATION_JSON_UTF_8);
                break;
            case XLSX:
                httpResponse.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                httpResponse.setHeader("Content-Disposition", "attachment; filename=" + templateName + ".xlsx");
                httpResponse.setHeader("Content-Transfer-Encoding", "7bit");
                break;
            default:
                break;
        }

        byte[] bytes = reportResult.v3;
        httpResponse.setContentLength(bytes.length);
        httpResponse.getOutputStream().write(bytes);
        httpResponse.getOutputStream().flush();
        httpResponse.getOutputStream().close();
        return httpResponse;
    }

    public static Tuple3<ExtractFormat, String, byte[]> formatReport(ExtractFormat format,
                                                              String reportName,
                                                              List<List<Object>> reportRows,
                                                              List<String> headers) throws IOException {
        switch (format) {
            case XLSX:
                return tuple(format, reportName, mkExcelReport(reportName, reportRows, headers));
            case CSV:
                return tuple(format, reportName, mkCSVReport(reportRows, headers));
            default:
                throw new UnsupportedOperationException("This report does not support export format: " + format);
        }
    }

    public static byte[] mkCSVReport(List<List<Object>> reportRows, List<String> headers) throws IOException {
        StringWriter writer = new StringWriter();
        CsvListWriter csvWriter = new CsvListWriter(writer, CsvPreference.EXCEL_PREFERENCE);

        csvWriter.write(headers);
        reportRows.forEach(unchecked(row -> csvWriter.write(simplify(row))));
        csvWriter.flush();

        return writer.toString().getBytes();
    }

    public static List<Object> simplify(List<Object> row) {
        return map(row, value -> {
            if (value == null) return null;
            if (value instanceof Optional) {
                return ((Optional<?>) value).orElse(null);
            } else {
                return value;
            }
        });
    }

    public static byte[] mkExcelReport(String reportName, List<List<Object>> reportRows, List<String> headers) throws IOException {
        SXSSFWorkbook workbook = new SXSSFWorkbook(2000);
        SXSSFSheet sheet = workbook.createSheet(sanitizeSheetName(reportName));

        int colCount = writeExcelHeader(sheet, headers);
        writeExcelBody(reportRows, sheet);

        sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, colCount));
        sheet.createFreezePane(0, 1);

        return convertExcelToByteArray(workbook);
    }

    public static int writeExcelHeader(SXSSFSheet sheet, List<String> headers) {
        Row headerRow = sheet.createRow(0);
        AtomicInteger colNum = new AtomicInteger();

        headers.forEach(hdr -> writeExcelHeaderCell(headerRow, colNum, hdr));

        return colNum.get();
    }

    public static void writeExcelHeaderCell(Row headerRow, AtomicInteger colNum, String text) {
        Cell cell = headerRow.createCell(colNum.getAndIncrement());
        cell.setCellValue(text);
    }

    public static int writeExcelBody(List<List<Object>> reportRows, SXSSFSheet sheet) {
        AtomicInteger rowNum = new AtomicInteger(1);
        reportRows.forEach(values -> {
            Row row = sheet.createRow(rowNum.getAndIncrement());
            AtomicInteger colNum = new AtomicInteger(0);
            for (Object value : values) {
                Object v = value instanceof Optional
                        ? ((Optional<?>) value).orElse(null)
                        : value;

                int nextColNum = colNum.getAndIncrement();

                if (v == null) {
                    row.createCell(nextColNum);
                } else if (v instanceof Number) {
                    Cell cell = row.createCell(nextColNum, CellType.NUMERIC);
                    cell.setCellValue(((Number) v).doubleValue());
                } else {
                    Cell cell = row.createCell(nextColNum);
                    cell.setCellValue(Objects.toString(v));
                }

            }
        });
        return rowNum.get();
    }
}
