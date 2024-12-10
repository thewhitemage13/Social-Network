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
import org.thewhitemage13.entity.PostStatistic;
import org.thewhitemage13.service.PostStatisticServiceImpl;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Post Statistic Controller", description = "Operations for managing post statistics")
@RestController
@RequestMapping("/post-statistic")
public class PostStatisticController {
    private final PostStatisticServiceImpl postStatisticServiceImpl;

    @Autowired
    public PostStatisticController(PostStatisticServiceImpl postStatisticServiceImpl) {
        this.postStatisticServiceImpl = postStatisticServiceImpl;
    }

    @Operation(
            summary = "Delete post statistics by date",
            description = "Deletes post statistics for a specific date.",
            tags = {"Post Statistic Controller"}
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
            postStatisticServiceImpl.deleteStatisticByDate(date);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted post statistic");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Retrieve all post statistics",
            description = "Fetches a list of all post statistics.",
            tags = {"Post Statistic Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping
    public ResponseEntity<List<PostStatistic>> showAllStatistics() {
        try {
            return ResponseEntity.ok(postStatisticServiceImpl.getPostStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Retrieve post statistics by date",
            description = "Fetches post statistics for a specific date.",
            tags = {"Post Statistic Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistic retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{date}")
    public ResponseEntity<PostStatistic> showStatisticByDate(
            @Parameter(description = "Date of the statistic to fetch (ISO format: yyyy-MM-dd)", required = true)
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return ResponseEntity.ok(postStatisticServiceImpl.getStatisticByDate(date));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
