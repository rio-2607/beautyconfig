package com.beautyboss.slogen.config.service.enums;

/**
 * Author : Slogen
 * Date   : 2019/2/10
 */
public enum ConfigTypeEnum {

    String(100,"string"),
    Integer(101,"number"),
    Float(102,"float"),
    Double(103,"double"),
    Boolean(104,"boolean"),
    List(105,"list"),
    Map(106,"map");


    private int value;

    private String label;

    ConfigTypeEnum(int value,String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }


}
