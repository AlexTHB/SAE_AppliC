package gharach_aw.frame_analysis.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
import gharach_aw.frame_analysis.service.PacketCaptureProcessingService;
import gharach_aw.frame_analysis.service.PacketCaptureService;
import gharach_aw.frame_analysis.service.PacketProcessingService;
@RestController
public class PacketCaptureController {

    @Autowired
    private PacketCaptureProcessingService packetCaptureProcessingService;

    @Autowired
    private PacketCaptureService packetCaptureService;

    @Autowired
    private PacketProcessingService packetProcessingService;

    @PostMapping("packetCapture/upload")
    @ResponseBody
    public ResponseEntity<PacketCapture> savePacketCapture(@RequestParam("file") MultipartFile file) {
        PacketCapture packetCapture = packetCaptureProcessingService.extractPropertiesPacketCapture(file);
        List<Packet> packets = packetProcessingService.packetsExtract(file);
        packetCaptureService.save(packetCapture, packets);

        // Build the URI for the newly created resource
        URI resourceUri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(packetCapture.getId())
            .toUri();

        // Return the created packetCapture with 201 Created status and Location header
        return ResponseEntity.status(HttpStatus.CREATED).body(packetCapture);
    }
  
    @GetMapping("/packetCaptures")
    public ResponseEntity<List<PacketCapture>> getAllPacketCaptures() {
        List<PacketCapture> packetCaptures = packetCaptureService.findAllPacketCaptures();

        if (packetCaptures.isEmpty()) {
            return ResponseEntity.noContent()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        } else {
            return ResponseEntity.ok(packetCaptures);
        }
    }

    @GetMapping("packetCapture/{id}")
    public ResponseEntity<Optional<PacketCapture>> getPacketCaptureById(@PathVariable Long id) {
        Optional<PacketCapture> packetCapture = packetCaptureService.findById(id);
        return ResponseEntity.ok(packetCapture);
    }

    @DeleteMapping("/packetCapture/{id}")
    public ResponseEntity<String> deletePacketCaptureById(@PathVariable Long id) {
        packetCaptureService.deleteById(id);
        return ResponseEntity.ok("PacketCapture deleted successfully");
    }
}