package ua.com.alevel.persistence.type;

import lombok.Getter;

public enum DateFormatType {
    dd_mm_yy(1),
    m_d_yyyy(2),
    mmm_d_yy(3),
    dd_mmm_yyyy_00_00(4);

    @Getter
    private final int dateFormat;

    DateFormatType(int i) {
        dateFormat=i;
    }
}
