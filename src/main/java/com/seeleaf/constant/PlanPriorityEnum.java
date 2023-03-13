package com.seeleaf.constant;

public enum PlanPriorityEnum {
    NORMAL(0,"正常"),
    PRIOR(1, "优先"),
    IMPORTANT(2, "重要，紧急");


    private int value;
    private String text;

    public static PlanPriorityEnum getEnumByValue(Integer value){
        if (value == null){
            return null;
        }
        PlanPriorityEnum[] values = PlanPriorityEnum.values();
        for (PlanPriorityEnum planPriorityEnum: values){
            if (planPriorityEnum.getValue() == value){
                return planPriorityEnum;
            }
        }
        return null;
    }

    PlanPriorityEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
