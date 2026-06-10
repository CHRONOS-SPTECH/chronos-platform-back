package chronos.tech.application.util;

import chronos.tech.application.dto.request.LinhaPlanilhaDTO;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelProcessor {

    public static List<LinhaPlanilhaDTO> extrairDados(MultipartFile file) throws Exception {
        List<LinhaPlanilhaDTO> linhas = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Pula as duas primeiras linhas de cabeçalho
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Varre a linha em blocos de 6 colunas (Segunda a Sexta)
                for (int colBase = 1; colBase < sheet.getRow(1).getLastCellNum(); colBase += 6) {

                    if (hasDadosNoBloco(row, colBase)) {
                        String data = getCelulaString(row.getCell(colBase));       // Coluna B, H, N...
                        String horario = getCelulaString(row.getCell(colBase + 1)); // Coluna C, I, O...
                        String turma = getCelulaString(row.getCell(colBase + 2));   // Coluna D, J, P...
                        String materia = getCelulaString(row.getCell(colBase + 3)); // Coluna E, K, Q...
                        String professor = getCelulaString(row.getCell(colBase + 4));// Coluna F, L, R...

                        linhas.add(new LinhaPlanilhaDTO(i + 1, data, horario, materia, professor, turma));
                    }
                }
            }
        }
        return java.util.Collections.unmodifiableList(linhas);
    }

    private static boolean hasDadosNoBloco(Row row, int colBase) {
        Cell celulaData = row.getCell(colBase);
        Cell celulaTurma = row.getCell(colBase + 2);

        return celulaData != null && !getCelulaString(celulaData).isEmpty()
                && celulaTurma != null && !getCelulaString(celulaTurma).isEmpty();
    }

    private static String getCelulaString(Cell cell) {
        if (cell == null) return "";

        if (cell.getCellType() == CellType.NUMERIC) {
            // Formatação de data (dd/MM/yyyy)
            if (DateUtil.isCellDateFormatted(cell)) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                return sdf.format(cell.getDateCellValue());
            }
            // Formatação de horário (HH:mm)
            if (cell.getNumericCellValue() < 1.0) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
                return sdf.format(cell.getDateCellValue());
            }
        }

        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }
}