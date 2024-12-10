package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.entity.LikeStatistic;
import org.thewhitemage13.service.LikeStatisticServiceImpl;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Like Statistic Controller", description = "Operations for managing like statistics")
@RestController
@RequestMapping("/like-statistic")
public class LikeStatisticController {
    private final LikeStatisticServiceImpl likeStatisticServiceImpl;

    @Autowired
    public LikeStatisticController(LikeStatisticServiceImpl likeStatisticServiceImpl) {
        this.likeStatisticServiceImpl = likeStatisticServiceImpl;
    }

    @Operation(
            summary = "Delete like statistics by date",
            description = "Deletes like statistics for a specific date.",
            tags = {"Like Statistic Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistic successfully deleted"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @DeleteMapping("/{date}")
    public ResponseEntity<String> deleteByDate(
            @Parameter(description = "Date of the statistics to delete (ISO format: yyyy-MM-dd)", required = true)
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            likeStatisticServiceImpl.deleteStatisticByDate(date);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted like statistic");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Retrieve all like statistics",
            description = "Fetches a list of all like statistics.",
            tags = {"Like Statistic Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping
    public ResponseEntity<List<LikeStatistic>> showAllStatistics() {
        try {
            return ResponseEntity.ok(likeStatisticServiceImpl.getAllLikeStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Retrieve like statistics by date",
            description = "Fetches like statistics for a specific date.",
            tags = {"Like Statistic Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistic retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{date}")
    public ResponseEntity<LikeStatistic> showStatisticByDate(
            @Parameter(description = "Date of the statistic to fetch (ISO format: yyyy-MM-dd)", required = true)
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return ResponseEntity.ok(likeStatisticServiceImpl.getLikeStatisticByDate(date));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
