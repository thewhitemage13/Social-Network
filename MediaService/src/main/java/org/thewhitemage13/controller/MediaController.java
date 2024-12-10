package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thewhitemage13.entity.Media;
import org.thewhitemage13.exception.MediaNotFoundException;
import org.thewhitemage13.service.MediaServiceImpl;

import java.io.IOException;

@Tag(name = "Media Controller", description = "Operations for managing media files")
@RestController
@RequestMapping("/media")
public class MediaController {
    private final MediaServiceImpl mediaServiceImpl;

    @Autowired
    public MediaController(MediaServiceImpl mediaServiceImpl) {
        this.mediaServiceImpl = mediaServiceImpl;
    }

    @Operation(summary = "Verify media URL", description = "Verifies the existence or validity of a media file using its URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification result returned successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/verification")
    public ResponseEntity<Boolean> mediaVerification(@RequestParam("url") String url) {
        try {
            return ResponseEntity.ok(mediaServiceImpl.mediaVerification(url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @Operation(summary = "Upload media file", description = "Uploads a media file for a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "500", description = "File upload failed due to server error")
    })
    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> upload(@PathVariable("userId") Long userId, @RequestPart("file") MultipartFile file) {
        try {
            String fileUrl = mediaServiceImpl.uploadMedia(userId, file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete media file", description = "Deletes a media file by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Media file not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{mediaId}")
    public ResponseEntity<String> delete(@PathVariable Long mediaId) {
        try {
            mediaServiceImpl.deleteMedia(mediaId);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (MediaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media with id = %s not found.".formatted(mediaId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }

    }

    @Operation(summary = "Get media information", description = "Retrieves information about a media file by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Media information retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Media file not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{mediaId}")
    public ResponseEntity<String> getInformation(@PathVariable Long mediaId) {
        try {
            Media media = mediaServiceImpl.getMedia(mediaId);
            return ResponseEntity.ok(media.toString());
        }catch (MediaNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media with id = %s not found.".formatted(mediaId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }
}
