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

public class CategoriesBussiness {
    public static List<Categories> findAll() {
        //1. Tạo kết nối đến CSDL --> tạo đối tượng callableStatement
        Connection conn = null;
        CallableStatement callSt = null;
        List<Categories> listCategories = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_categories()}");
            //2. set giá trị các tham số vào và đăng ký kiểu dữ liệu cho tham số trả ra
            //3. Thực hiện procedure và nhận kết quả --> listCategories
            ResultSet rs = callSt.executeQuery();
            listCategories = new ArrayList<>();
            while (rs.next()) {
                //Duyệt 1 dòng dữ liệu trong resultset
                Categories catalog = new Categories();
                catalog.setCatalogId(rs.getInt("catalog_id"));
                catalog.setCatalogName(rs.getString("catalog_name"));
                catalog.setDescriptions(rs.getString("descriptions"));
                catalog.setStatus(rs.getBoolean("catalog_status"));
                listCategories.add(catalog);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //Đóng kết nối khi làm việc xong
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listCategories;
    }

    public static boolean create(Categories catalog) {
        //1. Tạo đối tượng Connection và CallableStatement
        //2. Gọi procedure
        //3. Thực hiện procedure và nhận kết quả
        //4. Đóng Connection và CallableStatement
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call create_catalog(?,?,?)}");
            //Set giá trị cho tham số vào và đăng ký kiểu dữ liệu cho tham số ra
            callSt.setString(1, catalog.getCatalogName());
            callSt.setString(2, catalog.getDescriptions());
            callSt.setBoolean(3, catalog.isStatus());
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

    public static boolean updateCatalog(Categories catalog) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call update_catalog(?,?,?,?)}");
            callSt.setInt(1, catalog.getCatalogId());
            callSt.setString(2, catalog.getCatalogName());
            callSt.setString(3, catalog.getDescriptions());
            callSt.setBoolean(4, catalog.isStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    public static boolean isExistCatalog(int catalogId) {
        //false: Mã danh mục chưa tồn tại
        //true: mã danh muục đã tồn tại
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call is_exist_catalog(?,?)}");
            //set giá trị tham số vào
            callSt.setInt(1, catalogId);
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

    public static Categories getCatalogById(int catalogId) {
        Connection conn = null;
        CallableStatement callSt = null;
        Categories catalog = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_catalog_by_id(?)}");
            callSt.setInt(1, catalogId);
            ResultSet rs = callSt.executeQuery();
            catalog = new Categories();
            if (rs.next()) {
                catalog.setCatalogId(rs.getInt("catalog_id"));
                catalog.setCatalogName(rs.getString("catalog_name"));
                catalog.setDescriptions(rs.getString("descriptions"));
                catalog.setStatus(rs.getBoolean("catalog_status"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return catalog;
    }

    public static boolean delete(int catalogId) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call delete_catalog(?)}");
            callSt.setInt(1, catalogId);
            callSt.executeUpdate();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    public static List<Categories> searchCatalog(String catalog_name) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Categories> listCategories = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_Catalog_By_CatalogName(?)}");
            callSt.setString(1, catalog_name);
            callSt.executeQuery();
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Categories catalog = new Categories();
                catalog.setCatalogId(rs.getInt("catalog_id"));
                catalog.setCatalogName(rs.getString("catalog_name"));
                catalog.setDescriptions(rs.getString("descriptions"));
                catalog.setStatus(rs.getBoolean("catalog_status"));
                listCategories.add(catalog);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listCategories;
    }

    public static List<Categories> sortCatalogByName() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Categories> listCategories = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call sortCatalogByName()}");
            callSt.executeQuery();
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Categories catalog = new Categories();
                catalog.setCatalogId(rs.getInt("catalog_id"));
                catalog.setCatalogName(rs.getString("catalog_name"));
                catalog.setDescriptions(rs.getString("descriptions"));
                catalog.setStatus(rs.getBoolean("catalog_status"));
                listCategories.add(catalog);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listCategories;
    }

    public static List<Categories> searchCatalogByCatalogId(int catalog_id) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Categories> listCategories = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call search_Catalog_By_CatalogId(?)}");
            callSt.setInt(1, catalog_id);
            callSt.executeQuery();
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Categories catalog = new Categories();
                catalog.setCatalogId(rs.getInt("catalog_id"));
                catalog.setCatalogName(rs.getString("catalog_name"));
                catalog.setDescriptions(rs.getString("descriptions"));
                catalog.setStatus(rs.getBoolean("catalog_status"));
                listCategories.add(catalog);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listCategories;
    }
}