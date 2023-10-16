package de.muenchen.oss.praktikumsplaner.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
public class ExcelImportException extends RuntimeException {
    private List<ExcelImportExceptionInfo> exceptionInfos;

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ExcelImportExceptionInfo {
        private int row;
        private String columName;
        private String value;
    }
}
