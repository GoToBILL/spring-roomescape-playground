package roomescape.time.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.time.domain.Time;
import roomescape.time.domain.TimeRepository;
import roomescape.time.dto.TimeRequestDto;
import roomescape.time.dto.TimeResponseDto;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TimeController {
    private final TimeRepository timeRepository;

    @PostMapping("/times")
    public ResponseEntity<TimeResponseDto> createTime(@RequestBody TimeRequestDto timeRequestDto) {
        Time times = timeRepository.save(timeRequestDto.toTime());
        return ResponseEntity.created(URI.create("/times/" + times.getId())).body(TimeResponseDto.makeResponse(times));
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponseDto>> getAllTimes() {

        List<Time> reservations = timeRepository.findAll();
        List<TimeResponseDto> responseDtos = reservations.stream()
                .map(TimeResponseDto::makeResponse)
                .toList();
        return ResponseEntity.ok(responseDtos);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<String> deleteTime(@PathVariable Long id) {
        timeRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
