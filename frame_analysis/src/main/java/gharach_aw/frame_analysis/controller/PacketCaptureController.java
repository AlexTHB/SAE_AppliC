package gharach_aw.frame_analysis.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import gharach_aw.frame_analysis.service.PacketCaptureProcessingService;
import gharach_aw.frame_analysis.service.PacketCaptureService;
import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
import gharach_aw.frame_analysis.persistence.entity.PacketCaptureDTO;
@RestController
public class PacketCaptureController {

    @Autowired
    private PacketCaptureProcessingService packetCaptureProcessingService;

    @Autowired
    private PacketCaptureService packetCaptureService;

    @PostMapping("/PacketCapture/upload")
    @ResponseBody
    public ResponseEntity<PacketCaptureDTO> savePacketCapture(@RequestParam("file") MultipartFile file) {
        PacketCapture packetCapture = packetCaptureProcessingService.extractPropertiesPacketCapture(file);
        packetCaptureService.save(packetCapture);

        // Build the URI for the newly created resource
        URI resourceUri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(packetCapture.getId())
            .toUri();

        PacketCaptureDTO packetCaptureDTO = packetCapture.convertToDTO();

        // Return the created packetCapture with 201 Created status and Location header
        return ResponseEntity.status(HttpStatus.CREATED).body(packetCaptureDTO);
    }

    @GetMapping("/packet_captures")
    public ResponseEntity<List<PacketCaptureDTO>> getAllPacketCaptures() {
        List<PacketCapture> packetCaptures = packetCaptureService.findAllPacketCaptures();
        List<PacketCaptureDTO> packetCapturesDTO = packetCaptureService.convertToPacketCaptureDTOList(packetCaptures);

        if (packetCapturesDTO.isEmpty()) {
            return ResponseEntity.noContent()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        } else {
            return ResponseEntity.ok(packetCapturesDTO);
        }
    }

    @GetMapping("packet_capture/{id}")
    public ResponseEntity<PacketCaptureDTO> getPacketCaptureById(@PathVariable Long id) {
        PacketCapture packetCapture = packetCaptureService.findById(id);
        PacketCaptureDTO packetCaptureDTO = packetCapture.convertToDTO();
        return ResponseEntity.ok(packetCaptureDTO);
    }

    @GetMapping("packet_capture/byFileName/{fileName}")
    public ResponseEntity<PacketCaptureDTO> getPacketCaptureByFileName(@PathVariable String fileName) {
        PacketCapture packetCapture = packetCaptureService.findByFileName(fileName);
        PacketCaptureDTO packetCaptureDTO = packetCapture.convertToDTO();
        return ResponseEntity.ok(packetCaptureDTO);
    }

    @PutMapping("/PacketCapture/updatePacketCapture")
    public ResponseEntity<String> updatePacketCapture(@RequestParam("file") MultipartFile file){
        PacketCapture packetCapture = packetCaptureProcessingService.extractPropertiesPacketCapture(file);
        packetCaptureService.updatePacketCapture(packetCapture);
        return ResponseEntity.ok("PacketCapture updated successfully");
    }

    @DeleteMapping("/PacketCapture/{id}")
    public ResponseEntity<String> deletePacketCaptureById(@PathVariable Long id) {
        packetCaptureService.deleteById(id);
        return ResponseEntity.ok("PacketCapture deleted successfully");
    }
}