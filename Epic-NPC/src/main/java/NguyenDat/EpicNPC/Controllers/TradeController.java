package NguyenDat.EpicNPC.Controllers;

import NguyenDat.EpicNPC.Entities.*;
import NguyenDat.EpicNPC.Services.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<Trade> create(@AuthenticationPrincipal User buyer, @RequestBody Trade trade) {
        return ResponseEntity.ok(tradeService.createTrade(buyer, trade));
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<Trade> accept(@AuthenticationPrincipal User actor, @PathVariable Long id) {
        return ResponseEntity.ok(tradeService.acceptTrade(actor, id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Trade> cancel(@AuthenticationPrincipal User actor, @PathVariable Long id) {
        return ResponseEntity.ok(tradeService.cancelTrade(actor, id));
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<List<TradeLog>> logs(@PathVariable Long id) {
        return ResponseEntity.ok(tradeService.getLogs(id));
    }

}
