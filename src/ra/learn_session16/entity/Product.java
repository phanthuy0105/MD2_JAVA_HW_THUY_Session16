package ra.learn_session16.entity;

import java.util.Scanner;

public class Product {
    private String productId;
    private String productName;
    private float price;
    private String productTitle;
    private int catalogId;
    private boolean productStatus;

    public Product() {
    }

    public Product(String productId, String productName, float price, String productTitle, int catalogId, boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productTitle = productTitle;
        this.catalogId = catalogId;
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public String toString() {
        return "ProductID: " + this.productId + " - ProductName: " + this.productName + " - Price: " + this.price
                + " - ProductTitle: " + this.productTitle + " - CatalogID: " + this.catalogId + " - Status: " + (this.productStatus ? "Active" : "Inactive");
    }

    public void inputData(Scanner scanner) {
        System.out.println("Nhập vào mã sản phẩm: ");
        this.productId = scanner.nextLine();
        System.out.println("Nhập vào tên sản phẩm: ");
        this.productName = scanner.nextLine();
        System.out.println("Nhập vào giá sản phẩm: ");
        this.price = Float.parseFloat(scanner.nextLine());
        System.out.println("Nhập vào tiêu đề sản phẩm: ");
        this.productTitle = scanner.nextLine();
        System.out.println("Chọn danh mục sản phẩm");
        this.catalogId = Integer.parseInt(scanner.nextLine());
        System.out.println("Nhập vào trạng thái danh mục:");
        this.productStatus = Boolean.parseBoolean(scanner.nextLine());
    }
}
