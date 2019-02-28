package com.beautyboss.slogen.config.resource.transfer;

import com.beautyboss.slogen.config.common.exceptions.ConfigException;
import org.springframework.beans.BeanUtils;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public class Transfer {

    public static <ENTITY,DO> DO transfer2DO(ENTITY entity,Class<? extends DO> clazz) {
        if(null == entity) {
            return null;
        }
        return  transfer(entity,clazz);
    }

    public static <ENTITY,DO> ENTITY transfer2Entity(DO object,Class<? extends ENTITY> clazz) {
        if(null == object) {
            return null;
        }
        return transfer(object,clazz);
    }

    public static <DEST> DEST transfer(Object src,Class<? extends DEST> clazz) {
        if(null == src) {
            return null;
        }
        try {
            DEST dest = clazz.newInstance();
            BeanUtils.copyProperties(src,dest);
            return dest;
        } catch (Exception e) {
            throw  new ConfigException();
        }
    }
}
