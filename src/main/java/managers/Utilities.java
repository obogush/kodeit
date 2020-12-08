package managers;

import com.github.javafaker.Faker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;


public class Utilities {
    private static XSSFSheet worksheet;
    private static Cell cell;
    private static FileInputStream inputStream; // open file.xlsx
    private static FileOutputStream outputStream;
    private static File anyFile;
    private static Path anyFilePath;
    private static XSSFWorkbook workbook;

    // wait for web elements until they become visible; timeout set for 5 seconds, but can be changed as needed
    public static void waitForVisibility(WebDriverWait wait, WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Locator problem!");
            // divert this method to another
        }
    }

    /**
     * java docs
     * create a new Excel workbook in given directory= path + fileName
     *
     * @param path
     * @param fileName_xlsx
     * @throws IOException
     */
    public static void createWorkbook(String path, String fileName_xlsx, String sheetName) {
        // src/main/resources/
        // String path_FileName="src/main/resources/file.xlsx";
        String path_FileName = path + fileName_xlsx; // why we concatenate? Because we want the file added to certain path
        workbook = new XSSFWorkbook();
        XSSFSheet tab1 = workbook.createSheet(sheetName);                   // pages/sheets of the workbook
        XSSFSheet tab2 = workbook.createSheet(sheetName + "123");  // another sheet of the workbook for testing purposes!
        try {
            outputStream = new FileOutputStream(path_FileName); // save the file in the given directory/path
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally { // below, we are just checking the file.xlsx if it exists(present of not) in the correct location
            anyFilePath = Path.of(path + fileName_xlsx); // get the file location
            anyFile = anyFilePath.toFile(); // access the file using anyFile location with toFile method
            boolean fileValidity = anyFile.exists(); // is file generated? True or false
            // anyFile =
            if (fileValidity) {
                System.out.println(fileName_xlsx + " is created successfully!" + "\n" + "Location: " + path);
            } else {
                System.out.println("Try again!" + "\n" + "Add a few characters for file name" + "\n" + "provide a correct file path");
                System.out.println("sheetName" + tab1.getSheetName() + "\n" + tab2.getSheetName() + "are invalid"
                        + "\n" + "character count MUST be greater than or equal to 1 and less than or equal to 31");
                System.out.println(fileName_xlsx + " is not created!!!" + "\n" + "Location: " + path);
            }
        }
    }

    /**
     * java docs
     * Provided @path and @sheet name, write a message in given row and column
     *
     * @param path
     * @param sheetName
     * @param value
     * @param row
     * @param column
     * @throws IOException
     */
    public static void writeExCell(String path, String sheetName, String value, int row, int column) throws IOException {
        //String safeName = WorkbookUtil.createSafeSheetName("[?BeehabCoding*?]");
        //define the file path where excel sheet will be generated
        File file = new File((path));
        inputStream = new FileInputStream(path);
        workbook = new XSSFWorkbook(inputStream);
        //create the blank workbook, another method here for example purposes
        //  Workbook wb= new XSSFWorkbook();
        //create the blank excel sheet
        // Sheet sh= wb.createSheet("Beehab");
        Sheet sheet = workbook.getSheet(sheetName);
        //define the location(row/column) in the excel workbook where writing take place
        Row rw = sheet.createRow(row);
        Cell cell = rw.createCell(column);
        //define input type
        // cell.setCellType(CellType.STRING);

        //define the data value
        cell.setCellValue(value);
        //write the workbook

        outputStream = new FileOutputStream(file);
        workbook.write(outputStream);

    }

    //This method is to read the test information from the exact cell,
    // in this we are passing parameters as Row num and Col num
    public static String getCellValue(String path, int RowNum, int ColNum) throws Exception {
        //path = "src/test/resources/test.xlsx";
        loadingExcelFile(path, "sheet"); // loading the given excel file and opening default sheet

        try {
            cell = worksheet.getRow(RowNum).getCell(ColNum);
            String cellValue = cell.getStringCellValue();
            return cellValue;

        } catch (Exception e) {
            return ""; // no need to return or catch exception because we have it in the method signature
        }

    }

    //This method is to load the File path and to open the Excel file,
    // Pass Excel Path and Sheetname as Arguments to this method
    // so we can set it when we use this method
    public static void loadingExcelFile(String Path, String SheetName) {

        try {
            // Open the Excel file using the given path
            FileInputStream ExcelFile = new FileInputStream(Path);

            // Access the required test data sheet
            workbook = new XSSFWorkbook(ExcelFile);
            worksheet = workbook.getSheet(SheetName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    Create a funny file name using java faker and random number
//    public static String CreateAuser(String file, int num) {
//        Random random = new Random();
//        Faker faker = new Faker();
//        file = faker.funnyName().name();
//        num = random.nextInt(100);
//       return file+"_"+num;
//    }

    // Create a file name using java faker and random number
    public static String generateFileName() {
        Random random = new Random();
        Faker faker = new Faker();
        String file = faker.shakespeare().asYouLikeItQuote();
        int num = random.nextInt(100);
        return file + "_" + num;
    }
}
