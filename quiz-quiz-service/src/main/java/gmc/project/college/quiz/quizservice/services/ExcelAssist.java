package gmc.project.college.quiz.quizservice.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import gmc.project.college.quiz.quizservice.daos.QuestionDao;
import gmc.project.college.quiz.quizservice.entities.OptionEntity;
import gmc.project.college.quiz.quizservice.entities.QuestionEntity;
import gmc.project.college.quiz.quizservice.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelAssist {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<UserEntity> listUsers;
	private List<OptionEntity> listOptions;
	
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "question", "option1", "option2", "option3", "option4", "correct" };

	public ExcelAssist(List<UserEntity> listUsers, List<OptionEntity> listOptions) {
      this.listUsers = listUsers;
      this.listOptions = listOptions;
      workbook = new XSSFWorkbook();
	}

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<QuestionEntity> excelToTutorials(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet("Sheet1");

			log.error("Count: " + workbook.getNumberOfSheets());
			log.error("Sheet Name: " + sheet.getSheetName());

			Iterator<Row> rows = sheet.iterator();

			List<QuestionEntity> questions = new ArrayList<>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				QuestionEntity question = new QuestionEntity();
				
				OptionEntity option1 = new OptionEntity();
				OptionEntity option2 = new OptionEntity();
				OptionEntity option3 = new OptionEntity();
				OptionEntity option4 = new OptionEntity();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					question.setQuestionId("QN-" + UUID.randomUUID());

					switch (cellIdx) {
					case 0:
						question.setValue(currentCell.getStringCellValue());
						break;

					case 1:
						option1.setOptionId("OPT-" + UUID.randomUUID());
						option1.setValue(currentCell.getStringCellValue());
						option1.setIsCorrect(false);
						question.getOptions().add(option1);
						break;

					case 2:
						option2.setOptionId("OPT-" + UUID.randomUUID());
						option2.setValue(currentCell.getStringCellValue());
						option2.setIsCorrect(false);
						question.getOptions().add(option2);
						break;
						
					case 3:
						option3.setOptionId("OPT-" + UUID.randomUUID());
						option3.setValue(currentCell.getStringCellValue());
						option3.setIsCorrect(false);
						question.getOptions().add(option3);
						break;

					case 4:
						option4.setOptionId("OPT-" + UUID.randomUUID());
						option4.setValue(currentCell.getStringCellValue());
						option4.setIsCorrect(true);
						question.getOptions().add(option4);
						break;

					default:
						break;
					}

					cellIdx++;
				}

				questions.add(question);
			}

			workbook.close();

			return questions;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Students");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "Roll_No", style);
		createCell(row, 1, "Full Name", style);
		createCell(row, 2, "Total Score", style);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (UserEntity student : listUsers) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, student.getRollNo(), style);
			createCell(row, columnCount++, student.getInitial() + " " + student.getFirstName() + " " + student.getLastName(), style);
			createCell(row, columnCount++, student.getTotalMarks(), style);

		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}

}