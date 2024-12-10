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
import org.thewhitemage13.entity.UserStatistic;
import org.thewhitemage13.service.UserStatisticServiceImpl;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "User Statistic Controller", description = "Operations for managing user statistics")
@RestController
@RequestMapping("/user-statistic")
public class UserStatisticController {
    private final UserStatisticServiceImpl userStatisticServiceImpl;

    @Autowired
    public UserStatisticController(UserStatisticServiceImpl userStatisticServiceImpl) {
        this.userStatisticServiceImpl = userStatisticServiceImpl;
    }

    @Operation(
            summary = "Delete user statistics by date",
            description = "Deletes user statistics for a specific date.",
            tags = {"User Statistic Controller"}
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
            userStatisticServiceImpl.deleteStatisticByDate(date);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted user statistic");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Retrieve all user statistics",
            description = "Fetches a list of all user statistics.",
            tags = {"User Statistic Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping
    public ResponseEntity<List<UserStatistic>> showAllStatistics() {
        try {
            return ResponseEntity.ok(userStatisticServiceImpl.getUserStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Retrieve user statistics by date",
            description = "Fetches user statistics for a specific date.",
            tags = {"User Statistic Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistic retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{date}")
    public ResponseEntity<UserStatistic> showStatisticByDate(
            @Parameter(description = "Date of the statistic to fetch (ISO format: yyyy-MM-dd)", required = true)
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return ResponseEntity.ok(userStatisticServiceImpl.getUserStatisticByDate(date));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
