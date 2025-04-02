# Income Expense Manager

![License](https://img.shields.io/badge/License-MIT-blue.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-green.svg)

**Income Expense Manager** is a simple web application built with Spring Boot and a lightweight HTML/CSS/JavaScript frontend. It helps small businesses or individuals manage stock, track finances, and generate invoices with PDF export functionality. The project integrates a backend API with a static frontend, served from a single Spring Boot application.

---

## Features

- **Stock Management**:
    - Add purchases (item name, vendor, quantity, cost price).
    - Record sales (item name, vendor, quantity, selling price).
- **Finance Tracking**:
    - Log income and expenses (category, amount, description).
    - Calculate net financial position.
- **Invoice Management**:
    - Create invoices with customer details and multiple items.
    - Generate and download invoices as PDF files.
- **Single-Page Interface**:
    - Responsive navbar to switch between Stock, Finance, and Invoice sections.
    - Client-side JavaScript for dynamic UI updates.

---

## Tech Stack

- **Backend**: Spring Boot 3.4.4, Java 21
- **Frontend**: HTML, CSS, JavaScript (served from `src/main/resources/static/`)
- **PDF Generation**: iText 7
- **Database**: MySQL (configurable to other databases)
- **Build Tool**: Maven

---

## Prerequisites

Before running the project, ensure you have the following installed:
- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads) (optional, for cloning the repo)

---

## Setup Instructions

### Clone the Repository
```bash
git clone https://github.com/ashishkumar-17/Income-Expense-Manager.git
cd Income-Expense-Manager
```

### Build the Project
```bash
mvn clean install
```

### Run the Application
```bash
mvn spring-boot:run
```

- The app will start on `http://localhost:8080/`.

---

## Usage

1. **Access the App**:
    - Open your browser and go to `http://localhost:8080/`.
    - The default view is the Stock section.

2. **Stock Management**:
    - **Add Purchase**: Enter item details and submit to record a purchase.
    - **Add Sale**: Log a sale with selling price and quantity.

3. **Finance Tracking**:
    - **Add Income/Expense**: Input financial transactions and submit.
    - **Calculate Net**: Click the "Calculate Net" button to see the net balance.

4. **Invoice Management**:
    - **Create Invoice**: Fill in customer details and add items, then submit.
    - **Generate PDF**: After creating an invoice, click "Generate PDF" to download it.
    - PDFs are saved to `src/main/resources/static/invoices/` and accessible via `http://localhost:8080/invoices/invoice_{id}.pdf`.

---

## Project Structure

```
Income-Expense-Manager/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/manager/
│   │   │       ├── controller/     # REST API controllers
|   |   |       ├── dto/            # DTOs classes
│   │   │       ├── exception/      # Exceptions classes
|   |   |       ├── mapper/         # Mapper classes
│   │   │       ├── model/          # Entity classes (e.g., Invoice, Stock)
│   │   │       ├── repository/     # JPA repositories
│   │   │       ├── service/        # Business logic
│   │   │       └── IncomeExpenseManagerApplication.java  # Main app
│   │   └── resources/
│   │       ├── static/                 # Frontend files
│   │       │   ├── index.html          # Main webpage
│   │       │   ├── style.css           # Styling
│   │       │   ├── script.js           # Client-side logic
│   │       │   └── company-logo.png    # Logo image
│   │       └── application.properties  # Config (e.g., MySQL database)
├── pom.xml  # Maven dependencies
└── README.md
```

---

## Configuration

- **Database**: Uses MySQL database by default. Modify `application.properties` to use H2/PostgreSQL:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/income_expense_db
  spring.datasource.username=root
  spring.datasource.password=YOUR_PASSWORD
  spring.jpa.hibernate.ddl-auto=update
  ```

[//]: # (- **PDF Output**: PDFs are saved to `src/main/resources/static/invoices/`. For production, consider a configurable directory &#40;see below&#41;.)

---

## Development Notes

[//]: # (- **PDF Access**: In development, PDFs are accessible via `http://localhost:8080/invoices/invoice_{id}.pdf`. For production, update `InvoiceService` to use a runtime directory and configure a resource handler:)

[//]: # (  ```java)

[//]: # (  @Value&#40;"${pdf.output.directory:./generated_pdfs}"&#41;)

[//]: # (  private String pdfOutputDir;)

[//]: # ()
[//]: # (  public String generateInvoice&#40;Long id&#41; throws IOException {)

[//]: # (      File file = new File&#40;pdfOutputDir + "/invoices/invoice_" + id + ".pdf"&#41;;)

[//]: # (      // ... PDF generation logic ...)

[//]: # (      return "/invoices/invoice_" + id + ".pdf";)

[//]: # (  })

[//]: # (  ```)

- **Frontend**: The UI is static and relies on JavaScript for interactivity. Consider migrating to a framework like React for scalability.

---

## Known Issues

- **PDF is not accessible**: Can't figure out how to access pdf through webpage. 

[//]: # (- **PDF in Production**: Writing to `static/` won’t work in a JAR. Use a byte array response or external storage instead.)

---

## Contributing

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

---
## Contact

For questions or suggestions, feel free to reach out:
- GitHub: [ashishkumar-17](https://github.com/ashishkumar-17)

[//]: # (- Email: [ashishkumar.govt@gmail.com] &#40;replace with your email if desired&#41;)

---

### Screenshots
*(You can add these later by taking screenshots of the app)*

- **Stock Section**: ![Stock](screenshots/stock.png)
- **Finance Section**: ![Finance](screenshots/finance.png)
- **Invoice PDF**: ![Invoice](screenshots/invoice.png)

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---
