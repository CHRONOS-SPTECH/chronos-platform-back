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

            // IMPORTANTE: Começa na linha 2 (índice 2 é a terceira linha física do Excel, pois a linha 1 e 2 são cabeçalhos)
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Vamos ler até 5 dias da semana na mesma linha (Segunda a Sexta)
                // Cada dia pula de 6 em 6 colunas
                // Segunda começa na col B (índice 1), Terça na col H (índice 7), Quarta na col N (índice 13)...
                for (int colBase = 1; colBase < sheet.getRow(1).getLastCellNum(); colBase += 6) {

                    // Verifica se a célula de Data e a de Turma daquele bloco estão preenchidas
                    if (hasDadosNoBloco(row, colBase)) {

                        String data = getCelulaString(row.getCell(colBase));       // B, H, N...
                        String horario = getCelulaString(row.getCell(colBase + 1)); // C, I, O...
                        String turma = getCelulaString(row.getCell(colBase + 2));   // D, J, P...
                        String materia = getCelulaString(row.getCell(colBase + 3)); // E, K, Q...
                        String professor = getCelulaString(row.getCell(colBase + 4));// F, L, R...

                        // Guarda a aula encontrada naquele bloco específico da linha
                        linhas.add(new LinhaPlanilhaDTO(i + 1, data, horario, materia, professor, turma));
                    }
                }
            }
        }
        return java.util.Collections.unmodifiableList(linhas);
    }

    private static boolean hasDadosNoBloco(Row row, int colBase) {
        // O bloco tem dados se a célula da data e da turma não estiverem vazias
        Cell celulaData = row.getCell(colBase);
        Cell celulaTurma = row.getCell(colBase + 2);
        return celulaData != null && !getCelulaString(celulaData).isEmpty()
                && celulaTurma != null && !getCelulaString(celulaTurma).isEmpty();
    }

    private static String getCelulaString(Cell cell) {
        if (cell == null) return "";

        // Se o Excel formatou a célula como Data ou Número nativo, evitamos que mude o tipo bruscamente
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                return sdf.format(cell.getDateCellValue());
            }
            // Trata horários formatados nativamente como número no Excel (Ex: 19:00)
            if (cell.getNumericCellValue() < 1.0) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
                return sdf.format(cell.getDateCellValue());
            }
        }

        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }
}