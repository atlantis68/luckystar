package com.luckystar.web.web.rest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.luckystar.web.domain.LaborUnion;
import com.luckystar.web.domain.User;
import com.luckystar.web.domain.UserInfoBoard;
import com.luckystar.web.domain.WorkTimeBoard;
import com.luckystar.web.repository.LaborUnionRepository;
import com.luckystar.web.repository.UserInfoBoardRepository;
import com.luckystar.web.repository.UserRepository;
import com.luckystar.web.repository.WorkTimeBoardRepository;
import com.luckystar.web.security.SecurityUtils;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class DashboardResource {
    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);


    @Autowired
    private UserInfoBoardRepository userInfoBoardRepository;
    @Autowired
    private WorkTimeBoardRepository workTimeBoardRepository;
    @Autowired
    private LaborUnionRepository laborUnionRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/chicken-infos-board")
    @Timed
    public ResponseEntity<List<UserInfoBoard>> getAllChickenInfosBoardToHtml(Long laborUnionId, String day, String searchCondition, @ApiParam Pageable pageable) {
    	log.info("{} request {}, laborUnionId = {}, day = {}, searchCondition = {}", 
    			SecurityUtils.getCurrentUserLogin(), "getAllChickenInfosBoardToHtml", laborUnionId, day, searchCondition);
    	
    	List<UserInfoBoard> list = getAllChickenInfosBoard(laborUnionId, day, searchCondition, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @GetMapping("/chicken-infos-excel")
    @Timed
    public void getAllChickenInfosBoardToExcel(Long laborUnionId, String day, String searchCondition, 
    		@ApiParam Pageable pageable, HttpServletRequest request, HttpServletResponse response) {
    	log.info("{} request {}, laborUnionId = {}, day = {}, searchCondition = {}", 
    			SecurityUtils.getCurrentUserLogin(), "getAllChickenInfosBoardToExcel", laborUnionId, day, searchCondition);
    	
    	List<UserInfoBoard> list = getAllChickenInfosBoard(laborUnionId, day, searchCondition, pageable);
		int rowSeq = 0;
		HSSFWorkbook wb = new HSSFWorkbook();
		CellStyle greenStyle = wb.createCellStyle();  
		greenStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());  
		greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
		HSSFSheet sheet = wb.createSheet(day);
		HSSFRow row = sheet.createRow(rowSeq++);   
		HSSFCell cellTitle = row.createCell(0);
		cellTitle.setCellValue("姓名");
		sheet.setColumnWidth(0, 20 * 256);
		cellTitle = row.createCell(1);
		cellTitle.setCellValue("昵称");
		sheet.setColumnWidth(1, 20 * 256);
		cellTitle = row.createCell(2);
		cellTitle.setCellValue("繁星ID");
		sheet.setColumnWidth(2, 15 * 256);
		cellTitle = row.createCell(3);
		cellTitle.setCellValue("星豆数");
		sheet.setColumnWidth(3, 10 * 256);
		cellTitle = row.createCell(4);
		cellTitle.setCellValue("星豆月总数");
		sheet.setColumnWidth(4, 15 * 256);
		cellTitle = row.createCell(5);
		cellTitle.setCellValue("任务数");
		sheet.setColumnWidth(5, 15 * 256);
		cellTitle = row.createCell(6);
		cellTitle.setCellValue("目标数");
		sheet.setColumnWidth(6, 15 * 256);
		cellTitle = row.createCell(7);
		cellTitle.setCellValue("月工作时长");
		sheet.setColumnWidth(7, 15 * 256);
		cellTitle = row.createCell(8);
		cellTitle.setCellValue("合格天数");
		sheet.setColumnWidth(8, 15 * 256);
		cellTitle = row.createCell(9);
		cellTitle.setCellValue("明星等级");
		sheet.setColumnWidth(9, 15 * 256);
		cellTitle = row.createCell(10);
		cellTitle.setCellValue("财富等级");
		sheet.setColumnWidth(10, 15 * 256);
		cellTitle = row.createCell(11);
		cellTitle.setCellValue("关注数");
		sheet.setColumnWidth(11, 15 * 256);
		cellTitle = row.createCell(12);
		cellTitle.setCellValue("开播日期");
		sheet.setColumnWidth(12, 15 * 256);
		for(UserInfoBoard userInfoBoard : list) {
			row = sheet.createRow(rowSeq++);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(userInfoBoard.getUserName());
			cell = row.createCell(1); 
			cell.setCellValue(userInfoBoard.getNickName());
			cell = row.createCell(2);
			cell.setCellValue(userInfoBoard.getStarId());
			cell = row.createCell(3);
			cell.setCellValue(userInfoBoard.getBeanByDay() != null ? userInfoBoard.getBeanByDay() : 0);
			cell = row.createCell(4);
			cell.setCellValue(userInfoBoard.getBeanByMonth() != null ? userInfoBoard.getBeanByMonth() : 0);
			cell = row.createCell(5);
			cell.setCellValue(userInfoBoard.getMinTask());
			if(userInfoBoard.getBeanByMonth() > userInfoBoard.getMinTask()) {
				cell.setCellStyle(greenStyle);
			}
			cell = row.createCell(6);
			cell.setCellValue(userInfoBoard.getMaxTask());
			if(userInfoBoard.getBeanByMonth() > userInfoBoard.getMaxTask()) {
				cell.setCellStyle(greenStyle);
			}
			cell = row.createCell(7);
			cell.setCellValue(formatTime(userInfoBoard.getWorkTimeByMonth() != null ? 
					userInfoBoard.getWorkTimeByMonth() : 0));
			cell = row.createCell(8);
			cell.setCellValue(userInfoBoard.getJudgeTimeByMonth());
			cell = row.createCell(9);
			cell.setCellValue(userInfoBoard.getStarLevel());
			cell = row.createCell(10);
			cell.setCellValue(userInfoBoard.getRichLevel());
			cell = row.createCell(11);
			cell.setCellValue(userInfoBoard.getFansCount() != null ? userInfoBoard.getFansCount() : 0);
			cell = row.createCell(12);
			cell.setCellValue(userInfoBoard.getRegDate().toString());
		}
		BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
		try {  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] content = os.toByteArray();  
            InputStream is = new ByteArrayInputStream(content);  
            response.reset();  
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");  
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((day + ".xls").getBytes(), "utf-8"));  
            ServletOutputStream out = response.getOutputStream();  
            bis = new BufferedInputStream(is);  
            bos = new BufferedOutputStream(out);  
            byte[] buff = new byte[2048];  
            int bytesRead;  
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
                bos.write(buff, 0, bytesRead);  
            }  
        } catch (Exception e) {  
            log.error("=====export exception=====", e);  
        }finally {  
            try {  
                if(bis != null)  
                    bis.close();  
                if(bos != null)  
                    bos.close();  
            } catch (IOException e) {  
                log.error("=====close flow exception=====", e);  
            }  
        }  
    }

    private List<UserInfoBoard> getAllChickenInfosBoard(Long laborUnionId, String day, String searchCondition, @ApiParam Pageable pageable) {
        List<UserInfoBoard> list = null;
        if(laborUnionId > 0) {
        	list = userInfoBoardRepository.getAllChickenInfosBoardByLid(laborUnionId, day, fuzzyQuery(searchCondition));
        } else {
        	Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        	list = userInfoBoardRepository.getAllChickenInfosBoard(user.get().getId(), day, fuzzyQuery(searchCondition));
        }
        ComparatorUserInfo comparatorUserInfo = new ComparatorUserInfo(pageable.getSort().toString());
        Collections.sort(list, comparatorUserInfo);
        return list;
    }

    private String fuzzyQuery(String query) {
        if (StringUtils.isEmpty(query)) {
            return "%";
        } else {
            return "%" + query + "%";
        }
    }

    @GetMapping("/work-time-board")
    @Timed
    public ResponseEntity<List<WorkTimeBoard>> getWorkTimeBoardToHtml(Long laborUnionId,String date, Integer day, String searchCondition, @ApiParam Pageable pageable) {
    	log.info("{} request {}, laborUnionId = {}, date = {}, day = {}, searchCondition = {}", SecurityUtils.getCurrentUserLogin(), 
    			"getWorkTimeBoardToHtml", laborUnionId, date, day, searchCondition);

    	List<WorkTimeBoard> list = getWorkTimeBoard(laborUnionId, date, day, searchCondition, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @GetMapping("/work-time-excel")
    @Timed
    public void getWorkTimeBoardToExcel(Long laborUnionId,String date, Integer day, String searchCondition, 
    		@ApiParam Pageable pageable, HttpServletRequest request, HttpServletResponse response) {
    	log.info("{} request {}, laborUnionId = {}, date = {}, day = {}, searchCondition = {}", SecurityUtils.getCurrentUserLogin(), 
    			"getWorkTimeBoardToExcel", laborUnionId, date, day, searchCondition);
    	
    	List<WorkTimeBoard> list = getWorkTimeBoard(laborUnionId, date, day, searchCondition, pageable);  	
    	Table<Long, LocalDate, WorkTimeBoard> table = HashBasedTable.create();
    	Set<LocalDate> allDates = new TreeSet<LocalDate>(new Comparator<LocalDate>() {

			@Override
			public int compare(LocalDate o1, LocalDate o2) {
				if(o1.isBefore(o2)) {
					return 1;
				} else if(o1.isAfter(o2)) {
					return -1;
				}
				return 0;
			}

    	});
       	for(WorkTimeBoard workTimeBoard : list) {
       		table.put(workTimeBoard.getStarId(), workTimeBoard.getCurDay(), workTimeBoard);
       		allDates.add(workTimeBoard.getCurDay());
    	}
       	
    	int rowSeq = 0;
    	int columnSeq = 5;
		HSSFWorkbook wb = new HSSFWorkbook();
		CellStyle yellowStyle = wb.createCellStyle();  
		yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());  
		yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
		CellStyle redStyle = wb.createCellStyle();  
		redStyle.setFillForegroundColor(IndexedColors.RED.getIndex());  
		redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
		HSSFSheet sheet = wb.createSheet("" + day);
		HSSFRow row = sheet.createRow(rowSeq++);   
		HSSFCell cellTitle = row.createCell(0);
		cellTitle.setCellValue("姓名");
		sheet.setColumnWidth(0, 20 * 256);
		cellTitle = row.createCell(1);
		cellTitle.setCellValue("昵称");
		sheet.setColumnWidth(1, 20 * 256);
		cellTitle = row.createCell(2);
		cellTitle.setCellValue("繁星ID");
		sheet.setColumnWidth(2, 15 * 256);
		cellTitle = row.createCell(3);
		cellTitle.setCellValue("月工作时长");
		sheet.setColumnWidth(3, 15 * 256);
		cellTitle = row.createCell(4);
		cellTitle.setCellValue("合格天数");
		sheet.setColumnWidth(4, 15 * 256);
		for(LocalDate curDay : allDates) {
			cellTitle = row.createCell(columnSeq);
			cellTitle.setCellValue("" + curDay);
			sheet.setColumnWidth(columnSeq++, 15 * 256);
		}
		cellTitle = row.createCell(columnSeq);
		cellTitle.setCellValue("总计");
		sheet.setColumnWidth(columnSeq, 15 * 256);

    	for(Long starId : table.rowKeySet()) {
    		row = sheet.createRow(rowSeq++);
    		boolean isCommon = true;  
    		columnSeq = 5;
    		Long sum = 0l;
    		for(LocalDate curDay : allDates) {
    			WorkTimeBoard workTimeBoard = table.get(starId, curDay);
    			if(workTimeBoard != null) {
    				if(isCommon) {
        				HSSFCell cell = row.createCell(0);
        				cell.setCellValue(workTimeBoard.getUserName());
        				cell = row.createCell(1); 
        				cell.setCellValue(workTimeBoard.getNickName());
        				cell = row.createCell(2);
        				cell.setCellValue(workTimeBoard.getStarId());
        				cell = row.createCell(3);
        				cell.setCellValue(formatTime(workTimeBoard.getWorkTimeByMonth() != null ? workTimeBoard.getWorkTimeByMonth() : 0l));
        				cell = row.createCell(4);
        				cell.setCellValue(workTimeBoard.getJudgeTimeByMonth());
        				cell = row.createCell(columnSeq++);
        				double curWorkTime = workTimeBoard.getWorkTime() != null ? workTimeBoard.getWorkTime() : 0d;
        				cell.setCellValue(formatTime((long)curWorkTime));
    					if(curWorkTime > 0 && curWorkTime < workTimeBoard.getBoundaryValue() * 60) {
    						cell.setCellStyle(yellowStyle);
    					} else if(curWorkTime == 0) {
    						cell.setCellStyle(redStyle);
    					}
    					sum += (long)curWorkTime;
    					isCommon = false;
    				} else {
    					HSSFCell cell = row.createCell(columnSeq++);
        				double curWorkTime = workTimeBoard.getWorkTime() != null ? workTimeBoard.getWorkTime() : 0d;
        				cell.setCellValue(formatTime((long)curWorkTime));
    					if(curWorkTime > 0 && curWorkTime < workTimeBoard.getBoundaryValue() * 60) {
    						cell.setCellStyle(yellowStyle);
    					} else if(curWorkTime == 0) {
    						cell.setCellStyle(redStyle);
    					}
    					sum += (long)curWorkTime;
    				}
    			} else {
					HSSFCell cell = row.createCell(columnSeq++);
					cell.setCellValue(formatTime(0l));
					cell.setCellStyle(redStyle);
    			}
    		}
    		HSSFCell cell = row.createCell(columnSeq);
    		cell.setCellValue(formatTime(sum));
    	}
		BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
		try {  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] content = os.toByteArray();  
            InputStream is = new ByteArrayInputStream(content);  
            response.reset();  
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");  
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((day + ".xls").getBytes(), "utf-8"));  
            ServletOutputStream out = response.getOutputStream();  
            bis = new BufferedInputStream(is);  
            bos = new BufferedOutputStream(out);  
            byte[] buff = new byte[2048];  
            int bytesRead;  
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
                bos.write(buff, 0, bytesRead);  
            }  
        } catch (Exception e) {  
            log.error("=====export exception=====", e);  
        }finally {  
            try {  
                if(bis != null)  
                    bis.close();  
                if(bos != null)  
                    bos.close();  
            } catch (IOException e) {  
                log.error("=====close flow exception=====", e);  
            }  
        } 
    }
    
    private List<WorkTimeBoard> getWorkTimeBoard(Long laborUnionId,String date, Integer day, String searchCondition, @ApiParam Pageable pageable) {
        DateTime dt = new DateTime(date);
        List<WorkTimeBoard> list = null;
        if (day == null) {
            day = 1;
        }
        if (day.equals(30)) {
        	if(laborUnionId > 0) {
        		list = workTimeBoardRepository.getWorkTimeBoardCurMonthByLid(laborUnionId, Long.valueOf(dt.toString("yyyyMM")), fuzzyQuery(searchCondition));
        	} else {
        		Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        		list = workTimeBoardRepository.getWorkTimeBoardCurMonth(user.get().getId(), Long.valueOf(dt.toString("yyyyMM")), fuzzyQuery(searchCondition));
        	}
        } else {
            List<String> days = new ArrayList<>();
            for (int i = 0; i <= day; i++) {
                days.add(dt.plusDays(-i).toString("yyyy-MM-dd"));
            }
            if(laborUnionId > 0) {
            	list = workTimeBoardRepository.getWorkTimeBoardByDayByLid(laborUnionId, days, fuzzyQuery(searchCondition));
            } else {
            	Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
            	list = workTimeBoardRepository.getWorkTimeBoardByDay(user.get().getId(), days, fuzzyQuery(searchCondition));
            }
        }
        ComparatorWorkTime comparatorWorkTime = new ComparatorWorkTime(pageable.getSort().toString());
        Collections.sort(list, comparatorWorkTime);
        return list;
    }
    
    @GetMapping("/kpi-by-user")
    @Timed
    public ResponseEntity<List<WorkTimeBoard>> getKpiByUserToHtml(Long laborUnionId,String date, Integer day, String searchCondition, @ApiParam Pageable pageable) {
    	log.info("{} request {}, laborUnionId = {}, date = {}, day = {}, searchCondition = {}", SecurityUtils.getCurrentUserLogin(), 
    			"getKpiByUserToHtml", laborUnionId, date, day, searchCondition);

    	List<WorkTimeBoard> list = getKpiByUser(laborUnionId, date, day, searchCondition, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @GetMapping("/kpi-by-user-excel")
    @Timed
    public void getKpiByUserToExcel(Long laborUnionId,String date, Integer day, String searchCondition, 
    		@ApiParam Pageable pageable, HttpServletRequest request, HttpServletResponse response) {
    	log.info("{} request {}, laborUnionId = {}, date = {}, day = {}, searchCondition = {}", SecurityUtils.getCurrentUserLogin(), 
    			"getKpiByUserToExcel", laborUnionId, date, day, searchCondition);
    	
    	List<WorkTimeBoard> list = getKpiByUser(laborUnionId, date, day, searchCondition, pageable);
    	Table<Long, LocalDate, WorkTimeBoard> table = HashBasedTable.create();
    	Set<LocalDate> allDates = new TreeSet<LocalDate>(new Comparator<LocalDate>() {

			@Override
			public int compare(LocalDate o1, LocalDate o2) {
				if(o1.isBefore(o2)) {
					return 1;
				} else if(o1.isAfter(o2)) {
					return -1;
				}
				return 0;
			}

    	});
       	for(WorkTimeBoard workTimeBoard : list) {
       		table.put(workTimeBoard.getStarId(), workTimeBoard.getCurDay(), workTimeBoard);
       		allDates.add(workTimeBoard.getCurDay());
    	}
    	int rowSeq = 0;
    	int columnSeq = 4;
		HSSFWorkbook wb = new HSSFWorkbook(); 
		HSSFSheet sheet = wb.createSheet("" + day);
		HSSFRow row = sheet.createRow(rowSeq++);   
		HSSFCell cellTitle = row.createCell(0);
		cellTitle.setCellValue("姓名");
		sheet.setColumnWidth(0, 20 * 256);
		cellTitle = row.createCell(1);
		cellTitle.setCellValue("昵称");
		sheet.setColumnWidth(1, 20 * 256);
		cellTitle = row.createCell(2);
		cellTitle.setCellValue("繁星ID");
		sheet.setColumnWidth(2, 15 * 256);
		cellTitle = row.createCell(3);
		cellTitle.setCellValue("月星豆数");
		sheet.setColumnWidth(3, 15 * 256);
		for(LocalDate curDay : allDates) {
			cellTitle = row.createCell(columnSeq);
			cellTitle.setCellValue("" + curDay);
			sheet.setColumnWidth(columnSeq++, 15 * 256);
		}
		cellTitle = row.createCell(columnSeq);
		cellTitle.setCellValue("总计");
		sheet.setColumnWidth(columnSeq, 15 * 256);
    	for(Long starId : table.rowKeySet()) {
    		row = sheet.createRow(rowSeq++);
    		boolean isCommon = true;  
    		columnSeq = 4;
    		Long sum = 0l;
    		for(LocalDate curDay : allDates) {
    			WorkTimeBoard workTimeBoard = table.get(starId, curDay);
    			if(workTimeBoard != null) {
    				if(isCommon) {
    					HSSFCell cell = row.createCell(0);
    					cell.setCellValue(workTimeBoard.getUserName());
    					cell = row.createCell(1); 
    					cell.setCellValue(workTimeBoard.getNickName());
    					cell = row.createCell(2);
    					cell.setCellValue(workTimeBoard.getStarId());
    					cell = row.createCell(3);
    					cell.setCellValue(workTimeBoard.getWorkTimeByMonth() != null ? workTimeBoard.getWorkTimeByMonth() : 0l);
    					cell = row.createCell(columnSeq++);
    					double curWorkTime = workTimeBoard.getWorkTime() != null ? workTimeBoard.getWorkTime() : 0d;
    					cell.setCellValue((long)curWorkTime);
    					sum += (long)curWorkTime;
    					isCommon = false;
    				} else {
    					HSSFCell cell = row.createCell(columnSeq++);
    					double curWorkTime = workTimeBoard.getWorkTime() != null ? workTimeBoard.getWorkTime() : 0d;
    					cell.setCellValue((long)curWorkTime);
    					sum += (long)curWorkTime;
    				}
    			} else {
					HSSFCell cell = row.createCell(columnSeq++);
					cell.setCellValue(0l);
    			}
    		}
    		HSSFCell cell = row.createCell(columnSeq);
    		cell.setCellValue(sum);
    	}
    	row = sheet.createRow(rowSeq);   
    	HSSFCell cell = row.createCell(0);
    	cell.setCellValue("合计");
    	for(int i = 3; i < columnSeq + 1; i++) {
    		cell = row.createCell(i);
    		long sum = 0;
    		for(int j = 1; j < rowSeq; j++) {
    			sum += sheet.getRow(j).getCell(i).getNumericCellValue();
    		}
    		cell.setCellValue(sum);
    	}
		BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
		try {  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] content = os.toByteArray();  
            InputStream is = new ByteArrayInputStream(content);  
            response.reset();  
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");  
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((day + ".xls").getBytes(), "utf-8"));  
            ServletOutputStream out = response.getOutputStream();  
            bis = new BufferedInputStream(is);  
            bos = new BufferedOutputStream(out);  
            byte[] buff = new byte[2048];  
            int bytesRead;  
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
                bos.write(buff, 0, bytesRead);  
            }  
        } catch (Exception e) {  
            log.error("=====export exception=====", e);  
        }finally {  
            try {  
                if(bis != null)  
                    bis.close();  
                if(bos != null)  
                    bos.close();  
            } catch (IOException e) {  
                log.error("=====close flow exception=====", e);  
            }  
        } 
    }
    
    private List<WorkTimeBoard> getKpiByUser(Long laborUnionId,String date, Integer day, String searchCondition, @ApiParam Pageable pageable) {
        DateTime dt = new DateTime(date);
        List<WorkTimeBoard> list = null;
        if (day == null) {
            day = 1;
        }
        if (day.equals(30)) {
        	if(laborUnionId > 0) {
        		list = workTimeBoardRepository.getKpiByUserCurMonthByLid(laborUnionId, Long.valueOf(dt.toString("yyyyMM")), fuzzyQuery(searchCondition));
        	} else {
        		Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        		list = workTimeBoardRepository.getKpiByUserCurMonth(user.get().getId(), Long.valueOf(dt.toString("yyyyMM")), fuzzyQuery(searchCondition));
        	}
        } else {
            List<String> days = new ArrayList<>();
            for (int i = 0; i <= day; i++) {
                days.add(dt.plusDays(-i).toString("yyyy-MM-dd"));
            }
            if(laborUnionId > 0) {
            	list = workTimeBoardRepository.getKpiByUserByDayByLid(laborUnionId, days, fuzzyQuery(searchCondition));
            } else {
            	Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
            	list = workTimeBoardRepository.getKpiByUserByDay(user.get().getId(), days, fuzzyQuery(searchCondition));
            }
        }
        ComparatorWorkTime comparatorWorkTime = new ComparatorWorkTime(pageable.getSort().toString());
        Collections.sort(list, comparatorWorkTime);
        return list;
    }

    @GetMapping("/kpi-by-laborunion")
    @Timed
    public ResponseEntity<List<WorkTimeBoard>> getKpiByLaborUnionToHtml(Long laborUnionId,String date, Integer day, String searchCondition, @ApiParam Pageable pageable) {
    	log.info("{} request {}, laborUnionId = {}, date = {}, day = {}, searchCondition = {}", SecurityUtils.getCurrentUserLogin(), 
    			"getKpiByLaborUnionToHtml", laborUnionId, date, day, searchCondition);

    	List<WorkTimeBoard> list = getKpiByLaborUnion(laborUnionId, date, day, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @GetMapping("/kpi-by-laborunion-excel")
    @Timed
    public void getKpiByLaborUnionToExcel(Long laborUnionId,String date, Integer day, String searchCondition, 
    		@ApiParam Pageable pageable, HttpServletRequest request, HttpServletResponse response) {
    	log.info("{} request {}, laborUnionId = {}, date = {}, day = {}, searchCondition = {}", SecurityUtils.getCurrentUserLogin(), 
    			"getKpiByLaborUnionToExcel", laborUnionId, date, day, searchCondition);
    	
    	List<WorkTimeBoard> list = getKpiByLaborUnion(laborUnionId, date, day, pageable);
    	Table<Long, LocalDate, WorkTimeBoard> table = HashBasedTable.create();
    	Set<LocalDate> allDates = new TreeSet<LocalDate>(new Comparator<LocalDate>() {

			@Override
			public int compare(LocalDate o1, LocalDate o2) {
				if(o1.isBefore(o2)) {
					return 1;
				} else if(o1.isAfter(o2)) {
					return -1;
				}
				return 0;
			}

    	});
       	for(WorkTimeBoard workTimeBoard : list) {
       		table.put(workTimeBoard.getStarId(), workTimeBoard.getCurDay(), workTimeBoard);
       		allDates.add(workTimeBoard.getCurDay());
    	}
    	int rowSeq = 0;
    	int columnSeq = 2;
		HSSFWorkbook wb = new HSSFWorkbook(); 
		HSSFSheet sheet = wb.createSheet("" + day);
		HSSFRow row = sheet.createRow(rowSeq++);   
		HSSFCell cellTitle = row.createCell(0);
		cellTitle.setCellValue("名称");
		sheet.setColumnWidth(0, 20 * 256);
		cellTitle = row.createCell(1);
		cellTitle.setCellValue("月星豆数");
		sheet.setColumnWidth(1, 15 * 256);
		for(LocalDate curDay : allDates) {
			cellTitle = row.createCell(columnSeq);
			cellTitle.setCellValue("" + curDay);
			sheet.setColumnWidth(columnSeq++, 15 * 256);
		}
		cellTitle = row.createCell(columnSeq);
		cellTitle.setCellValue("总计");
		sheet.setColumnWidth(columnSeq, 15 * 256);
    	for(Long starId : table.rowKeySet()) {
    		row = sheet.createRow(rowSeq++);
    		boolean isCommon = true;  
    		columnSeq = 2;
    		Long sum = 0l;
    		for(LocalDate curDay : allDates) {
    			WorkTimeBoard workTimeBoard = table.get(starId, curDay);
    			if(workTimeBoard != null) {
    				if(isCommon) {
    					HSSFCell cell = row.createCell(0);
    					cell.setCellValue(workTimeBoard.getUserName());
    					cell = row.createCell(1); 
    					cell.setCellValue(workTimeBoard.getWorkTimeByMonth() != null ? workTimeBoard.getWorkTimeByMonth() : 0l);
    					cell = row.createCell(columnSeq++);
    					double curWorkTime = workTimeBoard.getWorkTime() != null ? workTimeBoard.getWorkTime() : 0d;
    					cell.setCellValue((long)curWorkTime);
    					sum += (long)curWorkTime;
    					isCommon = false;
    				} else {
    					HSSFCell cell = row.createCell(columnSeq++);
    					double curWorkTime = workTimeBoard.getWorkTime() != null ? workTimeBoard.getWorkTime() : 0d;
    					cell.setCellValue((long)curWorkTime);
    					sum += (long)curWorkTime;
    				}
    			} else {
					HSSFCell cell = row.createCell(columnSeq++);
					cell.setCellValue(0l);
    			}
    		}
    		HSSFCell cell = row.createCell(columnSeq);
    		cell.setCellValue(sum);
    	}
    	row = sheet.createRow(rowSeq);   
    	HSSFCell cell = row.createCell(0);
    	cell.setCellValue("合计");
    	for(int i = 1; i < columnSeq + 1; i++) {
    		cell = row.createCell(i);
    		long sum = 0;
    		for(int j = 1; j < rowSeq; j++) {
    			sum += sheet.getRow(j).getCell(i).getNumericCellValue();
    		}
    		cell.setCellValue(sum);
    	}
		BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
		try {  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] content = os.toByteArray();  
            InputStream is = new ByteArrayInputStream(content);  
            response.reset();  
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");  
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((day + ".xls").getBytes(), "utf-8"));  
            ServletOutputStream out = response.getOutputStream();  
            bis = new BufferedInputStream(is);  
            bos = new BufferedOutputStream(out);  
            byte[] buff = new byte[2048];  
            int bytesRead;  
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
                bos.write(buff, 0, bytesRead);  
            }  
        } catch (Exception e) {  
            log.error("=====export exception=====", e);  
        }finally {  
            try {  
                if(bis != null)  
                    bis.close();  
                if(bos != null)  
                    bos.close();  
            } catch (IOException e) {  
                log.error("=====close flow exception=====", e);  
            }  
        } 
    }
    
    private List<WorkTimeBoard> getKpiByLaborUnion(Long laborUnionId,String date, Integer day, @ApiParam Pageable pageable) {
        DateTime dt = new DateTime(date);
        List<WorkTimeBoard> list = null;
        if (day == null) {
            day = 1;
        }
        if (day.equals(30)) {
        	if(laborUnionId > 0) {
        		list = workTimeBoardRepository.getKpiByLaborUnionCurMonthByLid(laborUnionId, Long.valueOf(dt.toString("yyyyMM")));
        	} else {
        		Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        		list = workTimeBoardRepository.getKpiByLaborUnionCurMonth(user.get().getId(), Long.valueOf(dt.toString("yyyyMM")));
        	}
        } else {
            List<String> days = new ArrayList<>();
            for (int i = 0; i <= day; i++) {
                days.add(dt.plusDays(-i).toString("yyyy-MM-dd"));
            }
            if(laborUnionId > 0) {
            	list = workTimeBoardRepository.getKpiByLaborUnionByDayByLid(laborUnionId, days);
            } else {
            	Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
            	list = workTimeBoardRepository.getKpiByLaborUnionByDay(user.get().getId(), days);
            }
        }
        if(list != null) {
        	Table<Long, String, Long> beanByUser = HashBasedTable.create();
        	Table<Long, LocalDate, WorkTimeBoard> beanByDate = HashBasedTable.create();
        	for(WorkTimeBoard workTimeBoard : list) {
        		WorkTimeBoard existInfo = beanByDate.get(workTimeBoard.getStarId(), workTimeBoard.getCurDay());
        		if(existInfo == null) {
        			beanByDate.put(workTimeBoard.getStarId(), workTimeBoard.getCurDay(), workTimeBoard);
        		} else {
        			Double bean = 0d;
        			if(existInfo.getWorkTime() != null) {
        				bean = existInfo.getWorkTime();
        			}
        			if(workTimeBoard.getWorkTime() != null) {
        				bean += workTimeBoard.getWorkTime();
        			}
        			existInfo.setWorkTime(bean);
        		}
        		if(!beanByUser.contains(workTimeBoard.getStarId(), workTimeBoard.getNickName())) {
        			beanByUser.put(workTimeBoard.getStarId(), workTimeBoard.getNickName(), 
        					workTimeBoard.getWorkTimeByMonth() != null ? workTimeBoard.getWorkTimeByMonth() : 0l);
        		} 
        	}
        	list.clear();
        	for(Long starId : beanByDate.rowKeySet()) {
        		Long allBean = 0l;
        		for(Long bean : beanByUser.row(starId).values()) {
        			if(bean != null) {
        				allBean += bean;        				
        			}
        		}
        		for(WorkTimeBoard workTimeBoard : beanByDate.row(starId).values()) {
        			workTimeBoard.setWorkTimeByMonth(allBean);
        			list.add(workTimeBoard);
        		}
        	}
        }
        ComparatorWorkTime comparatorWorkTime = new ComparatorWorkTime(pageable.getSort().toString());
        Collections.sort(list, comparatorWorkTime);
        return list;
    }

    @GetMapping("/recent-time")
    @Timed
    public ResponseEntity<HashMap<String,Object>> getRecentTime() {
        HashMap<String,Object> res = new HashMap<>();
        List<String> date = new ArrayList();

        DateTime dt = new DateTime();
        for (int i = 0; i < 7; i++) {
            date.add(dt.plusDays(-i).toString("yyyy-MM-dd"));
        }

        res.put("date",date);

        List<LaborUnion> laborUnions=null;
        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if(user.get().getLogin().equals("system")){
            laborUnions = laborUnionRepository.findAll();
        }else {
            laborUnions = laborUnionRepository.findByUserIsCurrentUser(user.get().getId());
        }
        LaborUnion all = new LaborUnion();
        all.setId(-1l);
        all.setlId(-1);
        all.setName("全部");
        all.setExportExcel(true);
        laborUnions.add(all);
        res.put("laborUnions",laborUnions);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    
	
	private String formatTime(long totalWorkTime) {
		long hours = totalWorkTime / 3600;
		long minutes = (totalWorkTime - hours * 3600) / 60;
		long seconds = totalWorkTime - hours * 3600 - minutes * 60;
		return hours + "时" + minutes + "分" + seconds + "秒";
	}

    class ComparatorUserInfo implements Comparator {
    	
    	private String column = "id";
    	
    	private boolean asc = true;
    	
    	public ComparatorUserInfo(String orderBy) {
    		try {
    			String[] conditions = orderBy.split(",");
    			if(conditions != null && conditions.length > 0) {
    				String[] condition = conditions[0].split(":");
    				if(condition != null && condition.length > 1) {
    					column = condition[0].toLowerCase().trim();
    					asc = condition[1].toLowerCase().trim().equals("asc") ? true : false;
    				}
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}

    	public int compare(Object arg0, Object arg1) {
    		
    		UserInfoBoard userInfoBoard0 = (UserInfoBoard)arg0;
    		UserInfoBoard userInfoBoard1 = (UserInfoBoard)arg1;

    		switch(column) {
	    		case "id" :
	    			return asc ? userInfoBoard0.getId().compareTo(userInfoBoard1.getId()) : userInfoBoard1.getId().compareTo(userInfoBoard0.getId());
	    		case "user_name" :
	    			return asc ? userInfoBoard0.getUserName().compareTo(userInfoBoard1.getUserName()) : userInfoBoard1.getUserName().compareTo(userInfoBoard0.getUserName());
	    		case "nick_name" :
	    			if(userInfoBoard0.getNickName() != null && userInfoBoard1.getNickName() != null) {
	    				return asc ? userInfoBoard0.getNickName().compareTo(userInfoBoard1.getNickName()) : userInfoBoard1.getNickName().compareTo(userInfoBoard0.getNickName());	    				
	    			} else if(userInfoBoard0.getNickName() != null && userInfoBoard1.getNickName() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getNickName() == null && userInfoBoard1.getNickName() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    		case "star_id" :
	    			return asc ? userInfoBoard0.getStarId().compareTo(userInfoBoard1.getStarId()) : userInfoBoard1.getStarId().compareTo(userInfoBoard0.getStarId());
	    		case "star_level" :
	    			if(userInfoBoard0.getStarLevel() != null && userInfoBoard1.getStarLevel() != null) {
	    				return asc ? userInfoBoard0.getStarLevel().compareTo(userInfoBoard1.getStarLevel()) : userInfoBoard1.getStarLevel().compareTo(userInfoBoard0.getStarLevel());	    				
	    			} else if(userInfoBoard0.getStarLevel() != null && userInfoBoard1.getStarLevel() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getStarLevel() == null && userInfoBoard1.getStarLevel() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    		case "rich_level" :
	    			if(userInfoBoard0.getRichLevel() != null && userInfoBoard1.getRichLevel() != null) {
	    				return asc ? userInfoBoard0.getRichLevel().compareTo(userInfoBoard1.getRichLevel()) : userInfoBoard1.getRichLevel().compareTo(userInfoBoard0.getRichLevel());	    				
	    			} else if(userInfoBoard0.getRichLevel() != null && userInfoBoard1.getRichLevel() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getRichLevel() == null && userInfoBoard1.getRichLevel() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    		case "fans_count" :
	    			if(userInfoBoard0.getFansCount() != null && userInfoBoard1.getFansCount() != null) {
	    				return asc ? userInfoBoard0.getFansCount().compareTo(userInfoBoard1.getFansCount()) : userInfoBoard1.getFansCount().compareTo(userInfoBoard0.getFansCount());	    				
	    			} else if(userInfoBoard0.getFansCount() != null && userInfoBoard1.getFansCount() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getFansCount() == null && userInfoBoard1.getFansCount() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    		case "bean_by_day" :
	    			if(userInfoBoard0.getBeanByDay() != null && userInfoBoard1.getBeanByDay() != null) {
	    				return asc ? userInfoBoard0.getBeanByDay().compareTo(userInfoBoard1.getBeanByDay()) : userInfoBoard1.getBeanByDay().compareTo(userInfoBoard0.getBeanByDay());	    				
	    			} else if(userInfoBoard0.getBeanByDay() != null && userInfoBoard1.getBeanByDay() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getBeanByDay() == null && userInfoBoard1.getBeanByDay() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    		case "bean_by_month" :
	    			if(userInfoBoard0.getBeanByMonth() != null && userInfoBoard1.getBeanByMonth() != null) {
	    				return asc ? userInfoBoard0.getBeanByMonth().compareTo(userInfoBoard1.getBeanByMonth()) : userInfoBoard1.getBeanByMonth().compareTo(userInfoBoard0.getBeanByMonth());	    				
	    			} else if(userInfoBoard0.getBeanByMonth() != null && userInfoBoard1.getBeanByMonth() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getBeanByMonth() == null && userInfoBoard1.getBeanByMonth() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    		case "min_task" :
	    			return asc ? userInfoBoard0.getMinTask().compareTo(userInfoBoard1.getMinTask()) : userInfoBoard1.getMinTask().compareTo(userInfoBoard0.getMinTask());
	    		case "max_task" :
	    			return asc ? userInfoBoard0.getMaxTask().compareTo(userInfoBoard1.getMaxTask()) : userInfoBoard1.getMaxTask().compareTo(userInfoBoard0.getMaxTask());
	    		case "worktime_by_month" :
	    			if(userInfoBoard0.getWorkTimeByMonth() != null && userInfoBoard1.getWorkTimeByMonth() != null) {
	    				return asc ? userInfoBoard0.getWorkTimeByMonth().compareTo(userInfoBoard1.getWorkTimeByMonth()) : userInfoBoard1.getWorkTimeByMonth().compareTo(userInfoBoard0.getWorkTimeByMonth());	    				
	    			} else if(userInfoBoard0.getWorkTimeByMonth() != null && userInfoBoard1.getWorkTimeByMonth() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getWorkTimeByMonth() == null && userInfoBoard1.getWorkTimeByMonth() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    		case "judegetime_by_month" :
	    			if(userInfoBoard0.getJudgeTimeByMonth() != null && userInfoBoard1.getJudgeTimeByMonth() != null) {
	    				return asc ? userInfoBoard0.getJudgeTimeByMonth().compareTo(userInfoBoard1.getJudgeTimeByMonth()) : userInfoBoard1.getJudgeTimeByMonth().compareTo(userInfoBoard0.getJudgeTimeByMonth());	    				
	    			} else if(userInfoBoard0.getJudgeTimeByMonth() != null && userInfoBoard1.getJudgeTimeByMonth() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getJudgeTimeByMonth() == null && userInfoBoard1.getJudgeTimeByMonth() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    		case "reg_date" :
	    			if(userInfoBoard0.getRegDate() != null && userInfoBoard1.getRegDate() != null) {
	    				return asc ? userInfoBoard0.getRegDate().compareTo(userInfoBoard1.getRegDate()) : userInfoBoard1.getRegDate().compareTo(userInfoBoard0.getRegDate());	    				
	    			} else if(userInfoBoard0.getRegDate() != null && userInfoBoard1.getRegDate() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getRegDate() == null && userInfoBoard1.getRegDate() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    			
    		}
    		return 0;
    	}

    }
    
class ComparatorWorkTime implements Comparator {
    	
    	private String column = "id";
    	
    	private boolean asc = true;
    	
    	public ComparatorWorkTime(String orderBy) {
    		try {
    			String[] conditions = orderBy.split(",");
    			if(conditions != null && conditions.length > 0) {
    				String[] condition = conditions[0].split(":");
    				if(condition != null && condition.length > 1) {
    					column = condition[0].toLowerCase().trim();
    					asc = condition[1].toLowerCase().trim().equals("asc") ? true : false;
    				}
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}

    	public int compare(Object arg0, Object arg1) {
    		
    		WorkTimeBoard userInfoBoard0 = (WorkTimeBoard)arg0;
    		WorkTimeBoard userInfoBoard1 = (WorkTimeBoard)arg1;

    		switch(column) {
	    		case "id" :
	    			return asc ? userInfoBoard0.getId().compareTo(userInfoBoard1.getId()) : userInfoBoard1.getId().compareTo(userInfoBoard0.getId());
	    		case "user_name" :
	    			return asc ? userInfoBoard0.getUserName().compareTo(userInfoBoard1.getUserName()) : userInfoBoard1.getUserName().compareTo(userInfoBoard0.getUserName());
	    		case "nick_name" :
	    			if(userInfoBoard0.getNickName() != null && userInfoBoard1.getNickName() != null) {
	    				return asc ? userInfoBoard0.getNickName().compareTo(userInfoBoard1.getNickName()) : userInfoBoard1.getNickName().compareTo(userInfoBoard0.getNickName());	    				
	    			} else if(userInfoBoard0.getNickName() != null && userInfoBoard1.getNickName() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getNickName() == null && userInfoBoard1.getNickName() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    		case "star_id" :
	    			return asc ? userInfoBoard0.getStarId().compareTo(userInfoBoard1.getStarId()) : userInfoBoard1.getStarId().compareTo(userInfoBoard0.getStarId());
	    		case "worktime_by_month" :
	    			if(userInfoBoard0.getWorkTimeByMonth() != null && userInfoBoard1.getWorkTimeByMonth() != null) {
	    				return asc ? userInfoBoard0.getWorkTimeByMonth().compareTo(userInfoBoard1.getWorkTimeByMonth()) : userInfoBoard1.getWorkTimeByMonth().compareTo(userInfoBoard0.getWorkTimeByMonth());	    				
	    			} else if(userInfoBoard0.getWorkTimeByMonth() != null && userInfoBoard1.getWorkTimeByMonth() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getWorkTimeByMonth() == null && userInfoBoard1.getWorkTimeByMonth() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    		case "judegetime_by_month" :
	    			if(userInfoBoard0.getJudgeTimeByMonth() != null && userInfoBoard1.getJudgeTimeByMonth() != null) {
	    				return asc ? userInfoBoard0.getJudgeTimeByMonth().compareTo(userInfoBoard1.getJudgeTimeByMonth()) : userInfoBoard1.getJudgeTimeByMonth().compareTo(userInfoBoard0.getJudgeTimeByMonth());	    				
	    			} else if(userInfoBoard0.getJudgeTimeByMonth() != null && userInfoBoard1.getJudgeTimeByMonth() == null) {
	    				return asc ? 1 : -1;
	    			} else if(userInfoBoard0.getJudgeTimeByMonth() == null && userInfoBoard1.getJudgeTimeByMonth() != null) {
	    				return asc ? -1 : 1;
	    			} 
	    			
    		}
    		return 0;
    	}
    	 
    }
}
