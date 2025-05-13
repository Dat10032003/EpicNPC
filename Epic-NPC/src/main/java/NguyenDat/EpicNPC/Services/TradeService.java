package NguyenDat.EpicNPC.Services;

import NguyenDat.EpicNPC.Entities.*;
import NguyenDat.EpicNPC.Repositories.TradeLogRepository;
import NguyenDat.EpicNPC.Repositories.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final TradeRepository tradeRepo;
    private final TradeLogRepository tradeLogRepo;

    public Trade createTrade(User buyer, Trade trade) {
        trade.setBuyer(buyer);
        return tradeRepo.save(trade);
    }

    public Trade acceptTrade(User actor, Long tradeId) {
        Trade trade = tradeRepo.findById(tradeId).orElseThrow();

        switch (trade.getStatus()) {
            case CREATED -> {
                trade.setStatus(TradeStatus.WAITING_MM);
                createLog(trade, actor, "Seller accepted trade");
            }
            case WAITING_MM -> {
                trade.setStatus(TradeStatus.SELLER_TRANSFERRED_TO_MM);
                createLog(trade, actor, "Middleman accepted trade");
            }
            default -> throw new IllegalStateException("Trade is not in an acceptable state.");
        }

        return tradeRepo.save(trade);
    }

    public Trade cancelTrade(User actor, Long tradeId) {
        Trade trade = tradeRepo.findById(tradeId).orElseThrow();
        trade.setStatus(TradeStatus.CANCELED);
        createLog(trade, actor, "Trade canceled");
        return tradeRepo.save(trade);
    }

    public List<TradeLog> getLogs(Long tradeId) {
        return tradeLogRepo.findAll().stream()
                .filter(log -> log.getTrade().getId().equals(tradeId))
                .toList();
    }

    private void createLog(Trade trade, User actor, String action) {
        TradeLog log = TradeLog.builder()
                .trade(trade)
                .actor(actor)
                .action(action)
                .build();
        tradeLogRepo.save(log);
    }
}

