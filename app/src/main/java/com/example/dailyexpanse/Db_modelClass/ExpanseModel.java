package com.example.dailyexpanse.Db_modelClass;

public class ExpanseModel {
    private String id;
    private String expense_amount;
    private String expense_date;
    private String expense_time;
    private String expense_item;
    private String expense_image;


    public ExpanseModel() {
    }

    public ExpanseModel(String id, String expense_amount, String expense_date, String expense_time, String expense_item, String expense_image) {
        this.id = id;
        this.expense_amount = expense_amount;
        this.expense_date = expense_date;
        this.expense_time = expense_time;
        this.expense_item = expense_item;
        this.expense_image = expense_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpense_amount() {
        return expense_amount;
    }

    public void setExpense_amount(String expense_amount) {
        this.expense_amount = expense_amount;
    }

    public String getExpense_date() {
        return expense_date;
    }

    public void setExpense_date(String expense_date) {
        this.expense_date = expense_date;
    }

    public String getExpense_time() {
        return expense_time;
    }

    public void setExpense_time(String expense_time) {
        this.expense_time = expense_time;
    }

    public String getExpense_item() {
        return expense_item;
    }

    public void setExpense_item(String expense_item) {
        this.expense_item = expense_item;
    }

    public String getExpense_image() {
        return expense_image;
    }

    public void setExpense_image(String expense_image) {
        this.expense_image = expense_image;
    }
}
