package NguyenDat.EpicNPC.Entities;

public enum TradeStatus {
    CREATED,        // Buyer vừa tạo
    WAITING_SELLER, // Chờ seller đồng ý
    WAITING_MM,     // Chờ middleman đồng ý
    SELLER_TRANSFERRED_TO_MM, // Seller đã gửi hàng cho MM
    BUYER_PAID_SELLER,        // Buyer đã thanh toán
    MM_TRANSFERRED_TO_BUYER,  // MM đã giao hàng cho buyer
    COMPLETED,       // Giao dịch hoàn tất
    CANCELED         // Giao dịch bị hủy
}
