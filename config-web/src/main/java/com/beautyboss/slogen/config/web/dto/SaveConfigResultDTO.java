package com.beautyboss.slogen.config.web.dto;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Data
public class SaveConfigResultDTO {

    private Long configId;

    private Map<Integer,SaveConfigInstanceResultDTO> envResult;

    public static SaveConfigResultDTO create(Long configId) {
        SaveConfigResultDTO dto = new SaveConfigResultDTO();
        dto.setConfigId(configId);
        dto.setEnvResult(Maps.newHashMap());
        return dto;
    }

}
