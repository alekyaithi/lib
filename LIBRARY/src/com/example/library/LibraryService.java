package com.example.library;
import java.sql.*;
import java.util.*;

public class LibraryService {

    public void addBook(String title, String author) throws SQLException {
        String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.executeUpdate();
            System.out.println("✅ Book added: " + title);
        }
    }

    public void addMember(String name) throws SQLException {
        String sql = "INSERT INTO members (name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            System.out.println("✅ Member added: " + name);
        }
    }

    public void issueBook(int bookId, int memberId) throws SQLException {
        String checkSql = "SELECT available FROM books WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && !rs.getBoolean("available")) {
                System.out.println("❌ Book already issued!");
                return;
            }
        }

        String issueSql = "INSERT INTO issued_books (book_id, member_id) VALUES (?, ?)";
        String updateSql = "UPDATE books SET available = false WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement issueStmt = conn.prepareStatement(issueSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            issueStmt.setInt(1, bookId);
            issueStmt.setInt(2, memberId);
            issueStmt.executeUpdate();

            updateStmt.setInt(1, bookId);
            updateStmt.executeUpdate();

            System.out.println("✅ Book issued (ID " + bookId + ") to Member (ID " + memberId + ")");
        }
    }

    public void returnBook(int bookId) throws SQLException {
        String deleteSql = "DELETE FROM issued_books WHERE book_id = ?";
        String updateSql = "UPDATE books SET available = true WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            deleteStmt.setInt(1, bookId);
            deleteStmt.executeUpdate();

            updateStmt.setInt(1, bookId);
            updateStmt.executeUpdate();

            System.out.println("✅ Book returned (ID " + bookId + ")");
        }
    }

    public void listBooks() throws SQLException {
        String sql = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\\n📚 Books in Library:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " +
                        rs.getString("title") + " by " +
                        rs.getString("author") + " " +
                        (rs.getBoolean("available") ? "[Available]" : "[Issued]"));
            }
        }
    }
}
