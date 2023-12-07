package gharach_aw.frame_analysis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import gharach_aw.frame_analysis.service.PacketCaptureService;
import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketCapture;
import gharach_aw.frame_analysis.persistence.entity.PacketCaptureDTO;
import gharach_aw.frame_analysis.persistence.entity.PacketDTO;

@RestController
public class PacketCaptureController {

    @Autowired
    private PacketCaptureService packetCaptureService;

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
        PacketCaptureDTO packetCaptureDTO = PacketCaptureDTO.convertToDTO(packetCapture);
        return ResponseEntity.ok(packetCaptureDTO);
    }

    @GetMapping("packet_capture/byFileName/{fileName}")
    public ResponseEntity<PacketCaptureDTO> getPacketCaptureByFileName(@PathVariable String fileName) {
        PacketCapture packetCapture = packetCaptureService.findByFileName(fileName);
        PacketCaptureDTO packetCaptureDTO = PacketCaptureDTO.convertToDTO(packetCapture);
        return ResponseEntity.ok(packetCaptureDTO);
    }

    @GetMapping("/{captureId}/packets")
    public ResponseEntity<List<PacketDTO>> getAllPacketsByCaptureId(@PathVariable Long captureId) {
        List<Packet> packets = packetCaptureService.findAllPacketsByCaptureId(captureId);
        List<PacketDTO> packetsDTO = packetCaptureService.convertToPacketDTOList(packets);
        if (packetsDTO.isEmpty()) {
            return ResponseEntity.noContent()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        } else {
            return ResponseEntity.ok(packetsDTO);
        }
    }

    @GetMapping("/packets/byFileName/{fileName}")
    public ResponseEntity<List<PacketDTO>> getAllPacketsByCaptureName(@PathVariable String fileName) {
        List<Packet> packets = packetCaptureService.findAllPacketsByCaptureName(fileName);
        List<PacketDTO> packetsDTO = packetCaptureService.convertToPacketDTOList(packets);
        if (packetsDTO.isEmpty()) {
            return ResponseEntity.noContent()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        } else {
            return ResponseEntity.ok(packetsDTO);
        }    
    }
}