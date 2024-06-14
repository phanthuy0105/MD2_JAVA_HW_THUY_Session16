package ra.learn_session16.business;

import ra.learn_session16.entity.Categories;
import ra.learn_session16.entity.Product;
import ra.learn_session16.util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ProductBussiness {
    public static List<Product> findAll() {
        //1. Tạo kết nối đến CSDL --> tạo đối tượng callableStatement
        Connection conn = null;
        CallableStatement callSt = null;
        List<Product> listProduct = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_products()}");
            //2. set giá trị các tham số vào và đăng ký kiểu dữ liệu cho tham số trả ra
            //3. Thực hiện procedure và nhận kết quả --> listCategories
            ResultSet rs = callSt.executeQuery();
            listProduct = new ArrayList<>();
            while (rs.next()) {
                //Duyệt 1 dòng dữ liệu trong resultset
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setPrice(rs.getFloat("price"));
                product.setProductTitle(rs.getString("product_title"));
                product.setCatalogId(rs.getInt("catalog_id"));
                product.setProductStatus(rs.getBoolean("product_status"));
                listProduct.add(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //Đóng kết nối khi làm việc xong
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listProduct;
    }

    public static boolean create(Product product) {
        //1. Tạo đối tượng Connection và CallableStatement
        //2. Gọi procedure
        //3. Thực hiện procedure và nhận kết quả
        //4. Đóng Connection và CallableStatement
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call create_product(?,?,?,?,?,?)}");
            //Set giá trị cho tham số vào và đăng ký kiểu dữ liệu cho tham số ra
            callSt.setString(1, product.getProductId());
            callSt.setString(2, product.getProductName());
            callSt.setFloat(3, product.getPrice());
            callSt.setString(4, product.getProductTitle());
            callSt.setInt(5, product.getCatalogId());
            callSt.setBoolean(6, product.isProductStatus());
            //Thực hiện procedure
            callSt.executeUpdate();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    public static boolean updateProduct(Product product) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call update_product(?,?,?,?,?,?)}");
            callSt.setString(1, product.getProductId());
            callSt.setString(2, product.getProductName());
            callSt.setFloat(3, product.getPrice());
            callSt.setString(4, product.getProductTitle());
            callSt.setInt(5, product.getCatalogId());
            callSt.setBoolean(6, product.isProductStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    public static boolean isExistProduct(String productId) {
        //false: Mã danh mục chưa tồn tại
        //true: mã danh muục đã tồn tại
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call is_exist_product(?,?)}");
            //set giá trị tham số vào
            callSt.setString(1, productId);
            //Đăng ký kiểu dữ liệu cho tham số trả ra
            callSt.registerOutParameter(2, Types.INTEGER);
            //Thực hiện procedure
            callSt.execute();
            //Lấy giá trị tham số trả ra
            boolean result = callSt.getBoolean(2);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    public static Product getProductById(String productId) {
        Connection conn = null;
        CallableStatement callSt = null;
        Product product = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_product_by_id(?)}");
            callSt.setString(1, productId);
            ResultSet rs = callSt.executeQuery();
            product = new Product();
            if (rs.next()) {
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setPrice(rs.getFloat("price"));
                product.setProductTitle(rs.getString("product_title"));
                product.setCatalogId(rs.getInt("catalog_id"));
                product.setProductStatus(rs.getBoolean("product_status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return product;
    }

    public static boolean delete(String productId) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call delete_product(?)}");
            callSt.setString(1, productId);
            callSt.executeUpdate();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    public static List<Product> searchProduct(String product_name) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Product> listProduct = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_Product_By_ProductName(?)}");
            callSt.setString(1, product_name);
            callSt.executeQuery();
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setPrice(rs.getFloat("price"));
                product.setProductTitle(rs.getString("product_title"));
                product.setCatalogId(rs.getInt("catalog_id"));
                product.setProductStatus(rs.getBoolean("product_status"));
                listProduct.add(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listProduct;
    }

    public static List<Product> countProductByCatalog(int catalogId) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Product> listProduct = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_cnt_product_by_catalog(?,?)}");
            callSt.setInt(1, catalogId);
            callSt.registerOutParameter(2, Types.INTEGER);
            callSt.executeQuery();
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setPrice(rs.getFloat("price"));
                product.setProductTitle(rs.getString("product_title"));
                product.setCatalogId(rs.getInt("catalog_id"));
                product.setProductStatus(rs.getBoolean("product_status"));
                listProduct.add(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listProduct;
    }
}
