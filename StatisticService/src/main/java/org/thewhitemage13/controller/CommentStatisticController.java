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
import org.thewhitemage13.entity.CommentStatistic;
import org.thewhitemage13.service.CommentStatisticServiceImpl;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Comment Statistic Controller", description = "Operations for managing comment statistics")
@RestController
@RequestMapping("/comment-statistic")
public class CommentStatisticController {
    private final CommentStatisticServiceImpl commentStatisticServiceImpl;

    @Autowired
    public CommentStatisticController(CommentStatisticServiceImpl commentStatisticServiceImpl) {
        this.commentStatisticServiceImpl = commentStatisticServiceImpl;
    }

    @Operation(
            summary = "Delete statistics by date",
            description = "Deletes comment statistics for a specific date.",
            tags = {"Comment Statistic Controller"}
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
            commentStatisticServiceImpl.deleteStatisticByDate(date);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted comment statistic");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Retrieve all statistics",
            description = "Fetches a list of all comment statistics.",
            tags = {"Comment Statistic Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping
    public ResponseEntity<List<CommentStatistic>> showAllStatistics() {
        try {
            return ResponseEntity.ok(commentStatisticServiceImpl.showAllStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Retrieve statistics by date",
            description = "Fetches comment statistics for a specific date.",
            tags = {"Comment Statistic Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistic retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{date}")
    public ResponseEntity<CommentStatistic> showStatisticByDate(
            @Parameter(description = "Date of the statistic to fetch (ISO format: yyyy-MM-dd)", required = true)
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return ResponseEntity.ok(commentStatisticServiceImpl.showStatisticsByDate(date));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
