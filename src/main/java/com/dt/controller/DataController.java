package com.dt.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dt.config.DocConstant;
import com.dt.model.TestData;
import com.dt.service.ASyncService;
import com.dt.util.FilterAndSortUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(DocConstant.API_VERSION)
public class DataController {

	private ASyncService<TestData> aService;

	public DataController(ASyncService<TestData> aService) {
		this.aService = aService;
	}

	@Operation(summary = "Fetches the data details", description = "This is used to fetch data details", responses = {
			@ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(hidden = true))),
			@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))) }, tags = {
					DocConstant.TAG_DATA })
	@PostMapping(value = DocConstant.TAG_DATA_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> list(HttpServletRequest request) {
		aService.generate();
		log.info("Started at {}", LocalDateTime.now());
		String sortColumn = request.getParameter("order[0][column]");
		boolean asc = "asc".equals(request.getParameter("order[0][dir]"));
		String sText = request.getParameter("search[value]");
		sText = Objects.isNull(sText) ? "" : sText.toLowerCase();
		String sStart = request.getParameter("start");
		int start = Objects.isNull(sStart) ? 0 : Integer.parseInt(sStart);
		String sLength = request.getParameter("length");
		int length = Objects.isNull(sLength) ? 10 : Integer.parseInt(sLength);
		length = length <= 0 ? 100 : length;
		int pageNumber = start / length + 1;
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("draw", request.getParameter("draw"));
		Map<Long, TestData> map = aService.store();
		dataMap.put("recordsTotal", map.size());
		Map<Long, List<TestData>> fnsMap = FilterAndSortUtil.filterAndSort(map.values(), sText, pageNumber, length,
				sortColumn, asc);
		fnsMap.entrySet().stream().forEach(entry -> {
			dataMap.put("recordsFiltered", entry.getKey());
			dataMap.put("data", entry.getValue());
		});
		log.info("Ended at {} for page {} having size {}", LocalDateTime.now(), pageNumber, length);
		return ResponseEntity.ok(dataMap);
	}
}