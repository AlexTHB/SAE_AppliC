package gharach_aw.frame_analysis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import gharach_aw.frame_analysis.persistence.entity.Packet;
import gharach_aw.frame_analysis.persistence.entity.PacketDTO;
import gharach_aw.frame_analysis.service.PacketService;

@RestController
public class PacketController {

    @Autowired
    private PacketService packetService;

    @GetMapping("/{captureId}/packets")
    public ResponseEntity<List<PacketDTO>> getAllPacketsByCaptureId(@PathVariable Long captureId) {
        List<Packet> packets = packetService.findAllPacketsByCaptureId(captureId);
        List<PacketDTO> packetsDTO = packetService.convertToPacketDTOList(packets);
        if (packetsDTO.isEmpty()) {
            return ResponseEntity.noContent()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        } else {
            return ResponseEntity.ok(packetsDTO);
        }
    }

    @GetMapping("/packets/byFileName/{fileName}")
    public ResponseEntity<List<PacketDTO>> getAllPacketsByCaptureName(@PathVariable String fileName) {
        List<Packet> packets = packetService.findAllPacketsByCaptureName(fileName);
        List<PacketDTO> packetsDTO = packetService.convertToPacketDTOList(packets);
        if (packetsDTO.isEmpty()) {
            return ResponseEntity.noContent()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        } else {
            return ResponseEntity.ok(packetsDTO);
        }    
    }
}
