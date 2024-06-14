package ra.learn_session16.presentation;

import ra.learn_session16.business.CategoriesBussiness;
import ra.learn_session16.business.ProductBussiness;
import ra.learn_session16.entity.Categories;
import ra.learn_session16.entity.Product;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Scanner;

public class Ecommerce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("*************Ecommerce***********");
            System.out.println("1. Quản lý danh mục");
            System.out.println("2. Quản lý sản phẩm");
            System.out.println("3. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    Ecommerce.displayCatalogMenu(scanner);
                    break;
                case 2:
                    Ecommerce.displayProductMenu(scanner);
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.err.println("Vui lòng chọn từ 1-3");
            }
        } while (true);
    }

    public static void displayCatalogMenu(Scanner scanner) {
        boolean isExist = true;
        do {
            System.out.println("**********CATEGORIES MANAGEMENT**********");
            System.out.println("1. Danh sách danh mục");
            System.out.println("2. Thêm mới danh mục");
            System.out.println("3. Cập nhật danh mục");
            System.out.println("4. Xóa danh mục");
            System.out.println("5. Tìm kiếm danh mục theo tên danh mục");
            System.out.println("6. Sắp xếp danh mục theo tên giảm dần");
            System.out.println("7. Tìm kiếm danh mục theo mã danh mục");
            System.out.println("8. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    Ecommerce.displayListCategories();
                    break;
                case 2:
                    Ecommerce.createCatalog(scanner);
                    break;
                case 3:
                    Ecommerce.updateCatalog(scanner);
                    break;
                case 4:
                    Ecommerce.deleteCatalog(scanner);
                    break;
                case 5:
                    Ecommerce.searchCatalogByCatalogName(scanner);
                    break;
                case 6:
                    Ecommerce.sortCatalogByCatalogName();
                    break;
                case 7:
                    Ecommerce.searchCatalogByCatalogId(scanner);
                    break;
                case 8:
                    isExist = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-8");
            }
        } while (isExist);
    }

    public static void displayListCategories() {
        List<Categories> listCategories = CategoriesBussiness.findAll();
        listCategories.stream().forEach(System.out::println);
    }

    public static void createCatalog(Scanner scanner) {
        Categories catalogNew = new Categories();
        catalogNew.inputData(scanner);
        boolean result = CategoriesBussiness.create(catalogNew);
        if (result) {
            System.out.println("Thêm mới danh mục thành công");
        } else {
            System.err.println("Có lỗi trong quá trình thêm mới danh mục");
        }
    }

    public static void updateCatalog(Scanner scanner) {
        System.out.println("Nhập vào mã danh mục cần cập nhật:");
        int catalogId = Integer.parseInt(scanner.nextLine());
        if (CategoriesBussiness.isExistCatalog(catalogId)) {
            //Đã tồn tại danh mục --> cập nhật danh mục
            //- Lấy thông tin danh mục cần cập nhật
            Categories catalogUpdate = CategoriesBussiness.getCatalogById(catalogId);
            boolean isExist = true;
            do {
                System.out.println("Chọn thông tin cần cập nhật:");
                System.out.println("1. Cập nhật tên danh mục");
                System.out.println("2. Cập nhật mô tả danh mục");
                System.out.println("3. Cập nhật trạng thái danh mục");
                System.out.println("4. Thoát");
                System.out.println("Lựa chọn của bạn:");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Nhập vào tên danh mục cần cập nhật:");
                        catalogUpdate.setCatalogName(scanner.nextLine());
                        break;
                    case 2:
                        System.out.println("nhập vào mô tả danh mục cập nhật:");
                        catalogUpdate.setDescriptions(scanner.nextLine());
                        break;
                    case 3:
                        System.out.println("Nhập vào trạng thái danh mục cập nhật:");
                        catalogUpdate.setStatus(Boolean.parseBoolean(scanner.nextLine()));
                        break;
                    default:
                        isExist = false;

                }
            } while (isExist);
            //Cập nhật danh mục
            boolean result = CategoriesBussiness.updateCatalog(catalogUpdate);
            if (result){
                System.out.println("Cập nhật danh mục thành công");
            }else{
                System.out.println("Có lỗi trong quá trình cập nhật danh mục");
            }
        } else {
            //Không tồn tại mã danh mục
            System.err.println("Mã danh mục không tồn tại");
        }
    }

    public static void deleteCatalog(Scanner scanner) {
        System.out.println("Nhập vào mã danh mục cần xóa:");
        int catalogId = Integer.parseInt(scanner.nextLine());
        //1. Kiểm tra danh mục có tồn tại không
        boolean isExist = CategoriesBussiness.isExistCatalog(catalogId);
        //2. Nếu tồn tại thực hiện xóa
        if (isExist){
            boolean result = CategoriesBussiness.delete(catalogId);
            if (result){
                System.out.println("Xóa danh mục thành công");
            }else{
                System.err.println("Có lỗi trong quá trình xóa danh mục");
            }
        }else{
            System.err.println("Mã danh mục không tồn tại");
        }
    }

    public static void searchCatalogByCatalogName(Scanner scanner) {
        System.out.println("Nhập vào tên danh mục cần tìm:");
        String catalogNameSearch = scanner.nextLine();

        System.out.println("KẾT QUẢ TÌM KIẾM:");
        List<Categories> listCategories =  CategoriesBussiness.searchCatalog(catalogNameSearch);
        listCategories.stream().forEach(System.out::println);
    }

    public static void sortCatalogByCatalogName() {
        System.out.println("Danh mục theo tên giảm dần: ");
        List<Categories> list =  CategoriesBussiness.sortCatalogByName();
        list.stream().forEach(System.out::println);
    }

    public static void searchCatalogByCatalogId(Scanner scanner) {
        System.out.println("Nhập vào mã danh mục cần tìm:");
        int catalogIdSearch = Integer.parseInt(scanner.nextLine());

        System.out.println("KẾT QUẢ TÌM KIẾM:");
        List<Categories> listCategories =  CategoriesBussiness.searchCatalogByCatalogId(catalogIdSearch);
        listCategories.stream().forEach(System.out::println);
    }

    public static void displayProductMenu(Scanner scanner) {
        boolean isExist = true;
        do {
            System.out.println("**********PRODUCT MANAGEMENT**********");
            System.out.println("1. Danh sách sản phẩm");
            System.out.println("2. Thêm mới sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Tìm kiếm sản phẩm theo tên sản phẩm");
            System.out.println("6. Thống kê sản phẩm theo danh mục sản phẩm");
            System.out.println("7. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    Ecommerce.displayListProduct();
                    break;
                case 2:
                    Ecommerce.createProduct(scanner);
                    break;
                case 3:
                    Ecommerce.updateProduct(scanner);
                    break;
                case 4:
                    Ecommerce.deleteProduct(scanner);
                    break;
                case 5:
                    Ecommerce.searchProductByProductName(scanner);
                    break;
                case 6:
                    Ecommerce.countProductByCatalog(scanner);
                    break;
                case 7:
                    isExist = false;
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-7");
            }
        } while (isExist);
    }

    public static void displayListProduct() {
        List<Product> listProduct = ProductBussiness.findAll();
        listProduct.stream().forEach(System.out::println);
    }

    public static void createProduct(Scanner scanner) {
        Product productNew = new Product();
        productNew.inputData(scanner);
        boolean result = ProductBussiness.create(productNew);
        if (result) {
            System.out.println("Thêm mới sản phẩm thành công");
        } else {
            System.err.println("Có lỗi trong quá trình thêm mới sản phẩm");
        }
    }

    public static void updateProduct(Scanner scanner) {
        System.out.println("Nhập vào mã sản phẩm cần cập nhật:");
        String productId = scanner.nextLine();
        if (ProductBussiness.isExistProduct(productId)) {
            Product productUpdate = ProductBussiness.getProductById(productId);
            boolean isExist = true;
            do {
                System.out.println("Chọn thông tin cần cập nhật:");
                System.out.println("1. Cập nhật mã sản phẩm");
                System.out.println("2. Cập nhật tên sản phẩm");
                System.out.println("3. Cập nhật giá sản phẩm");
                System.out.println("4. Cập nhật tiêu đề sản phẩm");
                System.out.println("5. Cập nhật danh mục sản phẩm");
                System.out.println("6. Cập nhật trạng thái sản phẩm");
                System.out.println("7. Thoát");
                System.out.println("Lựa chọn của bạn: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Nhập vào mã sản phẩm:");
                        productUpdate.setProductId(scanner.nextLine());
                        break;
                    case 2:
                        System.out.println("Nhập vào tên sản phẩm:");
                        productUpdate.setProductName(scanner.nextLine());
                        break;
                    case 3:
                        System.out.println("Nhập vào giá sản phẩm:");
                        productUpdate.setPrice(Float.parseFloat(scanner.nextLine()));
                        break;
                    case 4:
                        System.out.println("Nhập vào tiêu đề sản phẩm:");
                        productUpdate.setProductTitle(scanner.nextLine());
                        break;
                    case 5:
                        System.out.println("Nhập vào danh mục sản phẩm:");
                        productUpdate.setCatalogId(Integer.parseInt(scanner.nextLine()));
                        break;
                    case 6:
                        System.out.println("Nhập vào trạng thái sản phẩm cập nhật:");
                        productUpdate.setProductStatus(Boolean.parseBoolean(scanner.nextLine()));
                        break;
                    default:
                        isExist = false;

                }
            } while (isExist);
            //Cập nhật sản phẩm
            boolean result = ProductBussiness.updateProduct(productUpdate);
            if (result){
                System.out.println("Cập nhật sản phẩm thành công");
            }else{
                System.out.println("Có lỗi trong quá trình cập nhật sản phẩm");
            }
        } else {
            //Không tồn tại mã sản phẩm
            System.err.println("Mã sản phẩm không tồn tại");
        }
    }

    public static void deleteProduct(Scanner scanner) {
        System.out.println("Nhập vào mã sản phẩm cần xóa:");
        String productId = scanner.nextLine();
        //1. Kiểm tra sản phẩm có tồn tại không
        boolean isExist = ProductBussiness.isExistProduct(productId);
        //2. Nếu tồn tại thực hiện xóa
        if (isExist){
            boolean result = ProductBussiness.delete(productId);
            if (result){
                System.out.println("Xóa sản phẩm thành công");
            }else{
                System.err.println("Có lỗi trong quá trình xóa sản phẩm");
            }
        }else{
            System.err.println("Mã sản phẩm không tồn tại");
        }
    }

    public static void searchProductByProductName(Scanner scanner) {
        System.out.println("Nhập vào tên danh mục cần tìm:");
        String productNameSearch = scanner.nextLine();

        System.out.println("KẾT QUẢ TÌM KIẾM:");
        List<Product> listProduct =  ProductBussiness.searchProduct(productNameSearch);
        listProduct.stream().forEach(System.out::println);
    }

    public static void countProductByCatalog(Scanner scanner) {
        System.out.println("Nhập vào mã danh mục cần thống kê:");
        int catalogId = Integer.parseInt(scanner.nextLine());

        System.out.println("KẾT QUẢ TÌM KIẾM:");
        List<Product> listProduct = ProductBussiness.countProductByCatalog(catalogId);
        IntSummaryStatistics result = listProduct.stream().mapToInt(product -> product.getCatalogId()).summaryStatistics();
        System.out.println("Tổng số sản phẩm: " + result.getCount());
    }
}