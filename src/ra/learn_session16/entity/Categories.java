package ra.learn_session16.entity;

import java.util.Scanner;

public class Categories {
    private int catalogId;
    private String catalogName;
    private String descriptions;
    private boolean status;

    public Categories() {
    }

    public Categories(int catalogId, String catalogName, String descriptions, boolean status) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.descriptions = descriptions;
        this.status = status;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String toString() {
        return "CatalogID: " + this.catalogId + " - CatalogName: " + this.catalogName + " - Description: " + this.descriptions
                + " - Status: " + (this.status ? "Active" : "Inactive");
    }

    public void inputData(Scanner scanner) {
        System.out.println("Nhập vào tên danh mục:");
        this.catalogName = scanner.nextLine();
        System.out.println("Nhập vào mô tả danh mục:");
        this.descriptions = scanner.nextLine();
        System.out.println("Nhập vào trạng thái danh mục:");
        this.status = Boolean.parseBoolean(scanner.nextLine());
    }
}