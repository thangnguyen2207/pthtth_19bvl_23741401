package lab3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryManager {

	private static final Path INVENTORY_FILE = Path.of("data", "inventory.csv");

	private static final Path REPORT_FILE = Path.of("data", "inventory-report.txt");

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<Product> products = new ArrayList<>();
		System.out.println("===== QUẢN LÝ TỒN KHO =====");
		System.out.print("Nhập số lượng sản phẩm: ");
		int numberOfProducts = Integer.parseInt(scanner.nextLine());
		for (int i = 1; i <= numberOfProducts; i++) {
			System.out.println();
			System.out.println("Sản phẩm thứ " + i);
			System.out.print("Nhập mã sản phẩm: ");
			String code = scanner.nextLine();
			System.out.print("Nhập tên sản phẩm: ");
			String name = scanner.nextLine();
			System.out.print("Nhập đơn giá: ");
			double price = Double.parseDouble(scanner.nextLine());
			System.out.print("Nhập số lượng: ");
			int quantity = Integer.parseInt(scanner.nextLine());
			Product product = new Product(code, name, price, quantity);
			products.add(product);
		}
		// Lưu danh sách vào CSV
		try {
			saveToCsv(products);
			System.out.println();
			System.out.println("Đã lưu danh sách vào data/inventory.csv");
		} catch (IOException e) {
			System.err.println("Không thể ghi tệp data/inventory.csv: " + e.getMessage());
			scanner.close();
			return;
		}
		// Đọc lại danh sách từ CSV
		List<Product> loadedProducts = loadFromCsv();
		System.out.println();
		System.out.println("===== DANH SÁCH SẢN PHẨM =====");
		for (Product product : loadedProducts) {
			System.out.println(product);
		}
		// Tính tổng giá trị tồn kho
		double total = calculateTotalValue(loadedProducts);
		System.out.println();
		System.out.printf("Tổng giá trị tồn kho: %.2f%n", total);
		// Tìm sản phẩm có giá trị tồn kho cao nhất
		Product highest = findHighestValueProduct(loadedProducts);
		if (highest != null) {
			System.out.println();
			System.out.println("Sản phẩm có giá trị tồn kho cao nhất:");
			System.out.println(highest);
		}
		// Ghi báo cáo
		try {
			writeReport(loadedProducts);
			System.out.println();
			System.out.println("Đã ghi báo cáo vào data/inventory-report.txt");
		} catch (IOException e) {
			System.err.println("Không thể ghi tệp data/inventory-report.txt: " + e.getMessage());
		}
		scanner.close();
	}

	/**
	 * Lưu danh sách sản phẩm vào file CSV.
	 */
	public static void saveToCsv(List<Product> products) throws IOException {

		Files.createDirectories(INVENTORY_FILE.getParent());

		try (BufferedWriter writer = Files.newBufferedWriter(INVENTORY_FILE, StandardCharsets.UTF_8)) {

			writer.write("code,name,unitPrice,quantity");
			writer.newLine();

			for (Product product : products) {
				writer.write(product.getCode() + "," + product.getName() + "," + product.getUnitPrice() + ","
						+ product.getQuantity());
				writer.newLine();
			}
		}
	}

	/**
	 * Đọc danh sách sản phẩm từ file CSV.
	 */
	public static List<Product> loadFromCsv() {

		List<Product> products = new ArrayList<>();

		if (!Files.exists(INVENTORY_FILE)) {
			System.err.println("Không tìm thấy tệp: " + INVENTORY_FILE);
			return products;
		}

		try (BufferedReader reader = Files.newBufferedReader(INVENTORY_FILE, StandardCharsets.UTF_8)) {

			String line;
			int lineNumber = 0;

			while ((line = reader.readLine()) != null) {
				lineNumber++;

				// Bỏ qua dòng tiêu đề
				if (lineNumber == 1) {
					continue;
				}

				if (line.trim().isEmpty()) {
					continue;
				}

				String[] fields = line.split(",", -1);

				// CSV phải có 4 cột
				if (fields.length != 4) {
					System.err
							.println("Lỗi tệp " + INVENTORY_FILE + " - dòng " + lineNumber + ": thiếu hoặc thừa cột.");
					continue;
				}

				try {
					String code = fields[0].trim();
					String name = fields[1].trim();

					double price = Double.parseDouble(fields[2].trim());

					int quantity = Integer.parseInt(fields[3].trim());

					Product product = new Product(code, name, price, quantity);

					products.add(product);

				} catch (NumberFormatException e) {

					System.err.println(
							"Lỗi tệp " + INVENTORY_FILE + " - dòng " + lineNumber + ": dữ liệu số không hợp lệ.");

				} catch (IllegalArgumentException e) {

					System.err.println("Lỗi tệp " + INVENTORY_FILE + " - dòng " + lineNumber + ": " + e.getMessage());
				}
			}

		} catch (IOException e) {

			System.err.println("Không thể đọc tệp " + INVENTORY_FILE + ": " + e.getMessage());
		}

		return products;
	}

	/**
	 * Tính tổng giá trị tồn kho.
	 */
	public static double calculateTotalValue(List<Product> products) {

		double total = 0;

		for (Product product : products) {
			total += product.inventoryValue();
		}

		return total;
	}

	/**
	 * Tìm sản phẩm có giá trị tồn kho cao nhất.
	 */
	public static Product findHighestValueProduct(List<Product> products) {

		if (products.isEmpty()) {
			return null;
		}

		Product highest = products.get(0);

		for (Product product : products) {

			if (product.inventoryValue() > highest.inventoryValue()) {

				highest = product;
			}
		}

		return highest;
	}

	/**
	 * Ghi báo cáo tổng hợp.
	 */
	public static void writeReport(List<Product> products) throws IOException {

		Files.createDirectories(REPORT_FILE.getParent());

		double total = calculateTotalValue(products);

		Product highest = findHighestValueProduct(products);

		try (BufferedWriter writer = Files.newBufferedWriter(REPORT_FILE, StandardCharsets.UTF_8)) {

			writer.write("BÁO CÁO TỔNG HỢP TỒN KHO");
			writer.newLine();
			writer.write("==========================");
			writer.newLine();
			writer.newLine();

			writer.write("Danh sách sản phẩm:");
			writer.newLine();

			for (Product product : products) {
				writer.write(product.toString());
				writer.newLine();
			}

			writer.newLine();

			writer.write(String.format("Tổng giá trị tồn kho: %.2f", total));
			writer.newLine();

			if (highest != null) {
				writer.write("Sản phẩm có giá trị tồn kho cao nhất:");
				writer.newLine();

				writer.write(highest.toString());
				writer.newLine();
			}
		}
	}
}
