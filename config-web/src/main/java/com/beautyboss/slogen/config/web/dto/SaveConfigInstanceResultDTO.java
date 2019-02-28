package com.beautyboss.slogen.config.web.dto;

import lombok.Data;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Data
public class SaveConfigInstanceResultDTO {

    private boolean result;

    // 发生错误的情况下msg保存错误的原因
    private String msg;

    public static SaveConfigInstanceResultDTO create(boolean result,String msg) {
        SaveConfigInstanceResultDTO resultDTO = new SaveConfigInstanceResultDTO();
        resultDTO.setResult(result);
        resultDTO.setMsg(msg);
        return resultDTO;
    }

}
