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
import org.thewhitemage13.entity.MediaStatistic;
import org.thewhitemage13.service.MediaStatisticServiceImpl;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Media Statistic Controller", description = "Operations for managing media statistics")
@RestController
@RequestMapping("/media-statistic")
public class MediaStatisticController {
    private final MediaStatisticServiceImpl mediaStatisticServiceImpl;

    @Autowired
    public MediaStatisticController(MediaStatisticServiceImpl mediaStatisticServiceImpl) {
        this.mediaStatisticServiceImpl = mediaStatisticServiceImpl;
    }

    @Operation(
            summary = "Delete media statistics by date",
            description = "Deletes media statistics for a specific date.",
            tags = {"Media Statistic Controller"}
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
            mediaStatisticServiceImpl.deleteStatisticByDate(date);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted media statistic");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Retrieve all media statistics",
            description = "Fetches a list of all media statistics.",
            tags = {"Media Statistic Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping
    public ResponseEntity<List<MediaStatistic>> showAllStatistics() {
        try {
            return ResponseEntity.ok(mediaStatisticServiceImpl.getAllMediaStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(
            summary = "Retrieve media statistics by date",
            description = "Fetches media statistics for a specific date.",
            tags = {"Media Statistic Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistic retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{date}")
    public ResponseEntity<MediaStatistic> showStatisticByDate(
            @Parameter(description = "Date of the statistic to fetch (ISO format: yyyy-MM-dd)", required = true)
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return ResponseEntity.ok(mediaStatisticServiceImpl.getMediaStatisticByDate(date));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
